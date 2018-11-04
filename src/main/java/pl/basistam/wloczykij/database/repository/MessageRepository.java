package pl.basistam.wloczykij.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.basistam.wloczykij.database.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByEventGuid(String guid, Pageable pageable);
}
