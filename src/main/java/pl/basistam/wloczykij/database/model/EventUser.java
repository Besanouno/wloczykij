package pl.basistam.wloczykij.database.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_users")
public class EventUser {

    public EventUser(Long eventId, Long userId, EventUserStatus status) {
        this.id = new EventUser.Id(eventId, userId);
        this.status = status;
    }

    @EmbeddedId
    private Id id;

    @ManyToOne
    @JoinColumn(name = "event_id", insertable = false, updatable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private EventUserStatus status;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id implements Serializable {
        @Column(name = "event_id", nullable=false)
        private Long eventId;

        @Column(name = "user_id", nullable=false)
        private Long userId;
    }
}
