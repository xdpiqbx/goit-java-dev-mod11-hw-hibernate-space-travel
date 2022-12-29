/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.dpiqb;

import com.dpiqb.client.Client;
import com.dpiqb.client.ClientCrudService;
import com.dpiqb.db.DatabaseMigrateService;
import com.dpiqb.planet.Planet;
import com.dpiqb.planet.PlanetCrudService;
import com.dpiqb.ticket.Ticket;
import com.dpiqb.ticket.TicketCrudService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class App {
  public static void main(String[] args) {

    // All CRUD in tests.

    DatabaseMigrateService.migrateDatabase();

    System.out.println("\n ============ All Clients ============ ");

    ClientCrudService clientCrudService = new ClientCrudService();
    List<Client> clients = clientCrudService.readAllClients();
    for (Client client : clients) {
      System.out.println(client);
    }

    System.out.println("\n ============ All Planets ============ ");

    PlanetCrudService planetCrudService = new PlanetCrudService();
    List<Planet> planets = planetCrudService.readAllPlanets();
    for (Planet planet : planets) {
      System.out.println(planet);
    }

    System.out.println("\n ============ All Tickets ============ ");

    TicketCrudService ticketCrudService = new TicketCrudService();
    List<Ticket> tickets = ticketCrudService.readAllTickets();
    for (Ticket ticket : tickets) {
      System.out.println(ticket);
    }
  }
}
