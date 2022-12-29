package com.dpiqb.client;

import com.dpiqb.db.DatabaseMigrateServiceForTest;
import com.dpiqb.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Random;

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

    Client expectedClient = session.get(Client.class, actualClient.getId());
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
      Assertions.assertEquals(expectedClient.toString(), actualClient.toString());
    }
  }
  @Test
  public void updateTest(){
    Client actualClient = getRandomClientFromDB();
    actualClient.setName("Xenomorph");

    clientCrudService.update(actualClient);

    Client expectedClient = session.find(Client.class, actualClient.getId());
    Assertions.assertEquals(expectedClient, actualClient);
  }
  @Test
  public void updateIfUserHaveNothingChangeTest(){
    Client actualClient = getRandomClientFromDB();

    clientCrudService.update(actualClient);

    Client expectedClient = session.find(Client.class, actualClient.getId());
    Assertions.assertEquals(expectedClient, actualClient);
  }

  @Test
  public void deleteByIdTest(){
    List<Client> allIds = session.createQuery("select c.id from Client c", Client.class).list();
    Random random = new Random();
    int randInt = random.nextInt(1, allIds.size()-1);

    Client actualClient = session.get(Client.class, allIds.get(randInt));
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
    int size = actualClients.size();
    for (int i = 0; i < size; i++) {
      Assertions.assertEquals(expectedClients.get(i).toString(), actualClients.get(i).toString());
    }
  }
  public static Client getRandomClientFromDB(){
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
