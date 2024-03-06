package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class DonXuatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonXuatDTO.class);
        DonXuatDTO donXuatDTO1 = new DonXuatDTO();
        donXuatDTO1.setId(1L);
        DonXuatDTO donXuatDTO2 = new DonXuatDTO();
        assertThat(donXuatDTO1).isNotEqualTo(donXuatDTO2);
        donXuatDTO2.setId(donXuatDTO1.getId());
        assertThat(donXuatDTO1).isEqualTo(donXuatDTO2);
        donXuatDTO2.setId(2L);
        assertThat(donXuatDTO1).isNotEqualTo(donXuatDTO2);
        donXuatDTO1.setId(null);
        assertThat(donXuatDTO1).isNotEqualTo(donXuatDTO2);
    }
}
