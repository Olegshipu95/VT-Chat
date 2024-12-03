package chatcore.customer.repository;

import chatcore.customer.entity.UsersChats;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;


public interface UsersChatsRepository extends ReactiveCrudRepository<UsersChats, UUID> {
}
