package itmo.high_perf_sys.chat.dto.chat.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.high_perf_sys.chat.utils.ErrorMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class SearchChatRequest {
    @NotNull(message = ErrorMessages.ID_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.ID_CANNOT_BE_NEGATIVE)
    @JsonProperty("user_id")
    private UUID userId;

    @NotNull
    @JsonProperty("request")
    private String request;

    @NotNull(message = ErrorMessages.PAGE_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.PAGE_CANNOT_BE_NEGATIVE)
    @JsonProperty("page_number")
    private Long pageNumber = 0L;

    @NotNull(message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NULL)
    @Min(value = 0, message = ErrorMessages.COUNT_PAGE_CANNOT_BE_NEGATIVE)
    @JsonProperty("count_chats_on_page")
    private Long countChatsOnPage = 20L;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public Long getCountChatsOnPage() {
        return countChatsOnPage;
    }

    public void setCountChatsOnPage(Long countChatsOnPage) {
        this.countChatsOnPage = countChatsOnPage;
    }
}
