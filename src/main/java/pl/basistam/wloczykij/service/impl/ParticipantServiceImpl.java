package pl.basistam.wloczykij.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.basistam.wloczykij.common.util.AuthenticationHandler;
import pl.basistam.wloczykij.database.model.Event;
import pl.basistam.wloczykij.database.model.EventUser;
import pl.basistam.wloczykij.database.model.EventUserStatus;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.database.repository.EventUsersRepository;
import pl.basistam.wloczykij.service.api.EventService;
import pl.basistam.wloczykij.service.api.ParticipantService;

@Service
@Primary
public class ParticipantServiceImpl implements ParticipantService {

    private final EventService eventService;
    private final EventUsersRepository eventUsersRepository;
    private final AuthenticationHandler authenticationHandler;

    @Autowired
    public ParticipantServiceImpl(
            EventService eventService,
            EventUsersRepository eventUsersRepository,
            AuthenticationHandler authenticationHandler) {
        this.eventService = eventService;
        this.eventUsersRepository = eventUsersRepository;
        this.authenticationHandler = authenticationHandler;
    }


    @Override
    public void leave(String guid) {
        User user = authenticationHandler.getUser();
        Event event = eventService.findByGuid(guid);
        eventUsersRepository.findByIdAndStatus(new EventUser.Id(event.getId(), user.getId()), EventUserStatus.PARTICIPANT)
                .ifPresent(eventUser -> eventUser.setStatus(EventUserStatus.NONE));
    }

}
