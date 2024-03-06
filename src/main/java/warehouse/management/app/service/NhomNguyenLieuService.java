package warehouse.management.app.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.NhomNguyenLieu;
import warehouse.management.app.repository.NhomNguyenLieuRepository;
import warehouse.management.app.service.dto.NhomNguyenLieuDTO;
import warehouse.management.app.service.mapper.NhomNguyenLieuMapper;

/**
 * Service Implementation for managing {@link NhomNguyenLieu}.
 */
@Service
@Transactional
public class NhomNguyenLieuService {

    private final Logger log = LoggerFactory.getLogger(NhomNguyenLieuService.class);

    private final NhomNguyenLieuRepository nhomNguyenLieuRepository;

    private final NhomNguyenLieuMapper nhomNguyenLieuMapper;

    public NhomNguyenLieuService(NhomNguyenLieuRepository nhomNguyenLieuRepository, NhomNguyenLieuMapper nhomNguyenLieuMapper) {
        this.nhomNguyenLieuRepository = nhomNguyenLieuRepository;
        this.nhomNguyenLieuMapper = nhomNguyenLieuMapper;
    }

    /**
     * Save a nhomNguyenLieu.
     *
     * @param nhomNguyenLieuDTO the entity to save.
     * @return the persisted entity.
     */
    public NhomNguyenLieuDTO save(NhomNguyenLieuDTO nhomNguyenLieuDTO) {
        log.debug("Request to save NhomNguyenLieu : {}", nhomNguyenLieuDTO);
        NhomNguyenLieu nhomNguyenLieu = nhomNguyenLieuMapper.toEntity(nhomNguyenLieuDTO);
        nhomNguyenLieu = nhomNguyenLieuRepository.save(nhomNguyenLieu);
        return nhomNguyenLieuMapper.toDto(nhomNguyenLieu);
    }

    /**
     * Update a nhomNguyenLieu.
     *
     * @param nhomNguyenLieuDTO the entity to save.
     * @return the persisted entity.
     */
    public NhomNguyenLieuDTO update(NhomNguyenLieuDTO nhomNguyenLieuDTO) {
        log.debug("Request to update NhomNguyenLieu : {}", nhomNguyenLieuDTO);
        NhomNguyenLieu nhomNguyenLieu = nhomNguyenLieuMapper.toEntity(nhomNguyenLieuDTO);
        nhomNguyenLieu = nhomNguyenLieuRepository.save(nhomNguyenLieu);
        return nhomNguyenLieuMapper.toDto(nhomNguyenLieu);
    }

    /**
     * Partially update a nhomNguyenLieu.
     *
     * @param nhomNguyenLieuDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NhomNguyenLieuDTO> partialUpdate(NhomNguyenLieuDTO nhomNguyenLieuDTO) {
        log.debug("Request to partially update NhomNguyenLieu : {}", nhomNguyenLieuDTO);

        return nhomNguyenLieuRepository
            .findById(nhomNguyenLieuDTO.getId())
            .map(existingNhomNguyenLieu -> {
                nhomNguyenLieuMapper.partialUpdate(existingNhomNguyenLieu, nhomNguyenLieuDTO);

                return existingNhomNguyenLieu;
            })
            .map(nhomNguyenLieuRepository::save)
            .map(nhomNguyenLieuMapper::toDto);
    }

    /**
     * Get all the nhomNguyenLieus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NhomNguyenLieuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NhomNguyenLieus");
        return nhomNguyenLieuRepository.findAll(pageable).map(nhomNguyenLieuMapper::toDto);
    }

    /**
     * Get one nhomNguyenLieu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NhomNguyenLieuDTO> findOne(Long id) {
        log.debug("Request to get NhomNguyenLieu : {}", id);
        return nhomNguyenLieuRepository.findById(id).map(nhomNguyenLieuMapper::toDto);
    }

    /**
     * Delete the nhomNguyenLieu by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NhomNguyenLieu : {}", id);
        nhomNguyenLieuRepository.deleteById(id);
    }
}
