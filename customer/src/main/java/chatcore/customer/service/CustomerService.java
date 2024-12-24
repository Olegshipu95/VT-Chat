package chatcore.customer.service;

import chatcore.customer.dto.customer.request.CreateUserAccountRequest;
import chatcore.customer.dto.customer.request.UpdateUserInfoRequest;
import chatcore.customer.dto.customer.response.GetUserInfoResponse;
import chatcore.customer.entity.User;
import chatcore.customer.entity.UsersChats;
import chatcore.customer.exception.UserAccountNotFoundException;
import chatcore.customer.exception.UserAccountWasNotInsertException;
import chatcore.customer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {
    private final UserRepository userRepository;
    private final UsersChatsServiceClient usersChatsService;


    public Mono<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Mono<UUID> createAccount(CreateUserAccountRequest request) {
        log.debug("Create an account for: {}", request.name());
        UUID newId = UUID.randomUUID();
        return userRepository.saveNewUserAccount(
                        newId,
                        request.name(),
                        request.surname(),
                        request.email(),
                        request.briefDescription(),
                        request.city(),
                        request.birthday(),
                        request.logoUrl()
                ).then(Mono.just(newId))
                .flatMap(id -> {
                    UsersChats usersChats = new UsersChats();
                    usersChats.setId(UUID.randomUUID());
                    usersChats.setUserId(newId);
                    usersChats.setChats(new ArrayList<>());
                    return Mono.fromCallable(() -> usersChatsService.save(usersChats))
                            .thenReturn(newId);
                }).doOnSuccess(id -> log.info("User with ID: {} has been successfully created.", newId));
    }

    public Mono<UUID> updateAccount(UpdateUserInfoRequest request) {
        log.debug("UPDATE: start for id: {}", request.userId());
        return userRepository.findUserAccountById(request.userId())
                .switchIfEmpty(Mono.error(new UserAccountNotFoundException(request.userId())))
                .flatMap(existingAccount -> userRepository.updateUserAccount(
                        request.userId(),
                        request.name(),
                        request.surname(),
                        request.email(),
                        request.briefDescription(),
                        request.city(),
                        request.birthday(),
                        request.logoUrl()
                )).flatMap(value -> {
                    if (value == 0) {
                        return Mono.error(new UserAccountWasNotInsertException(request.userId()));
                    }
                    return Mono.just(request.userId());
                }).doOnSuccess(id -> log.info("UPDATE: ID: {} has been successfully updated.", request.userId()));
    }

    public Mono<GetUserInfoResponse> getAccountById(UUID id) {
        log.debug("GET: start for id: {}", id);
        return userRepository.findUserAccountById(id)
                .switchIfEmpty(Mono.error(new UserAccountNotFoundException(id)))
                .map(account -> new GetUserInfoResponse(
                        account.getId(),
                        account.getName(),
                        account.getSurname(),
                        account.getEmail(),
                        account.getBriefDescription(),
                        account.getCity(),
                        account.getBirthday(),
                        account.getLogoUrl()
                )).doOnSuccess(response -> log.info("GET: ID: {} has been successfully retrieved.", id));
    }

    public Mono<Void> deleteAccountById(UUID id) {
        log.debug("DELETE: start for id: {}", id);
        return userRepository.findUserAccountById(id)
                .switchIfEmpty(Mono.error(new UserAccountNotFoundException(id)))
                .flatMap(account -> userRepository.deleteUserAccountById(id))
                .doOnSuccess(voidValue -> log.info("DELETE: ID: {} has been successfully deleted.", id));
    }
}
