package com.lanimal.pollster.utility;

import com.lanimal.pollster.Configuration;
import com.lanimal.pollster.di.wrappers.PersistenceWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntityManagerUtilityTest {
    @Mock
    Configuration mockConfiguration;

    @Mock
    PersistenceWrapper mockPersistenceWrapper;

    @Spy
    @InjectMocks
    EntityManagerUtility utility;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @SuppressWarnings({"rawtypes", "resource", "unchecked"})
    @Test
    void createEntityManager_callCreateEntityManager() {
        // setup
        Supplier mockSupplier = mock(Supplier.class);
        doReturn(mockSupplier).when(utility)
                .getEntityManagerFactoryPropertiesSupplier();
        doReturn(null).when(utility)
                .createEntityManager(anyString(), anyBoolean(), any());

        // act
        utility.createEntityManager();

        // verify
        verify(utility).createEntityManager("com.lanimal.pollster", false, mockSupplier);
    }

    @SuppressWarnings({"rawtypes", "resource"})
    @Test
    void createEntityManager_checkResult() {
        // setup
        Supplier mockSupplier = mock(Supplier.class);
        doReturn(mockSupplier).when(utility)
                .getEntityManagerFactoryPropertiesSupplier();
        EntityManager mockEntityManager = mock(EntityManager.class);
        doReturn(mockEntityManager).when(utility)
                .createEntityManager(anyString(), anyBoolean(), any());

        // act
        EntityManager result = utility.createEntityManager();

        // verify
        assertThat(result, is(mockEntityManager));
    }

    @Test
    public void getEntityManagerFactoryPropertiesSupplier_connectionUrl_checkResult() {
        // setup
        when(mockConfiguration.getPollsterDbUrl()).thenReturn("jdbc.test.123");

        // act
        Map<String, String> result = utility.getEntityManagerFactoryPropertiesSupplier()
                .get();

        // verify
        assertThat(result.get("hibernate.connection.url"), is("jdbc.test.123"));
    }

    @Test
    public void getEntityManagerFactoryPropertiesSupplier_username_checkResult() {
        // setup
        when(mockConfiguration.getDbUserName()).thenReturn("etst1234");

        // act
        Map<String, String> result = utility.getEntityManagerFactoryPropertiesSupplier()
                .get();

        // verify
        assertThat(result.get("hibernate.connection.username"), is("etst1234"));
    }

    @Test
    public void getEntityManagerFactoryPropertiesSupplier_password_checkResult() {
        // setup
        when(mockConfiguration.getDbPassword()).thenReturn("pwd14");

        // act
        Map<String, String> result = utility.getEntityManagerFactoryPropertiesSupplier()
                .get();

        // verify
        assertThat(result.get("hibernate.connection.password"), is("pwd14"));
    }

    @SuppressWarnings({"rawtypes", "resource", "unchecked"})
    @Test
    public void createEntityManager_withParameters_callCreateEntityManagerAndOpenTransaction() {
        // setup
        Supplier mockSupplier = mock(Supplier.class);
        doReturn(null).when(utility)
                .createEntityManagerAndOpenTransaction(anyString(), anyBoolean(), any());

        // act
        utility.createEntityManager("test123", false, mockSupplier);

        // verify
        verify(utility).createEntityManagerAndOpenTransaction("test123", false, mockSupplier);
    }

    @SuppressWarnings({"rawtypes", "resource", "unchecked"})
    @Test
    public void createEntityManager_withParameters_checkResult() {
        // setup
        EntityManager mockEntityManager = mock(EntityManager.class);
        doReturn(mockEntityManager).when(utility)
                .createEntityManagerAndOpenTransaction(anyString(), anyBoolean(), any());

        // act
        EntityManager result = utility.createEntityManager("", true, mock(Supplier.class));

        // verify
        assertThat(result, is(mockEntityManager));
    }


    @SuppressWarnings({"resource", "unchecked"})
    @Test
    public void createEntityManager_withParameters_createEntityManagerAndOpenTransactionThrowException_resetFactory() {
        // setup
        PersistenceException persistenceException = new PersistenceException();
        doThrow(persistenceException).when(utility)
                .createEntityManagerAndOpenTransaction(anyString(), anyBoolean(), any());

        // act
        try {
            utility.createEntityManager("1", false, mock(Supplier.class));
            // verify
            fail();
        } catch (PersistenceException e) {
            assertThat(utility.getFactory(), is(nullValue()));
        }
    }

    @SuppressWarnings({"resource", "unchecked"})
    @Test
    public void createEntityManager_withParameters_createEntityManagerAndOpenTransactionThrowException_rethrowException() {
        // setup
        PersistenceException persistenceException = new PersistenceException("test123");
        doThrow(persistenceException).when(utility)
                .createEntityManagerAndOpenTransaction(anyString(), anyBoolean(), any());

        // act
        try {
            utility.createEntityManager("1", false, mock(Supplier.class));
            // verify
            fail();
        } catch (PersistenceException e) {
            assertThat(e, is(persistenceException));
        }
    }

    @SuppressWarnings({"resource", "unchecked", "rawtypes"})
    @Test
    public void createEntityManagerAndOpenTransaction_callGetEntityManagerFactory() {
        // setup
        doReturn(mock(EntityManagerFactory.class)).when(utility)
                .getEntityManagerFactory(any(), any());
        doNothing().when(utility).openTransaction(any(), anyBoolean());
        Supplier mockSupplier = mock(Supplier.class);

        // act
        utility.createEntityManagerAndOpenTransaction("123", true, mockSupplier);

        // verify
        verify(utility).getEntityManagerFactory("123", mockSupplier);
    }

    @SuppressWarnings({"resource", "unchecked"})
    @Test
    public void createEntityManagerAndOpenTransaction_callOpenTransaction() {
        // setup
        EntityManagerFactory mockEntityManagerFactory = mock(EntityManagerFactory.class);
        doReturn(mockEntityManagerFactory).when(utility)
                .getEntityManagerFactory(any(), any());
        EntityManager mockEntityManager = mock(EntityManager.class);
        when(mockEntityManagerFactory.createEntityManager()).thenReturn(mockEntityManager);
        doNothing().when(utility).openTransaction(any(), anyBoolean());

        // act
        utility.createEntityManagerAndOpenTransaction("", true, mock(Supplier.class));

        // verify
        verify(utility).openTransaction(mockEntityManager, true);
    }


    @SuppressWarnings({"unchecked"})
    @Test
    public void createEntityManagerAndOpenTransaction_checkResult() {
        // setup
        EntityManagerFactory mockEntityManagerFactory = mock(EntityManagerFactory.class);
        doReturn(mockEntityManagerFactory).when(utility)
                .getEntityManagerFactory(any(), any());
        EntityManager mockEntityManager = mock(EntityManager.class);
        when(mockEntityManagerFactory.createEntityManager()).thenReturn(mockEntityManager);
        doNothing().when(utility).openTransaction(any(), anyBoolean());

        // act
        EntityManager result = utility.createEntityManagerAndOpenTransaction("", true, mock(Supplier.class));

        // verify
        assertThat(result, is(mockEntityManager));
    }

    @Test
    public void openTransaction_callTransactionBegin() {
        // setup
        EntityManager mockEntityManager = mock(EntityManager.class);
        Transaction mockTransaction = mock(Transaction.class);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);

        // act
        utility.openTransaction(mockEntityManager, true);

        // verify
        verify(mockTransaction).begin();
    }

    @Test
    public void openTransaction_callTransactionSetRollbackOnly() {
        // setup
        EntityManager mockEntityManager = mock(EntityManager.class);
        Transaction mockTransaction = mock(Transaction.class);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);

        // act
        utility.openTransaction(mockEntityManager, true);

        // verify
        verify(mockTransaction).setRollbackOnly();
    }

    @Test
    public void openTransaction_rollbackOnlyFalse_doNotCallTransactionSetRollbackOnly() {
        // setup
        EntityManager mockEntityManager = mock(EntityManager.class);
        Transaction mockTransaction = mock(Transaction.class);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);

        // act
        utility.openTransaction(mockEntityManager, false);

        // verify
        verify(mockTransaction, never()).setRollbackOnly();
    }

    @SuppressWarnings({"resource", "unchecked", "rawtypes"})
    @Test
    public void getEntityManagerFactory_callCreateEntityManagerFactory() {
        // setup
        Supplier mockSupplier = mock(Supplier.class);
        Map mockMap = mock(Map.class);
        when(mockSupplier.get()).thenReturn(mockMap);

        // act
        utility.getEntityManagerFactory("1223", mockSupplier);

        // verify
        verify(utility).createEntityManagerFactory("1223", mockMap);
    }

    @SuppressWarnings({"resource", "unchecked"})
    @Test
    public void getEntityManagerFactory_factoryNotNull_doNotCallCreateEntityManagerFactory() {
        // setup
        utility.setFactory(mock(EntityManagerFactory.class));

        // act
        utility.getEntityManagerFactory("1223", mock(Supplier.class));

        // verify
        verify(utility, never()).createEntityManagerFactory(any(), any());
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void getEntityManagerFactory_factoryNotNull_checkResult() {
        // setup
        EntityManagerFactory mockEntityManagerFactory = mock(EntityManagerFactory.class);
        utility.setFactory(mockEntityManagerFactory);

        // act
        EntityManagerFactory result = utility.getEntityManagerFactory("1223", mock(Supplier.class));

        // verify
        assertThat(result, is(mockEntityManagerFactory));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void getEntityManagerFactory_factoryIsNull_checkFactoryIsSet() {
        // setup
        EntityManagerFactory mockEntityManagerFactory = mock(EntityManagerFactory.class);
        utility.setFactory(null);
        doReturn(mockEntityManagerFactory).when(utility)
                .createEntityManagerFactory(any(), any());

        // act
        utility.getEntityManagerFactory("1223", mock(Supplier.class));

        // verify
        assertThat(utility.getFactory(), is(mockEntityManagerFactory));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void getEntityManagerFactory_factoryIsNull_checkResult() {
        // setup
        EntityManagerFactory mockEntityManagerFactory = mock(EntityManagerFactory.class);
        doReturn(mockEntityManagerFactory).when(utility)
                .createEntityManagerFactory(any(), any());

        // act
        EntityManagerFactory result = utility.getEntityManagerFactory("1223", mock(Supplier.class));

        // verify
        assertThat(result, is(mockEntityManagerFactory));
    }

    @SuppressWarnings("resource")
    @Test
    public void createEntityManagerFactory_callPersistenceWrapperCreateEntityManagerFactory() {
        // setup

        // act
        utility.createEntityManagerFactory("test123", Map.of("test1", "test2"));

        // verify
        verify(mockPersistenceWrapper).createEntityManagerFactory("test123", Map.of("test1", "test2"));
    }

    @Test
    public void createEntityManagerFactory_checkResult() {
        // setup
        EntityManagerFactory mockEntityManagerFactory = mock(EntityManagerFactory.class);
        when(mockPersistenceWrapper.createEntityManagerFactory(anyString(), any()))
                .thenReturn(mockEntityManagerFactory);

        // act
        EntityManagerFactory result = utility.createEntityManagerFactory("test123", Map.of("test1", "test2"));

        // verify
        assertThat(result, is(mockEntityManagerFactory));
    }

}
