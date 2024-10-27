package itmo.high_perf_sys.chat.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import itmo.high_perf_sys.chat.entity.customer.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
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
    Long saveNewUserAccount(
            Long id,
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
    Long updateUserAccount(
            Long id,
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
    UserAccount findUserAccountById(Long id);

    @Modifying
    @Query("""
            DELETE FROM user_accounts 
            WHERE id = :id
            """
    )
    void deleteUserAccountById(Long id);
}