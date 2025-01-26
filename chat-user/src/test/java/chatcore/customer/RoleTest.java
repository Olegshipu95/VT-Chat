package chatcore.customer;

import org.junit.jupiter.api.Test;
import user.entity.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoleTest {

    @Test
    public void testRoleValues() {
        // Проверяем, что все значения перечисления Role соответствуют ожидаемым
        Role[] expectedRoles = {Role.CUSTOMER, Role.EXECUTOR, Role.SUPERVISOR};
        assertEquals(3, Role.values().length);
        for (Role role : expectedRoles) {
            assertTrue(roleExists(role));
        }
    }

    @Test
    public void testRoleToString() {
        // Проверяем, что строковое представление каждого значения соответствует ожидаемому
        assertEquals("CUSTOMER", Role.CUSTOMER.toString());
        assertEquals("EXECUTOR", Role.EXECUTOR.toString());
        assertEquals("SUPERVISOR", Role.SUPERVISOR.toString());
    }

    private boolean roleExists(Role role) {
        for (Role r : Role.values()) {
            if (r == role) {
                return true;
            }
        }
        return false;
    }
}