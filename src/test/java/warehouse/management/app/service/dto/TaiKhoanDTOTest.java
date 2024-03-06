package warehouse.management.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import warehouse.management.app.web.rest.TestUtil;

class TaiKhoanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaiKhoanDTO.class);
        TaiKhoanDTO taiKhoanDTO1 = new TaiKhoanDTO();
        taiKhoanDTO1.setId(UUID.randomUUID());
        TaiKhoanDTO taiKhoanDTO2 = new TaiKhoanDTO();
        assertThat(taiKhoanDTO1).isNotEqualTo(taiKhoanDTO2);
        taiKhoanDTO2.setId(taiKhoanDTO1.getId());
        assertThat(taiKhoanDTO1).isEqualTo(taiKhoanDTO2);
        taiKhoanDTO2.setId(UUID.randomUUID());
        assertThat(taiKhoanDTO1).isNotEqualTo(taiKhoanDTO2);
        taiKhoanDTO1.setId(null);
        assertThat(taiKhoanDTO1).isNotEqualTo(taiKhoanDTO2);
    }
}
