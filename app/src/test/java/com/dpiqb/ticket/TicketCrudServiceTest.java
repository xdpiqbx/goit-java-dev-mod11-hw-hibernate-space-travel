package com.dpiqb.ticket;

import com.dpiqb.db.DatabaseMigrateServiceForTest;
import com.dpiqb.db.HibernateUtil;
import com.dpiqb.planet.Planet;
import com.dpiqb.planet.PlanetCrudService;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import java.util.List;

public class TicketCrudServiceTest {
  static HibernateUtil util;
  static Session session;
  static TicketCrudService ticketCrudService;
  @BeforeAll
  public static void init(){
    DatabaseMigrateServiceForTest.migrateDatabase();
    util = HibernateUtil.getInstance();
    ticketCrudService = new TicketCrudService();
  }
  @Test
  public void readAllTicketsTest(){
    List<Ticket> expectedTickets = ticketCrudService.readAllTickets();
    List<Ticket> actualTickets = session.createQuery("from Ticket", Ticket.class).list();
    int size = expectedTickets.size();

    Assertions.assertEquals(expectedTickets.size(), actualTickets.size());
    for (int i = 0; i < size; i++) {
      Assertions.assertEquals(expectedTickets.get(i).getId(), actualTickets.get(i).getId());
      Assertions.assertEquals(expectedTickets.get(i).getClientId().toString(), actualTickets.get(i).getClientId().toString());
      Assertions.assertEquals(expectedTickets.get(i).getFromPlanetId().toString(), actualTickets.get(i).getFromPlanetId().toString());
      Assertions.assertEquals(expectedTickets.get(i).getToPlanetId().toString(), actualTickets.get(i).getToPlanetId().toString());
    }
  }
  @BeforeEach
  public void openSession() {
    session = util.getSessionFactory().openSession();
  }
  @AfterEach
  public void closeSession(){
    session.close();
  }
}
