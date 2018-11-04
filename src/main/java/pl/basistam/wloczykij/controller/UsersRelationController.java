package pl.basistam.wloczykij.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.basistam.wloczykij.json.RelationDto;
import pl.basistam.wloczykij.json.UserDto;
import pl.basistam.wloczykij.service.api.UsersRelationService;

import java.util.List;

@RestController
@RequestMapping("users/self/relations")
public class UsersRelationController {

    private final UsersRelationService usersRelationService;

    @Autowired
    public UsersRelationController(UsersRelationService usersRelationService) {
        this.usersRelationService = usersRelationService;
    }

    @PostMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    public void addRelation(@PathVariable("login") final String login) {
        usersRelationService.addRelation(login);
    }

    @DeleteMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    public void removeRelation(@PathVariable("login") final String login) {
        usersRelationService.removeRelation(login);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getRelations() {
        return usersRelationService.getRelations();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateRelations(@RequestBody List<RelationDto> relations) {
        usersRelationService.updateRelations(relations);
    }
}
