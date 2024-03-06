package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class ChiTietPhieuNhapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTietPhieuNhap.class);
        ChiTietPhieuNhap chiTietPhieuNhap1 = new ChiTietPhieuNhap();
        chiTietPhieuNhap1.setId(1L);
        ChiTietPhieuNhap chiTietPhieuNhap2 = new ChiTietPhieuNhap();
        chiTietPhieuNhap2.setId(chiTietPhieuNhap1.getId());
        assertThat(chiTietPhieuNhap1).isEqualTo(chiTietPhieuNhap2);
        chiTietPhieuNhap2.setId(2L);
        assertThat(chiTietPhieuNhap1).isNotEqualTo(chiTietPhieuNhap2);
        chiTietPhieuNhap1.setId(null);
        assertThat(chiTietPhieuNhap1).isNotEqualTo(chiTietPhieuNhap2);
    }
}
