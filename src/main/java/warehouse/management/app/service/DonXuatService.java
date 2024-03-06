package warehouse.management.app.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.DonXuat;
import warehouse.management.app.repository.DonXuatRepository;
import warehouse.management.app.service.dto.DonXuatDTO;
import warehouse.management.app.service.mapper.DonXuatMapper;

/**
 * Service Implementation for managing {@link DonXuat}.
 */
@Service
@Transactional
public class DonXuatService {

    private final Logger log = LoggerFactory.getLogger(DonXuatService.class);

    private final DonXuatRepository donXuatRepository;

    private final DonXuatMapper donXuatMapper;

    public DonXuatService(DonXuatRepository donXuatRepository, DonXuatMapper donXuatMapper) {
        this.donXuatRepository = donXuatRepository;
        this.donXuatMapper = donXuatMapper;
    }

    /**
     * Save a donXuat.
     *
     * @param donXuatDTO the entity to save.
     * @return the persisted entity.
     */
    public DonXuatDTO save(DonXuatDTO donXuatDTO) {
        log.debug("Request to save DonXuat : {}", donXuatDTO);
        DonXuat donXuat = donXuatMapper.toEntity(donXuatDTO);
        donXuat = donXuatRepository.save(donXuat);
        return donXuatMapper.toDto(donXuat);
    }

    /**
     * Update a donXuat.
     *
     * @param donXuatDTO the entity to save.
     * @return the persisted entity.
     */
    public DonXuatDTO update(DonXuatDTO donXuatDTO) {
        log.debug("Request to update DonXuat : {}", donXuatDTO);
        DonXuat donXuat = donXuatMapper.toEntity(donXuatDTO);
        donXuat = donXuatRepository.save(donXuat);
        return donXuatMapper.toDto(donXuat);
    }

    /**
     * Partially update a donXuat.
     *
     * @param donXuatDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DonXuatDTO> partialUpdate(DonXuatDTO donXuatDTO) {
        log.debug("Request to partially update DonXuat : {}", donXuatDTO);

        return donXuatRepository
            .findById(donXuatDTO.getId())
            .map(existingDonXuat -> {
                donXuatMapper.partialUpdate(existingDonXuat, donXuatDTO);

                return existingDonXuat;
            })
            .map(donXuatRepository::save)
            .map(donXuatMapper::toDto);
    }

    /**
     * Get all the donXuats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DonXuatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DonXuats");
        return donXuatRepository.findAll(pageable).map(donXuatMapper::toDto);
    }

    /**
     * Get one donXuat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DonXuatDTO> findOne(Long id) {
        log.debug("Request to get DonXuat : {}", id);
        return donXuatRepository.findById(id).map(donXuatMapper::toDto);
    }

    /**
     * Delete the donXuat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DonXuat : {}", id);
        donXuatRepository.deleteById(id);
    }
}
