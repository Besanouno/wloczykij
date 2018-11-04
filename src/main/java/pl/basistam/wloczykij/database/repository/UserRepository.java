package pl.basistam.wloczykij.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.basistam.wloczykij.database.model.User;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.login = :login AND u.active = true")
    Optional<User> findUserByLogin(@Param("login") String login);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByLogin(String login);

    @Query("SELECT u FROM User u " +
            "WHERE u.login LIKE CONCAT(:pattern, '%') " +
            "OR u.firstName LIKE CONCAT(:pattern, '%') " +
            "OR u.lastName LIKE CONCAT(:pattern, '%') " +
            "OR CONCAT(u.firstName, ' ', u.lastName, '%') LIKE CONCAT(:pattern, '%')")
    Page<User> findAllByPattern(Pageable pageable, @Param("pattern") String pattern);
}
