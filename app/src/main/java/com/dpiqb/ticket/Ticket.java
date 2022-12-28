package com.dpiqb.ticket;

import com.dpiqb.client.Client;
import com.dpiqb.planet.Planet;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "ticket")
@Entity
@Data
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn(
    name="client_id",
    nullable=false)
  private Client clientId;
  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn(
    name="from_planet_id",
    nullable=false)
  private Planet fromPlanetId;
  @ManyToOne (cascade = CascadeType.ALL)
  @JoinColumn(
    name="to_planet_id",
    nullable=false)
  private Planet toPlanetId;
}
