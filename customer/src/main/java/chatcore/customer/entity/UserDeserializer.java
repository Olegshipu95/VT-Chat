package chatcore.customer.entity;

import chatcore.customer.service.CustomerService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.UUID;

@Component
public class UserDeserializer extends JsonDeserializer<User> {

    @Autowired
    private CustomerService customerService;

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String id = jsonParser.getText();
        return customerService.findById(UUID.fromString(id)).block();
    }
}
