package user.controller;

import user.dto.request.CreateUserAccountRequest;
import user.dto.request.UpdateUserInfoRequest;
import user.dto.response.GetUserInfoResponse;
import user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/accounts/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Mono<ResponseEntity<UUID>> createAccount(@Valid @RequestBody CreateUserAccountRequest createUserAccountRequest) {
        return userService.createAccount(createUserAccountRequest)
                .map(id -> ResponseEntity.status(HttpStatus.CREATED).body(id));
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public Mono<ResponseEntity<UUID>> updateAccount(@RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        return userService.updateAccount(updateUserInfoRequest)
                .map(id -> ResponseEntity.status(HttpStatus.OK).body(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Mono<ResponseEntity<GetUserInfoResponse>> getAccountById(@PathVariable(value = "id") UUID id) {
        return userService.getAccountById(id)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<ResponseEntity<Void>> deleteAccountById(@PathVariable(value = "id") UUID id) {
        return userService.deleteAccountById(id)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>build()));
    }
}