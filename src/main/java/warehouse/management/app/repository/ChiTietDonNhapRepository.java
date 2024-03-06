package warehouse.management.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.ChiTietDonNhap;

/**
 * Spring Data JPA repository for the ChiTietDonNhap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChiTietDonNhapRepository extends JpaRepository<ChiTietDonNhap, Long> {
    List<ChiTietDonNhap> findByDonNhapId(Long id);
}
