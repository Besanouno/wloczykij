package pl.basistam.wloczykij.service.api;

import pl.basistam.wloczykij.json.RelationDto;
import pl.basistam.wloczykij.json.UserDto;

import java.util.List;

public interface UsersRelationService {
    void addRelation(String login);
    void removeRelation(String login);
    List<UserDto> getRelations();
    void updateRelations(List<RelationDto> relations);
}
