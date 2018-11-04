package pl.basistam.wloczykij.service.api;

import org.springframework.data.domain.Page;
import pl.basistam.wloczykij.database.model.Event;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.json.EventDto;
import pl.basistam.wloczykij.json.EventInputDto;

import java.util.List;

public interface EventService {

    Page<EventDto> getPublicEventsPage(int page, int size);

    void remove(String guid);

    String save(EventInputDto json);

    void updateEvent(String guid, EventInputDto json);

    Event findByGuid(String guid);

    EventDto getEvent(String guid);

    List<EventDto> getActiveEvents(String[] statusCode);

    List<EventDto> getArchiveEvents();

    boolean participate(Event event, User user);
}
