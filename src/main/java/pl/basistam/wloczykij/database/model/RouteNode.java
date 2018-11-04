package pl.basistam.wloczykij.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "route_nodes")
public class RouteNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public RouteNode(Event event, int trailId) {
        this.event = event;
        this.trailId = trailId;
    }

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "trail_id")
    private int trailId;
}
