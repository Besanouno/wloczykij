package pl.basistam.wloczykij.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.annotations.PrivateOwned;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name = "participants_limit")
    private Integer participantsLimit;

    @Column(name = "place_of_meeting")
    private String placeOfMeeting;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Builder.Default
    private String guid = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @Builder.Default
    @Column(name = "public_access")
    private boolean publicAccess = true;

    @OneToMany(mappedBy = "event")
    public List<EventUser> eventUsers;

    @Builder.Default
    private boolean active = true;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @PrivateOwned
    @OneToMany(mappedBy = "event")
    private List<RouteNode> route;

    public void setRouteByTrailIds(List<Integer> trailIds) {
        if (trailIds != null)
            this.route = trailIds.stream()
                    .map(trailId -> new RouteNode(this, trailId))
                    .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return name;
    }
}
