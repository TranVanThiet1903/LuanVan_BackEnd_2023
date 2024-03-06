package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChiTietDonNhapMapperTest {

    private ChiTietDonNhapMapper chiTietDonNhapMapper;

    @BeforeEach
    public void setUp() {
        chiTietDonNhapMapper = new ChiTietDonNhapMapperImpl();
    }
}
