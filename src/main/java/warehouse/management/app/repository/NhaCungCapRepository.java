package warehouse.management.app.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.NhaCungCap;

/**
 * Spring Data JPA repository for the NhaCungCap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, Long> {}
