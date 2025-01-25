package chatcore.feed;

import chatcore.feed.configuration.security.AuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationFilter.class)
public class AuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new EmptyController())
                .addFilter(authenticationFilter)
                .build();
    }

    @Test
    void testDoFilterInternal_Success() throws Exception {
        String userId = "123";
        String username = "testUser";
        List<String> userRoles = List.of("ROLE_USER", "ROLE_ADMIN");
        String userRolesHeader = objectMapper.writeValueAsString(userRoles);

        mockMvc.perform(get("/test")
                        .header("UserId", userId)
                        .header("Username", username)
                        .header("UserRoles", userRolesHeader))
                .andExpect(status().isOk());

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(authentication);
        assertEquals(userId, authentication.getPrincipal());
        assertEquals(username, authentication.getCredentials());
        assertEquals(userRoles.size(), authentication.getAuthorities().size());
        assertEquals(new SimpleGrantedAuthority("ROLE_USER"), authentication.getAuthorities().iterator().next());
    }

    @Test
    void testDoFilterInternal_MissingHeaders() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk());

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_EmptyRoles() throws Exception {
        String userId = "123";
        String username = "testUser";
        String userRolesHeader = "[]";

        mockMvc.perform(get("/test")
                        .header("UserId", userId)
                        .header("Username", username)
                        .header("UserRoles", userRolesHeader))
                .andExpect(status().isOk());

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
    }

    // Dummy controller to handle the request
    static class EmptyController {
        public String test() {
            return "test";
        }
    }
}
