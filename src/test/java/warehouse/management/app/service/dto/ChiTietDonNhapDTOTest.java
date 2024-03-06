package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class ChiTietDonNhapDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTietDonNhapDTO.class);
        ChiTietDonNhapDTO chiTietDonNhapDTO1 = new ChiTietDonNhapDTO();
        chiTietDonNhapDTO1.setId(1L);
        ChiTietDonNhapDTO chiTietDonNhapDTO2 = new ChiTietDonNhapDTO();
        assertThat(chiTietDonNhapDTO1).isNotEqualTo(chiTietDonNhapDTO2);
        chiTietDonNhapDTO2.setId(chiTietDonNhapDTO1.getId());
        assertThat(chiTietDonNhapDTO1).isEqualTo(chiTietDonNhapDTO2);
        chiTietDonNhapDTO2.setId(2L);
        assertThat(chiTietDonNhapDTO1).isNotEqualTo(chiTietDonNhapDTO2);
        chiTietDonNhapDTO1.setId(null);
        assertThat(chiTietDonNhapDTO1).isNotEqualTo(chiTietDonNhapDTO2);
    }
}
