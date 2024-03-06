package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class DonNhapDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonNhapDTO.class);
        DonNhapDTO donNhapDTO1 = new DonNhapDTO();
        donNhapDTO1.setId(1L);
        DonNhapDTO donNhapDTO2 = new DonNhapDTO();
        assertThat(donNhapDTO1).isNotEqualTo(donNhapDTO2);
        donNhapDTO2.setId(donNhapDTO1.getId());
        assertThat(donNhapDTO1).isEqualTo(donNhapDTO2);
        donNhapDTO2.setId(2L);
        assertThat(donNhapDTO1).isNotEqualTo(donNhapDTO2);
        donNhapDTO1.setId(null);
        assertThat(donNhapDTO1).isNotEqualTo(donNhapDTO2);
    }
}
