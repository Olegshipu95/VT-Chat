package itmo.high_perf_sys.chat.dto.chat.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class SearchMessageRequest {
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
    @JsonProperty("chat_id")
    private UUID chatId;

    @NotNull(message = ErrorMessages.REQUEST_CANNOT_BE_NULL)
    @JsonProperty("request")
    private String request;

    @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
    @JsonProperty("page_number")
    private Long pageNumber = 0L;

    @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
    @JsonProperty("count_messages_on_page")
    private Long countMessagesOnPage = 20L;

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getCountMessagesOnPage() {
        return countMessagesOnPage;
    }

    public void setCountMessagesOnPage(Long countMessagesOnPage) {
        this.countMessagesOnPage = countMessagesOnPage;
    }
}
