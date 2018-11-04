package pl.basistam.wloczykij.json;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EventDto {
    private String name;
    private String description;
    private Integer participantsLimit;
    private String placeOfMeeting;
    private Date startDate;
    private Date endDate;
    private String guid;
    private String initiator;
    private boolean publicAccess;
    private List<EventUserDto> eventUsers;
    private List<Integer> trailIds;
}
