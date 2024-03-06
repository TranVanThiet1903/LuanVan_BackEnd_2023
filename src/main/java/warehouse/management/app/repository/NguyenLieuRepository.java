package warehouse.management.app.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.NguyenLieu;

/**
 * Spring Data JPA repository for the NguyenLieu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NguyenLieuRepository extends JpaRepository<NguyenLieu, Long> {}
