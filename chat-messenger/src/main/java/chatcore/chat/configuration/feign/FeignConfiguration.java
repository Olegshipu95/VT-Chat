//package chatcore.chat.config.feign;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import feign.codec.Decoder;
//import feign.codec.Encoder;
//import feign.jackson.JacksonDecoder;
//import feign.jackson.JacksonEncoder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FeignConfiguration {
//
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Bean
//    public Decoder decoder() {
//        return new JacksonDecoder(mapper);
//    }
//
//    @Bean
//    public Encoder encoder() {
//        return new JacksonEncoder(mapper);
//    }
//}