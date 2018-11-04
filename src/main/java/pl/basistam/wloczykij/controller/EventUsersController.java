package pl.basistam.wloczykij.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.basistam.wloczykij.service.api.*;

@RestController
@RequestMapping("/events")
public class EventUsersController {

    private final ParticipantService participantService;
    private final InvitationService invitationService;
    private final ApplicationService applicationService;

    @Autowired
    public EventUsersController(
            ParticipantService participantService,
            InvitationService invitationService,
            ApplicationService applicationService) {
        this.participantService = participantService;
        this.invitationService = invitationService;
        this.applicationService = applicationService;
    }

    @PostMapping("/{guid}/user/{id}/invite")
    @ResponseStatus(HttpStatus.OK)
    public void invite(
            @PathVariable("guid") final String eventGuid,
            @PathVariable("id") final Long login) {
        invitationService.invite(login, eventGuid);
    }

    @PostMapping("/{guid}/invitations/acceptance")
    @ResponseStatus(HttpStatus.OK)
    public void acceptInvitation(@PathVariable("guid") final String eventGuid) {
        invitationService.acceptInvitation(eventGuid);
    }

    @PostMapping("/{guid}/invitations/rejection")
    @ResponseStatus(HttpStatus.OK)
    public void rejectInvitation(@PathVariable("guid") final String eventGuid) {
        invitationService.rejectInvitation(eventGuid);
    }

    @PostMapping("{guid}/applications")
    @ResponseStatus(HttpStatus.OK)
    public void apply(@PathVariable("guid") final String eventGuid) {
        applicationService.apply(eventGuid);
    }

    @DeleteMapping("{guid}/applications")
    @ResponseStatus(HttpStatus.OK)
    public void resign(@PathVariable("guid") final String eventGuid) {
        applicationService.resign(eventGuid);
    }

    @DeleteMapping("{guid}/users/self")
    @ResponseStatus(HttpStatus.OK)
    public void leave(
            @PathVariable("guid") final String guid
    ) {
        participantService.leave(guid);
    }
}
