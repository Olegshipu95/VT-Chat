package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.dto.customer.response.GetUserInfoResponse;
import itmo.high_perf_sys.chat.entity.customer.UserAccount;
import itmo.high_perf_sys.chat.exception.UserAccountNotFoundException;
import itmo.high_perf_sys.chat.exception.UserAccountWasNotInsertException;
import itmo.high_perf_sys.chat.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private UserAccountRepository userAccountRepository;

    public UUID createAccount(CreateUserAccountRequest request) {
        UUID id = UUID.randomUUID();
        userAccountRepository.saveNewUserAccount(
                id,
                request.name(),
                request.surname(),
                request.email(),
                request.briefDescription(),
                request.city(),
                request.birthday(),
                request.logoUrl()
        );
        return userAccountRepository.findIdById(id)
                .orElseThrow(() -> new UserAccountWasNotInsertException(id));
    }

    public UUID updateAccount(UpdateUserInfoRequest request) {
        UserAccount existingAccount = userAccountRepository.findUserAccountById(request.userid());
        if (existingAccount == null) {
            throw new UserAccountNotFoundException(request.userid());
        }
        int value = userAccountRepository.updateUserAccount(
                request.userid(),
                request.name(),
                request.surname(),
                request.email(),
                request.briefDescription(),
                request.city(),
                request.birthday(),
                request.logoUrl()
        );
        Optional.of(value)
                .filter(v -> v != 0)
                .orElseThrow(() -> new UserAccountWasNotInsertException(request.userid()));
        return request.userid();
    }

    public GetUserInfoResponse getAccountById(UUID id){
        UserAccount account = userAccountRepository.findUserAccountById(id);
        if (account == null) {
            throw new UserAccountNotFoundException(id);
        }
        return new GetUserInfoResponse(
                account.getId(),
                account.getName(),
                account.getSurname(),
                account.getEmail(),
                account.getBriefDescription(),
                account.getCity(),
                account.getBirthday(),
                account.getLogoUrl()
        );
    }

    public void deleteAccountById(UUID id){
        UserAccount account = userAccountRepository.findUserAccountById(id);
        if (account == null) {
            throw new UserAccountNotFoundException(id);
        }
        userAccountRepository.deleteUserAccountById(id);
    }

}
