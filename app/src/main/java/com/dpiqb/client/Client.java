package com.dpiqb.client;

import com.dpiqb.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Table(name = "client")
@Entity
@Data
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  @Column(name = "name")
  private String name;
  @OneToMany (mappedBy = "clientId", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Ticket> tickets;
}
