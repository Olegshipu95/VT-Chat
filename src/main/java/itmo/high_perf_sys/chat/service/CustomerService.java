package itmo.high_perf_sys.chat.service;

import itmo.high_perf_sys.chat.dto.customer.request.CreateUserAccountRequest;
import itmo.high_perf_sys.chat.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    private UserAccountRepository userAccountRepository;

    public UUID createAccount(CreateUserAccountRequest request) {
        UUID id = UUID.randomUUID();
        return userAccountRepository.saveNewUserAccount(id, request.name(), request.surname(), request.email(),
                request.briefDescription(), request.city(), request.birthday(), request.logoUrl());
    }

    public UUID updateAccount()
}
