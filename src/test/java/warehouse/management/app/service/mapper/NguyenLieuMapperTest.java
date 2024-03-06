package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NguyenLieuMapperTest {

    private NguyenLieuMapper nguyenLieuMapper;

    @BeforeEach
    public void setUp() {
        nguyenLieuMapper = new NguyenLieuMapperImpl();
    }
}
