package pl.basistam.wloczykij.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.basistam.wloczykij.database.model.Event;
import pl.basistam.wloczykij.database.model.User;

import java.util.List;
import java.util.Optional;

public interface EventDao extends PagingAndSortingRepository<Event, Long> {
    Optional<Event> findByGuid(String guid);

    @Query("SELECT e FROM Event e WHERE e.id IN (SELECT eu.event.id FROM EventUser eu WHERE eu.user = :user AND eu.status IN (:statusCodes)) AND e.startDate >= current_timestamp AND e.active = true")
    List<Event> findAllUpcomingEventsByUserAndStatusCode(@Param("user") User user, @Param("statusCodes") String[] statusCodes);

    @Query("SELECT e FROM Event e WHERE e.id IN (SELECT eu.event.id FROM EventUser eu WHERE eu.user = :user AND eu.status IN ('ADMIN', 'PARTICIPANT')) AND e.startDate < current_timestamp AND e.active = true")
    List<Event> findAllArchiveEventsByUser(@Param("user") User user);

    @Query("SELECT e FROM Event e WHERE e.id NOT IN (SELECT eu.event.id FROM EventUser eu WHERE eu.user = :user AND eu.status IN ('ADMIN', 'PARTICIPANT', 'INVITED')) AND e.startDate >= current_timestamp AND e.active = true AND e.publicAccess=true")
    Page<Event> getPublicEventsByNotUser(Pageable pageable, @Param("user") User user);

}
