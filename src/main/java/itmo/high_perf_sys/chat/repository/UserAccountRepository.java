package itmo.high_perf_sys.chat.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import itmo.high_perf_sys.chat.entity.customer.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO user_accounts (
                id, name, surname, email,
                briefDescription, city, birthday, logoUrl
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
    @Query("""
                    UPDATE UserAccount u
                    SET u.name = :name, u.surname = :surname,\s
                        u.email = :email, u.briefDescription = :briefDescription,\s
                        u.city = :city, u.birthday = :birthday, u.logoUrl = :logoUrl
                    WHERE u.id = :id
            """
    )
    int updateUserAccount(
            UUID id,
            String name,
            String surname,
            String email,
            String briefDescription,
            String city,
            LocalDate birthday,
            String logoUrl
    );


    @Query("SELECT u FROM UserAccount u WHERE u.id = :id")
    UserAccount findUserAccountById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserAccount u WHERE u.id = :id")
    void deleteUserAccountById(@Param("id") UUID id);

    @Query("SELECT u.id FROM UserAccount u WHERE u.id = :id")
    Optional<UUID> findIdById(@Param("id") UUID id);
}