package com.dpiqb.client;

import com.dpiqb.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientCrudService {
//  Create
  public void create(Client client){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      Transaction transaction = session.beginTransaction();
      session.persist(client);
      transaction.commit();
    }
  }
//  Read
  public Client readById (long id){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      return session.get(Client.class, id);
    }
  }
//  Update
  public void update(Client client){
    // 0. get client object with updated data
    // 1. Find by id
    Client actualClient = this.readById(client.getId());
    // 2. Compare
    //    if nothing has changed, so nothing to persist
    //    else -> session.persist(session.merge(client))
    if(actualClient.equals(client)){
      return;
    }
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      Transaction transaction = session.beginTransaction();
      session.persist(session.merge(client));
      transaction.commit();
    }
  }
//  Delete
  public void deleteById(long id){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      Transaction transaction = session.beginTransaction();
      session.remove(session.get(Client.class, id));
      transaction.commit();
    }
  }
  public List<Client> readAllClients (){
    try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
      return session.createQuery("from Client", Client.class).list();
    }
  }
}
