package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhieuNhapMapperTest {

    private PhieuNhapMapper phieuNhapMapper;

    @BeforeEach
    public void setUp() {
        phieuNhapMapper = new PhieuNhapMapperImpl();
    }
}
