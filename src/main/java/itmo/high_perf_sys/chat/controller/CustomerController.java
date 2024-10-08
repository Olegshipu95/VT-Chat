package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.user.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.user.UserDto;
import jakarta.validation.Valid;
import itmo.high_perf_sys.chat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/users")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createAccount(@Valid @RequestBody CreateUserAccountRequest createUserAccountRequest){
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<UserDto> updateAccount(@Valid @RequestBody CreateUserAccountRequest createUserAccountRequest){
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getAccountById(@Valid @RequestBody CreateUserAccountRequest createUserAccountRequest){
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteAccountById(@Valid @RequestBody CreateUserAccountRequest createUserAccountRequest){
        return ResponseEntity.noContent().build();
    }

}
