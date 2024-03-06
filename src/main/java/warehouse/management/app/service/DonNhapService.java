package warehouse.management.app.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.DonNhap;
import warehouse.management.app.repository.DonNhapRepository;
import warehouse.management.app.service.dto.DonNhapDTO;
import warehouse.management.app.service.mapper.DonNhapMapper;

/**
 * Service Implementation for managing {@link DonNhap}.
 */
@Service
@Transactional
public class DonNhapService {

    private final Logger log = LoggerFactory.getLogger(DonNhapService.class);

    private final DonNhapRepository donNhapRepository;

    private final DonNhapMapper donNhapMapper;

    public DonNhapService(DonNhapRepository donNhapRepository, DonNhapMapper donNhapMapper) {
        this.donNhapRepository = donNhapRepository;
        this.donNhapMapper = donNhapMapper;
    }

    /**
     * Save a donNhap.
     *
     * @param donNhapDTO the entity to save.
     * @return the persisted entity.
     */
    public DonNhapDTO save(DonNhapDTO donNhapDTO) {
        log.debug("Request to save DonNhap : {}", donNhapDTO);
        DonNhap donNhap = donNhapMapper.toEntity(donNhapDTO);
        donNhap = donNhapRepository.save(donNhap);
        return donNhapMapper.toDto(donNhap);
    }

    /**
     * Update a donNhap.
     *
     * @param donNhapDTO the entity to save.
     * @return the persisted entity.
     */
    public DonNhapDTO update(DonNhapDTO donNhapDTO) {
        log.debug("Request to update DonNhap : {}", donNhapDTO);
        DonNhap donNhap = donNhapMapper.toEntity(donNhapDTO);
        donNhap = donNhapRepository.save(donNhap);
        return donNhapMapper.toDto(donNhap);
    }

    /**
     * Partially update a donNhap.
     *
     * @param donNhapDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DonNhapDTO> partialUpdate(DonNhapDTO donNhapDTO) {
        log.debug("Request to partially update DonNhap : {}", donNhapDTO);

        return donNhapRepository
            .findById(donNhapDTO.getId())
            .map(existingDonNhap -> {
                donNhapMapper.partialUpdate(existingDonNhap, donNhapDTO);

                return existingDonNhap;
            })
            .map(donNhapRepository::save)
            .map(donNhapMapper::toDto);
    }

    /**
     * Get all the donNhaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DonNhapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DonNhaps");
        return donNhapRepository.findAll(pageable).map(donNhapMapper::toDto);
    }

    /**
     * Get one donNhap by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DonNhapDTO> findOne(Long id) {
        log.debug("Request to get DonNhap : {}", id);
        return donNhapRepository.findById(id).map(donNhapMapper::toDto);
    }

    /**
     * Delete the donNhap by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DonNhap : {}", id);
        donNhapRepository.deleteById(id);
    }
}
