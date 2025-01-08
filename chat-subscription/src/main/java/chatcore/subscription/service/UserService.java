 package chatcore.subscription.service;

import chatcore.subscription.entity.User;
import chatcore.subscription.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

}
