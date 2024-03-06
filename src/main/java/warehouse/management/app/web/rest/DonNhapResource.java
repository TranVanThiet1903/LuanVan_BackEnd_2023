package warehouse.management.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import warehouse.management.app.domain.ChiTietDonNhap;
import warehouse.management.app.domain.ChiTietKho;
import warehouse.management.app.domain.ChiTietPhieuNhap;
import warehouse.management.app.repository.DonNhapRepository;
import warehouse.management.app.service.*;
import warehouse.management.app.service.dto.*;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;
import warehouse.management.app.web.rest.vm.*;

/**
 * REST controller for managing {@link warehouse.management.app.domain.DonNhap}.
 */
@RestController
@RequestMapping("/api")
public class DonNhapResource {

    private final Logger log = LoggerFactory.getLogger(DonNhapResource.class);

    private static final String ENTITY_NAME = "lvProjectDonNhap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonNhapService donNhapService;

    private final DonNhapRepository donNhapRepository;

    private final NhaKhoService nhaKhoService;

    private final ChiTietDonNhapService chiTietDonNhapService;

    private final NhaCungCapService nhaCungCapService;

    private final PhieuNhapService phieuNhapService;

    private final TaiKhoanService taiKhoanService;

    private final ChiTietPhieuNhapService chiTietPhieuNhapService;

    private final NguyenLieuService nguyenLieuService;

    private final ChiTietKhoService chiTietKhoService;

    public DonNhapResource(
        DonNhapService donNhapService,
        DonNhapRepository donNhapRepository,
        NhaKhoService nhaKhoService,
        ChiTietDonNhapService chiTietDonNhapService,
        NhaCungCapService nhaCungCapService,
        PhieuNhapService phieuNhapService,
        TaiKhoanService taiKhoanService,
        ChiTietPhieuNhapService chiTietPhieuNhapService,
        NguyenLieuService nguyenLieuService,
        ChiTietKhoService chiTietKhoService
    ) {
        this.donNhapService = donNhapService;
        this.donNhapRepository = donNhapRepository;
        this.nhaKhoService = nhaKhoService;
        this.chiTietDonNhapService = chiTietDonNhapService;
        this.nhaCungCapService = nhaCungCapService;
        this.phieuNhapService = phieuNhapService;
        this.taiKhoanService = taiKhoanService;
        this.chiTietPhieuNhapService = chiTietPhieuNhapService;
        this.nguyenLieuService = nguyenLieuService;
        this.chiTietKhoService = chiTietKhoService;
    }

