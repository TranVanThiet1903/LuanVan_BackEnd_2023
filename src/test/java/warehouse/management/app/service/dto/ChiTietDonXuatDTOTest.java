package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class ChiTietDonXuatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTietDonXuatDTO.class);
        ChiTietDonXuatDTO chiTietDonXuatDTO1 = new ChiTietDonXuatDTO();
        chiTietDonXuatDTO1.setId(1L);
        ChiTietDonXuatDTO chiTietDonXuatDTO2 = new ChiTietDonXuatDTO();
        assertThat(chiTietDonXuatDTO1).isNotEqualTo(chiTietDonXuatDTO2);
        chiTietDonXuatDTO2.setId(chiTietDonXuatDTO1.getId());
        assertThat(chiTietDonXuatDTO1).isEqualTo(chiTietDonXuatDTO2);
        chiTietDonXuatDTO2.setId(2L);
        assertThat(chiTietDonXuatDTO1).isNotEqualTo(chiTietDonXuatDTO2);
        chiTietDonXuatDTO1.setId(null);
        assertThat(chiTietDonXuatDTO1).isNotEqualTo(chiTietDonXuatDTO2);
    }
}
