package bridge;

import entity.Magazine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class MagazineBridge {

    public void createMagazine(Magazine magazine){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(magazine);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public void deleteMagazine(Long id){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Magazine todeleteMagazine = entityManager.find(Magazine.class, id);
        if(todeleteMagazine != null){
            entityManager.getTransaction().begin();
            entityManager.remove(todeleteMagazine);
            entityManager.getTransaction().commit();
        }
        entityManager.close();
        entityManagerFactory.close();
    }


    public void updateMagazine(Magazine magazine){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Magazine toUpdateMagazine = entityManager.find(Magazine.class, magazine.getId());
        if(toUpdateMagazine != null){
            entityManager.getTransaction().begin();
            entityManager.merge(magazine);
            entityManager.getTransaction().commit();
        }
        entityManager.close();
        entityManagerFactory.close();
    }

    public Magazine getMagazine(Long id){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Magazine magazine = entityManager.find(Magazine.class, id);
        if(magazine != null){
            entityManager.close();
            entityManagerFactory.close();
            return magazine;
        }
        entityManager.close();
        entityManagerFactory.close();
        return null;
    }



}
