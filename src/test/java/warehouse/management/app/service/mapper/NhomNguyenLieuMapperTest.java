package warehouse.management.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NhomNguyenLieuMapperTest {

    private NhomNguyenLieuMapper nhomNguyenLieuMapper;

    @BeforeEach
    public void setUp() {
        nhomNguyenLieuMapper = new NhomNguyenLieuMapperImpl();
    }
}
