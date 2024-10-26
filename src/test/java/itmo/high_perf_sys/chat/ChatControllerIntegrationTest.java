package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.chat.request.CreateChatRequest;
import itmo.high_perf_sys.chat.dto.chat.response.*;
import itmo.high_perf_sys.chat.entity.Message;
import itmo.high_perf_sys.chat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.DynamicPropertyRegistry;
import org.springframework.boot.test.context.DynamicPropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Testcontainers
@WebMvcTest(ChatController.class)
public class ChatControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChatService chatService;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        // Настройка начальных данных для тестов
    }

    @Test
    public void testCreateChat() throws Exception {
        CreateChatRequest request = new CreateChatRequest();
        // Установите значения для request

        mockMvc.perform(MockMvcRequestBuilders.post("/chats/chat/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testSearchChat() throws Exception {
        Long userId = 1L;
        String request = "test";
        Long pageNumber = 0L;
        Long countChatsOnPage = 20L;

        mockMvc.perform(MockMvcRequestBuilders.get("/chats/search")
                        .param("userId", userId.toString())
                        .param("request", request)
                        .param("pageNumber", pageNumber.toString())
                        .param("countChatsOnPage", countChatsOnPage.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testDeleteChat() throws Exception {
        Long chatId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/chats/chat/{chatId}", chatId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAllChatsByUserId() throws Exception {
        Long userId = 1L;
        Long pageNumber = 0L;
        Long countChatsOnPage = 20L;

        mockMvc.perform(MockMvcRequestBuilders.get("/chats/{userId}", userId)
                        .param("pageNumber", pageNumber.toString())
                        .param("countChatsOnPage", countChatsOnPage.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAllMessagesByChatId() throws Exception {
        Long chatId = 1L;
        Long pageNumber = 0L;
        Long countMessagesOnPage = 20L;

        mockMvc.perform(MockMvcRequestBuilders.get("/chats/{chatId}", chatId)
                        .param("pageNumber", pageNumber.toString())
                        .param("countMessagesOnPage", countMessagesOnPage.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testSendMessage() throws Exception {
        Message message = new Message();
        // Установите значения для message

        mockMvc.perform(MockMvcRequestBuilders.post("/chats/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(message)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testSubscribe() throws Exception {
        Long chatId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/chats/subscribe/{chatId}", chatId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

