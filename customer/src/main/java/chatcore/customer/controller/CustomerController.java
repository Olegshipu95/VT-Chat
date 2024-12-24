package chatcore.customer.controller;

import chatcore.customer.dto.customer.request.CreateUserAccountRequest;
import chatcore.customer.dto.customer.request.UpdateUserInfoRequest;
import chatcore.customer.dto.customer.response.GetUserInfoResponse;
import chatcore.customer.service.CustomerService;
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
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Mono<ResponseEntity<UUID>> createAccount(@Valid @RequestBody CreateUserAccountRequest createUserAccountRequest) {
        return customerService.createAccount(createUserAccountRequest)
                .map(id -> ResponseEntity.status(HttpStatus.CREATED).body(id));
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public Mono<ResponseEntity<UUID>> updateAccount(@RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        return customerService.updateAccount(updateUserInfoRequest)
                .map(id -> ResponseEntity.status(HttpStatus.OK).body(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Mono<ResponseEntity<GetUserInfoResponse>> getAccountById(@PathVariable(value = "id") UUID id) {
        return customerService.getAccountById(id)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<ResponseEntity<Void>> deleteAccountById(@PathVariable(value = "id") UUID id) {
        return customerService.deleteAccountById(id)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>build()));
    }
}