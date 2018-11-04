package pl.basistam.wloczykij.service.api;

import org.springframework.data.domain.Page;
import pl.basistam.wloczykij.json.MessageDto;

public interface MessageService {
    Page<MessageDto> getMessages(String eventGuid, int page, int size);
    void postMessage(String guid, String content);
}
