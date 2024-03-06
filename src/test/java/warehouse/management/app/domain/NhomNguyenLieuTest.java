package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class NhomNguyenLieuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhomNguyenLieu.class);
        NhomNguyenLieu nhomNguyenLieu1 = new NhomNguyenLieu();
        nhomNguyenLieu1.setId(1L);
        NhomNguyenLieu nhomNguyenLieu2 = new NhomNguyenLieu();
        nhomNguyenLieu2.setId(nhomNguyenLieu1.getId());
        assertThat(nhomNguyenLieu1).isEqualTo(nhomNguyenLieu2);
        nhomNguyenLieu2.setId(2L);
        assertThat(nhomNguyenLieu1).isNotEqualTo(nhomNguyenLieu2);
        nhomNguyenLieu1.setId(null);
        assertThat(nhomNguyenLieu1).isNotEqualTo(nhomNguyenLieu2);
    }
}
