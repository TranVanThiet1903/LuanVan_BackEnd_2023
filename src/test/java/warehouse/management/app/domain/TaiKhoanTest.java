package warehouse.management.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class TaiKhoanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaiKhoan.class);
        TaiKhoan taiKhoan1 = new TaiKhoan();
        taiKhoan1.setId(UUID.randomUUID());
        TaiKhoan taiKhoan2 = new TaiKhoan();
        taiKhoan2.setId(taiKhoan1.getId());
        assertThat(taiKhoan1).isEqualTo(taiKhoan2);
        taiKhoan2.setId(UUID.randomUUID());
        assertThat(taiKhoan1).isNotEqualTo(taiKhoan2);
        taiKhoan1.setId(null);
        assertThat(taiKhoan1).isNotEqualTo(taiKhoan2);
    }
}
