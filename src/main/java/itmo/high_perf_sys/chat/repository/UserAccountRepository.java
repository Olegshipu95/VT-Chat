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

public interface UserAccountRepository extends JpaRepository<User, UUID> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO users (
                id, name, surname, email,
                brief_description, city, birthday, logo_url
            )
            VALUES (:id, :name, :surname, :email,
                    :briefDescription, :city, :birthday, :logoUrl)
            """, nativeQuery = true)
    void saveNewUserAccount(@Param("id") UUID id,
                            @Param("name") String name,
                            @Param("surname") String surname,
                            @Param("email") String email,
                            @Param("briefDescription") String briefDescription,
                            @Param("city") String city,
                            @Param("birthday") LocalDate birthday,
                            @Param("logoUrl") String logoUrl);

    @Modifying
    @Transactional
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
    int updateUserAccount(
            @Param("id") UUID id,
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("email") String email,
            @Param("briefDescription") String briefDescription,
            @Param("city") String city,
            @Param("birthday") LocalDate birthday,
            @Param("logoUrl") String logoUrl
    );

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    User findUserAccountById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    void deleteUserAccountById(@Param("id") UUID id);

    @Query(value = "SELECT id FROM users WHERE id = :id", nativeQuery = true)
    Optional<UUID> findIdById(@Param("id") UUID id);
}