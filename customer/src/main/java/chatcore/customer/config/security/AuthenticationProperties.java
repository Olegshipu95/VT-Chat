package chatcore.customer.config.security;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.security.jwt")
public class AuthenticationProperties {

    private final String secret;
    private final long access;
    private final long refresh;

    public AuthenticationProperties(String secret, long access, long refresh) {
        this.secret = secret;
        this.access = access;
        this.refresh = refresh;
    }
}