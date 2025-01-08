package user.repository;

import user.entity.UsersChats;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;


public interface UsersChatsRepository extends ReactiveCrudRepository<UsersChats, UUID> {
}
