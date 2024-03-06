package warehouse.management.app.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.ChiTietDonXuat;
import warehouse.management.app.repository.ChiTietDonXuatRepository;
import warehouse.management.app.service.dto.ChiTietDonXuatDTO;
import warehouse.management.app.service.mapper.ChiTietDonXuatMapper;

/**
 * Service Implementation for managing {@link ChiTietDonXuat}.
 */
@Service
@Transactional
public class ChiTietDonXuatService {

    private final Logger log = LoggerFactory.getLogger(ChiTietDonXuatService.class);

    private final ChiTietDonXuatRepository chiTietDonXuatRepository;

    private final ChiTietDonXuatMapper chiTietDonXuatMapper;

    public ChiTietDonXuatService(ChiTietDonXuatRepository chiTietDonXuatRepository, ChiTietDonXuatMapper chiTietDonXuatMapper) {
        this.chiTietDonXuatRepository = chiTietDonXuatRepository;
        this.chiTietDonXuatMapper = chiTietDonXuatMapper;
    }

    /**
     * Save a chiTietDonXuat.
     *
     * @param chiTietDonXuatDTO the entity to save.
     * @return the persisted entity.
     */
    public ChiTietDonXuatDTO save(ChiTietDonXuatDTO chiTietDonXuatDTO) {
        log.debug("Request to save ChiTietDonXuat : {}", chiTietDonXuatDTO);
        ChiTietDonXuat chiTietDonXuat = chiTietDonXuatMapper.toEntity(chiTietDonXuatDTO);
        chiTietDonXuat = chiTietDonXuatRepository.save(chiTietDonXuat);
        return chiTietDonXuatMapper.toDto(chiTietDonXuat);
    }

    /**
     * Update a chiTietDonXuat.
     *
     * @param chiTietDonXuatDTO the entity to save.
     * @return the persisted entity.
     */
    public ChiTietDonXuatDTO update(ChiTietDonXuatDTO chiTietDonXuatDTO) {
        log.debug("Request to update ChiTietDonXuat : {}", chiTietDonXuatDTO);
        ChiTietDonXuat chiTietDonXuat = chiTietDonXuatMapper.toEntity(chiTietDonXuatDTO);
        chiTietDonXuat = chiTietDonXuatRepository.save(chiTietDonXuat);
        return chiTietDonXuatMapper.toDto(chiTietDonXuat);
    }

    /**
     * Partially update a chiTietDonXuat.
     *
     * @param chiTietDonXuatDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChiTietDonXuatDTO> partialUpdate(ChiTietDonXuatDTO chiTietDonXuatDTO) {
        log.debug("Request to partially update ChiTietDonXuat : {}", chiTietDonXuatDTO);

        return chiTietDonXuatRepository
            .findById(chiTietDonXuatDTO.getId())
            .map(existingChiTietDonXuat -> {
                chiTietDonXuatMapper.partialUpdate(existingChiTietDonXuat, chiTietDonXuatDTO);

                return existingChiTietDonXuat;
            })
            .map(chiTietDonXuatRepository::save)
            .map(chiTietDonXuatMapper::toDto);
    }

    /**
     * Get all the chiTietDonXuats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChiTietDonXuatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChiTietDonXuats");
        return chiTietDonXuatRepository.findAll(pageable).map(chiTietDonXuatMapper::toDto);
    }

    /**
     * Get one chiTietDonXuat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChiTietDonXuatDTO> findOne(Long id) {
        log.debug("Request to get ChiTietDonXuat : {}", id);
        return chiTietDonXuatRepository.findById(id).map(chiTietDonXuatMapper::toDto);
    }

    /**
     * Delete the chiTietDonXuat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChiTietDonXuat : {}", id);
        chiTietDonXuatRepository.deleteById(id);
    }

    public List<ChiTietDonXuat> findByDonXuatId(Long id) {
        return chiTietDonXuatRepository.findByDonXuatId(id);
    }
}
