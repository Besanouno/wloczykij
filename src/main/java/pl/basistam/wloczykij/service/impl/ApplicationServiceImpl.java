package pl.basistam.wloczykij.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.basistam.wloczykij.common.util.AuthenticationHandler;
import pl.basistam.wloczykij.database.model.Event;
import pl.basistam.wloczykij.database.model.EventUser;
import pl.basistam.wloczykij.database.model.EventUserStatus;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.database.repository.EventUsersRepository;
import pl.basistam.wloczykij.service.api.ApplicationService;
import pl.basistam.wloczykij.service.api.EventService;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final EventService eventService;
    private final AuthenticationHandler authenticationHandler;
    private final EventUsersRepository eventUserRepository;

    @Autowired
    public ApplicationServiceImpl(
            EventService eventService,
            AuthenticationHandler authenticationHandler,
            EventUsersRepository eventUserRepository) {
        this.eventService = eventService;
        this.authenticationHandler = authenticationHandler;
        this.eventUserRepository = eventUserRepository;
    }

    @Override
    public void apply(String eventGuid) {
        User user = authenticationHandler.getUser();
        Event event = eventService.findByGuid(eventGuid);

        if (!isUserIncludedInEvent(user, event)) {
            createWaitingEventUser(user, event);
        }
    }

    @Override
    public void resign(String eventGuid) {
        User user = authenticationHandler.getUser();
        Event event = eventService.findByGuid(eventGuid);
        eventUserRepository.findByIdAndStatus(new EventUser.Id(event.getId(), user.getId()), EventUserStatus.WAITING)
                .ifPresent(
                        eventUser -> {
                            eventUser.setStatus(EventUserStatus.NONE);
                            eventUserRepository.save(eventUser);
                        }
                );
    }

    private boolean isUserIncludedInEvent(User user, Event event) {
        List<EventUser> eventUsers = event.getEventUsers();
        return eventUsers.stream().anyMatch(
                eu -> eu.getUser().getId().equals(user.getId()) && isStatusParticipable(eu.getStatus())
        );
    }

    private boolean isStatusParticipable(EventUserStatus status) {
        return !EventUserStatus.REJECTED.equals(status)
                && !(EventUserStatus.NONE.equals(status));
    }

    private void createWaitingEventUser(User user, Event event) {
        EventUser eventUser = new EventUser(event.getId(), user.getId(), EventUserStatus.WAITING);
        eventUserRepository.save(eventUser);
    }

}
