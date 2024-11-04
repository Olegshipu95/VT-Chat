package itmo.high_perf_sys.chat.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatTypeTest {

    @Test
    public void testEnumValues() {
        assertThat(ChatType.values()).hasSize(2);
        assertThat(ChatType.values()).containsExactly(ChatType.PAIRED, ChatType.GROUP);
    }

    @Test
    public void testEnumNames() {
        assertThat(ChatType.PAIRED.name()).isEqualTo("PAIRED");
        assertThat(ChatType.GROUP.name()).isEqualTo("GROUP");
    }

    @Test
    public void testEnumTypeNames() {
        assertThat(ChatType.PAIRED.typeName).isEqualTo("PAIRED");
        assertThat(ChatType.GROUP.typeName).isEqualTo("GROUP");
    }

    @Test
    public void testEnumOrdinals() {
        assertThat(ChatType.PAIRED.ordinal()).isEqualTo(0);
        assertThat(ChatType.GROUP.ordinal()).isEqualTo(1);
    }

    @Test
    public void testEnumValueOf() {
        assertThat(ChatType.valueOf("PAIRED")).isEqualTo(ChatType.PAIRED);
        assertThat(ChatType.valueOf("GROUP")).isEqualTo(ChatType.GROUP);
    }
}
