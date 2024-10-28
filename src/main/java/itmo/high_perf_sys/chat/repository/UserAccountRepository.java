package itmo.high_perf_sys.chat.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import itmo.high_perf_sys.chat.entity.customer.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, UUID> {
    @Query("""
            INSERT INTO user_accounts (
                id, name, surname, email,
                briefDescription, city, birthday, logoUrl
            )
            VALUES (:id, :name, :surname, :email,
                    :briefDescription, :city, :birthday, :logoUrl)
            RETURNING id;
            """
    )
    UUID saveNewUserAccount(
            UUID id,
            String name,
            String surname,
            String email,
            String briefDescription,
            String city,
            LocalDate birthday,
            String logoUrl
    );

    @Query("""
            UPDATE user_accounts
            SET name = :name, surname = :surname, 
                email = :email, briefDescription = :briefDescription, city = :city, 
                birthday = :birthday, logoUrl = :logoUrl
            WHERE id = :id
            RETURNING id;
            """
    )
    UUID updateUserAccount(
            UUID id,
            String name,
            String surname,
            String email,
            String briefDescription,
            String city,
            LocalDate birthday,
            String logoUrl
    );

    @Query("""
            SELECT * FROM user_accounts
            WHERE id = :id
            """
    )
    UserAccount findUserAccountById(UUID id);

    @Modifying
    @Query("""
            DELETE FROM user_accounts 
            WHERE id = :id
            """
    )
    void deleteUserAccountById(UUID id);
}