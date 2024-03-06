package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChiTietDonXuatMapperTest {

    private ChiTietDonXuatMapper chiTietDonXuatMapper;

    @BeforeEach
    public void setUp() {
        chiTietDonXuatMapper = new ChiTietDonXuatMapperImpl();
    }
}
