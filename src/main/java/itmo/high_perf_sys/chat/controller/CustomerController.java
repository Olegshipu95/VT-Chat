package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.dto.customer.response.GetUserInfoResponse;
import itmo.high_perf_sys.chat.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public ResponseEntity<UUID> createAccount(@Valid @RequestBody CreateUserAccountRequest createUserAccountRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createAccount(createUserAccountRequest));
    }

    @PutMapping
    public ResponseEntity<UUID> updateAccount(@RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateAccount(updateUserInfoRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserInfoResponse> getAccountById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAccountById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteAccountById(@PathVariable(value = "id") UUID id) {
        customerService.deleteAccountById(id);
    }

    @GetMapping("/{id}/subscribe")
    public ResponseEntity<UpdateUserInfoRequest> getSubscribes(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.noContent().build();
    }

}
