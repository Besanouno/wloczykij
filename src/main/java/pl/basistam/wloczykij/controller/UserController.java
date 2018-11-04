package pl.basistam.wloczykij.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.basistam.wloczykij.json.UserDto;
import pl.basistam.wloczykij.json.UserInputDto;
import pl.basistam.wloczykij.json.validation.PutValidation;
import pl.basistam.wloczykij.json.validation.PostValidation;
import pl.basistam.wloczykij.service.api.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Validated(PostValidation.class) final UserInputDto json) {
        userService.save(json);
    }

    @DeleteMapping("/self")
    @ResponseStatus(HttpStatus.OK)
    public void removeSelf() {
        userService.remove();
    }

    @PutMapping("/self")
    @ResponseStatus(HttpStatus.OK)
    public void editSelf(@RequestBody @Validated(PutValidation.class) final UserInputDto json) {
        userService.edit(json);
    }

    @GetMapping("/self")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getSelfDetails() {
        return userService.getSelfDetails();
    }

    @GetMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserDetails(@PathVariable("login") final String login) {
        return userService.getUserDetails(login);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getUsersSimpleDetails(
            @RequestParam("pattern") final String pattern,
            @RequestParam("page") final int page,
            @RequestParam("size") final int size) {
        return userService.getUsersByPattern(pattern, page, size);
    } 
}

