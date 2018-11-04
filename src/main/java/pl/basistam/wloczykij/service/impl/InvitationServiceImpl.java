package pl.basistam.wloczykij.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.basistam.wloczykij.common.exceptions.EventAccessException;
import pl.basistam.wloczykij.common.util.AuthenticationHandler;
import pl.basistam.wloczykij.database.model.Event;
import pl.basistam.wloczykij.database.model.EventUser;
import pl.basistam.wloczykij.database.model.EventUserStatus;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.database.repository.EventUsersRepository;
import pl.basistam.wloczykij.service.api.EventService;
import pl.basistam.wloczykij.service.api.InvitationService;

import java.util.Arrays;
import java.util.Optional;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final EventService eventService;
    private final EventUsersRepository eventUsersRepository;
    private final AuthenticationHandler authenticationHandler;

    @Autowired
    public InvitationServiceImpl(
            EventService eventService,
            EventUsersRepository eventUsersRepository,
            AuthenticationHandler authenticationHandler) {
        this.eventService = eventService;
        this.eventUsersRepository = eventUsersRepository;
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    public void acceptInvitation(String eventGuid) {
        Event event = eventService.findByGuid(eventGuid);
        verifyParticipantsLimit(event);
        changeInvitationStatus(event, EventUserStatus.PARTICIPANT);
    }

    @Override
    public void rejectInvitation(String eventGuid) {
        Event event = eventService.findByGuid(eventGuid);
        changeInvitationStatus(event, EventUserStatus.REJECTED);
    }

    private void changeInvitationStatus(Event event, EventUserStatus status) {
        User user = authenticationHandler.getUser();
        Optional<EventUser> any = event.getEventUsers().stream()
                .filter(
                        eventUser -> eventUser.getUser().getId().equals(user.getId())
                                && EventUserStatus.INVITED.equals(eventUser.getStatus())
                )
                .findAny();
        if (any.isPresent()) {
            EventUser eventUser = any.get();
            eventUser.setStatus(status);
            eventUsersRepository.save(eventUser);
        } else {
            throw new EventAccessException("No invitation found");
        }
    }

    private void verifyParticipantsLimit(Event event) {
        Integer participantsLimit = event.getParticipantsLimit();
        if (participantsLimit != null && eventUsersRepository.countParticipants(event) >= participantsLimit) {
            throw new EventAccessException("Event " + event.getGuid() + " already achieved eventUsers limit");
        }
    }
    @Override
    public void invite(Long userId, String eventGuid) {
        Event event = eventService.findByGuid(eventGuid);
        Optional<EventUser> any = event.getEventUsers().stream()
                .filter(
                        eventUser -> eventUser.getUser().getId().equals(userId))
                .findAny();
        if (any.isPresent()) {
            EventUser eventUser = any.get();
            if (canBeInvited(eventUser)) {
                eventUser.setStatus(EventUserStatus.INVITED);
            }
        } else {
            EventUser eventUser = new EventUser(event.getId(), userId, EventUserStatus.INVITED);
            eventUsersRepository.save(eventUser);
        }
    }

    private boolean canBeInvited(EventUser status) {
        return Arrays.asList(EventUserStatus.PARTICIPANT, EventUserStatus.INVITED, EventUserStatus.ADMIN, EventUserStatus.WAITING).contains(status);
    }


}
