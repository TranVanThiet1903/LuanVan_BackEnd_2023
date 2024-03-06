package warehouse.management.app.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.PhieuNhap;

/**
 * Spring Data JPA repository for the PhieuNhap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhieuNhapRepository extends JpaRepository<PhieuNhap, Long> {}
