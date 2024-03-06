package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class NguyenLieuDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NguyenLieuDTO.class);
        NguyenLieuDTO nguyenLieuDTO1 = new NguyenLieuDTO();
        nguyenLieuDTO1.setId(1L);
        NguyenLieuDTO nguyenLieuDTO2 = new NguyenLieuDTO();
        assertThat(nguyenLieuDTO1).isNotEqualTo(nguyenLieuDTO2);
        nguyenLieuDTO2.setId(nguyenLieuDTO1.getId());
        assertThat(nguyenLieuDTO1).isEqualTo(nguyenLieuDTO2);
        nguyenLieuDTO2.setId(2L);
        assertThat(nguyenLieuDTO1).isNotEqualTo(nguyenLieuDTO2);
        nguyenLieuDTO1.setId(null);
        assertThat(nguyenLieuDTO1).isNotEqualTo(nguyenLieuDTO2);
    }
}
