package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NhaKhoMapperTest {

    private NhaKhoMapper nhaKhoMapper;

    @BeforeEach
    public void setUp() {
        nhaKhoMapper = new NhaKhoMapperImpl();
    }
}
