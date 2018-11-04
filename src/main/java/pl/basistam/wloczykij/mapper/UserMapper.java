package pl.basistam.wloczykij.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.json.UserDto;
import pl.basistam.wloczykij.json.UserInputDto;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toEntity(UserInputDto json) {
        return modelMapper.map(json, User.class);
    }

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
