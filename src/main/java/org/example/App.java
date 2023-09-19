package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.log4j.Logger;

import static org.example.service.JpaService.selectMembersNoAutoAllowedByCriteriaApi;

/**
 * Hello world!
 */
public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class);
    public static void main(String[] args) {
        LOGGER.info("start application");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("osbbPersistenceUnit");
        EntityManager em = emf.createEntityManager();

        selectMembersNoAutoAllowedByCriteriaApi(em);

        em.close();
        emf.close();
        LOGGER.info("stop application");
    }


}
