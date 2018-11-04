package pl.basistam.wloczykij.database.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(exclude = {"relations", "password", "roles"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @Column(name = "year_of_birth")
    private int yearOfBirth;

    private String city;

    @Builder.Default
    private boolean active = true;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_relations",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "related_user_id", referencedColumnName = "id")})
    private List<User> relations;
}
