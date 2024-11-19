package itmo.high_perf_sys.chat.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import itmo.high_perf_sys.chat.entity.User;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, UUID> {

}