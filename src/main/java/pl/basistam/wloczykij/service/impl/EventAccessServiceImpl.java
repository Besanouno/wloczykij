package pl.basistam.wloczykij.service.impl;

import org.springframework.stereotype.Service;
import pl.basistam.wloczykij.common.exceptions.EventAccessException;
import pl.basistam.wloczykij.database.model.Event;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.database.repository.EventUsersRepository;
import pl.basistam.wloczykij.service.api.EventAccessService;

@Service
public class EventAccessServiceImpl implements EventAccessService {

    private final EventUsersRepository eventUsersRepository;

    public EventAccessServiceImpl(
            EventUsersRepository eventUsersRepository) {
        this.eventUsersRepository = eventUsersRepository;
    }

    @Override
    public void verifyAccess(Event event, User user) throws EventAccessException {
//        Optional<EventUser> participant = eventUsersRepository.findByIdAndStatus(new EventUser.Id(event.getId(), user.getId()), EventUserStatusValue.ADMIN);
//        if (!participant.isPresent()) {
//            throw new EventAccessException("User " + user.getLogin() + " does not have access to event" + event.getGuid());
//        }
    }
}
