package pl.basistam.wloczykij.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.basistam.wloczykij.common.exceptions.CredentialsTakenException;
import pl.basistam.wloczykij.common.exceptions.ResourceNotFoundException;
import pl.basistam.wloczykij.common.util.AuthenticationHandler;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.database.repository.UserRepository;
import pl.basistam.wloczykij.json.UserDto;
import pl.basistam.wloczykij.json.UserInputDto;
import pl.basistam.wloczykij.mapper.UserMapper;
import pl.basistam.wloczykij.service.api.UserService;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final String KEYWORD_LOGIN = "self";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationHandler authenticationHandler;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            AuthenticationHandler authenticationHandler) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    public User save(UserInputDto json) {
        if (KEYWORD_LOGIN.equalsIgnoreCase(json.getLogin()) || userRepository.existsByLogin(json.getLogin())) {
            throw new CredentialsTakenException("Podany login jest już zajęty!");
        } else if (userRepository.existsByEmailIgnoreCase(json.getEmail())) {
            throw new CredentialsTakenException("Podany email jest już zajęty!");
        }
        json.setPassword(new BCryptPasswordEncoder().encode(json.getPassword()));
        User user = userMapper.toEntity(json);
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public User remove() {
        User user = authenticationHandler.getUser();
        user.setActive(false);
        return userRepository.save(user);
    }

    @Override
    public User edit(UserInputDto json) {
        User user = authenticationHandler.getUser();

        if (!StringUtils.isEmpty(json.getFirstName()))
            user.setFirstName(json.getFirstName());

        if (!StringUtils.isEmpty(json.getLastName()))
            user.setLastName(json.getLastName());

        if (!StringUtils.isEmpty(json.getCity()))
            user.setCity(json.getCity());

        if (json.getYearOfBirth() != null)
            user.setYearOfBirth(json.getYearOfBirth());

        return userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("User with login: " + login + " does not exist"));
    }

    @Override
    public UserDto getSelfDetails() {
        return userMapper.toDto(authenticationHandler.getUser());
    }

    @Override
    public UserDto getUserDetails(String login) {
        return userMapper.toDto(findByLogin(login));
    }

    @Override
    public Page<UserDto> getUsersByPattern(String pattern, int page, int size) {
        return userRepository
                .findAllByPattern(new PageRequest(page, size), pattern)
                .map(userMapper::toDto);
    }
}
