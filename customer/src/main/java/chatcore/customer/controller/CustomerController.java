package chatcore.customer.controller;

import chatcore.customer.dto.customer.request.CreateUserAccountRequest;
import chatcore.customer.dto.customer.request.UpdateUserInfoRequest;
import chatcore.customer.dto.customer.response.GetUserInfoResponse;
import chatcore.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

}
