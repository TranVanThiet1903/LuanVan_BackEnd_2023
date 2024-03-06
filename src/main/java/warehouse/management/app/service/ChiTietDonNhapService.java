package warehouse.management.app.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.ChiTietDonNhap;
import warehouse.management.app.repository.ChiTietDonNhapRepository;
import warehouse.management.app.service.dto.ChiTietDonNhapDTO;
import warehouse.management.app.service.mapper.ChiTietDonNhapMapper;

/**
 * Service Implementation for managing {@link ChiTietDonNhap}.
 */
@Service
@Transactional
public class ChiTietDonNhapService {

    private final Logger log = LoggerFactory.getLogger(ChiTietDonNhapService.class);

    private final ChiTietDonNhapRepository chiTietDonNhapRepository;

    private final ChiTietDonNhapMapper chiTietDonNhapMapper;

    public ChiTietDonNhapService(ChiTietDonNhapRepository chiTietDonNhapRepository, ChiTietDonNhapMapper chiTietDonNhapMapper) {
        this.chiTietDonNhapRepository = chiTietDonNhapRepository;
        this.chiTietDonNhapMapper = chiTietDonNhapMapper;
    }

    /**
     * Save a chiTietDonNhap.
     *
     * @param chiTietDonNhapDTO the entity to save.
     * @return the persisted entity.
     */
    public ChiTietDonNhapDTO save(ChiTietDonNhapDTO chiTietDonNhapDTO) {
        log.debug("Request to save ChiTietDonNhap : {}", chiTietDonNhapDTO);
        ChiTietDonNhap chiTietDonNhap = chiTietDonNhapMapper.toEntity(chiTietDonNhapDTO);
        chiTietDonNhap = chiTietDonNhapRepository.save(chiTietDonNhap);
        return chiTietDonNhapMapper.toDto(chiTietDonNhap);
    }

    /**
     * Update a chiTietDonNhap.
     *
     * @param chiTietDonNhapDTO the entity to save.
     * @return the persisted entity.
     */
    public ChiTietDonNhapDTO update(ChiTietDonNhapDTO chiTietDonNhapDTO) {
        log.debug("Request to update ChiTietDonNhap : {}", chiTietDonNhapDTO);
        ChiTietDonNhap chiTietDonNhap = chiTietDonNhapMapper.toEntity(chiTietDonNhapDTO);
        chiTietDonNhap = chiTietDonNhapRepository.save(chiTietDonNhap);
        return chiTietDonNhapMapper.toDto(chiTietDonNhap);
    }

    /**
     * Partially update a chiTietDonNhap.
     *
     * @param chiTietDonNhapDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChiTietDonNhapDTO> partialUpdate(ChiTietDonNhapDTO chiTietDonNhapDTO) {
        log.debug("Request to partially update ChiTietDonNhap : {}", chiTietDonNhapDTO);

        return chiTietDonNhapRepository
            .findById(chiTietDonNhapDTO.getId())
            .map(existingChiTietDonNhap -> {
                chiTietDonNhapMapper.partialUpdate(existingChiTietDonNhap, chiTietDonNhapDTO);

                return existingChiTietDonNhap;
            })
            .map(chiTietDonNhapRepository::save)
            .map(chiTietDonNhapMapper::toDto);
    }

    /**
     * Get all the chiTietDonNhaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChiTietDonNhapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChiTietDonNhaps");
        return chiTietDonNhapRepository.findAll(pageable).map(chiTietDonNhapMapper::toDto);
    }

    /**
     * Get one chiTietDonNhap by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChiTietDonNhapDTO> findOne(Long id) {
        log.debug("Request to get ChiTietDonNhap : {}", id);
        return chiTietDonNhapRepository.findById(id).map(chiTietDonNhapMapper::toDto);
    }

    /**
     * Delete the chiTietDonNhap by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChiTietDonNhap : {}", id);
        chiTietDonNhapRepository.deleteById(id);
    }

    public List<ChiTietDonNhap> findByDonNhapId(Long id) {
        return chiTietDonNhapRepository.findByDonNhapId(id);
    }
}
