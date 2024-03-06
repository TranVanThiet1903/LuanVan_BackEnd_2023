package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class PhieuNhapDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhieuNhapDTO.class);
        PhieuNhapDTO phieuNhapDTO1 = new PhieuNhapDTO();
        phieuNhapDTO1.setId(1L);
        PhieuNhapDTO phieuNhapDTO2 = new PhieuNhapDTO();
        assertThat(phieuNhapDTO1).isNotEqualTo(phieuNhapDTO2);
        phieuNhapDTO2.setId(phieuNhapDTO1.getId());
        assertThat(phieuNhapDTO1).isEqualTo(phieuNhapDTO2);
        phieuNhapDTO2.setId(2L);
        assertThat(phieuNhapDTO1).isNotEqualTo(phieuNhapDTO2);
        phieuNhapDTO1.setId(null);
        assertThat(phieuNhapDTO1).isNotEqualTo(phieuNhapDTO2);
    }
}
