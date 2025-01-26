package user.service;

//import user.config.feign.FeignConfiguration;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;
import user.entity.UsersChats;

@FeignClient(name = "chat-messenger-cloud")
public interface UsersChatsServiceClient {


    @Headers("Content-Type: application/json")
    @RequestMapping(method = RequestMethod.POST, value = "/chats/addUserChats")
    UsersChats save(@RequestBody UsersChats usersChats);
}
