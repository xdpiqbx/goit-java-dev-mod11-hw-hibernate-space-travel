package com.dpiqb.db;

import com.dpiqb.client.Client;
import com.dpiqb.planet.Planet;
import com.dpiqb.ticket.Ticket;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
  private static final HibernateUtil INSTANCE;
  static {
    INSTANCE = new HibernateUtil();
  }
  @Getter
  private final SessionFactory sessionFactory;
  private HibernateUtil(){
    sessionFactory = new Configuration()
      .addAnnotatedClass(Client.class)
      .addAnnotatedClass(Planet.class)
      .addAnnotatedClass(Ticket.class)
      .buildSessionFactory();
  }
  public static HibernateUtil getInstance(){
    return INSTANCE;
  }
  public void close(){
    sessionFactory.close();
  }
}
