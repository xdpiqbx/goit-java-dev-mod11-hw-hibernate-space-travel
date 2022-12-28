package com.dpiqb.ticket;

import com.dpiqb.db.HibernateUtil;
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
  public List<Ticket> readAllTickets (){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      return session.createQuery("from Ticket", Ticket.class).list();
    }
  }
}
