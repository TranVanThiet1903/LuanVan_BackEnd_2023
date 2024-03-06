package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class NhomNguyenLieuDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhomNguyenLieuDTO.class);
        NhomNguyenLieuDTO nhomNguyenLieuDTO1 = new NhomNguyenLieuDTO();
        nhomNguyenLieuDTO1.setId(1L);
        NhomNguyenLieuDTO nhomNguyenLieuDTO2 = new NhomNguyenLieuDTO();
        assertThat(nhomNguyenLieuDTO1).isNotEqualTo(nhomNguyenLieuDTO2);
        nhomNguyenLieuDTO2.setId(nhomNguyenLieuDTO1.getId());
        assertThat(nhomNguyenLieuDTO1).isEqualTo(nhomNguyenLieuDTO2);
        nhomNguyenLieuDTO2.setId(2L);
        assertThat(nhomNguyenLieuDTO1).isNotEqualTo(nhomNguyenLieuDTO2);
        nhomNguyenLieuDTO1.setId(null);
        assertThat(nhomNguyenLieuDTO1).isNotEqualTo(nhomNguyenLieuDTO2);
    }
}
