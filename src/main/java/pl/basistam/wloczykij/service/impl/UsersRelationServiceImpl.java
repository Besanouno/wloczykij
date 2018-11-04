package pl.basistam.wloczykij.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.basistam.wloczykij.common.exceptions.ResourceNotFoundException;
import pl.basistam.wloczykij.common.util.AuthenticationHandler;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.database.repository.UserRepository;
import pl.basistam.wloczykij.json.RelationDto;
import pl.basistam.wloczykij.json.UserDto;
import pl.basistam.wloczykij.mapper.UserMapper;
import pl.basistam.wloczykij.service.api.UserService;
import pl.basistam.wloczykij.service.api.UsersRelationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersRelationServiceImpl implements UsersRelationService {

    private final AuthenticationHandler authenticationHandler;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UsersRelationServiceImpl(
            AuthenticationHandler authenticationHandler,
            UserService userService,
            UserRepository userRepository,
            UserMapper userMapper) {
        this.authenticationHandler = authenticationHandler;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void addRelation(String login) {
        User user = authenticationHandler.getUser();
        List<User> friends = user.getRelations();
        saveRelationIfNotPresent(login, friends);
        userRepository.save(user);
    }

    private void saveRelationIfNotPresent(String login, List<User> friends) {
        User friend = userService.findByLogin(login);
        if (!friends.contains(friend)) {
            friends.add(friend);
        }
    }

    @Override
    public void removeRelation(String login) {
        User user = authenticationHandler.getUser();
        removeFriendIfPresent(login, user.getRelations());
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getRelations() {
        User user = authenticationHandler.getUser();
        return user.getRelations()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateRelations(List<RelationDto> relations) throws ResourceNotFoundException {
        User user = authenticationHandler.getUser();
        List<User> friends = user.getRelations();
        for (RelationDto r: relations) {
            updateRelation(r, friends);
        }
        userRepository.save(user);
    }

    private void updateRelation(RelationDto r, List<User> friends) {
        if (r.isRelated()) {
            saveRelationIfNotPresent(r.getLogin(), friends);
        } else {
            removeFriendIfPresent(r.getLogin(), friends);
        }
    }

    private void removeFriendIfPresent(String login, List<User> friends) {
        friends.removeIf(u -> u.getLogin().equals(login));
    }
}
