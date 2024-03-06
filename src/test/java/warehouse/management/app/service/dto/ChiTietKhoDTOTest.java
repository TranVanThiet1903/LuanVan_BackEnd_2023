package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class ChiTietKhoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTietKhoDTO.class);
        ChiTietKhoDTO chiTietKhoDTO1 = new ChiTietKhoDTO();
        chiTietKhoDTO1.setId(1L);
        ChiTietKhoDTO chiTietKhoDTO2 = new ChiTietKhoDTO();
        assertThat(chiTietKhoDTO1).isNotEqualTo(chiTietKhoDTO2);
        chiTietKhoDTO2.setId(chiTietKhoDTO1.getId());
        assertThat(chiTietKhoDTO1).isEqualTo(chiTietKhoDTO2);
        chiTietKhoDTO2.setId(2L);
        assertThat(chiTietKhoDTO1).isNotEqualTo(chiTietKhoDTO2);
        chiTietKhoDTO1.setId(null);
        assertThat(chiTietKhoDTO1).isNotEqualTo(chiTietKhoDTO2);
    }
}
