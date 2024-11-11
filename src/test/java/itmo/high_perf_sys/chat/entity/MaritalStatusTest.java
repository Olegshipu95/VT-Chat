package itmo.high_perf_sys.chat.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MaritalStatusTest {

    @Test
    public void testEnumValues() {
        assertThat(MaritalStatus.values()).hasSize(4);
        assertThat(MaritalStatus.values()).containsExactly(
                MaritalStatus.SINGLE,
                MaritalStatus.DATING,
                MaritalStatus.MARRIED,
                MaritalStatus.DIVORCED
        );
    }

    @Test
    public void testEnumNames() {
        assertThat(MaritalStatus.SINGLE.name()).isEqualTo("SINGLE");
        assertThat(MaritalStatus.DATING.name()).isEqualTo("DATING");
        assertThat(MaritalStatus.MARRIED.name()).isEqualTo("MARRIED");
        assertThat(MaritalStatus.DIVORCED.name()).isEqualTo("DIVORCED");
    }

    @Test
    public void testEnumStatusNames() {
        assertThat(MaritalStatus.SINGLE.statusName).isEqualTo("SINGLE");
        assertThat(MaritalStatus.DATING.statusName).isEqualTo("DATING");
        assertThat(MaritalStatus.MARRIED.statusName).isEqualTo("MARRIED");
        assertThat(MaritalStatus.DIVORCED.statusName).isEqualTo("DIVORCED");
    }

    @Test
    public void testEnumOrdinals() {
        assertThat(MaritalStatus.SINGLE.ordinal()).isEqualTo(0);
        assertThat(MaritalStatus.DATING.ordinal()).isEqualTo(1);
        assertThat(MaritalStatus.MARRIED.ordinal()).isEqualTo(2);
        assertThat(MaritalStatus.DIVORCED.ordinal()).isEqualTo(3);
    }

    @Test
    public void testEnumValueOf() {
        assertThat(MaritalStatus.valueOf("SINGLE")).isEqualTo(MaritalStatus.SINGLE);
        assertThat(MaritalStatus.valueOf("DATING")).isEqualTo(MaritalStatus.DATING);
        assertThat(MaritalStatus.valueOf("MARRIED")).isEqualTo(MaritalStatus.MARRIED);
        assertThat(MaritalStatus.valueOf("DIVORCED")).isEqualTo(MaritalStatus.DIVORCED);
    }
}
