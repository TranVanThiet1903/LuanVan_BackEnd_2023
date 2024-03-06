package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class ChiTietDonNhapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTietDonNhap.class);
        ChiTietDonNhap chiTietDonNhap1 = new ChiTietDonNhap();
        chiTietDonNhap1.setId(1L);
        ChiTietDonNhap chiTietDonNhap2 = new ChiTietDonNhap();
        chiTietDonNhap2.setId(chiTietDonNhap1.getId());
        assertThat(chiTietDonNhap1).isEqualTo(chiTietDonNhap2);
        chiTietDonNhap2.setId(2L);
        assertThat(chiTietDonNhap1).isNotEqualTo(chiTietDonNhap2);
        chiTietDonNhap1.setId(null);
        assertThat(chiTietDonNhap1).isNotEqualTo(chiTietDonNhap2);
    }
}
