package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaiKhoanMapperTest {

    private TaiKhoanMapper taiKhoanMapper;

    @BeforeEach
    public void setUp() {
        taiKhoanMapper = new TaiKhoanMapperImpl();
    }
}
