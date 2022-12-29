package com.dpiqb.planet;

import com.dpiqb.Helper;
import com.dpiqb.db.DatabaseMigrateServiceForTest;
import com.dpiqb.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.List;

public class PlanetCrudServiceTest {
  static HibernateUtil util;
  static Session session;
  static PlanetCrudService planetCrudService;
  @BeforeAll
  public static void init(){
    DatabaseMigrateServiceForTest.migrateDatabase();
    util = HibernateUtil.getInstance();
    planetCrudService = new PlanetCrudService();
  }
  @BeforeEach
  public void openSession() {
    session = util.getSessionFactory().openSession();
  }
  @Test
  public void createTest(){
    String actualPlanetName = "Pandora";

    Planet actualPlanet = new Planet();
    actualPlanet.setName(actualPlanetName);

    planetCrudService.create(actualPlanet);

    Planet expectedPlanet = session.get(Planet.class, actualPlanet.getId());
    Assertions.assertEquals(expectedPlanet.toString(), actualPlanet.toString());
  }
  @Test
  public void readByIdTest(){
    List<Planet> actualPlanets = session.createQuery(
        "from Planet", Planet.class)
      .setMaxResults(5)
      .list();

    for (Planet actualPlanet : actualPlanets) {
      Planet expectedPlanet = planetCrudService.readById(actualPlanet.getId());
      Assertions.assertEquals(expectedPlanet.toString(), actualPlanet.toString());
    }
  }
  @Test
  public void updateTest(){
    Planet actualPlanet = Helper.getRandomPlanetFromDB();
    String name = "Prometheus";

    actualPlanet.setName(name);

    planetCrudService.update(actualPlanet);

    Query<Planet> query = session.createQuery("from Planet where id = :id", Planet.class);
    query.setParameter("id", actualPlanet.getId());
    Planet expectedPlanet = query.stream().findFirst().orElse(null);

    assert expectedPlanet != null;
    Assertions.assertEquals(expectedPlanet.toString(), actualPlanet.toString());
  }
  @Test
  public void updateIfUserHaveNothingChangeTest(){
    Planet actualPlanet = Helper.getRandomPlanetFromDB();

    planetCrudService.update(actualPlanet);

    Query<Planet> query = session.createQuery("from Planet where id = :id", Planet.class);
    query.setParameter("id", actualPlanet.getId());
    Planet expectedPlanet = query.stream().findFirst().orElse(null);

    assert expectedPlanet != null;
    Assertions.assertEquals(expectedPlanet.toString(), actualPlanet.toString());
  }
  @Test
  public void deleteByIdTest(){
    Planet actualPlanet = Helper.getRandomPlanetFromDB();
    String id = actualPlanet.getId();

    planetCrudService.deleteById(id);

    Query<Planet> query = session.createQuery("from Planet where id = :id", Planet.class);
    query.setParameter("id", id);
    Planet expectedPlanet = query.stream().findFirst().orElse(null);

    Assertions.assertNull(expectedPlanet);
  }
  @Test
  public void readAllPlanetsTest(){
    List<Planet> expectedPlanets = planetCrudService.readAllPlanets();
    List<Planet> actualPlanets = session.createQuery("from Planet", Planet.class).list();
    int size = actualPlanets.size();
    for (int i = 0; i < size; i++) {
      Assertions.assertEquals(expectedPlanets.get(i).toString(), actualPlanets.get(i).toString());
    }
  }
  @AfterEach
  public void closeSession(){
    session.close();
  }
}
