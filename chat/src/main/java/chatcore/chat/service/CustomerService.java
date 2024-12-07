package chatcore.chat.service;


import chatcore.chat.dto.customer.request.CreateUserAccountRequest;
import chatcore.chat.dto.customer.request.UpdateUserInfoRequest;
import chatcore.chat.dto.customer.response.GetUserInfoResponse;
import chatcore.chat.entity.User;
import chatcore.chat.entity.UsersChats;
import chatcore.chat.exception.UserAccountNotFoundException;
import chatcore.chat.exception.UserAccountWasNotInsertException;
import chatcore.chat.repository.UserRepository;
import chatcore.chat.repository.chat.UsersChatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CustomerService {
    private final UserRepository userRepository;
    private final UsersChatsRepository usersChatsRepository;

    @Autowired
    public CustomerService(UserRepository userRepository, UsersChatsRepository usersChatsRepository) {
        this.userRepository = userRepository;
        this.usersChatsRepository = usersChatsRepository;
    }

    public User findById(UUID id) {
        return userRepository.findById(id).get();
    }


    public UUID createAccount(CreateUserAccountRequest request) {
        log.debug("Create an account for: {}", request.name());
        UUID newId = UUID.randomUUID();
        userRepository.saveNewUserAccount(
                newId,
                request.name(),
                request.surname(),
                request.email(),
                request.briefDescription(),
                request.city(),
                request.birthday(),
                request.logoUrl()
        );
        UsersChats usersChats = new UsersChats();
        usersChats.setId(UUID.randomUUID());
        usersChats.setUserId(newId);
        usersChats.setChats(new ArrayList<UUID>());
        usersChatsRepository.save(usersChats);

        log.info("User with ID: {} has been successfully created.", newId);
        return newId;
    }

    public UUID updateAccount(UpdateUserInfoRequest request) {
        log.debug("UPDATE: start for id: {}", request.userId());
        User existingAccount = userRepository.findUserAccountById(request.userId());
        if (existingAccount == null) {
            log.debug("UPDATE: id {} does not exist", request.userId());
            throw new UserAccountNotFoundException(request.userId());
        }
        int value = userRepository.updateUserAccount(
                request.userId(),
                request.name(),
                request.surname(),
                request.email(),
                request.briefDescription(),
                request.city(),
                request.birthday(),
                request.logoUrl()
        );
        Optional.of(value)
                .filter(v -> v != 0)
                .orElseThrow(() -> new UserAccountWasNotInsertException(request.userId()));

        log.info("UPDATE: ID: {} has been successfully updated.", request.userId());
        return request.userId();
    }

    public GetUserInfoResponse getAccountById(UUID id) {
        log.debug("GET: start for id: {}", id);
        User account = userRepository.findUserAccountById(id);
        if (account == null) {
            log.debug("GET: id {} does not exist", id);
            throw new UserAccountNotFoundException(id);
        }
        log.info("GET: ID: {} has been successfully retrieved.", id);
        return new GetUserInfoResponse(
                account.getId(),
                account.getName(),
                account.getSurname(),
                account.getEmail(),
                account.getBriefDescription(),
                account.getCity(),
                account.getBirthday(),
                account.getLogoUrl()
        );
    }

    public void deleteAccountById(UUID id) {
        log.debug("DELETE: start for id: {}", id);
        User account = userRepository.findUserAccountById(id);
        if (account == null) {
            throw new UserAccountNotFoundException(id);
        }
        userRepository.deleteUserAccountById(id);
        log.info("DELETE: ID: {} has been successfully deleted.", id);
    }
}
