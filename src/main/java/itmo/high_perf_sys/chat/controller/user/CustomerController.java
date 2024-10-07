package itmo.high_perf_sys.chat.controller.user;

import itmo.high_perf_sys.chat.controller.user.dto.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.controller.user.dto.UserDto;
import jakarta.validation.Valid;
import itmo.high_perf_sys.chat.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/users")
public class CustomerController {
    private final CustomerService customerService;

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
