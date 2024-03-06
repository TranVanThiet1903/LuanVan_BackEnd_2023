package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class ChiTietDonXuatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTietDonXuat.class);
        ChiTietDonXuat chiTietDonXuat1 = new ChiTietDonXuat();
        chiTietDonXuat1.setId(1L);
        ChiTietDonXuat chiTietDonXuat2 = new ChiTietDonXuat();
        chiTietDonXuat2.setId(chiTietDonXuat1.getId());
        assertThat(chiTietDonXuat1).isEqualTo(chiTietDonXuat2);
        chiTietDonXuat2.setId(2L);
        assertThat(chiTietDonXuat1).isNotEqualTo(chiTietDonXuat2);
        chiTietDonXuat1.setId(null);
        assertThat(chiTietDonXuat1).isNotEqualTo(chiTietDonXuat2);
    }
}
