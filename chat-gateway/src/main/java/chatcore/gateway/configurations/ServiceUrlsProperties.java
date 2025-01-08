package chatcore.gateway.configurations;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.services.urls")
public class ServiceUrlsProperties {

    private final String customer;
    private final String news;
    private final String feed;
    private final String chat;
    private final String sub;

    public ServiceUrlsProperties(String customer, String news, String feed, String chat, String sub) {
        this.customer = customer;
        this.news = news;
        this.feed = feed;
        this.chat = chat;
        this.sub = sub;
    }

}