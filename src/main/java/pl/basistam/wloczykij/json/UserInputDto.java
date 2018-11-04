package pl.basistam.wloczykij.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import pl.basistam.wloczykij.json.validation.PostValidation;
import pl.basistam.wloczykij.json.validation.JsonValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDto implements Serializable {

    @NotBlank(message = "Email is required", groups = PostValidation.class)
    @Email(message = "Invalid email format", groups = PostValidation.class)
    @Size(max = 255, message = "Email can be maximum 255 symbols length", groups = PostValidation.class)
    private String email;

    @NotBlank(message = "Login is required", groups = PostValidation.class)
    @Pattern(regexp = "[\\p{Alnum}._]{3,16}$",message = "Invalid login format", groups = PostValidation.class)
    @Size(min = 3, max = 16, message = "Login length must be minimum 3 and maximum 16 symbols", groups = PostValidation.class)
    private String login;

    @NotNull(message = "Password is required", groups = PostValidation.class)
    @Size(min = 8, message = "Password must be minimum 8 symbols length", groups = PostValidation.class)
    private String password;

    @NotNull(message = "First name is required", groups = PostValidation.class)
    @Size(max = 255, message = "First name can be maximum 255 letters length", groups = JsonValidation.class)
    private String firstName;

    @NotNull(message = "Last name is required", groups = PostValidation.class)
    @Size(max = 255, message = "Last name can be maximum 255 letters length", groups = JsonValidation.class)
    private String lastName;

    @NotNull(message = "Year of birth is required", groups = PostValidation.class)
    private Integer yearOfBirth;

    @Size(max = 255, message = "City can by maximum 255 letters length", groups = JsonValidation.class)
    private String city;
}
