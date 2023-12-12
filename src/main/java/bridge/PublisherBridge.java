package bridge;

import entity.Publisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
    public void deletePublisher(Publisher publisher) {
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
    public void updatePublisher(Publisher publisher) {
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

    // Get datatable row
    public Publisher getPublisher(Long id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Publisher publisher = entityManager.find(Publisher.class, id);
        if (publisher != null) {
            entityManager.close();
            entityManagerFactory.close();
            return publisher;
        }
        entityManager.close();
        entityManagerFactory.close();
        return null;
    }

    // Get all datatable rows
    public List<Publisher> getAllPublishers() {
        List<Publisher> allPublishers;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        allPublishers = entityManager.createQuery("SELECT p FROM Publisher p").getResultList();
        entityManager.close();
        entityManagerFactory.close();
        return allPublishers;
    }

    //Delete multiplePublisher
    public void deleteMultiplePublishers(List<Publisher> publisherList) {
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
}
