package pl.basistam.wloczykij.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.basistam.wloczykij.json.MessageDto;
import pl.basistam.wloczykij.service.api.MessageService;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    private final MessageService messageService;

    @Autowired
    public MessagesController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{guid}")
    @ResponseStatus(HttpStatus.OK)
    public Page<MessageDto> getPage(
            @PathVariable(name = "guid") final String guid,
            @RequestParam(name = "page", defaultValue = "0") final int page,
            @RequestParam(name = "size", defaultValue = "15") final int size) {
        return messageService.getMessages(guid, page, size);
    }

    @PostMapping("/{guid}")
    @ResponseStatus(HttpStatus.OK)
    public void postMessage(
            @PathVariable(name = "guid") final String guid,
            @RequestBody final String content
    ) {
        messageService.postMessage(guid, content);
    }
}
