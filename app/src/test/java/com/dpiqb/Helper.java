package com.dpiqb;

import com.dpiqb.client.Client;
import com.dpiqb.db.HibernateUtil;
import com.dpiqb.planet.Planet;
import com.dpiqb.ticket.Ticket;
import org.hibernate.Session;

public class Helper {
  public static Planet getRandomPlanetFromDB(){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      return session
        .createQuery("from Planet where 1=1 order by rand()", Planet.class)
        .setMaxResults(1)
        .getSingleResult();
    }
  }
  public static Client getRandomClientFromDB(){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      return session
        .createQuery("from Client where 1=1 order by rand()", Client.class)
        .setMaxResults(1)
        .getSingleResult();
    }
  }
  public static Ticket getRandomTicketFromDB(){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      return session
        .createQuery("from Ticket where 1=1 order by rand()", Ticket.class)
        .setMaxResults(1)
        .getSingleResult();
    }
  }
}
