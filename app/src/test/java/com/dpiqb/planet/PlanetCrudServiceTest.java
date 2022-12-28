package com.dpiqb.planet;

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
    Assertions.assertEquals(expectedPlanet, actualPlanet);
  }
  @Test
  public void readByIdTest(){
    List<Planet> actualPlanets = session.createQuery(
        "from Planet", Planet.class)
      .setMaxResults(5)
      .list();

    for (Planet actualPlanet : actualPlanets) {
      Planet expectedPlanet = planetCrudService.readById(actualPlanet.getId());
      Assertions.assertEquals(expectedPlanet, actualPlanet);
    }
  }
  @Test
  public void updateTest(){
    Planet actualPlanet = getRandomPlanetFromDB();
    String name = "Prometheus";

    actualPlanet.setName(name);

    planetCrudService.update(actualPlanet);

    Query<Planet> query = session.createQuery("from Planet where id = :id", Planet.class);
    query.setParameter("id", actualPlanet.getId());
    Planet expectedPlanet = query.stream().findFirst().orElse(null);

    assert expectedPlanet != null;
    Assertions.assertEquals(expectedPlanet, actualPlanet);
  }
  @Test
  public void updateIfUserHaveNothingChangeTest(){
    Planet actualPlanet = getRandomPlanetFromDB();

    planetCrudService.update(actualPlanet);

    Query<Planet> query = session.createQuery("from Planet where id = :id", Planet.class);
    query.setParameter("id", actualPlanet.getId());
    Planet expectedPlanet = query.stream().findFirst().orElse(null);

    assert expectedPlanet != null;
    Assertions.assertEquals(expectedPlanet, actualPlanet);
  }
  @Test
  public void deleteByIdTest(){
    Planet actualPlanet = getRandomPlanetFromDB();
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
    Assertions.assertIterableEquals(expectedPlanets, actualPlanets);
  }
  private static Planet getRandomPlanetFromDB(){
    return session
      .createQuery("from Planet where 1=1 order by rand()", Planet.class)
      .setMaxResults(1)
      .getSingleResult();
  }
  @AfterEach
  public void closeSession(){
    session.close();
  }
}