    /**
     * {@code POST  /don-nhaps} : Create a new donNhap.
     *
     * @param donNhapRequest the donNhapDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donNhapDTO, or with status {@code 400 (Bad Request)} if the donNhap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/don-nhap")
    public ResponseEntity<?> createDonNhap(@Valid @RequestBody DonNhapRequest donNhapRequest, @RequestHeader("Authorization") String jwt)
        throws Exception {
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapService.findByPhieuNhapId(donNhapRequest.getIdPhieuNhap());
        Optional<PhieuNhapDTO> phieuNhapDTO = phieuNhapService.findOne(donNhapRequest.getIdPhieuNhap());
        PhieuNhapDTO phieuNhap;
        if (phieuNhapDTO.isPresent()) {
            phieuNhap = phieuNhapDTO.get();
        } else {
            throw new Exception("Không tìm thấy phiếu nhập có ID " + donNhapRequest.getIdPhieuNhap());
        }
        DonNhapDTO donNhapDTO = new DonNhapDTO();
        donNhapDTO.setNgayLap(Instant.now());
        donNhapDTO.setPhiShip(phieuNhap.getPhiShip());
        donNhapDTO.setTongTienThanhToan(donNhapRequest.getTongTienThanhToan());
        donNhapDTO.setGiamGia(phieuNhap.getGiamGia());
        donNhapDTO.setTienNo(donNhapRequest.getTienNo());
        donNhapDTO.setGhiChu(phieuNhap.getGhiChu());
        donNhapDTO.setNguoiXacNhan(null);
        donNhapDTO.setPhieuNhap(phieuNhap);
        //        donNhapDTO.setTrangThai("Chờ Duyệt");

        Optional<NhaKhoDTO> nhaKhoDTO = nhaKhoService.findOne(phieuNhap.getNhaKho().getId());
        NhaKhoDTO nhaKho;
        if (nhaKhoDTO.isPresent()) {
            nhaKho = nhaKhoDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhà kho có ID " + phieuNhap.getNhaKho().getId());
        }
        NhaKhoDTO nhaKhoDTO1 = new NhaKhoDTO();
        nhaKhoDTO1.setId(nhaKho.getId());
        nhaKhoDTO1.setNgayTao(nhaKho.getNgayTao());
        nhaKhoDTO1.setTenKho(nhaKho.getTenKho());
        nhaKhoDTO1.setLoai(nhaKho.getLoai());
        nhaKhoDTO1.setDiaChi(nhaKho.getDiaChi());
        donNhapDTO.setNhaKho(nhaKhoDTO1);

        Optional<NhaCungCapDTO> nhaCungCapDTO = nhaCungCapService.findOne(phieuNhap.getNhaCungCap().getId());
        NhaCungCapDTO nhaCungCap;
        if (nhaCungCapDTO.isPresent()) {
            nhaCungCap = nhaCungCapDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhà cung cấp có ID " + phieuNhap.getNhaCungCap().getId());
        }
        NhaCungCapDTO nhaCungCapDTO1 = new NhaCungCapDTO();
        nhaCungCapDTO1.setId(nhaCungCap.getId());
        nhaCungCapDTO1.setTenNCC(nhaCungCap.getTenNCC());
        nhaCungCapDTO1.setEmail(nhaCungCap.getEmail());
        nhaCungCapDTO1.setSoDT(nhaCungCap.getSoDT());
        nhaCungCapDTO1.setDiaChi(nhaCungCap.getDiaChi());
        nhaCungCapDTO1.setGhiChu(nhaCungCapDTO1.getGhiChu());
        donNhapDTO.setNhaCungCap(nhaCungCapDTO1);

        String token = jwt.replace("Bearer ", "");
        GetUserID getUserID = new GetUserID();
        UUID id = getUserID.getIDFromToken(token);
        TaiKhoanDTO taiKhoanDTO = taiKhoanService.findOne(id).orElse(null);
        donNhapDTO.setTaiKhoan(taiKhoanDTO);

        donNhapDTO.setTongTienHang(Long.valueOf(0));
        DonNhapDTO result = donNhapService.save(donNhapDTO);
        Long tongTienHang = Long.valueOf(0);

        for (ChiTietPhieuNhap chiTietPhieuNhap : chiTietPhieuNhapList) {
            ChiTietDonNhapDTO chiTietDonNhapDTO = new ChiTietDonNhapDTO();
            chiTietDonNhapDTO.setDonNhap(result);
            chiTietDonNhapDTO.setSoLuong(chiTietPhieuNhap.getSoLuong());

            Optional<NguyenLieuDTO> nguyenLieu = nguyenLieuService.findOne(chiTietPhieuNhap.getNguyenLieu().getId());
            NguyenLieuDTO nguyenLieuDTO;
            if (nguyenLieu.isPresent()) {
                nguyenLieuDTO = nguyenLieu.get();
            } else {
                throw new Exception("Không tìm thấy nguyên liệu có ID " + chiTietPhieuNhap.getNguyenLieu().getId());
            }

            NguyenLieuDTO nguyenLieuDTO1 = new NguyenLieuDTO();
            nguyenLieuDTO1.setId(nguyenLieuDTO.getId());
            nguyenLieuDTO1.setNhomNguyenLieu(nguyenLieuDTO.getNhomNguyenLieu());
            nguyenLieuDTO1.setGiaNhap(nguyenLieuDTO.getGiaNhap());
            nguyenLieuDTO1.setTenNguyenLieu(nguyenLieuDTO.getTenNguyenLieu());
            nguyenLieuDTO1.setvAT(nguyenLieuDTO.getvAT());
            nguyenLieuDTO1.setMoTa(nguyenLieuDTO.getMoTa());
            nguyenLieuDTO1.setHinhAnh(nguyenLieuDTO.getHinhAnh());
            nguyenLieuDTO1.setNhaCungCap(nguyenLieuDTO.getNhaCungCap());
            nguyenLieuDTO1.setDonViTinh(nguyenLieuDTO1.getDonViTinh());

            chiTietDonNhapDTO.setNguyenLieu(nguyenLieuDTO);
            chiTietDonNhapDTO.setThanhTien(
                (chiTietPhieuNhap.getSoLuong() * nguyenLieuDTO.getGiaNhap()) +
                (chiTietPhieuNhap.getSoLuong() * nguyenLieuDTO.getGiaNhap() * nguyenLieuDTO.getvAT()) /
                100
            );

            ChiTietDonNhapDTO chiTietResult = chiTietDonNhapService.save(chiTietDonNhapDTO);
            log.debug(String.valueOf(chiTietResult.getThanhTien()));
            tongTienHang = tongTienHang + chiTietResult.getThanhTien();
            //            ChiTietKho findChiTietKho = chiTietKhoService.findByNguyenLieuIdAndNhaKhoId(nguyenLieuDTO.getId(), nhaKho.getId());
            //            Optional<ChiTietKhoDTO> chiTietKhoDTO;
            //            if (findChiTietKho != null) {
            //                chiTietKhoDTO = chiTietKhoService.findOne(findChiTietKho.getId());
            //                ChiTietKhoDTO chiTietKhoDTO1 = new ChiTietKhoDTO();
            //                chiTietKhoDTO1.setId(findChiTietKho.getId());
            //                chiTietKhoDTO1.setNhaKho(chiTietKhoDTO.get().getNhaKho());
            //                chiTietKhoDTO1.setNguyenLieu(chiTietKhoDTO.get().getNguyenLieu());
            //                chiTietKhoDTO1.setSoLuong(findChiTietKho.getSoLuong() + chiTietPhieuNhap.getSoLuong());
            //
            //                chiTietKhoService.update(chiTietKhoDTO1);
            //            } else {
            //                ChiTietKhoDTO chiTietKhoDTO1 = new ChiTietKhoDTO();
            //                chiTietKhoDTO1.setNhaKho(nhaKho);
            //                chiTietKhoDTO1.setNguyenLieu(nguyenLieuDTO);
            //                chiTietKhoDTO1.setSoLuong(chiTietPhieuNhap.getSoLuong());
            //
            //                chiTietKhoService.save(chiTietKhoDTO1);
            //            }
        }

        DonNhapDTO donNhapDTO1 = new DonNhapDTO();
        donNhapDTO1.setId(result.getId());
        donNhapDTO1.setTongTienHang(tongTienHang);
        donNhapDTO1.setNguoiXacNhan(result.getNguoiXacNhan());
        donNhapDTO1.setTaiKhoan(result.getTaiKhoan());
        donNhapDTO1.setPhieuNhap(result.getPhieuNhap());
        donNhapDTO1.setNhaKho(result.getNhaKho());
        donNhapDTO1.setNgayLap(result.getNgayLap());
        donNhapDTO1.setNhaCungCap(result.getNhaCungCap());
        donNhapDTO1.setGhiChu(result.getGhiChu());
        donNhapDTO1.setGiamGia(result.getGiamGia());
        donNhapDTO1.setTienNo(result.getTienNo());
        donNhapDTO1.setTongTienThanhToan(result.getTongTienThanhToan());
        donNhapDTO1.setTienNo(result.getTienNo());
        donNhapDTO1.setPhiShip(result.getPhiShip());
        //        donNhapDTO1.setTrangThai(result.getTrangThai());

        DonNhapDTO updateTienHang = donNhapService.update(donNhapDTO1);

        DonNhapVM donNhapVM = new DonNhapVM();
        donNhapVM.setDonNhapDTO(updateTienHang);

        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapService.findByDonNhapId(result.getId());
        donNhapVM.setChiTietDonNhapList(chiTietDonNhapList);

        log.debug("REST request to save DonNhap : {}", donNhapDTO);
        if (donNhapDTO.getId() != null) {
            throw new BadRequestAlertException("A new donNhap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //        DonNhapDTO result = donNhapService.save(donNhapDTO);
        return ResponseEntity
            .created(new URI("/api/don-nhap/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(donNhapVM);
    }

    //    /**
    //     * {@code PUT  /don-nhaps/:id} : Updates an existing donNhap.
    //     *
    //     * @param id the id of the donNhapDTO to save.
    //     * @param donNhapDTO the donNhapDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donNhapDTO,
    //     * or with status {@code 400 (Bad Request)} if the donNhapDTO is not valid,
    //     * or with status {@code 500 (Internal Server Error)} if the donNhapDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PutMapping("/don-nhap/{id}")
    //    public ResponseEntity<DonNhapDTO> updateDonNhap(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @Valid @RequestBody DonNhapDTO donNhapDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to update DonNhap : {}, {}", id, donNhapDTO);
    //        if (donNhapDTO.getId() == null) {
    //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, donNhapDTO.getId())) {
    //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!donNhapRepository.existsById(id)) {
    //            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        DonNhapDTO result = donNhapService.update(donNhapDTO);
    //        return ResponseEntity
    //            .ok()
    //            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donNhapDTO.getId().toString()))
    //            .body(result);
    //    }
    //
    //    /**
    //     * {@code PATCH  /don-nhaps/:id} : Partial updates given fields of an existing donNhap, field will ignore if it is null
    //     *
    //     * @param id the id of the donNhapDTO to save.
    //     * @param donNhapDTO the donNhapDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donNhapDTO,
    //     * or with status {@code 400 (Bad Request)} if the donNhapDTO is not valid,
    //     * or with status {@code 404 (Not Found)} if the donNhapDTO is not found,
    //     * or with status {@code 500 (Internal Server Error)} if the donNhapDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PatchMapping(value = "/don-nhap/{id}", consumes = { "application/json", "application/merge-patch+json" })
    //    public ResponseEntity<DonNhapDTO> partialUpdateDonNhap(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @NotNull @RequestBody DonNhapDTO donNhapDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to partial update DonNhap partially : {}, {}", id, donNhapDTO);
    //        if (donNhapDTO.getId() == null) {
    //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, donNhapDTO.getId())) {
    //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!donNhapRepository.existsById(id)) {
    //            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        Optional<DonNhapDTO> result = donNhapService.partialUpdate(donNhapDTO);
    //
    //        return ResponseUtil.wrapOrNotFound(
    //            result,
    //            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donNhapDTO.getId().toString())
    //        );
    //    }

    /**
     * {@code GET  /don-nhaps} : get all the donNhaps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donNhaps in body.
     */
    @GetMapping("/don-nhap")
    public ResponseEntity<List<?>> getAllDonNhaps(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DonNhaps");
        List<DonNhapVM2> donNhapVM2List = new ArrayList<>();

        Page<DonNhapDTO> page = donNhapService.findAll(pageable);
        for (DonNhapDTO donNhapDTO : page) {
            DonNhapVM2 donNhapVM2 = new DonNhapVM2();
            Optional<TaiKhoanDTO> taiKhoanDTO = taiKhoanService.findOne(donNhapDTO.getNguoiXacNhan());
            //            TaiKhoanDTO taiKhoanDTO1 = new TaiKhoanDTO();
            if (taiKhoanDTO.isPresent()) {
                //                taiKhoanDTO1 = taiKhoanDTO.get();
                donNhapVM2.setNguoiXacNhanDon(taiKhoanDTO.get().getHoTen());
            } else {
                donNhapVM2.setNguoiXacNhanDon(null);
            }
            donNhapVM2.setDonNhapDTO(donNhapDTO);
            donNhapVM2List.add(donNhapVM2);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(donNhapVM2List);
    }

    /**
     * {@code GET  /don-nhaps/:id} : get the "id" donNhap.
     *
     * @param id the id of the donNhapDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donNhapDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/don-nhap/{id}")
    public ResponseEntity<?> getDonNhap(@PathVariable Long id) {
        log.debug("REST request to get DonNhap : {}", id);
        Optional<DonNhapDTO> donNhapDTO = donNhapService.findOne(id);
        DonNhapDTO donNhapDTO1 = new DonNhapDTO();
        if (donNhapDTO.isPresent()) {
            donNhapDTO1 = donNhapDTO.get();
        }
        DonNhapAllInfo donNhapAllInfo = new DonNhapAllInfo();
        donNhapAllInfo.setDonNhapDTO(donNhapDTO1);
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapService.findByDonNhapId(id);
        donNhapAllInfo.setChiTietDonNhapList(chiTietDonNhapList);
        Optional<TaiKhoanDTO> taiKhoanDTO = taiKhoanService.findOne(donNhapDTO1.getNguoiXacNhan());
        if (taiKhoanDTO.isPresent()) {
            donNhapAllInfo.setNguoiXacNhanDon(taiKhoanDTO.get().getHoTen());
        } else {
            donNhapAllInfo.setNguoiXacNhanDon(null);
        }
        return ResponseEntity.ok(donNhapAllInfo);
    }

    @GetMapping("/don-nhap/{id}/xac-nhan")
    public ResponseEntity<?> getUUID(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        String token = jwt.replace("Bearer ", "");
        GetUserID getUserID = new GetUserID();
        UUID userID = getUserID.getIDFromToken(token);
        Optional<DonNhapDTO> donNhapDTO = donNhapService.findOne(id);
        DonNhapDTO donNhapDTO1;
        if (donNhapDTO.isPresent()) {
            donNhapDTO1 = donNhapDTO.get();
        } else {
            throw new Exception("Không tìm thấy đơn nhập có ID " + id);
        }
        log.debug("{}", userID);
        donNhapDTO1.setNguoiXacNhan(userID);
        DonNhapDTO result = donNhapService.update(donNhapDTO1);
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapService.findByDonNhapId(result.getId());
        for (ChiTietDonNhap chiTietDonNhap : chiTietDonNhapList) {
            ChiTietKho findChiTietKho = chiTietKhoService.findByNguyenLieuIdAndNhaKhoId(
                chiTietDonNhap.getNguyenLieu().getId(),
                result.getNhaKho().getId()
            );
            Optional<ChiTietKhoDTO> chiTietKhoDTO;
            if (findChiTietKho != null) {
                chiTietKhoDTO = chiTietKhoService.findOne(findChiTietKho.getId());
                ChiTietKhoDTO chiTietKhoDTO1 = new ChiTietKhoDTO();
                chiTietKhoDTO1.setId(findChiTietKho.getId());
                chiTietKhoDTO1.setNhaKho(chiTietKhoDTO.get().getNhaKho());
                chiTietKhoDTO1.setNguyenLieu(chiTietKhoDTO.get().getNguyenLieu());
                chiTietKhoDTO1.setSoLuong(findChiTietKho.getSoLuong() + chiTietDonNhap.getSoLuong());

                chiTietKhoService.update(chiTietKhoDTO1);
            } else {
                Optional<NguyenLieuDTO> nguyenLieuDTO = nguyenLieuService.findOne(chiTietDonNhap.getNguyenLieu().getId());
                NguyenLieuDTO nguyenLieuDTO1 = new NguyenLieuDTO();
                if (nguyenLieuDTO.isPresent()) {
                    nguyenLieuDTO1 = nguyenLieuDTO.get();
                }
                ChiTietKhoDTO chiTietKhoDTO1 = new ChiTietKhoDTO();
                chiTietKhoDTO1.setNhaKho(result.getNhaKho());
                chiTietKhoDTO1.setNguyenLieu(nguyenLieuDTO1);
                chiTietKhoDTO1.setSoLuong(chiTietDonNhap.getSoLuong());

                chiTietKhoService.save(chiTietKhoDTO1);
            }
        }
        return ResponseEntity.ok(result);
    }
    //    /**
    //     * {@code DELETE  /don-nhaps/:id} : delete the "id" donNhap.
    //     *
    //     * @param id the id of the donNhapDTO to delete.
    //     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    //     */
    //    @DeleteMapping("/don-nhap/{id}")
    //    public ResponseEntity<Void> deleteDonNhap(@PathVariable Long id) {
    //        log.debug("REST request to delete DonNhap : {}", id);
    //        donNhapService.delete(id);
    //        return ResponseEntity
    //            .noContent()
    //            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
    //            .build();
    //    }
}
