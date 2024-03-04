package bridge;

import entity.Settings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jetbrains.annotations.NotNull;


public class SettingsBridge {
    String databaseTableName = "default";

    public Settings getSetting(String id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Settings settings = entityManager.find(Settings.class, id);
        if (settings != null) {
            entityManager.close();
            entityManagerFactory.close();
            return settings;
        }
        entityManager.close();
        entityManagerFactory.close();
        return null;
    }

    public void updateSettings(@NotNull Settings settings) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(databaseTableName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Settings toUpdatePublisher = entityManager.find(Settings.class, settings.getSettingName());
        entityManager.getTransaction().begin();
        if (toUpdatePublisher != null) {
            entityManager.merge(settings);
        } else {
            entityManager.persist(settings);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
