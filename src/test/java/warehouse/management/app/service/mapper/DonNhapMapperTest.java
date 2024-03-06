package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonNhapMapperTest {

    private DonNhapMapper donNhapMapper;

    @BeforeEach
    public void setUp() {
        donNhapMapper = new DonNhapMapperImpl();
    }
}
