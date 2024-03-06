package warehouse.management.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.ChiTietDonXuat;

/**
 * Spring Data JPA repository for the ChiTietDonXuat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChiTietDonXuatRepository extends JpaRepository<ChiTietDonXuat, Long> {
    List<ChiTietDonXuat> findByDonXuatId(Long id);
}
