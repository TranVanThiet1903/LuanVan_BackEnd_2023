package warehouse.management.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.*;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import javax.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import warehouse.management.app.domain.ChiTietDonXuat;
import warehouse.management.app.domain.ChiTietKho;
import warehouse.management.app.repository.DonXuatRepository;
import warehouse.management.app.service.*;
import warehouse.management.app.service.dto.*;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;
import warehouse.management.app.web.rest.vm.*;

/**
 * REST controller for managing {@link warehouse.management.app.domain.DonXuat}.
 */
@RestController
@RequestMapping("/api")
public class DonXuatResource {

    private final Logger log = LoggerFactory.getLogger(DonXuatResource.class);

    private static final String ENTITY_NAME = "lvProjectDonXuat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonXuatService donXuatService;

    private final DonXuatRepository donXuatRepository;

    private final NhaKhoService nhaKhoService;

    private final NhaCungCapService nhaCungCapService;

    private final ChiTietDonXuatService chiTietDonXuatService;

    private final ChiTietKhoService chiTietKhoService;

    private final TaiKhoanService taiKhoanService;

    private final NguyenLieuService nguyenLieuService;

    public DonXuatResource(
        DonXuatService donXuatService,
        DonXuatRepository donXuatRepository,
        NhaKhoService nhaKhoService,
        NhaCungCapService nhaCungCapService,
        ChiTietDonXuatService chiTietDonXuatService,
        ChiTietKhoService chiTietKhoService,
        TaiKhoanService taiKhoanService,
        NguyenLieuService nguyenLieuService
    ) {
        this.donXuatService = donXuatService;
        this.donXuatRepository = donXuatRepository;
        this.nhaKhoService = nhaKhoService;
        this.nhaCungCapService = nhaCungCapService;
        this.chiTietDonXuatService = chiTietDonXuatService;
        this.chiTietKhoService = chiTietKhoService;
        this.taiKhoanService = taiKhoanService;
        this.nguyenLieuService = nguyenLieuService;
    }

