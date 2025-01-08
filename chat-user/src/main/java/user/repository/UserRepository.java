package user.repository;

import user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    @Query(value = """
            INSERT INTO users (
                id, name, surname, email,
                brief_description, city, birthday, logo_url
            )
            VALUES (:id, :name, :surname, :email,
                    :briefDescription, :city, :birthday, :logoUrl)
            """, nativeQuery = true)
    Mono<Void> saveNewUserAccount(UUID id, String name, String surname, String email,
                                  String briefDescription, String city, LocalDate birthday, String logoUrl);

    @Query(value = """
                    UPDATE users
                    SET name = :name,
                        surname = :surname,
                        email = :email,
                        brief_description = :briefDescription,
                        city = :city,
                        birthday = :birthday,
                        logo_url = :logoUrl
                    WHERE id = :id
            """, nativeQuery = true)
    Mono<Integer> updateUserAccount(UUID id, String name, String surname, String email,
                                    String briefDescription, String city, LocalDate birthday, String logoUrl);

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    Mono<User> findUserAccountById(UUID id);

    @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    Mono<Void> deleteUserAccountById(UUID id);

    @Query(value = "SELECT id FROM users WHERE id = :id", nativeQuery = true)
    Mono<UUID> findIdById(UUID id);
}