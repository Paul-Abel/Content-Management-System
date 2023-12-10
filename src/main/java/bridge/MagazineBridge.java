package bridge;

import entity.Magazine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

// Method for datatable Access for the Magazine
public class MagazineBridge {

    String databaseTableName = "default";

    // Create datatable row
    public void createMagazine(Magazine magazine) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(magazine);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    // Delete datatable row
    public void deleteMagazine(Long id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Magazine todeleteMagazine = entityManager.find(Magazine.class, id);
        if (todeleteMagazine != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(todeleteMagazine);
            entityManager.getTransaction().commit();
        }
        entityManager.close();
        entityManagerFactory.close();
    }

    // Update datatable row
    public void updateMagazine(Magazine magazine) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Magazine toUpdateMagazine = entityManager.find(Magazine.class, magazine.getId());
        if (toUpdateMagazine != null) {
            entityManager.getTransaction().begin();
            entityManager.merge(magazine);
            entityManager.getTransaction().commit();
        }
        entityManager.close();
        entityManagerFactory.close();
    }

    // Get datatable row
    public Magazine getMagazine(Long id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Magazine magazine = entityManager.find(Magazine.class, id);
        if (magazine != null) {
            entityManager.close();
            entityManagerFactory.close();
            return magazine;
        }
        entityManager.close();
        entityManagerFactory.close();
        return null;
    }

    // Get all datatable rows
    public List<Magazine> getAllMagazines() {
        List<Magazine> allMagazine;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        allMagazine = entityManager.createQuery("SELECT m FROM Magazine m").getResultList();
        entityManager.close();
        entityManagerFactory.close();
        return allMagazine;
    }

    //Delete multipleMagazines
    public void deleteMultipleMagazines(List<Magazine> magazineList) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (Magazine magazine : magazineList) {
            Magazine todeleteMagazine = entityManager.find(Magazine.class, magazine.getId());
            if (todeleteMagazine != null) {

                entityManager.remove(todeleteMagazine);

            }
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
