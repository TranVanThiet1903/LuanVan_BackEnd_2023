package warehouse.management.app.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.PhieuNhap;
import warehouse.management.app.repository.PhieuNhapRepository;
import warehouse.management.app.service.dto.PhieuNhapDTO;
import warehouse.management.app.service.mapper.PhieuNhapMapper;

/**
 * Service Implementation for managing {@link PhieuNhap}.
 */
@Service
@Transactional
public class PhieuNhapService {

    private final Logger log = LoggerFactory.getLogger(PhieuNhapService.class);

    private final PhieuNhapRepository phieuNhapRepository;

    private final PhieuNhapMapper phieuNhapMapper;

    public PhieuNhapService(PhieuNhapRepository phieuNhapRepository, PhieuNhapMapper phieuNhapMapper) {
        this.phieuNhapRepository = phieuNhapRepository;
        this.phieuNhapMapper = phieuNhapMapper;
    }

    /**
     * Save a phieuNhap.
     *
     * @param phieuNhapDTO the entity to save.
     * @return the persisted entity.
     */
    public PhieuNhapDTO save(PhieuNhapDTO phieuNhapDTO) {
        log.debug("Request to save PhieuNhap : {}", phieuNhapDTO);
        PhieuNhap phieuNhap = phieuNhapMapper.toEntity(phieuNhapDTO);
        phieuNhap = phieuNhapRepository.save(phieuNhap);
        return phieuNhapMapper.toDto(phieuNhap);
    }

    /**
     * Update a phieuNhap.
     *
     * @param phieuNhapDTO the entity to save.
     * @return the persisted entity.
     */
    public PhieuNhapDTO update(PhieuNhapDTO phieuNhapDTO) {
        log.debug("Request to update PhieuNhap : {}", phieuNhapDTO);
        PhieuNhap phieuNhap = phieuNhapMapper.toEntity(phieuNhapDTO);
        phieuNhap = phieuNhapRepository.save(phieuNhap);
        return phieuNhapMapper.toDto(phieuNhap);
    }

    /**
     * Partially update a phieuNhap.
     *
     * @param phieuNhapDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PhieuNhapDTO> partialUpdate(PhieuNhapDTO phieuNhapDTO) {
        log.debug("Request to partially update PhieuNhap : {}", phieuNhapDTO);

        return phieuNhapRepository
            .findById(phieuNhapDTO.getId())
            .map(existingPhieuNhap -> {
                phieuNhapMapper.partialUpdate(existingPhieuNhap, phieuNhapDTO);

                return existingPhieuNhap;
            })
            .map(phieuNhapRepository::save)
            .map(phieuNhapMapper::toDto);
    }

    /**
     * Get all the phieuNhaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PhieuNhapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PhieuNhaps");
        return phieuNhapRepository.findAll(pageable).map(phieuNhapMapper::toDto);
    }

    /**
     * Get one phieuNhap by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PhieuNhapDTO> findOne(Long id) {
        log.debug("Request to get PhieuNhap : {}", id);
        return phieuNhapRepository.findById(id).map(phieuNhapMapper::toDto);
    }

    /**
     * Delete the phieuNhap by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PhieuNhap : {}", id);
        phieuNhapRepository.deleteById(id);
    }
}
