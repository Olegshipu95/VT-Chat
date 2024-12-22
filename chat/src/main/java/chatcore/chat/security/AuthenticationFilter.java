package chatcore.chat.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final String USER_ID = "UserId";
    private static final String USERNAME = "Username";
    private static final String USER_ROLES = "UserRoles";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        String userId = request.getHeader(USER_ID);
        String username = request.getHeader(USERNAME);
        String userRolesHeader = request.getHeader(USER_ROLES);

        if (userId != null && username != null && userRolesHeader != null && !userRolesHeader.isEmpty()) {
            logger.info(userRolesHeader);
            List<String> userRoles = objectMapper.readValue(userRolesHeader, new TypeReference<List<String>>() {});

            List<SimpleGrantedAuthority> authorities = userRoles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userId, username, authorities)
            );
        }

        filterChain.doFilter(request, response);
    }

}