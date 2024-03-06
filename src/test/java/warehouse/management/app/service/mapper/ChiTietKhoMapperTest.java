package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChiTietKhoMapperTest {

    private ChiTietKhoMapper chiTietKhoMapper;

    @BeforeEach
    public void setUp() {
        chiTietKhoMapper = new ChiTietKhoMapperImpl();
    }
}
