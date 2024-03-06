package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class NhaKhoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhaKhoDTO.class);
        NhaKhoDTO nhaKhoDTO1 = new NhaKhoDTO();
        nhaKhoDTO1.setId(1L);
        NhaKhoDTO nhaKhoDTO2 = new NhaKhoDTO();
        assertThat(nhaKhoDTO1).isNotEqualTo(nhaKhoDTO2);
        nhaKhoDTO2.setId(nhaKhoDTO1.getId());
        assertThat(nhaKhoDTO1).isEqualTo(nhaKhoDTO2);
        nhaKhoDTO2.setId(2L);
        assertThat(nhaKhoDTO1).isNotEqualTo(nhaKhoDTO2);
        nhaKhoDTO1.setId(null);
        assertThat(nhaKhoDTO1).isNotEqualTo(nhaKhoDTO2);
    }
}
