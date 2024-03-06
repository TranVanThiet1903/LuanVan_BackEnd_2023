package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class NguyenLieuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NguyenLieu.class);
        NguyenLieu nguyenLieu1 = new NguyenLieu();
        nguyenLieu1.setId(1L);
        NguyenLieu nguyenLieu2 = new NguyenLieu();
        nguyenLieu2.setId(nguyenLieu1.getId());
        assertThat(nguyenLieu1).isEqualTo(nguyenLieu2);
        nguyenLieu2.setId(2L);
        assertThat(nguyenLieu1).isNotEqualTo(nguyenLieu2);
        nguyenLieu1.setId(null);
        assertThat(nguyenLieu1).isNotEqualTo(nguyenLieu2);
    }
}
