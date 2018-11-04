package pl.basistam.wloczykij.common.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.basistam.wloczykij.database.model.EventUser;
import pl.basistam.wloczykij.database.model.User;
import pl.basistam.wloczykij.json.EventUserDto;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new AbstractConverter<User, String>() {
            @Override
            protected String convert(User type) {
                return type.getLogin();
            }
        });
        modelMapper.addConverter(new AbstractConverter<EventUser, EventUserDto>() {
            @Override
            protected EventUserDto convert(EventUser eventUser) {
                User user = eventUser.getUser();
                return EventUserDto.builder()
                        .userId(user.getId())
                        .login(user.getLogin())
                        .name(user.getFirstName() + " " + user.getLastName())
                        .status(eventUser.getStatus())
                        .build();
            }
        });
        modelMapper.addConverter(new AbstractConverter<EventUserDto, EventUser>() {
            @Override
            protected EventUser convert(EventUserDto eventUserDto) {
                return new EventUser();
            }
        });
        return modelMapper;
    }
}
