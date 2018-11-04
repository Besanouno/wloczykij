package pl.basistam.wloczykij.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.basistam.wloczykij.database.model.Message;
import pl.basistam.wloczykij.json.MessageDto;

@Component
public class MessageMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public MessageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MessageDto toDto(Message message) {
        MessageDto dto = modelMapper.map(message, MessageDto.class);
        dto.setUserLogin(message.getUser().getLogin());
        return dto;
    }

}
