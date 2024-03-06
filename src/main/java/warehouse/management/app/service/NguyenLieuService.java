package warehouse.management.app.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.NguyenLieu;
import warehouse.management.app.repository.NguyenLieuRepository;
import warehouse.management.app.service.dto.NguyenLieuDTO;
import warehouse.management.app.service.mapper.NguyenLieuMapper;

/**
 * Service Implementation for managing {@link NguyenLieu}.
 */
@Service
@Transactional
public class NguyenLieuService {

    private final Logger log = LoggerFactory.getLogger(NguyenLieuService.class);

    private final NguyenLieuRepository nguyenLieuRepository;

    private final NguyenLieuMapper nguyenLieuMapper;

    public NguyenLieuService(NguyenLieuRepository nguyenLieuRepository, NguyenLieuMapper nguyenLieuMapper) {
        this.nguyenLieuRepository = nguyenLieuRepository;
        this.nguyenLieuMapper = nguyenLieuMapper;
    }

    /**
     * Save a nguyenLieu.
     *
     * @param nguyenLieuDTO the entity to save.
     * @return the persisted entity.
     */
    public NguyenLieuDTO save(NguyenLieuDTO nguyenLieuDTO) {
        log.debug("Request to save NguyenLieu : {}", nguyenLieuDTO);
        NguyenLieu nguyenLieu = nguyenLieuMapper.toEntity(nguyenLieuDTO);
        nguyenLieu = nguyenLieuRepository.save(nguyenLieu);
        return nguyenLieuMapper.toDto(nguyenLieu);
    }

    /**
     * Update a nguyenLieu.
     *
     * @param nguyenLieuDTO the entity to save.
     * @return the persisted entity.
     */
    public NguyenLieuDTO update(NguyenLieuDTO nguyenLieuDTO) {
        log.debug("Request to update NguyenLieu : {}", nguyenLieuDTO);
        NguyenLieu nguyenLieu = nguyenLieuMapper.toEntity(nguyenLieuDTO);
        nguyenLieu = nguyenLieuRepository.save(nguyenLieu);
        return nguyenLieuMapper.toDto(nguyenLieu);
    }

    /**
     * Partially update a nguyenLieu.
     *
     * @param nguyenLieuDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NguyenLieuDTO> partialUpdate(NguyenLieuDTO nguyenLieuDTO) {
        log.debug("Request to partially update NguyenLieu : {}", nguyenLieuDTO);

        return nguyenLieuRepository
            .findById(nguyenLieuDTO.getId())
            .map(existingNguyenLieu -> {
                nguyenLieuMapper.partialUpdate(existingNguyenLieu, nguyenLieuDTO);

                return existingNguyenLieu;
            })
            .map(nguyenLieuRepository::save)
            .map(nguyenLieuMapper::toDto);
    }

    /**
     * Get all the nguyenLieus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NguyenLieuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NguyenLieus");
        return nguyenLieuRepository.findAll(pageable).map(nguyenLieuMapper::toDto);
    }

    /**
     * Get one nguyenLieu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NguyenLieuDTO> findOne(Long id) {
        log.debug("Request to get NguyenLieu : {}", id);
        return nguyenLieuRepository.findById(id).map(nguyenLieuMapper::toDto);
    }

    /**
     * Delete the nguyenLieu by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NguyenLieu : {}", id);
        nguyenLieuRepository.deleteById(id);
    }
}
