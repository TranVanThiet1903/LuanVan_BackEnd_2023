package warehouse.management.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.ChiTietKho;
import warehouse.management.app.domain.NhaKho;
import warehouse.management.app.repository.NhaKhoRepository;
import warehouse.management.app.service.dto.ChiTietKhoDTO;
import warehouse.management.app.service.dto.NguyenLieuDTO;
import warehouse.management.app.service.dto.NhaKhoDTO;
import warehouse.management.app.service.mapper.NhaKhoMapper;

/**
 * Service Implementation for managing {@link NhaKho}.
 */
@Service
@Transactional
public class NhaKhoService {

    private final Logger log = LoggerFactory.getLogger(NhaKhoService.class);

    private final NhaKhoRepository nhaKhoRepository;

    private final NhaKhoMapper nhaKhoMapper;

    private final ChiTietKhoService chiTietKhoService;

    public NhaKhoService(NhaKhoRepository nhaKhoRepository, NhaKhoMapper nhaKhoMapper, ChiTietKhoService chiTietKhoService) {
        this.nhaKhoRepository = nhaKhoRepository;
        this.nhaKhoMapper = nhaKhoMapper;
        this.chiTietKhoService = chiTietKhoService;
    }

    /**
     * Save a nhaKho.
     *
     * @param nhaKhoDTO the entity to save.
     * @return the persisted entity.
     */
    public NhaKhoDTO save(NhaKhoDTO nhaKhoDTO) {
        log.debug("Request to save NhaKho : {}", nhaKhoDTO);
        NhaKho nhaKho = nhaKhoMapper.toEntity(nhaKhoDTO);
        nhaKho = nhaKhoRepository.save(nhaKho);
        return nhaKhoMapper.toDto(nhaKho);
    }

    /**
     * Update a nhaKho.
     *
     * @param nhaKhoDTO the entity to save.
     * @return the persisted entity.
     */
    public NhaKhoDTO update(NhaKhoDTO nhaKhoDTO) {
        log.debug("Request to update NhaKho : {}", nhaKhoDTO);
        NhaKho nhaKho = nhaKhoMapper.toEntity(nhaKhoDTO);
        nhaKho = nhaKhoRepository.save(nhaKho);
        return nhaKhoMapper.toDto(nhaKho);
    }

    /**
     * Partially update a nhaKho.
     *
     * @param nhaKhoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NhaKhoDTO> partialUpdate(NhaKhoDTO nhaKhoDTO) {
        log.debug("Request to partially update NhaKho : {}", nhaKhoDTO);

        return nhaKhoRepository
            .findById(nhaKhoDTO.getId())
            .map(existingNhaKho -> {
                nhaKhoMapper.partialUpdate(existingNhaKho, nhaKhoDTO);

                return existingNhaKho;
            })
            .map(nhaKhoRepository::save)
            .map(nhaKhoMapper::toDto);
    }

    /**
     * Get all the nhaKhos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NhaKhoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NhaKhos");
        return nhaKhoRepository.findAll(pageable).map(nhaKhoMapper::toDto);
    }

    /**
     * Get one nhaKho by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NhaKhoDTO> findOne(Long id) {
        log.debug("Request to get NhaKho : {}", id);
        return nhaKhoRepository.findById(id).map(nhaKhoMapper::toDto);
    }

    /**
     * Delete the nhaKho by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NhaKho : {}", id);
        nhaKhoRepository.deleteById(id);
    }
    //    public NhaKhoDTO findOneWithChiTietKho(Long id) {
    //        NhaKho nhaKho = nhaKhoRepository.findById(id).orElse(null);
    //        if (nhaKho == null) {
    //            // Xử lý trường hợp không tìm thấy NhaKho
    //            return null;
    //        }
    //
    //        List<ChiTietKho> danhSachChiTietKho = chiTietKhoService.findByNhaKhoId(id);
    //        List<ChiTietKhoDTO> danhSachChiTietKhoDTO = danhSachChiTietKho.stream()
    //            .map(this::convertToChiTietKhoDTO)
    //            .collect(Collectors.toList());
    //
    //        NhaKhoDTO nhaKhoDTO = new NhaKhoDTO();
    //        nhaKhoDTO.setId(nhaKho.getId());
    //        nhaKhoDTO.setTenKho(nhaKho.getTenKho());
    //        nhaKhoDTO.setDiaChi(nhaKho.getDiaChi());
    //        nhaKhoDTO.setLoai(nhaKho.getLoai());
    //        nhaKhoDTO.setNgayTao(nhaKho.getNgayTao());
    //        nhaKhoDTO.setDanhSachChiTietKho(danhSachChiTietKhoDTO);
    //
    //        return nhaKhoDTO;
    //    }
    //
    //    public ChiTietKhoDTO convertToChiTietKhoDTO(ChiTietKho chiTietKho) {
    //        ChiTietKhoDTO chiTietKhoDTO = new ChiTietKhoDTO();
    //        chiTietKhoDTO.setId(chiTietKho.getId());
    //        chiTietKhoDTO.setSoLuong(chiTietKho.getSoLuong());
    //        chiTietKhoDTO.setNhaKho(convertToNhaKhoDTO(chiTietKho.getNhaKho()));
    //        chiTietKhoDTO.setNguyenLieu(convertToNguyenLieuDTO(chiTietKho.getNguyenLieu()));
    //        return chiTietKhoDTO;
    //    }
    //
    //    public NhaKhoDTO convertToNhaKhoDTO(NhaKho nhaKho) {
    //        NhaKhoDTO nhaKhoDTO = new NhaKhoDTO();
    //        nhaKhoDTO.setId(nhaKho.getId());
    //        nhaKhoDTO.setTenKho(nhaKho.getTenKho());
    //        nhaKhoDTO.setDiaChi(nhaKho.getDiaChi());
    //        nhaKhoDTO.setLoai(nhaKho.getLoai());
    //        nhaKhoDTO.setNgayTao(nhaKho.getNgayTao());
    //        return nhaKhoDTO;
    //    }
    //
    //    public NguyenLieuDTO convertToNguyenLieuDTO(NguyenLieuDTO nguyenLieu) {
    //        NguyenLieuDTO nguyenLieuDTO = new NguyenLieuDTO();
    //        nguyenLieuDTO.setId(nguyenLieu.getId());
    //        // Các thuộc tính khác của NguyenLieuDTO
    //        return nguyenLieuDTO;
    //    }
}
