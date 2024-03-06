package warehouse.management.app.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.ChiTietKho;
import warehouse.management.app.domain.NhaKho;
import warehouse.management.app.repository.ChiTietKhoRepository;
import warehouse.management.app.service.dto.ChiTietKhoDTO;
import warehouse.management.app.service.dto.NhaKhoDTO;
import warehouse.management.app.service.mapper.ChiTietKhoMapper;

/**
 * Service Implementation for managing {@link ChiTietKho}.
 */
@Service
@Transactional
public class ChiTietKhoService {

    private final Logger log = LoggerFactory.getLogger(ChiTietKhoService.class);

    private final ChiTietKhoRepository chiTietKhoRepository;

    private final ChiTietKhoMapper chiTietKhoMapper;

    public ChiTietKhoService(ChiTietKhoRepository chiTietKhoRepository, ChiTietKhoMapper chiTietKhoMapper) {
        this.chiTietKhoRepository = chiTietKhoRepository;
        this.chiTietKhoMapper = chiTietKhoMapper;
    }

    public List<ChiTietKho> findByNhaKhoId(Long nhaKhoId) {
        return chiTietKhoRepository.findByNhaKhoId(nhaKhoId);
    }

    /**
     * Save a chiTietKho.
     *
     * @param chiTietKhoDTO the entity to save.
     * @return the persisted entity.
     */
    public ChiTietKhoDTO save(ChiTietKhoDTO chiTietKhoDTO) {
        log.debug("Request to save ChiTietKho : {}", chiTietKhoDTO);
        ChiTietKho chiTietKho = chiTietKhoMapper.toEntity(chiTietKhoDTO);
        chiTietKho = chiTietKhoRepository.save(chiTietKho);
        return chiTietKhoMapper.toDto(chiTietKho);
    }

    /**
     * Update a chiTietKho.
     *
     * @param chiTietKhoDTO the entity to save.
     * @return the persisted entity.
     */
    public ChiTietKhoDTO update(ChiTietKhoDTO chiTietKhoDTO) {
        log.debug("Request to update ChiTietKho : {}", chiTietKhoDTO);
        ChiTietKho chiTietKho = chiTietKhoMapper.toEntity(chiTietKhoDTO);
        chiTietKho = chiTietKhoRepository.save(chiTietKho);
        return chiTietKhoMapper.toDto(chiTietKho);
    }

    /**
     * Partially update a chiTietKho.
     *
     * @param chiTietKhoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChiTietKhoDTO> partialUpdate(ChiTietKhoDTO chiTietKhoDTO) {
        log.debug("Request to partially update ChiTietKho : {}", chiTietKhoDTO);

        return chiTietKhoRepository
            .findById(chiTietKhoDTO.getId())
            .map(existingChiTietKho -> {
                chiTietKhoMapper.partialUpdate(existingChiTietKho, chiTietKhoDTO);

                return existingChiTietKho;
            })
            .map(chiTietKhoRepository::save)
            .map(chiTietKhoMapper::toDto);
    }

    /**
     * Get all the chiTietKhos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChiTietKhoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChiTietKhos");
        return chiTietKhoRepository.findAll(pageable).map(chiTietKhoMapper::toDto);
    }

    /**
     * Get one chiTietKho by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChiTietKhoDTO> findOne(Long id) {
        log.debug("Request to get ChiTietKho : {}", id);
        return chiTietKhoRepository.findById(id).map(chiTietKhoMapper::toDto);
    }

    /**
     * Delete the chiTietKho by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChiTietKho : {}", id);
        chiTietKhoRepository.deleteById(id);
    }

    public ChiTietKho findByNguyenLieuId(Long id) {
        return chiTietKhoRepository.findByNguyenLieuId(id);
    }

    public ChiTietKho findByNguyenLieuIdAndNhaKhoId(Long id, Long id1) {
        return chiTietKhoRepository.findByNguyenLieuIdAndNhaKhoId(id, id1);
    }
}
