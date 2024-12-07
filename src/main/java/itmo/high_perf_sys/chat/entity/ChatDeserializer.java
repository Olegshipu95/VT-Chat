package itmo.high_perf_sys.chat.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import itmo.high_perf_sys.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class ChatDeserializer extends JsonDeserializer<Chat> {

    @Autowired
    private ChatService chatService;

    @Override
    public Chat deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String id = jsonParser.getText();
        return chatService.findById(UUID.fromString(id));
    }
}

