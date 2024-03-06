package warehouse.management.app.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.ChiTietPhieuNhap;
import warehouse.management.app.repository.ChiTietPhieuNhapRepository;
import warehouse.management.app.service.dto.ChiTietPhieuNhapDTO;
import warehouse.management.app.service.mapper.ChiTietPhieuNhapMapper;

/**
 * Service Implementation for managing {@link ChiTietPhieuNhap}.
 */
@Service
@Transactional
public class ChiTietPhieuNhapService {

    private final Logger log = LoggerFactory.getLogger(ChiTietPhieuNhapService.class);

    private final ChiTietPhieuNhapRepository chiTietPhieuNhapRepository;

    private final ChiTietPhieuNhapMapper chiTietPhieuNhapMapper;

    public ChiTietPhieuNhapService(ChiTietPhieuNhapRepository chiTietPhieuNhapRepository, ChiTietPhieuNhapMapper chiTietPhieuNhapMapper) {
        this.chiTietPhieuNhapRepository = chiTietPhieuNhapRepository;
        this.chiTietPhieuNhapMapper = chiTietPhieuNhapMapper;
    }

    /**
     * Save a chiTietPhieuNhap.
     *
     * @param chiTietPhieuNhapDTO the entity to save.
     * @return the persisted entity.
     */
    public ChiTietPhieuNhapDTO save(ChiTietPhieuNhapDTO chiTietPhieuNhapDTO) {
        log.debug("Request to save ChiTietPhieuNhap : {}", chiTietPhieuNhapDTO);
        ChiTietPhieuNhap chiTietPhieuNhap = chiTietPhieuNhapMapper.toEntity(chiTietPhieuNhapDTO);
        chiTietPhieuNhap = chiTietPhieuNhapRepository.save(chiTietPhieuNhap);
        return chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);
    }

    /**
     * Update a chiTietPhieuNhap.
     *
     * @param chiTietPhieuNhapDTO the entity to save.
     * @return the persisted entity.
     */
    public ChiTietPhieuNhapDTO update(ChiTietPhieuNhapDTO chiTietPhieuNhapDTO) {
        log.debug("Request to update ChiTietPhieuNhap : {}", chiTietPhieuNhapDTO);
        ChiTietPhieuNhap chiTietPhieuNhap = chiTietPhieuNhapMapper.toEntity(chiTietPhieuNhapDTO);
        chiTietPhieuNhap = chiTietPhieuNhapRepository.save(chiTietPhieuNhap);
        return chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);
    }

    /**
     * Partially update a chiTietPhieuNhap.
     *
     * @param chiTietPhieuNhapDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChiTietPhieuNhapDTO> partialUpdate(ChiTietPhieuNhapDTO chiTietPhieuNhapDTO) {
        log.debug("Request to partially update ChiTietPhieuNhap : {}", chiTietPhieuNhapDTO);

        return chiTietPhieuNhapRepository
            .findById(chiTietPhieuNhapDTO.getId())
            .map(existingChiTietPhieuNhap -> {
                chiTietPhieuNhapMapper.partialUpdate(existingChiTietPhieuNhap, chiTietPhieuNhapDTO);

                return existingChiTietPhieuNhap;
            })
            .map(chiTietPhieuNhapRepository::save)
            .map(chiTietPhieuNhapMapper::toDto);
    }

    /**
     * Get all the chiTietPhieuNhaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChiTietPhieuNhapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChiTietPhieuNhaps");
        return chiTietPhieuNhapRepository.findAll(pageable).map(chiTietPhieuNhapMapper::toDto);
    }

    /**
     * Get one chiTietPhieuNhap by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChiTietPhieuNhapDTO> findOne(Long id) {
        log.debug("Request to get ChiTietPhieuNhap : {}", id);
        return chiTietPhieuNhapRepository.findById(id).map(chiTietPhieuNhapMapper::toDto);
    }

    /**
     * Delete the chiTietPhieuNhap by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChiTietPhieuNhap : {}", id);
        chiTietPhieuNhapRepository.deleteById(id);
    }

    public List<ChiTietPhieuNhap> findByPhieuNhapId(Long id) {
        return chiTietPhieuNhapRepository.findByPhieuNhapId(id);
    }
}
