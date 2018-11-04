package pl.basistam.wloczykij.service.api;


public interface InvitationService {
    void acceptInvitation(String eventGuid);
    void rejectInvitation(String eventGuid);
    void invite(Long userId, String eventGuid);
}
