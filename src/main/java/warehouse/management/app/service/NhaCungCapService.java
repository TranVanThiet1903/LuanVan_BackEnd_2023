package warehouse.management.app.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.NhaCungCap;
import warehouse.management.app.repository.NhaCungCapRepository;
import warehouse.management.app.service.dto.NhaCungCapDTO;
import warehouse.management.app.service.mapper.NhaCungCapMapper;

/**
 * Service Implementation for managing {@link NhaCungCap}.
 */
@Service
@Transactional
public class NhaCungCapService {

    private final Logger log = LoggerFactory.getLogger(NhaCungCapService.class);

    private final NhaCungCapRepository nhaCungCapRepository;

    private final NhaCungCapMapper nhaCungCapMapper;

    public NhaCungCapService(NhaCungCapRepository nhaCungCapRepository, NhaCungCapMapper nhaCungCapMapper) {
        this.nhaCungCapRepository = nhaCungCapRepository;
        this.nhaCungCapMapper = nhaCungCapMapper;
    }

    /**
     * Save a nhaCungCap.
     *
     * @param nhaCungCapDTO the entity to save.
     * @return the persisted entity.
     */
    public NhaCungCapDTO save(NhaCungCapDTO nhaCungCapDTO) {
        log.debug("Request to save NhaCungCap : {}", nhaCungCapDTO);
        NhaCungCap nhaCungCap = nhaCungCapMapper.toEntity(nhaCungCapDTO);
        nhaCungCap = nhaCungCapRepository.save(nhaCungCap);
        return nhaCungCapMapper.toDto(nhaCungCap);
    }

    /**
     * Update a nhaCungCap.
     *
     * @param nhaCungCapDTO the entity to save.
     * @return the persisted entity.
     */
    public NhaCungCapDTO update(NhaCungCapDTO nhaCungCapDTO) {
        log.debug("Request to update NhaCungCap : {}", nhaCungCapDTO);
        NhaCungCap nhaCungCap = nhaCungCapMapper.toEntity(nhaCungCapDTO);
        nhaCungCap = nhaCungCapRepository.save(nhaCungCap);
        return nhaCungCapMapper.toDto(nhaCungCap);
    }

    /**
     * Partially update a nhaCungCap.
     *
     * @param nhaCungCapDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NhaCungCapDTO> partialUpdate(NhaCungCapDTO nhaCungCapDTO) {
        log.debug("Request to partially update NhaCungCap : {}", nhaCungCapDTO);

        return nhaCungCapRepository
            .findById(nhaCungCapDTO.getId())
            .map(existingNhaCungCap -> {
                nhaCungCapMapper.partialUpdate(existingNhaCungCap, nhaCungCapDTO);

                return existingNhaCungCap;
            })
            .map(nhaCungCapRepository::save)
            .map(nhaCungCapMapper::toDto);
    }

    /**
     * Get all the nhaCungCaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NhaCungCapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NhaCungCaps");
        return nhaCungCapRepository.findAll(pageable).map(nhaCungCapMapper::toDto);
    }

    /**
     * Get one nhaCungCap by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NhaCungCapDTO> findOne(Long id) {
        log.debug("Request to get NhaCungCap : {}", id);
        return nhaCungCapRepository.findById(id).map(nhaCungCapMapper::toDto);
    }

    /**
     * Delete the nhaCungCap by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NhaCungCap : {}", id);
        nhaCungCapRepository.deleteById(id);
    }
}
