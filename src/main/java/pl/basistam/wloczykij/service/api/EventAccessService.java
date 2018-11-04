package pl.basistam.wloczykij.service.api;

import pl.basistam.wloczykij.common.exceptions.EventAccessException;
import pl.basistam.wloczykij.database.model.Event;
import pl.basistam.wloczykij.database.model.User;

public interface EventAccessService {
    void verifyAccess(Event event, User user) throws EventAccessException;
}
