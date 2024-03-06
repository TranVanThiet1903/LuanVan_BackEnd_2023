package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class ChiTietPhieuNhapDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTietPhieuNhapDTO.class);
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO1 = new ChiTietPhieuNhapDTO();
        chiTietPhieuNhapDTO1.setId(1L);
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO2 = new ChiTietPhieuNhapDTO();
        assertThat(chiTietPhieuNhapDTO1).isNotEqualTo(chiTietPhieuNhapDTO2);
        chiTietPhieuNhapDTO2.setId(chiTietPhieuNhapDTO1.getId());
        assertThat(chiTietPhieuNhapDTO1).isEqualTo(chiTietPhieuNhapDTO2);
        chiTietPhieuNhapDTO2.setId(2L);
        assertThat(chiTietPhieuNhapDTO1).isNotEqualTo(chiTietPhieuNhapDTO2);
        chiTietPhieuNhapDTO1.setId(null);
        assertThat(chiTietPhieuNhapDTO1).isNotEqualTo(chiTietPhieuNhapDTO2);
    }
}
