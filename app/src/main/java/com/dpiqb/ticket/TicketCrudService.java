package com.dpiqb.ticket;

import com.dpiqb.db.HibernateUtil;
import com.dpiqb.planet.Planet;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TicketCrudService {
  //  Create
  public void create(Ticket ticket){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      Transaction transaction = session.beginTransaction();
      session.persist(ticket);
      transaction.commit();
    }
  }
  //  Read
  public Ticket readById (long id){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      return session.get(Ticket.class, id);
    }
  }
  //  Update
  public void update(Ticket planet){
    Ticket actualTicket = this.readById(planet.getId());
    if(actualTicket.equals(planet)){
      return;
    }
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      Transaction transaction = session.beginTransaction();
      session.persist(session.merge(planet));
      transaction.commit();
    }
  }
  public void deleteById(long id){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      Transaction transaction = session.beginTransaction();
      session.remove(session.get(Ticket.class, id));
      transaction.commit();
    }
  }
  public List<Ticket> readAllTickets (){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      return session.createQuery("from Ticket", Ticket.class).list();
    }
  }
}
