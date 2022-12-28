package com.dpiqb.planet;

import com.dpiqb.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Random;

public class PlanetCrudService {
  //  Create
  public void create(Planet planet){
    planet.setId(idGenerator(planet.getName()));
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      Transaction transaction = session.beginTransaction();
      session.persist(planet);
      transaction.commit();
    }
  }
  //  Read
  public Planet readById (String id){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      return session.get(Planet.class, id);
    }
  }
  //  Update
  public void update(Planet planet){
    Planet actualPlanet = this.readById(planet.getId());
    if(actualPlanet.equals(planet)){
      return;
    }
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      Transaction transaction = session.beginTransaction();
      session.persist(session.merge(planet));
      transaction.commit();
    }
  }
  //  Delete
  public void deleteById(String id){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      Transaction transaction = session.beginTransaction();
      session.remove(session.get(Planet.class, id));
      transaction.commit();
    }
  }
  public List<Planet> readAllPlanets (){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      return session.createQuery("from Planet", Planet.class).list();
    }
  }
  private static String idGenerator(String name){
    return name.toUpperCase()
      + new Random()
      .ints(1, 10000)
      .findAny()
      .getAsInt();
  }
}
