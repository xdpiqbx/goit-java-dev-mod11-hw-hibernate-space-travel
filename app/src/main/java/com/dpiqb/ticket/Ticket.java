package com.dpiqb.ticket;

import com.dpiqb.client.Client;
import com.dpiqb.planet.Planet;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "ticket")
@Entity
@Data
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Setter(AccessLevel.NONE)
  private long id;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @ManyToOne (cascade = CascadeType.REFRESH)
  @JoinColumn(
    name="client_id",
    nullable=false)
  private Client client;
  @ManyToOne (cascade = CascadeType.REFRESH)
  @JoinColumn(
    name="from_planet_id",
    nullable=false)
  private Planet fromPlanet;
  @ManyToOne (cascade = CascadeType.REFRESH)
  @JoinColumn(
    name="to_planet_id",
    nullable=false)
  private Planet toPlanet;
}
