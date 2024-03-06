package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class DonXuatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonXuat.class);
        DonXuat donXuat1 = new DonXuat();
        donXuat1.setId(1L);
        DonXuat donXuat2 = new DonXuat();
        donXuat2.setId(donXuat1.getId());
        assertThat(donXuat1).isEqualTo(donXuat2);
        donXuat2.setId(2L);
        assertThat(donXuat1).isNotEqualTo(donXuat2);
        donXuat1.setId(null);
        assertThat(donXuat1).isNotEqualTo(donXuat2);
    }
}
