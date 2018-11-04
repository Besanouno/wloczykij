package pl.basistam.wloczykij.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.basistam.wloczykij.common.util.AuthenticationHandler;
import pl.basistam.wloczykij.database.model.Event;
import pl.basistam.wloczykij.database.model.Message;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.database.repository.MessageRepository;
import pl.basistam.wloczykij.json.MessageDto;
import pl.basistam.wloczykij.mapper.MessageMapper;
import pl.basistam.wloczykij.service.api.EventService;
import pl.basistam.wloczykij.service.api.MessageService;

import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final AuthenticationHandler authenticationHandler;
    private final EventService eventService;

    @Autowired
    public MessageServiceImpl(
            MessageRepository messageRepository,
            MessageMapper messageMapper,
            AuthenticationHandler authenticationHandler,
            EventService eventService) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.authenticationHandler = authenticationHandler;
        this.eventService = eventService;
    }

    @Override
    public Page<MessageDto> getMessages(String eventGuid, int page, int size) {
        return messageRepository
                .findAllByEventGuid(eventGuid, new PageRequest(page, size, Sort.Direction.DESC, "date"))
                .map(messageMapper::toDto);
    }

    @Override
    public void postMessage(String guid, String content) {
        User user = authenticationHandler.getUser();
        Event event = eventService.findByGuid(guid);
        if (eventService.participate(event, user)) {
            Message message = createMessage(event, user, content);
            messageRepository.save(message);
        }
    }

    private Message createMessage(Event event, User user, String content) {
        return Message.builder()
                .event(event)
                .user(user)
                .content(content)
                .date(new Date(System.currentTimeMillis()))
                .build();
    }
}
