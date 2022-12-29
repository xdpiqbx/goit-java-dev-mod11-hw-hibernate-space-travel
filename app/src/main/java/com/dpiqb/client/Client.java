package com.dpiqb.client;

import com.dpiqb.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Table(name = "client")
@Entity
@Data
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  @Column(name = "id")
  private long id;
  @Column(name = "name")
  private String name;
  @OneToMany (mappedBy = "clientId", cascade = CascadeType.REMOVE)
  @ToString.Exclude
  private List<Ticket> tickets;
}
