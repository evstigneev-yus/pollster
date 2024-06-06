package com.lanimal.pollster.di.wrappers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class PersistenceWrapper {

    @Inject
    public PersistenceWrapper() {
    }

    public EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map<String, String> properties) {
        return Persistence.createEntityManagerFactory(persistenceUnitName, properties);
    }
}
