package pl.basistam.wloczykij.service.api;

import org.springframework.data.domain.Page;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.json.UserDto;
import pl.basistam.wloczykij.json.UserInputDto;

public interface UserService {
    User save(UserInputDto json);
    User remove();
    User edit(UserInputDto json);
    User findByLogin(String login);
    UserDto getSelfDetails();
    UserDto getUserDetails(String login);
    Page<UserDto> getUsersByPattern(String pattern, int page, int size);
}
