package chatcore.customer.service;

import chatcore.customer.entity.UsersChats;
import chatcore.customer.repository.UsersChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersChatsService {

    private final UsersChatsRepository usersChatsRepository;

    @Autowired
    public UsersChatsService(UsersChatsRepository usersChatsRepository) {
        this.usersChatsRepository = usersChatsRepository;
    }

    public Mono<UsersChats> save(UsersChats usersChats) {
        return usersChatsRepository.save(usersChats);
    }
}
