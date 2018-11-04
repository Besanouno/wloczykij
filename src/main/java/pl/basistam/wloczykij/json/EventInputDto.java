package pl.basistam.wloczykij.json;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import pl.basistam.wloczykij.json.validation.PostValidation;
import pl.basistam.wloczykij.json.validation.JsonValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventInputDto implements Serializable {
    @NotBlank(message = "Event name cannot be empty", groups = PostValidation.class)
    @Size(max = 16, message = "Event name can be max 15 letters length", groups = JsonValidation.class)
    private String name;
    @Size(max = 1000, message = "Event description can be max 1000 letters length", groups = JsonValidation.class)
    private String description;
    private Integer participantsLimit;
    @NotBlank(message = "Event place of meeting cannot be empty", groups = PostValidation.class)
    @Size(max = 255, message = "Event place of meeting can be max 255 letters length", groups = JsonValidation.class)
    private String placeOfMeeting;
    @NotNull(message = "Event start date cannot be empty", groups = PostValidation.class)
    private Date startDate;
    private Date endDate;
    private Boolean publicAccess;
    private List<EventUserDto> eventUsers;
    private List<Integer> trailIds;
}
