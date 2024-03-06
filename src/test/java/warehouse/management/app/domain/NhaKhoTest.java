package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class NhaKhoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhaKho.class);
        NhaKho nhaKho1 = new NhaKho();
        nhaKho1.setId(1L);
        NhaKho nhaKho2 = new NhaKho();
        nhaKho2.setId(nhaKho1.getId());
        assertThat(nhaKho1).isEqualTo(nhaKho2);
        nhaKho2.setId(2L);
        assertThat(nhaKho1).isNotEqualTo(nhaKho2);
        nhaKho1.setId(null);
        assertThat(nhaKho1).isNotEqualTo(nhaKho2);
    }
}
