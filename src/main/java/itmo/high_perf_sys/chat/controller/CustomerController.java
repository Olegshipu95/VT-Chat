package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.dto.customer.response.GetUserInfoResponse;
import itmo.high_perf_sys.chat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UUID> createAccount(@RequestBody CreateUserAccountRequest createUserAccountRequest) {
        return new ResponseEntity<>(UUID.randomUUID(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UUID> updateAccount(@RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        return new ResponseEntity<>(UUID.randomUUID(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserInfoResponse> getAccountById(@PathVariable UUID id) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable UUID id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/subscribe")
    public ResponseEntity<UpdateUserInfoRequest> getSubscribes(@PathVariable UUID id) {
        return ResponseEntity.noContent().build();
    }

}
