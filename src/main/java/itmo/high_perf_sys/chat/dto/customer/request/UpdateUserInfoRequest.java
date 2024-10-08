package itmo.high_perf_sys.chat.dto.customer.request;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateUserInfoRequest(
        UUID userid,
        String name,
        String surname,
        String email,
        String briefDescription,
        String city,
        LocalDate birthday,
        String logoUrl
) {
}