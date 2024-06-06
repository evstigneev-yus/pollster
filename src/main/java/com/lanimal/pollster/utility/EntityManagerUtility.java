package com.lanimal.pollster.utility;

import com.lanimal.pollster.Configuration;
import com.lanimal.pollster.di.wrappers.PersistenceWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Singleton
public class EntityManagerUtility {

    @Inject
    Configuration configuration;

    @Inject
    PersistenceWrapper persistenceWrapper;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private EntityManagerFactory factory;

    @Inject
    public EntityManagerUtility() {
    }

    public EntityManager createEntityManager() {
        return createEntityManager("com.lanimal.pollster", false, getEntityManagerFactoryPropertiesSupplier());
    }

    Supplier<Map<String, String>> getEntityManagerFactoryPropertiesSupplier() {
        return () -> {
            HashMap<String, String> properties = new HashMap<>();
            String dbUrl = configuration.getPollsterDbUrl();
            properties.put("hibernate.connection.url", dbUrl);
            String userName = configuration.getDbUserName();
            properties.put("hibernate.connection.username", userName);
            String password = configuration.getDbPassword();
            properties.put("hibernate.connection.password", password);
            return properties;
        };
    }

    EntityManager createEntityManager(String persistenceUnitName, boolean rollbackOnly,
                                      Supplier<Map<String, String>> entityManagerFactoryPropertiesSupplier) {
        try {
            return createEntityManagerAndOpenTransaction(persistenceUnitName, rollbackOnly,
                    entityManagerFactoryPropertiesSupplier);
        } catch (PersistenceException e) {
            factory = null;
            throw e;
        }
    }

    EntityManager createEntityManagerAndOpenTransaction(String persistenceUnitName, boolean rollbackOnly,
                                                        Supplier<Map<String, String>> entityManagerFactoryPropertiesSupplier) {
        EntityManagerFactory entityManagerFactory = getEntityManagerFactory(persistenceUnitName,
                entityManagerFactoryPropertiesSupplier);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        openTransaction(entityManager, rollbackOnly);
        return entityManager;
    }

    void openTransaction(EntityManager entityManager, boolean rollbackOnly) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (rollbackOnly) {
            transaction.setRollbackOnly();
        }
    }

    EntityManagerFactory getEntityManagerFactory(String persistenceUnitName,
                                                 Supplier<Map<String, String>> getEntityManagerPropertiesFunction) {
        if (factory != null) {
            return factory;
        }
        factory = createEntityManagerFactory(persistenceUnitName,
                getEntityManagerPropertiesFunction.get());
        return factory;
    }

    EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map<String, String> properties) {
        return persistenceWrapper.createEntityManagerFactory(persistenceUnitName, properties);
    }
}