    /**
     * {@code POST  /don-xuats} : Create a new donXuat.
     *
     * @param donXuatRequest the donXuatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donXuatDTO, or with status {@code 400 (Bad Request)} if the donXuat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/don-xuat")
    public ResponseEntity<?> createDonXuat(@Valid @RequestBody DonXuatRequest donXuatRequest, @RequestHeader("Authorization") String jwt)
        throws Exception {
        DonXuatDTO donXuatDTO = new DonXuatDTO();
        DonXuatDTO donXuatDTO1 = new DonXuatDTO();
        donXuatDTO.setNgayLap(Instant.now());
        donXuatDTO.setNguoiXacNhan(null);

        Optional<NhaKhoDTO> nhaKhoDTO = nhaKhoService.findOne(donXuatRequest.getIdKho());
        NhaKhoDTO nhaKho;
        if (nhaKhoDTO.isPresent()) {
            nhaKho = nhaKhoDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhà kho có ID " + donXuatRequest.getIdKho());
        }
        NhaKhoDTO nhaKhoDTO1 = new NhaKhoDTO();
        nhaKhoDTO1.setId(nhaKho.getId());
        nhaKhoDTO1.setNgayTao(nhaKho.getNgayTao());
        nhaKhoDTO1.setTenKho(nhaKho.getTenKho());
        nhaKhoDTO1.setLoai(nhaKho.getLoai());
        nhaKhoDTO1.setDiaChi(nhaKho.getDiaChi());
        donXuatDTO.setNhaKho(nhaKhoDTO1);

        String token = jwt.replace("Bearer ", "");
        GetUserID getUserID = new GetUserID();
        UUID id = getUserID.getIDFromToken(token);
        TaiKhoanDTO taiKhoanDTO = taiKhoanService.findOne(id).orElse(null);
        donXuatDTO.setTaiKhoan(taiKhoanDTO);

        donXuatDTO.setTongTienHang(Long.valueOf(0));
        Long tongTienHang = Long.valueOf(0);

        for (ChiTietPhieuNhapRequest chiTietDonXuat : donXuatRequest.getChiTietPhieuNhapRequestList()) {
            ChiTietDonXuatDTO chiTietDonXuatDTO = new ChiTietDonXuatDTO();

            chiTietDonXuatDTO.setSoLuong(chiTietDonXuat.getSoLuong());

            Optional<NguyenLieuDTO> nguyenLieu = nguyenLieuService.findOne(chiTietDonXuat.getIdNguyenLieu());
            NguyenLieuDTO nguyenLieuDTO;
            if (nguyenLieu.isPresent()) {
                nguyenLieuDTO = nguyenLieu.get();
            } else {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Không tìm thấy nguyên liệu có ID " + chiTietDonXuat.getIdNguyenLieu());
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
            nguyenLieuDTO1.setDonViTinh(nguyenLieuDTO.getDonViTinh());

            chiTietDonXuatDTO.setNguyenLieu(nguyenLieuDTO);
            chiTietDonXuatDTO.setThanhTien(
                (chiTietDonXuat.getSoLuong() * nguyenLieuDTO.getGiaNhap()) +
                (chiTietDonXuat.getSoLuong() * nguyenLieuDTO.getGiaNhap() * nguyenLieuDTO.getvAT()) /
                100
            );

            ChiTietKho findChiTietKho = chiTietKhoService.findByNguyenLieuIdAndNhaKhoId(nguyenLieuDTO.getId(), nhaKho.getId());
            Optional<ChiTietKhoDTO> chiTietKhoDTO;
            if (findChiTietKho != null) {
                chiTietKhoDTO = chiTietKhoService.findOne(findChiTietKho.getId());
                if (findChiTietKho.getSoLuong() > chiTietDonXuat.getSoLuong()) {} else if (
                    Objects.equals(findChiTietKho.getSoLuong(), chiTietDonXuat.getSoLuong())
                ) {} else {
                    return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Số lượng xuất ra của nguyên liệu " + nguyenLieuDTO.getTenNguyenLieu() + " lớn hơn số lượng có trong kho.");
                }
            } else {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Nguyên liệu " + nguyenLieuDTO.getTenNguyenLieu() + " không có sẵn trong kho.");
            }

            DonXuatDTO result = donXuatService.save(donXuatDTO);
            chiTietDonXuatDTO.setDonXuat(result);

            donXuatDTO1.setId(result.getId());
            donXuatDTO1.setNgayLap(result.getNgayLap());
            donXuatDTO1.setNguoiXacNhan(result.getNguoiXacNhan());
            donXuatDTO1.setTaiKhoan(result.getTaiKhoan());
            donXuatDTO1.setNhaKho(result.getNhaKho());

            ChiTietDonXuatDTO chiTietResult = chiTietDonXuatService.save(chiTietDonXuatDTO);

            tongTienHang = tongTienHang + chiTietResult.getThanhTien();
        }

        donXuatDTO1.setTongTienHang(tongTienHang);

        DonXuatDTO updateTienHang = donXuatService.update(donXuatDTO1);

        DonXuatVM donXuatVM = new DonXuatVM();
        donXuatVM.setDonXuatDTO(updateTienHang);

        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatService.findByDonXuatId(updateTienHang.getId());
        donXuatVM.setChiTietDonXuatList(chiTietDonXuatList);

        log.debug("REST request to save DonXuat : {}", donXuatDTO);
        if (donXuatDTO.getId() != null) {
            throw new BadRequestAlertException("A new donXuat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return ResponseEntity
            .created(new URI("/api/don-xuat/" + updateTienHang.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, updateTienHang.getId().toString()))
            .body(donXuatVM);
    }

    @PostMapping("/don-xuat/excel")
    public ResponseEntity<?> createDonXuatByExcelFile(
        @ModelAttribute DonXuatExcelRequest donXuatExcelRequest,
        @RequestHeader("Authorization") String jwt
    ) throws Exception {
        DonXuatDTO donXuatDTO = new DonXuatDTO();
        DonXuatDTO donXuatDTO1 = new DonXuatDTO();
        donXuatDTO.setNgayLap(Instant.now());
        donXuatDTO.setNguoiXacNhan(null);

        Optional<NhaKhoDTO> nhaKhoDTO = nhaKhoService.findOne(donXuatExcelRequest.getIdKho());
        NhaKhoDTO nhaKho;
        if (nhaKhoDTO.isPresent()) {
            nhaKho = nhaKhoDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhà kho có ID " + donXuatExcelRequest.getIdKho());
        }
        NhaKhoDTO nhaKhoDTO1 = new NhaKhoDTO();
        nhaKhoDTO1.setId(nhaKho.getId());
        nhaKhoDTO1.setNgayTao(nhaKho.getNgayTao());
        nhaKhoDTO1.setTenKho(nhaKho.getTenKho());
        nhaKhoDTO1.setLoai(nhaKho.getLoai());
        nhaKhoDTO1.setDiaChi(nhaKho.getDiaChi());
        donXuatDTO.setNhaKho(nhaKhoDTO1);

        String token = jwt.replace("Bearer ", "");
        GetUserID getUserID = new GetUserID();
        UUID id = getUserID.getIDFromToken(token);
        TaiKhoanDTO taiKhoanDTO = taiKhoanService.findOne(id).orElse(null);
        donXuatDTO.setTaiKhoan(taiKhoanDTO);

        donXuatDTO.setTongTienHang(Long.valueOf(0));

        Long tongTienHang = Long.valueOf(0);

        Workbook workbook = WorkbookFactory.create(donXuatExcelRequest.getFile().getInputStream());

        Sheet sheet = workbook.getSheetAt(0);
        boolean isFirstRow = true;
        for (Row chiTietDonXuat : sheet) {
            if (isFirstRow) {
                isFirstRow = false;
                continue; // Bỏ qua dòng đầu tiên
            }
            ChiTietDonXuatDTO chiTietDonXuatDTO = new ChiTietDonXuatDTO();

            Long soLuong = (long) chiTietDonXuat.getCell(1).getNumericCellValue();
            Long idNguyenLieu = (long) chiTietDonXuat.getCell(0).getNumericCellValue();
            chiTietDonXuatDTO.setSoLuong(soLuong);

            Optional<NguyenLieuDTO> nguyenLieu = nguyenLieuService.findOne(idNguyenLieu);
            NguyenLieuDTO nguyenLieuDTO;
            if (nguyenLieu.isPresent()) {
                nguyenLieuDTO = nguyenLieu.get();
            } else {
                throw new Exception("Không tìm thấy nguyên liệu có ID " + idNguyenLieu);
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
            nguyenLieuDTO1.setDonViTinh(nguyenLieuDTO.getDonViTinh());

            chiTietDonXuatDTO.setNguyenLieu(nguyenLieuDTO);
            chiTietDonXuatDTO.setThanhTien(
                (soLuong * nguyenLieuDTO.getGiaNhap()) + (soLuong * nguyenLieuDTO.getGiaNhap() * nguyenLieuDTO.getvAT()) / 100
            );

            ChiTietKho findChiTietKho = chiTietKhoService.findByNguyenLieuIdAndNhaKhoId(nguyenLieuDTO.getId(), nhaKho.getId());
            Optional<ChiTietKhoDTO> chiTietKhoDTO;
            if (findChiTietKho != null) {
                if (findChiTietKho.getSoLuong() > soLuong) {} else if (Objects.equals(findChiTietKho.getSoLuong(), soLuong)) {} else {
                    return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Số lượng xuất ra của nguyên liệu " + nguyenLieuDTO.getTenNguyenLieu() + " lớn hơn số lượng có trong kho.");
                }
            } else {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Nguyên liệu " + nguyenLieuDTO.getTenNguyenLieu() + " không có sẵn trong kho.");
            }

            DonXuatDTO result = donXuatService.save(donXuatDTO);
            chiTietDonXuatDTO.setDonXuat(result);

            donXuatDTO1.setId(result.getId());
            donXuatDTO1.setNgayLap(result.getNgayLap());
            donXuatDTO1.setNguoiXacNhan(result.getNguoiXacNhan());
            donXuatDTO1.setTaiKhoan(result.getTaiKhoan());
            donXuatDTO1.setNhaKho(result.getNhaKho());

            ChiTietDonXuatDTO chiTietResult = chiTietDonXuatService.save(chiTietDonXuatDTO);
            tongTienHang = tongTienHang + chiTietResult.getThanhTien();
        }

        donXuatDTO1.setTongTienHang(tongTienHang);

        DonXuatDTO updateTienHang = donXuatService.update(donXuatDTO1);

        DonXuatVM donXuatVM = new DonXuatVM();
        donXuatVM.setDonXuatDTO(updateTienHang);

        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatService.findByDonXuatId(updateTienHang.getId());
        donXuatVM.setChiTietDonXuatList(chiTietDonXuatList);

        log.debug("REST request to save DonXuat : {}", donXuatDTO);
        if (donXuatDTO.getId() != null) {
            throw new BadRequestAlertException("A new donXuat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return ResponseEntity
            .created(new URI("/api/don-xuat/" + updateTienHang.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, updateTienHang.getId().toString()))
            .body(donXuatVM);
    }

    //    /**
    //     * {@code PUT  /don-xuats/:id} : Updates an existing donXuat.
    //     *
    //     * @param id the id of the donXuatDTO to save.
    //     * @param donXuatDTO the donXuatDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donXuatDTO,
    //     * or with status {@code 400 (Bad Request)} if the donXuatDTO is not valid,
    //     * or with status {@code 500 (Internal Server Error)} if the donXuatDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PutMapping("/don-xuat/{id}")
    //    public ResponseEntity<DonXuatDTO> updateDonXuat(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @Valid @RequestBody DonXuatDTO donXuatDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to update DonXuat : {}, {}", id, donXuatDTO);
    //        if (donXuatDTO.getId() == null) {
    //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, donXuatDTO.getId())) {
    //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!donXuatRepository.existsById(id)) {
    //            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        DonXuatDTO result = donXuatService.update(donXuatDTO);
    //        return ResponseEntity
    //            .ok()
    //            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donXuatDTO.getId().toString()))
    //            .body(result);
    //    }
    //
    //    /**
    //     * {@code PATCH  /don-xuats/:id} : Partial updates given fields of an existing donXuat, field will ignore if it is null
    //     *
    //     * @param id the id of the donXuatDTO to save.
    //     * @param donXuatDTO the donXuatDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donXuatDTO,
    //     * or with status {@code 400 (Bad Request)} if the donXuatDTO is not valid,
    //     * or with status {@code 404 (Not Found)} if the donXuatDTO is not found,
    //     * or with status {@code 500 (Internal Server Error)} if the donXuatDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PatchMapping(value = "/don-xuat/{id}", consumes = { "application/json", "application/merge-patch+json" })
    //    public ResponseEntity<DonXuatDTO> partialUpdateDonXuat(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @NotNull @RequestBody DonXuatDTO donXuatDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to partial update DonXuat partially : {}, {}", id, donXuatDTO);
    //        if (donXuatDTO.getId() == null) {
    //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, donXuatDTO.getId())) {
    //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!donXuatRepository.existsById(id)) {
    //            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        Optional<DonXuatDTO> result = donXuatService.partialUpdate(donXuatDTO);
    //
    //        return ResponseUtil.wrapOrNotFound(
    //            result,
    //            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donXuatDTO.getId().toString())
    //        );
    //    }

    /**
     * {@code GET  /don-xuats} : get all the donXuats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donXuats in body.
     */
    @GetMapping("/don-xuat")
    public ResponseEntity<List<?>> getAllDonXuats(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DonXuats");
        List<DonXuatVM2> donXuatVM2List = new ArrayList<>();
        Page<DonXuatDTO> page = donXuatService.findAll(pageable);
        for (DonXuatDTO donXuatDTO : page) {
            DonXuatVM2 donXuatVM2 = new DonXuatVM2();
            Optional<TaiKhoanDTO> taiKhoanDTO = taiKhoanService.findOne(donXuatDTO.getNguoiXacNhan());
            if (taiKhoanDTO.isPresent()) {
                donXuatVM2.setNguoiXacNhanDon(taiKhoanDTO.get().getHoTen());
            } else {
                donXuatVM2.setNguoiXacNhanDon(null);
            }
            donXuatVM2.setDonXuatDTO(donXuatDTO);
            donXuatVM2List.add(donXuatVM2);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(donXuatVM2List);
    }

    /**
     * {@code GET  /don-xuats/:id} : get the "id" donXuat.
     *
     * @param id the id of the donXuatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donXuatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/don-xuat/{id}")
    public ResponseEntity<?> getDonXuat(@PathVariable Long id) {
        log.debug("REST request to get DonXuat : {}", id);
        DonXuatAllInfo donXuatAllInfo = new DonXuatAllInfo();
        Optional<DonXuatDTO> donXuatDTO = donXuatService.findOne(id);
        if (donXuatDTO.isPresent()) {
            donXuatAllInfo.setDonXuatDTO(donXuatDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy đơn xuất có ID = " + id + ".");
        }
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatService.findByDonXuatId(id);
        donXuatAllInfo.setChiTietDonXuatList(chiTietDonXuatList);
        Optional<TaiKhoanDTO> taiKhoanDTO = taiKhoanService.findOne(donXuatDTO.get().getNguoiXacNhan());
        if (taiKhoanDTO.isPresent()) {
            donXuatAllInfo.setNguoiXacNhanDon(taiKhoanDTO.get().getHoTen());
        } else {
            donXuatAllInfo.setNguoiXacNhanDon(null);
        }
        return ResponseEntity.ok(donXuatAllInfo);
    }

    @GetMapping("/don-xuat/{id}/xac-nhan")
    public ResponseEntity<?> getUUID(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        String token = jwt.replace("Bearer ", "");
        GetUserID getUserID = new GetUserID();
        UUID userID = getUserID.getIDFromToken(token);
        Optional<DonXuatDTO> donXuatDTO = donXuatService.findOne(id);
        DonXuatDTO donXuatDTO1;
        if (donXuatDTO.isPresent()) {
            donXuatDTO1 = donXuatDTO.get();
        } else {
            throw new Exception("Không tìm thấy đơn xuất có ID " + id);
        }
        log.debug("{}", userID);
        donXuatDTO1.setNguoiXacNhan(userID);
        DonXuatDTO result = donXuatService.update(donXuatDTO1);
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatService.findByDonXuatId(id);
        for (ChiTietDonXuat chiTietDonXuat : chiTietDonXuatList) {
            ChiTietKho findChiTietKho = chiTietKhoService.findByNguyenLieuIdAndNhaKhoId(
                chiTietDonXuat.getNguyenLieu().getId(),
                result.getNhaKho().getId()
            );
            Optional<ChiTietKhoDTO> chiTietKhoDTO;
            if (findChiTietKho != null) {
                chiTietKhoDTO = chiTietKhoService.findOne(findChiTietKho.getId());
                ChiTietKhoDTO chiTietKhoDTO1 = new ChiTietKhoDTO();
                chiTietKhoDTO1.setId(findChiTietKho.getId());
                chiTietKhoDTO1.setNhaKho(chiTietKhoDTO.get().getNhaKho());
                chiTietKhoDTO1.setNguyenLieu(chiTietKhoDTO.get().getNguyenLieu());
                if (findChiTietKho.getSoLuong() > chiTietDonXuat.getSoLuong()) {
                    chiTietKhoDTO1.setSoLuong(findChiTietKho.getSoLuong() - chiTietDonXuat.getSoLuong());
                    chiTietKhoService.update(chiTietKhoDTO1);
                } else if (Objects.equals(findChiTietKho.getSoLuong(), chiTietDonXuat.getSoLuong())) {
                    chiTietKhoService.delete(findChiTietKho.getId());
                } else {
                    throw new Exception(
                        "Số lượng xuất ra của nguyên liệu " +
                        chiTietDonXuat.getNguyenLieu().getTenNguyenLieu() +
                        " lớn hơn số lượng có trong kho."
                    );
                }
            } else {
                throw new Exception("Nguyên liệu " + chiTietDonXuat.getNguyenLieu().getTenNguyenLieu() + " không có sẵn trong kho.");
            }
        }
        return ResponseEntity.ok(result);
    }
    //    /**
    //     * {@code DELETE  /don-xuats/:id} : delete the "id" donXuat.
    //     *
    //     * @param id the id of the donXuatDTO to delete.
    //     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    //     */
    //    @DeleteMapping("/don-xuat/{id}")
    //    public ResponseEntity<Void> deleteDonXuat(@PathVariable Long id) {
    //        log.debug("REST request to delete DonXuat : {}", id);
    //        donXuatService.delete(id);
    //        return ResponseEntity
    //            .noContent()
    //            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
    //            .build();
    //    }
}
