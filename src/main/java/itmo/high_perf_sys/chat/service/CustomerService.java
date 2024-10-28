package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.entity.customer.UserAccount;
import itmo.high_perf_sys.chat.exception.UserAccountNotFoundException;
import itmo.high_perf_sys.chat.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    private UserAccountRepository userAccountRepository;

    public UUID createAccount(CreateUserAccountRequest request) {
        UUID id = UUID.randomUUID();
        return userAccountRepository.saveNewUserAccount(
                id,
                request.name(),
                request.surname(),
                request.email(),
                request.briefDescription(),
                request.city(),
                request.birthday(),
                request.logoUrl()
        );
    }

    public UUID updateAccount(UpdateUserInfoRequest request){
        UserAccount existingAccount = userAccountRepository.findUserAccountById(request.userid());
        if (existingAccount == null) {
            throw new UserAccountNotFoundException(request.userid());
        }
        return userAccountRepository.updateUserAccount(
                request.userid(),
                request.name(),
                request.surname(),
                request.email(),
                request.briefDescription(),
                request.city(),
                request.birthday(),
                request.logoUrl()
        );
    }
}
