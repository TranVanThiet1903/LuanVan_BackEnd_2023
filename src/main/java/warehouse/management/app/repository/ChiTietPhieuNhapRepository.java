package warehouse.management.app.repository;

import io.netty.util.AsyncMapping;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.ChiTietPhieuNhap;

/**
 * Spring Data JPA repository for the ChiTietPhieuNhap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChiTietPhieuNhapRepository extends JpaRepository<ChiTietPhieuNhap, Long> {
    List<ChiTietPhieuNhap> findByPhieuNhapId(Long phieuNhapId);
}
