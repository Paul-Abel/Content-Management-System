package bridge;

import entity.Publisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// Method for datatable Access for the Publisher
public class PublisherBridge {
    String databaseTableName = "default";

    // Create datatable row
    public void createPublisher(Publisher publisher) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(publisher);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    // Delete datatable row
    public void deletePublisher(@NotNull Publisher publisher) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Publisher todeletePublisher = entityManager.find(Publisher.class, publisher.getId());
        if (todeletePublisher != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(todeletePublisher);
            entityManager.getTransaction().commit();
        }
        entityManager.close();
        entityManagerFactory.close();
    }

    // Update datatable row
    public void updatePublisher(@NotNull Publisher publisher) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Publisher toUpdatePublisher = entityManager.find(Publisher.class, publisher.getId());
        if (toUpdatePublisher != null) {
            entityManager.getTransaction().begin();
            entityManager.merge(publisher);
            entityManager.getTransaction().commit();
        }
        entityManager.close();
        entityManagerFactory.close();
    }

    // Get all datatable rows
    public List<Publisher> getAllPublishers() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Publisher> allPublishers = entityManager.createQuery("SELECT p FROM Publisher p").getResultList();
        entityManager.close();
        entityManagerFactory.close();
        return allPublishers;
    }

    public List<Object[]> getMagazinesWithPublisherNames(@NotNull Publisher publisher) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Object[]> magazinesAndPublishers = entityManager.createQuery("SELECT m.title, p.name FROM Magazine m JOIN m.publisher p WHERE p.id = :publisherId", Object[].class).setParameter("publisherId", publisher.getId()).getResultList();

        entityManager.close();
        entityManagerFactory.close();
        return magazinesAndPublishers;
    }


    //Delete multiplePublisher
    public void deleteMultiplePublishers(@NotNull List<Publisher> publisherList) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (Publisher publisher : publisherList) {
            Publisher todeletepublisher = entityManager.find(Publisher.class, publisher.getId());
            if (todeletepublisher != null) {

                entityManager.remove(todeletepublisher);
            }
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    //Check if name is already taken
    public boolean isPublisherNameTaken(@NotNull String publisherName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Query to check if a publisher with the given name exists
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(p) FROM Publisher p WHERE p.name = :publisherName", Long.class);
        query.setParameter("publisherName", publisherName);
        long count = query.getSingleResult();

        entityManager.close();
        entityManagerFactory.close();

        return count > 0; // True if a publisher with this name exists, false otherwise
    }
}
