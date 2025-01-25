//package user.config;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class SwaggerConfigTest {
//
//    @Autowired
//    private SwaggerConfig swaggerConfig;
//
//    @Test
//    public void testOpenAPIConfiguration() {
//        OpenAPI openAPI = swaggerConfig.openAPI();
//
//        assertNotNull(openAPI);
//        Info info = openAPI.getInfo();
//        assertNotNull(info);
//        assertEquals("SportsUser API", info.getTitle());
//        assertEquals("1.0", info.getVersion());
//
//        assertNotNull(openAPI.getSecurity());
//        assertEquals(1, openAPI.getSecurity().size());
//        SecurityRequirement securityRequirement = openAPI.getSecurity().get(0);
//        assertTrue(securityRequirement.containsKey("Bearer Authentication"));
//
//        assertNotNull(openAPI.getComponents());
//        assertNotNull(openAPI.getComponents().getSecuritySchemes());
//        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("Bearer Authentication");
//        assertNotNull(securityScheme);
//        assertEquals(SecurityScheme.Type.HTTP, securityScheme.getType());
//        assertEquals("bearer", securityScheme.getScheme());
//        assertEquals("JWT", securityScheme.getBearerFormat());
//    }
//}