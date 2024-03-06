package warehouse.management.app.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.NhomNguyenLieu;

/**
 * Spring Data JPA repository for the NhomNguyenLieu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhomNguyenLieuRepository extends JpaRepository<NhomNguyenLieu, Long> {}
