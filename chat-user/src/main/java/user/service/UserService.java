package user.service;

import user.dto.request.CreateUserAccountRequest;
import user.dto.request.UpdateUserInfoRequest;
import user.dto.response.GetUserInfoResponse;
import user.entity.User;
import user.entity.UsersChats;
import user.exception.UserAccountNotFoundException;
import user.exception.UserAccountWasNotInsertException;
import user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UsersChatsServiceClient usersChatsService;

    public UserService(UserRepository userRepository, UsersChatsServiceClient usersChatsService) {
        this.userRepository = userRepository;
        this.usersChatsService = usersChatsService;
    }

    public Mono<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Mono<UUID> createAccount(CreateUserAccountRequest request) {
        log.debug("Create an account for: {}", request.name());
        UUID newId = UUID.randomUUID();
        User newUser = new User(
                newId,
                request.name(),
                request.surname(),
                request.email(),
                request.briefDescription(),
                request.city(),
                request.birthday(),
                request.logoUrl()
        );
        return userRepository.save(newUser).then(Mono.just(newId))
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
        User updatedUser = new User(
                        request.userId(),
                request.name(),
                request.surname(),
                request.email(),
                request.briefDescription(),
                request.city(),
                request.birthday(),
                request.logoUrl()
        );

        return Mono.just(userRepository.findById(request.userId()))
                .switchIfEmpty(Mono.error(new UserAccountNotFoundException(request.userId())))
                .flatMap(existingAccount -> userRepository.save(updatedUser))
                .map(User::getId);
    }

    public Mono<GetUserInfoResponse> getAccountById(UUID id) {
        log.debug("GET: start for id: {}", id);
        return userRepository.findById(id)
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
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserAccountNotFoundException(id)))
                .flatMap(account -> userRepository.deleteById(id))
                .doOnSuccess(voidValue -> log.info("DELETE: ID: {} has been successfully deleted.", id));
    }
}
