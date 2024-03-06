package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class PhieuNhapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhieuNhap.class);
        PhieuNhap phieuNhap1 = new PhieuNhap();
        phieuNhap1.setId(1L);
        PhieuNhap phieuNhap2 = new PhieuNhap();
        phieuNhap2.setId(phieuNhap1.getId());
        assertThat(phieuNhap1).isEqualTo(phieuNhap2);
        phieuNhap2.setId(2L);
        assertThat(phieuNhap1).isNotEqualTo(phieuNhap2);
        phieuNhap1.setId(null);
        assertThat(phieuNhap1).isNotEqualTo(phieuNhap2);
    }
}
