package chatcore.customer;

import org.junit.jupiter.api.Test;
import user.entity.MaritalStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MaritalStatusTest {

    @Test
    public void testMaritalStatusValues() {
        // Проверяем, что все значения перечисления MaritalStatus соответствуют ожидаемым
        MaritalStatus[] expectedStatuses = {
                MaritalStatus.SINGLE,
                MaritalStatus.DATING,
                MaritalStatus.MARRIED,
                MaritalStatus.DIVORCED
        };

        assertEquals(4, MaritalStatus.values().length);
        for (MaritalStatus status : expectedStatuses) {
            assertTrue(statusExists(status));
        }
    }

    @Test
    public void testMaritalStatusToString() {
        // Проверяем, что строковое представление каждого значения соответствует ожидаемому
        assertEquals("SINGLE", MaritalStatus.SINGLE.toString());
        assertEquals("DATING", MaritalStatus.DATING.toString());
        assertEquals("MARRIED", MaritalStatus.MARRIED.toString());
        assertEquals("DIVORCED", MaritalStatus.DIVORCED.toString());
    }

    private boolean statusExists(MaritalStatus status) {
        for (MaritalStatus s : MaritalStatus.values()) {
            if (s == status) {
                return true;
            }
        }
        return false;
    }
}