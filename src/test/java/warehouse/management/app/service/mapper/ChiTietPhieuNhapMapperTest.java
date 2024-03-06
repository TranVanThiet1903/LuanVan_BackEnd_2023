package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChiTietPhieuNhapMapperTest {

    private ChiTietPhieuNhapMapper chiTietPhieuNhapMapper;

    @BeforeEach
    public void setUp() {
        chiTietPhieuNhapMapper = new ChiTietPhieuNhapMapperImpl();
    }
}
