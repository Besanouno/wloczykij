package pl.basistam.wloczykij.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.basistam.wloczykij.database.model.Event;
import pl.basistam.wloczykij.database.model.EventUser;
import pl.basistam.wloczykij.database.model.EventUserStatus;

import java.util.Optional;

public interface EventUsersRepository extends JpaRepository<EventUser, EventUser.Id> {
    @Query("SELECT COUNT(eu) FROM EventUser eu WHERE eu.event = :event AND (eu.status = 'PARTICIPANT' OR eu.status = 'ADMIN')")
    Integer countParticipants(@Param("event") Event event);

    Optional<EventUser> findByIdAndStatus(EventUser.Id eventUserId, EventUserStatus status);
}
