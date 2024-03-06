package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonXuatMapperTest {

    private DonXuatMapper donXuatMapper;

    @BeforeEach
    public void setUp() {
        donXuatMapper = new DonXuatMapperImpl();
    }
}
