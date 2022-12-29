package com.dpiqb.ticket;

import com.dpiqb.Helper;
import com.dpiqb.db.DatabaseMigrateServiceForTest;
import com.dpiqb.db.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
  @BeforeEach
  public void openSession() {
    session = util.getSessionFactory().openSession();
  }
  @Test
  public void createTest(){
    Ticket actualTicket = new Ticket();
    actualTicket.setClientId(Helper.getRandomClientFromDB());
    actualTicket.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    actualTicket.setFromPlanetId(Helper.getRandomPlanetFromDB());
    actualTicket.setToPlanetId(Helper.getRandomPlanetFromDB());

    ticketCrudService.create(actualTicket);

    Ticket expectedTicket = session.find(Ticket.class, actualTicket.getId());
    Assertions.assertEquals(expectedTicket.toString(), actualTicket.toString());
  }
  @Test
  public void readByIdTestTest(){
    List<Ticket> actualTickets = session.createQuery(
        "from Ticket", Ticket.class)
      .setMaxResults(5)
      .list();

    for (Ticket actualTicket : actualTickets) {
      Ticket expectedTicket = ticketCrudService.readById(actualTicket.getId());
      Assertions.assertEquals(expectedTicket.toString(), actualTicket.toString());
    }
  }
  @Test
  public void update(){
    Ticket actualTicket = Helper.getRandomTicketFromDB();
    actualTicket.setClientId(Helper.getRandomClientFromDB());
    actualTicket.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    actualTicket.setFromPlanetId(Helper.getRandomPlanetFromDB());
    actualTicket.setToPlanetId(Helper.getRandomPlanetFromDB());

    ticketCrudService.update(actualTicket);

    Ticket expectedTicket = session.find(Ticket.class, actualTicket.getId());
    Assertions.assertEquals(expectedTicket.toString(), actualTicket.toString());
  }
  @Test
  public void updateIfUserHaveNothingChangeTest(){
    Ticket actualTicket = Helper.getRandomTicketFromDB();

    ticketCrudService.update(actualTicket);

    Ticket expectedTicket = session.find(Ticket.class, actualTicket.getId());
    Assertions.assertEquals(expectedTicket.toString(), actualTicket.toString());
  }
  @Test
  public void deleteById(){
    long id = 2L;

    ticketCrudService.deleteById(id);

    Ticket expectedTicket = session.find(Ticket.class, id);
    Assertions.assertNull(expectedTicket);
  }
  @Test
  public void readAllTicketsTest(){
    List<Ticket> expectedTickets = ticketCrudService.readAllTickets();
    List<Ticket> actualTickets = session.createQuery("from Ticket", Ticket.class).list();
    int size = expectedTickets.size();

    Assertions.assertEquals(expectedTickets.size(), actualTickets.size());
    for (int i = 0; i < size; i++) {
      Assertions.assertEquals(expectedTickets.toString(), actualTickets.toString());
    }
  }
  @AfterEach
  public void closeSession(){
    session.close();
  }
}
