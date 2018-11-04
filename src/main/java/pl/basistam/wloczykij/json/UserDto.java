package pl.basistam.wloczykij.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private int yearOfBirth;
    private String city;
    private Date creationDate;
}
