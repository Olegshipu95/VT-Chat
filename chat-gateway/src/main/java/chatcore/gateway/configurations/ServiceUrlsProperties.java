package chatcore.gateway.configurations;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.services.urls")
public class ServiceUrlsProperties {

    private final String user;
    private final String news;
    private final String feed;
    private final String messenger;
    private final String subscription;

    public ServiceUrlsProperties(String user, String news, String feed, String messenger, String subscription) {
        this.user = user;
        this.news = news;
        this.feed = feed;
        this.messenger = messenger;
        this.subscription = subscription;
    }

}