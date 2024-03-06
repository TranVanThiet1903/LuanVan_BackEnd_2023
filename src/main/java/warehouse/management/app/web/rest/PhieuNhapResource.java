package warehouse.management.app.web.rest;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import warehouse.management.app.domain.ChiTietPhieuNhap;
import warehouse.management.app.domain.NhaKho;
import warehouse.management.app.repository.ChiTietPhieuNhapRepository;
import warehouse.management.app.repository.NguyenLieuRepository;
import warehouse.management.app.repository.PhieuNhapRepository;
import warehouse.management.app.service.*;
import warehouse.management.app.service.dto.*;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;
import warehouse.management.app.web.rest.vm.*;

/**
 * REST controller for managing {@link warehouse.management.app.domain.PhieuNhap}.
 */
@RestController
@RequestMapping("/api")
public class PhieuNhapResource {

    private final Logger log = LoggerFactory.getLogger(PhieuNhapResource.class);

    private static final String ENTITY_NAME = "lvProjectPhieuNhap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhieuNhapService phieuNhapService;

    private final PhieuNhapRepository phieuNhapRepository;

    private final ChiTietPhieuNhapService chiTietPhieuNhapService;

    private final ChiTietPhieuNhapRepository chiTietPhieuNhapRepository;

    private final NguyenLieuRepository nguyenLieuRepository;

    private final NguyenLieuService nguyenLieuService;

    private final NhaKhoService nhaKhoService;

    private final NhaCungCapService nhaCungCapService;

    private final TaiKhoanService taiKhoanService;

    @Autowired
    private JavaMailSender javaMailSender;

    public PhieuNhapResource(
        PhieuNhapService phieuNhapService,
        PhieuNhapRepository phieuNhapRepository,
        ChiTietPhieuNhapService chiTietPhieuNhapService,
        ChiTietPhieuNhapRepository chiTietPhieuNhapRepository,
        NguyenLieuRepository nguyenLieuRepository,
        NguyenLieuService nguyenLieuService,
        NhaKhoService nhaKhoService,
        NhaCungCapService nhaCungCapService,
        TaiKhoanService taiKhoanService
    ) {
        this.phieuNhapService = phieuNhapService;
        this.phieuNhapRepository = phieuNhapRepository;
        this.chiTietPhieuNhapService = chiTietPhieuNhapService;
        this.chiTietPhieuNhapRepository = chiTietPhieuNhapRepository;
        this.nguyenLieuRepository = nguyenLieuRepository;
        this.nguyenLieuService = nguyenLieuService;
        this.nhaKhoService = nhaKhoService;
        this.nhaCungCapService = nhaCungCapService;
        this.taiKhoanService = taiKhoanService;
    }

