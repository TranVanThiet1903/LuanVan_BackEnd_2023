package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class ChiTietKhoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTietKho.class);
        ChiTietKho chiTietKho1 = new ChiTietKho();
        chiTietKho1.setId(1L);
        ChiTietKho chiTietKho2 = new ChiTietKho();
        chiTietKho2.setId(chiTietKho1.getId());
        assertThat(chiTietKho1).isEqualTo(chiTietKho2);
        chiTietKho2.setId(2L);
        assertThat(chiTietKho1).isNotEqualTo(chiTietKho2);
        chiTietKho1.setId(null);
        assertThat(chiTietKho1).isNotEqualTo(chiTietKho2);
    }
}
