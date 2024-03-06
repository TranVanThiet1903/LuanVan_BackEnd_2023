package warehouse.management.app.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.DonNhap;

/**
 * Spring Data JPA repository for the DonNhap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonNhapRepository extends JpaRepository<DonNhap, Long> {}