    /**
     * {@code POST  /phieu-nhaps} : Create a new phieuNhap.
     *
     * @param phieuNhapRequest the phieuNhapDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phieuNhapDTO, or with status {@code 400 (Bad Request)} if the phieuNhap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phieu-nhap")
    public ResponseEntity<?> createPhieuNhap(
        @Valid @RequestBody PhieuNhapRequest phieuNhapRequest,
        @RequestHeader("Authorization") String jwt
    ) throws Exception {
        PhieuNhapDTO phieuNhapDTO = new PhieuNhapDTO();
        phieuNhapDTO.setNgayLap(Instant.now());
        phieuNhapDTO.setPhiShip(phieuNhapRequest.getPhiShip());
        phieuNhapDTO.setTongTienThanhToan(phieuNhapRequest.getTongTienThanhToan());
        phieuNhapDTO.setGiamGia(phieuNhapRequest.getGiamGia());
        phieuNhapDTO.setTienNo(Long.valueOf(0));
        phieuNhapDTO.setGhiChu(phieuNhapRequest.getGhiChu());
        phieuNhapDTO.setvAT(Long.valueOf(0));
        //        phieuNhapDTO.setTrangThai("Đã Gửi");

        Optional<NhaKhoDTO> nhaKhoDTO = nhaKhoService.findOne(phieuNhapRequest.getIdKho());
        NhaKhoDTO nhaKho;
        if (nhaKhoDTO.isPresent()) {
            nhaKho = nhaKhoDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhà kho có ID " + phieuNhapRequest.getIdKho());
        }
        NhaKhoDTO nhaKhoDTO1 = new NhaKhoDTO();
        nhaKhoDTO1.setId(nhaKho.getId());
        nhaKhoDTO1.setNgayTao(nhaKho.getNgayTao());
        nhaKhoDTO1.setTenKho(nhaKho.getTenKho());
        nhaKhoDTO1.setLoai(nhaKho.getLoai());
        nhaKhoDTO1.setDiaChi(nhaKho.getDiaChi());
        phieuNhapDTO.setNhaKho(nhaKhoDTO1);

        Optional<NhaCungCapDTO> nhaCungCapDTO = nhaCungCapService.findOne(phieuNhapRequest.getIdNCC());
        NhaCungCapDTO nhaCungCap;
        if (nhaCungCapDTO.isPresent()) {
            nhaCungCap = nhaCungCapDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhà cung cấp có ID " + phieuNhapRequest.getIdNCC());
        }
        NhaCungCapDTO nhaCungCapDTO1 = new NhaCungCapDTO();
        nhaCungCapDTO1.setId(nhaCungCap.getId());
        nhaCungCapDTO1.setTenNCC(nhaCungCap.getTenNCC());
        nhaCungCapDTO1.setEmail(nhaCungCap.getEmail());
        nhaCungCapDTO1.setSoDT(nhaCungCap.getSoDT());
        nhaCungCapDTO1.setDiaChi(nhaCungCap.getDiaChi());
        nhaCungCapDTO1.setGhiChu(nhaCungCap.getGhiChu());
        phieuNhapDTO.setNhaCungCap(nhaCungCapDTO1);

        String token = jwt.replace("Bearer ", "");
        GetUserID getUserID = new GetUserID();
        UUID id = getUserID.getIDFromToken(token);
        TaiKhoanDTO taiKhoanDTO = taiKhoanService.findOne(id).orElse(null);
        phieuNhapDTO.setTaiKhoan(taiKhoanDTO);

        phieuNhapDTO.setTongTienHang(Long.valueOf(0));
        PhieuNhapDTO result = phieuNhapService.save(phieuNhapDTO);
        Long tongTienHang = Long.valueOf(0);

        for (ChiTietPhieuNhapRequest chiTietPhieuNhap : phieuNhapRequest.getChiTietPhieuNhapList()) {
            ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = new ChiTietPhieuNhapDTO();
            chiTietPhieuNhapDTO.setPhieuNhap(result);
            if (chiTietPhieuNhap.getSoLuong() <= 0) {
                throw new BadRequestAlertException("BAD_REQUEST", ENTITY_NAME, "Số lượng nhập vào không hợp lệ");
            }
            chiTietPhieuNhapDTO.setSoLuong(chiTietPhieuNhap.getSoLuong());

            Optional<NguyenLieuDTO> nguyenLieu = nguyenLieuService.findOne(chiTietPhieuNhap.getIdNguyenLieu());
            NguyenLieuDTO nguyenLieuDTO;
            if (nguyenLieu.isPresent()) {
                nguyenLieuDTO = nguyenLieu.get();
            } else {
                throw new Exception("Không tìm thấy nguyên liệu có ID " + chiTietPhieuNhap.getIdNguyenLieu());
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

            chiTietPhieuNhapDTO.setNguyenLieu(nguyenLieuDTO);
            chiTietPhieuNhapDTO.setThanhTien(
                (chiTietPhieuNhap.getSoLuong() * nguyenLieuDTO.getGiaNhap()) +
                (chiTietPhieuNhap.getSoLuong() * nguyenLieuDTO.getGiaNhap() * nguyenLieuDTO.getvAT()) /
                100
            );

            ChiTietPhieuNhapDTO chiTietResult = chiTietPhieuNhapService.save(chiTietPhieuNhapDTO);
            log.debug(String.valueOf(chiTietResult.getThanhTien()));
            tongTienHang = tongTienHang + chiTietResult.getThanhTien();
        }

        PhieuNhapDTO phieuNhapDTO1 = new PhieuNhapDTO();
        phieuNhapDTO1.setId(result.getId());
        phieuNhapDTO1.setTongTienHang(tongTienHang - tongTienHang * result.getGiamGia() / 100 + result.getPhiShip());
        phieuNhapDTO1.setvAT(result.getvAT());
        phieuNhapDTO1.setTaiKhoan(result.getTaiKhoan());
        phieuNhapDTO1.setNhaKho(result.getNhaKho());
        phieuNhapDTO1.setNgayLap(result.getNgayLap());
        phieuNhapDTO1.setNhaCungCap(result.getNhaCungCap());
        phieuNhapDTO1.setGhiChu(result.getGhiChu());
        phieuNhapDTO1.setGiamGia(result.getGiamGia());
        Long tienNo = tongTienHang - result.getTongTienThanhToan();
        phieuNhapDTO1.setTienNo(tienNo);
        log.debug("tien no: " + tienNo);
        phieuNhapDTO1.setTongTienThanhToan(result.getTongTienThanhToan());
        //        phieuNhapDTO1.setTienNo(result.getTienNo());
        phieuNhapDTO1.setPhiShip(result.getPhiShip());

        PhieuNhapDTO updateTienHang = phieuNhapService.update(phieuNhapDTO1);

        PhieuNhapVM phieuNhapVM = new PhieuNhapVM();
        phieuNhapVM.setPhieuNhapDTO(updateTienHang);
        log.debug("tien no sau khi update: " + updateTienHang.getTienNo());

        List<ChiTietPhieuNhap> chiTietPhieuNhapDTOList = chiTietPhieuNhapService.findByPhieuNhapId(result.getId());
        phieuNhapVM.setChiTietPhieuNhapList(chiTietPhieuNhapDTOList);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(nhaCungCap.getEmail());
        helper.setSubject("V/v NHẬP NGUYÊN LIỆU CHO NHÀ HÀNG Serpent Restaurant");

        String mailData1 =
            "<html>" +
            "<body>" +
            "<h2 style=\"text-align: center;\">Phiếu Nhập Nguyên Liệu</h2>" +
            "<table border=\"1\">" +
            "<tr>" +
            "<th>Nguyên Liệu</th>" +
            "<th>Số Lượng</th>" +
            "<th>Giá Nhập</th>" +
            "<th>Tổng Tiền</th>" +
            "</tr>";
        for (ChiTietPhieuNhapRequest chiTietPhieuNhap : phieuNhapRequest.getChiTietPhieuNhapList()) {
            Optional<NguyenLieuDTO> nguyenLieu = nguyenLieuService.findOne(chiTietPhieuNhap.getIdNguyenLieu());
            NguyenLieuDTO nguyenLieuDTO;
            if (nguyenLieu.isPresent()) {
                nguyenLieuDTO = nguyenLieu.get();
            } else {
                throw new Exception("Không tìm thấy nguyên liệu có ID " + chiTietPhieuNhap.getIdNguyenLieu());
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

            Long thanhTien =
                (
                    (chiTietPhieuNhap.getSoLuong() * nguyenLieuDTO.getGiaNhap()) +
                    (chiTietPhieuNhap.getSoLuong() * nguyenLieuDTO.getGiaNhap() * nguyenLieuDTO.getvAT()) /
                    100
                );

            String mailData2 =
                "<tr>" +
                "<td>" +
                nguyenLieuDTO.getTenNguyenLieu() +
                "</td>" +
                "<td>" +
                chiTietPhieuNhap.getSoLuong() +
                " " +
                nguyenLieuDTO.getDonViTinh() +
                "</td>" +
                "<td>" +
                nguyenLieuDTO.getGiaNhap() +
                "đ</td>" +
                "<td>" +
                thanhTien +
                "đ</td>" +
                "</tr>";
            mailData1 = mailData1 + mailData2;
        }

        mailData1 =
            mailData1 +
            "</table><h4>Tổng Tiền Hàng: " +
            updateTienHang.getTongTienHang() +
            "đ</h4>" +
            "<h4>Tổng Tiền Thanh Toán: " +
            updateTienHang.getTongTienThanhToan() +
            "đ</h4>" +
            "<h4>Tiền Còn Nợ: " +
            updateTienHang.getTienNo() +
            "đ</h4></body></html>";
        helper.setText(mailData1, true);

        javaMailSender.send(message);

        log.debug("REST request to save PhieuNhap : {}", phieuNhapDTO);
        if (phieuNhapDTO.getId() != null) {
            throw new BadRequestAlertException("A new phieuNhap cannot already have an ID", ENTITY_NAME, "idexists");
        }

        return ResponseEntity
            .created(new URI("/api/phieu-nhap/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(phieuNhapVM);
        //        return ResponseEntity.ok(phieuNhapRequest);
    }

    @PostMapping("/phieu-nhap/excel")
    public ResponseEntity<?> createPhieuNhapByExcelFile(
        @ModelAttribute PhieuNhapExcelRequest phieuNhapExcelRequest,
        @RequestHeader("Authorization") String jwt
    ) throws Exception {
        PhieuNhapDTO phieuNhapDTO = new PhieuNhapDTO();
        phieuNhapDTO.setNgayLap(Instant.now());
        phieuNhapDTO.setPhiShip(phieuNhapExcelRequest.getPhiShip());
        phieuNhapDTO.setTongTienThanhToan(phieuNhapExcelRequest.getTongTienThanhToan());
        phieuNhapDTO.setGiamGia(phieuNhapExcelRequest.getGiamGia());
        phieuNhapDTO.setTienNo(phieuNhapExcelRequest.getTienNo());
        phieuNhapDTO.setGhiChu(phieuNhapExcelRequest.getGhiChu());
        phieuNhapDTO.setvAT(Long.valueOf(0));
        //        phieuNhapDTO.setTrangThai("Đã Gửi");

        Optional<NhaKhoDTO> nhaKhoDTO = nhaKhoService.findOne(phieuNhapExcelRequest.getIdKho());
        NhaKhoDTO nhaKho;
        if (nhaKhoDTO.isPresent()) {
            nhaKho = nhaKhoDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhà kho có ID " + phieuNhapExcelRequest.getIdKho());
        }
        NhaKhoDTO nhaKhoDTO1 = new NhaKhoDTO();
        nhaKhoDTO1.setId(nhaKho.getId());
        nhaKhoDTO1.setNgayTao(nhaKho.getNgayTao());
        nhaKhoDTO1.setTenKho(nhaKho.getTenKho());
        nhaKhoDTO1.setLoai(nhaKho.getLoai());
        nhaKhoDTO1.setDiaChi(nhaKho.getDiaChi());
        phieuNhapDTO.setNhaKho(nhaKhoDTO1);

        Optional<NhaCungCapDTO> nhaCungCapDTO = nhaCungCapService.findOne(phieuNhapExcelRequest.getIdNCC());
        NhaCungCapDTO nhaCungCap;
        if (nhaCungCapDTO.isPresent()) {
            nhaCungCap = nhaCungCapDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhà cung cấp có ID " + phieuNhapExcelRequest.getIdNCC());
        }
        NhaCungCapDTO nhaCungCapDTO1 = new NhaCungCapDTO();
        nhaCungCapDTO1.setId(nhaCungCap.getId());
        nhaCungCapDTO1.setTenNCC(nhaCungCap.getTenNCC());
        nhaCungCapDTO1.setEmail(nhaCungCap.getEmail());
        nhaCungCapDTO1.setSoDT(nhaCungCap.getSoDT());
        nhaCungCapDTO1.setDiaChi(nhaCungCap.getDiaChi());
        nhaCungCapDTO1.setGhiChu(nhaCungCapDTO1.getGhiChu());
        phieuNhapDTO.setNhaCungCap(nhaCungCapDTO1);

        String token = jwt.replace("Bearer ", "");
        GetUserID getUserID = new GetUserID();
        UUID id = getUserID.getIDFromToken(token);
        TaiKhoanDTO taiKhoanDTO = taiKhoanService.findOne(id).orElse(null);
        phieuNhapDTO.setTaiKhoan(taiKhoanDTO);

        phieuNhapDTO.setTongTienHang(Long.valueOf(0));
        PhieuNhapDTO result = phieuNhapService.save(phieuNhapDTO);
        Long tongTienHang = Long.valueOf(0);

        String mailData1 =
            "<html>" +
            "<body>" +
            "<h2 style=\"text-align: center;\">Phiếu Nhập Nguyên Liệu</h2>" +
            "<table border=\"1\">" +
            "<tr>" +
            "<th>Nguyên Liệu</th>" +
            "<th>Số Lượng</th>" +
            "<th>Giá Nhập</th>" +
            "<th>Tổng Tiền</th>" +
            "</tr>";

        Workbook workbook = WorkbookFactory.create(phieuNhapExcelRequest.getFile().getInputStream());

        Sheet sheet = workbook.getSheetAt(0);
        boolean isFirstRow = true;
        for (Row chiTietPhieuNhap : sheet) {
            if (isFirstRow) {
                isFirstRow = false;
                continue; // Bỏ qua dòng đầu tiên
            }
            ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = new ChiTietPhieuNhapDTO();
            chiTietPhieuNhapDTO.setPhieuNhap(result);
            Long soLuong = (long) chiTietPhieuNhap.getCell(1).getNumericCellValue();
            Long idNguyenLieu = (long) chiTietPhieuNhap.getCell(0).getNumericCellValue();
            chiTietPhieuNhapDTO.setSoLuong(soLuong);

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

            chiTietPhieuNhapDTO.setNguyenLieu(nguyenLieuDTO);
            chiTietPhieuNhapDTO.setThanhTien(
                (soLuong * nguyenLieuDTO.getGiaNhap()) + (soLuong * nguyenLieuDTO.getGiaNhap() * nguyenLieuDTO.getvAT()) / 100
            );

            ChiTietPhieuNhapDTO chiTietResult = chiTietPhieuNhapService.save(chiTietPhieuNhapDTO);
            log.debug(String.valueOf(chiTietResult.getThanhTien()));
            tongTienHang = tongTienHang + chiTietResult.getThanhTien();

            String mailData2 =
                "<tr>" +
                "<td>" +
                nguyenLieuDTO.getTenNguyenLieu() +
                "</td>" +
                "<td>" +
                chiTietResult.getSoLuong() +
                " " +
                nguyenLieuDTO.getDonViTinh() +
                "</td>" +
                "<td>" +
                nguyenLieuDTO.getGiaNhap() +
                "đ</td>" +
                "<td>" +
                chiTietResult.getThanhTien() +
                "đ</td>" +
                "</tr>";
            mailData1 = mailData1 + mailData2;
        }

        PhieuNhapDTO phieuNhapDTO1 = new PhieuNhapDTO();
        phieuNhapDTO1.setId(result.getId());
        phieuNhapDTO1.setTongTienHang(tongTienHang - tongTienHang * result.getGiamGia() / 100 + result.getPhiShip());
        phieuNhapDTO1.setvAT(result.getvAT());
        phieuNhapDTO1.setTaiKhoan(result.getTaiKhoan());
        phieuNhapDTO1.setNhaKho(result.getNhaKho());
        phieuNhapDTO1.setNgayLap(result.getNgayLap());
        phieuNhapDTO1.setNhaCungCap(result.getNhaCungCap());
        phieuNhapDTO1.setGhiChu(result.getGhiChu());
        phieuNhapDTO1.setGiamGia(result.getGiamGia());
        phieuNhapDTO1.setTienNo(result.getTienNo());
        phieuNhapDTO1.setTongTienThanhToan(result.getTongTienThanhToan());
        phieuNhapDTO1.setTienNo(result.getTienNo());
        phieuNhapDTO1.setPhiShip(result.getPhiShip());

        PhieuNhapDTO updateTienHang = phieuNhapService.update(phieuNhapDTO1);

        PhieuNhapVM phieuNhapVM = new PhieuNhapVM();
        phieuNhapVM.setPhieuNhapDTO(updateTienHang);

        List<ChiTietPhieuNhap> chiTietPhieuNhapDTOList = chiTietPhieuNhapService.findByPhieuNhapId(result.getId());
        phieuNhapVM.setChiTietPhieuNhapList(chiTietPhieuNhapDTOList);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(nhaCungCap.getEmail());
        helper.setSubject("V/v NHẬP NGUYÊN LIỆU CHO NHÀ HÀNG SERPENT RESTAURANT");

        mailData1 =
            mailData1 +
            "</table><h4>Tổng Tiền Hàng: " +
            updateTienHang.getTongTienHang() +
            "đ</h4>" +
            "<h4>Tổng Tiền Thanh Toán: " +
            updateTienHang.getTongTienThanhToan() +
            "đ</h4>" +
            "<h4>Tiền Còn Nợ: " +
            updateTienHang.getTienNo() +
            "đ</h4></body></html>";
        helper.setText(mailData1, true);

        javaMailSender.send(message);

        log.debug("REST request to save PhieuNhap : {}", phieuNhapDTO);
        if (phieuNhapDTO.getId() != null) {
            throw new BadRequestAlertException("A new phieuNhap cannot already have an ID", ENTITY_NAME, "idexists");
        }

        return ResponseEntity
            .created(new URI("/api/phieu-nhap/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(phieuNhapVM);
        //        return ResponseEntity.ok(phieuNhapRequest);
    }

    /**
     * {@code PUT  /phieu-nhaps/:id} : Updates an existing phieuNhap.
     *
     * @param id the id of the phieuNhapDTO to save.
     * @param phieuNhapDTO the phieuNhapDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phieuNhapDTO,
     * or with status {@code 400 (Bad Request)} if the phieuNhapDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phieuNhapDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //    @PutMapping("/phieu-nhap/{id}")
    //    public ResponseEntity<PhieuNhapDTO> updatePhieuNhap(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @Valid @RequestBody PhieuNhapDTO phieuNhapDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to update PhieuNhap : {}, {}", id, phieuNhapDTO);
    //        if (phieuNhapDTO.getId() == null) {
    //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, phieuNhapDTO.getId())) {
    //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!phieuNhapRepository.existsById(id)) {
    //            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        PhieuNhapDTO result = phieuNhapService.update(phieuNhapDTO);
    //        return ResponseEntity
    //            .ok()
    //            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phieuNhapDTO.getId().toString()))
    //            .body(result);
    //    }
    //
    //    /**
    //     * {@code PATCH  /phieu-nhaps/:id} : Partial updates given fields of an existing phieuNhap, field will ignore if it is null
    //     *
    //     * @param id the id of the phieuNhapDTO to save.
    //     * @param phieuNhapDTO the phieuNhapDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phieuNhapDTO,
    //     * or with status {@code 400 (Bad Request)} if the phieuNhapDTO is not valid,
    //     * or with status {@code 404 (Not Found)} if the phieuNhapDTO is not found,
    //     * or with status {@code 500 (Internal Server Error)} if the phieuNhapDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PatchMapping(value = "/phieu-nhap/{id}", consumes = { "application/json", "application/merge-patch+json" })
    //    public ResponseEntity<PhieuNhapDTO> partialUpdatePhieuNhap(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @NotNull @RequestBody PhieuNhapDTO phieuNhapDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to partial update PhieuNhap partially : {}, {}", id, phieuNhapDTO);
    //        if (phieuNhapDTO.getId() == null) {
    //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, phieuNhapDTO.getId())) {
    //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!phieuNhapRepository.existsById(id)) {
    //            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        Optional<PhieuNhapDTO> result = phieuNhapService.partialUpdate(phieuNhapDTO);
    //
    //        return ResponseUtil.wrapOrNotFound(
    //            result,
    //            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phieuNhapDTO.getId().toString())
    //        );
    //    }

    /**
     * {@code GET  /phieu-nhaps} : get all the phieuNhaps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phieuNhaps in body.
     */
    @GetMapping("/phieu-nhap")
    public ResponseEntity<List<PhieuNhapDTO>> getAllPhieuNhaps(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PhieuNhaps");
        Page<PhieuNhapDTO> page = phieuNhapService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phieu-nhaps/:id} : get the "id" phieuNhap.
     *
     * @param id the id of the phieuNhapDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phieuNhapDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phieu-nhap/{id}")
    public ResponseEntity<?> getPhieuNhap(@PathVariable Long id) {
        log.debug("REST request to get PhieuNhap : {}", id);
        Optional<PhieuNhapDTO> phieuNhapDTO = phieuNhapService.findOne(id);
        PhieuNhapDTO phieuNhapDTO1 = new PhieuNhapDTO();
        if (phieuNhapDTO.isPresent()) {
            phieuNhapDTO1 = phieuNhapDTO.get();
        }
        //        PhieuNhapDTO phieuNhapDTO2 = new PhieuNhapDTO();
        //        phieuNhapDTO2.setPhiShip(phieuNhapDTO1.getPhiShip());

        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapService.findByPhieuNhapId(id);

        PhieuNhapVM phieuNhapVM = new PhieuNhapVM();
        phieuNhapVM.setChiTietPhieuNhapList(chiTietPhieuNhapList);
        phieuNhapVM.setPhieuNhapDTO(phieuNhapDTO1);
        return ResponseEntity.ok(phieuNhapVM);
    }
    //    /**
    //     * {@code DELETE  /phieu-nhaps/:id} : delete the "id" phieuNhap.
    //     *
    //     * @param id the id of the phieuNhapDTO to delete.
    //     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    //     */
    //    @DeleteMapping("/phieu-nhap/{id}")
    //    public ResponseEntity<Void> deletePhieuNhap(@PathVariable Long id) {
    //        log.debug("REST request to delete PhieuNhap : {}", id);
    //        phieuNhapService.delete(id);
    //        return ResponseEntity
    //            .noContent()
    //            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
    //            .build();
    //    }
}
