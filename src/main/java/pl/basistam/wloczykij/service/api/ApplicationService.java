package pl.basistam.wloczykij.service.api;

public interface ApplicationService {
    void apply(String eventGuid);
    void resign(String eventGuid);
}
