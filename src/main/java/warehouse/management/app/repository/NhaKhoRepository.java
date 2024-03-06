package warehouse.management.app.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.NhaKho;

/**
 * Spring Data JPA repository for the NhaKho entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhaKhoRepository extends JpaRepository<NhaKho, Long> {}
