package warehouse.management.app.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import warehouse.management.app.domain.ChiTietKho;
import warehouse.management.app.service.dto.ChiTietKhoDTO;
import warehouse.management.app.service.dto.NhaKhoDTO;

/**
 * Spring Data JPA repository for the ChiTietKho entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChiTietKhoRepository extends JpaRepository<ChiTietKho, Long> {
    List<ChiTietKho> findByNhaKhoId(Long nhaKhoId);

    ChiTietKho findByNguyenLieuId(Long id);

    ChiTietKho findByNguyenLieuIdAndNhaKhoId(Long id, Long id1);
}
