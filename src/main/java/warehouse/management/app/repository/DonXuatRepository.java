package warehouse.management.app.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.DonXuat;

/**
 * Spring Data JPA repository for the DonXuat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonXuatRepository extends JpaRepository<DonXuat, Long> {}
