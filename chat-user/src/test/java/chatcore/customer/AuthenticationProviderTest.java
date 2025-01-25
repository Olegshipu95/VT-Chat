package chatcore.customer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import user.config.security.AuthenticationProperties;
import user.config.security.AuthenticationProvider;
import user.entity.Role;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthenticationProviderTest {

    @InjectMocks
    private AuthenticationProvider authenticationProvider;

    @Mock
    private AuthenticationProperties props;

    private SecretKey key;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(props.getSecret()).thenReturn("mysecretkey12345678901234567890"); // Должен быть 32 байта
        when(props.getAccess()).thenReturn(1L); // 1 час
        when(props.getRefresh()).thenReturn(24L); // 24 часа
        authenticationProvider.init();
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Test
    public void testCreateAccessToken() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";
        Set<Role> roles = Set.of(Role.CUSTOMER);

        String token = authenticationProvider.createAccessToken(userId, username, roles);

        assertNotNull(token);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        assertEquals(username, claims.getSubject());
        assertEquals(userId.toString(), claims.get("id", String.class));
        assertEquals(roles, claims.get("roles"));
    }

    @Test
    public void testCreateRefreshToken() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";

        String token = authenticationProvider.createRefreshToken(userId, username);

        assertNotNull(token);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        assertEquals(username, claims.getSubject());
        assertEquals(userId.toString(), claims.get("id", String.class));
    }

    @Test
    public void testIsValid_ValidToken() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";
        Set<Role> roles = Set.of(Role.CUSTOMER);
        String token = authenticationProvider.createAccessToken(userId, username, roles);

        assertTrue(authenticationProvider.isValid(token));
    }

    @Test
    public void testIsValid_ExpiredToken() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";
        Set<Role> roles = Set.of(Role.CUSTOMER);
        String token = authenticationProvider.createAccessToken(userId, username, roles);

        // Устанавливаем токен как истекший
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        claims.setExpiration(Date.from(new Date().toInstant().minus(Duration.ofHours(1))));

        assertFalse(authenticationProvider.isValid(token));
    }

    @Test
    public void testGetAuthentication() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";
        Set<Role> roles = Set.of(Role.CUSTOMER);
        String token = authenticationProvider.createAccessToken(userId, username, roles);

        var auth = authenticationProvider.getAuthentication(token);

        assertEquals(userId.toString(), auth.getPrincipal());
        assertEquals(username, auth.getName());
        assertEquals(1, auth.getAuthorities().size());
        assertEquals("CUSTOMER", auth.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    public void testGetIdFromToken() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";
        Set<Role> roles = Set.of(Role.CUSTOMER);
        String token = authenticationProvider.createAccessToken(userId, username, roles);

        String id = authenticationProvider.getIdFromToken(token);

        assertEquals(userId.toString(), id);
    }

    @Test
    public void testGetUsernameFromToken() {
        UUID userId = UUID.randomUUID();
        String username = "testuser";
        Set<Role> roles = Set.of(Role.CUSTOMER);
        String token = authenticationProvider.createAccessToken(userId, username, roles);

        String extractedUsername = authenticationProvider.getUsernameFromToken(token);

        assertEquals(username, extractedUsername);
    }
}