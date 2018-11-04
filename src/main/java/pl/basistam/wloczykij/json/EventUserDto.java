package pl.basistam.wloczykij.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.basistam.wloczykij.database.model.EventUserStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventUserDto {
    private String login;
    private String name;
    private Long userId;
    private EventUserStatus status;
}
