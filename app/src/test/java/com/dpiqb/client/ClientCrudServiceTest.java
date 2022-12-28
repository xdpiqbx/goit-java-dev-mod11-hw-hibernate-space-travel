package com.dpiqb.client;

import com.dpiqb.db.DatabaseMigrateServiceForTest;
import com.dpiqb.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.List;

public class ClientCrudServiceTest {
  static HibernateUtil util;
  static Session session;
  static ClientCrudService clientCrudService;

  @BeforeAll
  public static void init(){
    DatabaseMigrateServiceForTest.migrateDatabase();
    util = HibernateUtil.getInstance();
    clientCrudService = new ClientCrudService();
  }
  @BeforeEach
  public void openSession() {
    session = util.getSessionFactory().openSession();
  }
  @Test
  public void createTest(){
    // to create  new client we need just name
    String actualClientName = "Elisabeth Shaw";

    Client actualClient = new Client();
    actualClient.setName(actualClientName);

    clientCrudService.create(actualClient);

    Query<Client> query = session.createQuery("from Client where id = :id", Client.class);
    query.setParameter("id", actualClient.getId());
    Client expectedClient = query.stream().findFirst().orElse(null);
    assert expectedClient != null;
    Assertions.assertEquals(expectedClient.getName(), actualClient.getName());
  }
  @Test
  public void readByIdTest(){
    List<Client> actualClients = session.createQuery(
        "from Client", Client.class)
      .setMaxResults(5)
      .list();

    for (Client actualClient : actualClients) {
      Client expectedClient = clientCrudService.readById(actualClient.getId());
      Assertions.assertEquals(expectedClient.getId(), actualClient.getId());
      Assertions.assertEquals(expectedClient.getName(), actualClient.getName());
    }
  }
  @Test
  public void updateTest(){
    // to update client we need id and new data
    // i will update random client (imagine that it was transmitted by the user)
    Client actualClient = getRandomClientFromDB();
    actualClient.setName("Xenomorph");

    clientCrudService.update(actualClient);

    Query<Client> query = session.createQuery("from Client where id = :id", Client.class);
    query.setParameter("id", actualClient.getId());
    Client expectedClient = query.stream().findFirst().orElse(null);

    assert expectedClient != null;
    Assertions.assertEquals(expectedClient, actualClient);
  }
  @Test
  public void updateIfUserHaveNothingChangeTest(){
    Client actualClient = getRandomClientFromDB();

    clientCrudService.update(actualClient);

    Query<Client> query = session.createQuery("from Client where id = :id", Client.class);
    query.setParameter("id", actualClient.getId());
    Client expectedClient = query.stream().findFirst().orElse(null);

    assert expectedClient != null;
    Assertions.assertEquals(expectedClient, actualClient);
  }

  @Test
  public void deleteByIdTest(){
    Client actualClient = getRandomClientFromDB();
    long id = actualClient.getId();

    clientCrudService.deleteById(id);

    Query<Client> query = session.createQuery("from Client where id = :id", Client.class);
    query.setParameter("id", id);
    Client expectedClient = query.stream().findFirst().orElse(null);

    Assertions.assertNull(expectedClient);
  }

  @Test
  public void readAllClientsTest(){
    List<Client> expectedClients = clientCrudService.readAllClients();
    List<Client> actualClients = session.createQuery("from Client", Client.class).list();
    Assertions.assertIterableEquals(expectedClients, actualClients);
  }

  private static Client getRandomClientFromDB(){
    return session
      .createQuery("from Client where 1=1 order by rand()", Client.class)
      .setMaxResults(1)
      .getSingleResult();
  }

  @AfterEach
  public void closeSession(){
    session.close();
  }
}
