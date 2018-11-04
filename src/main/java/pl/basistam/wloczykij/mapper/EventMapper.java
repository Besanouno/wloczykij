package pl.basistam.wloczykij.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.basistam.wloczykij.database.model.Event;
import pl.basistam.wloczykij.json.EventDto;
import pl.basistam.wloczykij.json.EventInputDto;

import java.util.UUID;

@Component
public class EventMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Event toEntity(EventInputDto json) {
        Event event = modelMapper.map(json, Event.class);
        event.setGuid(UUID.randomUUID().toString());
        return event;
    }

    public EventDto toDetailsJson(Event entity) { return modelMapper.map(entity, EventDto.class);}
}
