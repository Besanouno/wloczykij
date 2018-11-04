package pl.basistam.wloczykij.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.basistam.wloczykij.json.EventDto;
import pl.basistam.wloczykij.json.EventInputDto;
import pl.basistam.wloczykij.json.validation.PostValidation;
import pl.basistam.wloczykij.json.validation.PutValidation;
import pl.basistam.wloczykij.service.api.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody @Validated(PostValidation.class) EventInputDto json) {
        return eventService.save(json);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getEvents(@RequestParam final String[] statusCodes) {
        return eventService.getActiveEvents(statusCodes);
    }

    @GetMapping("/archive")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getArchiveEvents() {
        return eventService.getArchiveEvents();
    }

    @GetMapping("/public")
    @ResponseStatus(HttpStatus.OK)
    public Page<EventDto> getPublicEvents(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size) {
        return eventService.getPublicEventsPage(page, size);
    }

    @PutMapping("/{guid}")
    @ResponseStatus(HttpStatus.OK)
    public void update(
            @PathVariable("guid") String guid,
            @RequestBody @Validated(PutValidation.class) EventInputDto dto) {
        eventService.updateEvent(guid, dto);
    }

    @GetMapping("/{guid}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto getEvent(@PathVariable final String guid) {
        return eventService.getEvent(guid);
    }

    @DeleteMapping("/{guid}")
    @ResponseStatus(HttpStatus.OK)
    public void removeEvent(@PathVariable final String guid) {
        eventService.remove(guid);
    }

}
