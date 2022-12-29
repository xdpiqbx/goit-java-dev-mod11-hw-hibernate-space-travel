package com.dpiqb.planet;

import com.dpiqb.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Table(name = "planet")
@Entity
@Data
public class Planet {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @OneToMany (mappedBy = "fromPlanet", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Ticket> fromTickets;
    @OneToMany (mappedBy = "toPlanet", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Ticket> toTickets;
}
