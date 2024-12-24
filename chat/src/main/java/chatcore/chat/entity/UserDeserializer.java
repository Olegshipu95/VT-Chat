package chatcore.chat.entity;

import chatcore.chat.dto.customer.response.GetUserInfoResponse;
import chatcore.chat.service.CustomerServiceClient;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class UserDeserializer extends JsonDeserializer<User> {

    @Autowired
    private CustomerServiceClient customerService;

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String id = jsonParser.getText();
        ResponseEntity<GetUserInfoResponse> responseEntity = customerService.getAccountById(UUID.fromString(id)).block();
        GetUserInfoResponse getUserInfoResponse = responseEntity.getBody();
        return new User(
                getUserInfoResponse.userid(),
                getUserInfoResponse.name(),
                getUserInfoResponse.surname(),
                getUserInfoResponse.email(),
                getUserInfoResponse.briefDescription(),
                getUserInfoResponse.city(),
                getUserInfoResponse.birthday(),
                getUserInfoResponse.logoUrl()
        );
    }
}
