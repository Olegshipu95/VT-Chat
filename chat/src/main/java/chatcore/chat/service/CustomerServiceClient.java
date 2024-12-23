package chatcore.chat.service;


import chatcore.chat.config.feign.FeignConfiguration;
import chatcore.chat.dto.customer.response.GetUserInfoResponse;
import chatcore.chat.entity.User;
import chatcore.chat.repository.UserRepository;
import chatcore.chat.repository.chat.UsersChatsRepository;
import feign.Headers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.UUID;


@FeignClient(value = "${spring.cloud.openfeign.sports-order.name}", configuration = FeignConfiguration.class)
public interface CustomerServiceClient {

    @Headers("Content-Type: application/json")
    @RequestMapping(method = RequestMethod.GET, value = "/accounts/users/{id}")
    Mono<ResponseEntity<GetUserInfoResponse>> getAccountById(@PathVariable("id") UUID id);
}
