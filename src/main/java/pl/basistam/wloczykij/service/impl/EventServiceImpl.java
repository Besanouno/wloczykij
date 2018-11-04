package pl.basistam.wloczykij.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.basistam.wloczykij.common.exceptions.ResourceNotFoundException;
import pl.basistam.wloczykij.common.util.AuthenticationHandler;
import pl.basistam.wloczykij.database.model.*;
import pl.basistam.wloczykij.database.repository.EventDao;
import pl.basistam.wloczykij.database.repository.EventUsersRepository;
import pl.basistam.wloczykij.json.EventDto;
import pl.basistam.wloczykij.json.EventInputDto;
import pl.basistam.wloczykij.json.EventUserDto;
import pl.basistam.wloczykij.mapper.EventMapper;
import pl.basistam.wloczykij.service.api.EventAccessService;
import pl.basistam.wloczykij.service.api.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;
    private final EventMapper eventMapper;
    private final EventUsersRepository eventUsersRepository;
    private final EventAccessService eventAccessService;
    private final AuthenticationHandler authenticationHandler;

    @Autowired
    public EventServiceImpl(
            EventDao eventDao,
            EventMapper eventMapper,
            EventUsersRepository eventUsersRepository,
            EventAccessService eventAccessService,
            AuthenticationHandler authenticationHandler) {
        this.eventDao = eventDao;
        this.eventMapper = eventMapper;
        this.eventUsersRepository = eventUsersRepository;
        this.eventAccessService = eventAccessService;
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    @Transactional
    public String save(EventInputDto dto) {
        Event event = eventMapper.toEntity(dto);
        User user = authenticationHandler.getUser();
        event.setInitiator(user);
        event.setActive(true);
        event.setRouteByTrailIds(dto.getTrailIds());
        event = eventDao.save(event);
        updateEventUsers(dto, event);
        return event.getGuid();
    }

    @Override
    @Transactional
    public void updateEvent(String guid, EventInputDto dto) {
        Event event = findByGuid(guid);
        eventAccessService.verifyAccess(event, authenticationHandler.getUser());

        if (!StringUtils.isEmpty(dto.getName()))
            event.setName(dto.getName());

        if (!StringUtils.isEmpty(dto.getDescription()))
            event.setDescription(dto.getDescription());

        if (dto.getStartDate() != null)
            event.setStartDate(dto.getStartDate());

        if (dto.getEndDate() != null)
            event.setEndDate(dto.getEndDate());

        if (!StringUtils.isEmpty(dto.getPlaceOfMeeting()))
            event.setPlaceOfMeeting(dto.getPlaceOfMeeting());

        if (dto.getParticipantsLimit() != null)
            event.setParticipantsLimit(dto.getParticipantsLimit());

        if (dto.getPublicAccess() != null)
            event.setPublicAccess(dto.getPublicAccess());

        event.setRoute(new ArrayList<>());

        if (!CollectionUtils.isEmpty(dto.getTrailIds())) {
            event.setRouteByTrailIds(dto.getTrailIds());
        }

        if (dto.getEventUsers() != null) {
            updateEventUsers(dto, event);
        }
    }

    private void updateEventUsers(EventInputDto dto, Event event) {
        for (EventUserDto e: dto.getEventUsers()) {
            EventUser eventUser = new EventUser(event.getId(), e.getUserId(), e.getStatus());
            eventUsersRepository.save(eventUser);
        }
    }

    @Override
    public Event findByGuid(String guid) {
        return eventDao.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Event with guid " + guid + " not found"));
    }

    @Override
    public EventDto getEvent(String guid) {
        Event event = findByGuid(guid);
        EventDto eventDto = eventMapper.toDetailsJson(event);
        eventDto.setTrailIds(event.getRoute().stream()
                .sorted((routeNode, t1) -> (int) (routeNode.getId() - t1.getId()))
                .map(RouteNode::getTrailId)
                .collect(Collectors.toList()));
        return eventDto;
    }

    @Override
    public List<EventDto> getActiveEvents(String[] statusCodes) {
        User user = authenticationHandler.getUser();
        return eventDao.findAllUpcomingEventsByUserAndStatusCode(user, statusCodes)
                .stream()
                .map(eventMapper::toDetailsJson)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getArchiveEvents() {
        User user = authenticationHandler.getUser();
        return eventDao.findAllArchiveEventsByUser(user)
                .stream()
                .map(eventMapper::toDetailsJson)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EventDto> getPublicEventsPage(int page, int size) {
        User user = authenticationHandler.getUser();
        return eventDao
                .getPublicEventsByNotUser(new PageRequest(page, size), user)
                .map(eventMapper::toDetailsJson);
    }

    @Override
    public void remove(String guid) {
        Event event = findByGuid(guid);
        event.setActive(false);
    }

    @Override
    public boolean participate(Event event, User user) {
        return event.getEventUsers().stream().anyMatch(
                eu -> eu.getUser().getId().equals(user.getId())
                        && (EventUserStatus.PARTICIPANT.equals(eu.getStatus()) || EventUserStatus.PARTICIPANT.equals(eu.getStatus()))
                );
    }
}
