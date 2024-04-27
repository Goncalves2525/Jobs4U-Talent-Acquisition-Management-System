package infrastructure.persistance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtility {

    private static EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }

    static public void dropAllDataBaseObjects () {
        dropAllTables();
        dropAllSequences();
        // add more, if needed.
    }

    static public void clearAllTables() {
        List<String> tableNames = listTables();
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (String tableName : tableNames) {
            String sql = "DELETE FROM " + tableName;
            em.createNativeQuery(sql).executeUpdate();
        }
        tx.commit();
        em.close();
    }

    private static void dropAllTables() {
        List<String> names = listTables();
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (String name : names) {
            String sql = "DROP TABLE IF EXISTS " + name + " CASCADE;";
            em.createNativeQuery(sql).executeUpdate();
        }
        tx.commit();
        em.close();
    }

    private static List<String> listTables() {
        EntityManager entityManager = getEntityManager();
        List<Object[]> objectsArray = entityManager.createNativeQuery("SHOW TABLES").getResultList();
        List<String> names = new ArrayList<>();
        for (Object[] obj : objectsArray) {
            names.add((String) obj[0]);
        }
        return names;
    }

    private static void dropAllSequences() {
        List<String> names = listSequences();
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (String name : names) {
            String sql = "DROP SEQUENCE IF EXISTS " + name + ";";
            em.createNativeQuery(sql).executeUpdate();
        }
        tx.commit();
        em.close();
    }

    private static List<String> listSequences() {
        EntityManager entityManager = getEntityManager();
        List<Object[]> objectsArray = entityManager.createNativeQuery("SELECT * FROM INFORMATION_SCHEMA.SEQUENCES").getResultList();
        List<String> names = new ArrayList<>();
        for (Object[] obj : objectsArray) {
            names.add((String) obj[2]);
        }
        return names;
    }

}
