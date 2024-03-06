package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class DonNhapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonNhap.class);
        DonNhap donNhap1 = new DonNhap();
        donNhap1.setId(1L);
        DonNhap donNhap2 = new DonNhap();
        donNhap2.setId(donNhap1.getId());
        assertThat(donNhap1).isEqualTo(donNhap2);
        donNhap2.setId(2L);
        assertThat(donNhap1).isNotEqualTo(donNhap2);
        donNhap1.setId(null);
        assertThat(donNhap1).isNotEqualTo(donNhap2);
    }
}
