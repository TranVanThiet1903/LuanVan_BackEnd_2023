package warehouse.management.app.web.rest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import jodd.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import warehouse.management.app.repository.NguyenLieuRepository;
import warehouse.management.app.service.NguyenLieuService;
import warehouse.management.app.service.NhaCungCapService;
import warehouse.management.app.service.NhomNguyenLieuService;
import warehouse.management.app.service.dto.NguyenLieuDTO;
import warehouse.management.app.service.dto.NhaCungCapDTO;
import warehouse.management.app.service.dto.NhomNguyenLieuDTO;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;
import warehouse.management.app.web.rest.vm.NguyenLieuRequest;
import warehouse.management.app.web.rest.vm.UpdateNguyenLieuRequest;

/**
 * REST controller for managing {@link warehouse.management.app.domain.NguyenLieu}.
 */
@RestController
@RequestMapping("/api")
public class NguyenLieuResource {

    private final Logger log = LoggerFactory.getLogger(NguyenLieuResource.class);

    private static final String ENTITY_NAME = "lvProjectNguyenLieu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NguyenLieuService nguyenLieuService;

    private final NguyenLieuRepository nguyenLieuRepository;

    private final NhomNguyenLieuService nhomNguyenLieuService;

    private final NhaCungCapService nhaCungCapService;

    public NguyenLieuResource(
        NguyenLieuService nguyenLieuService,
        NguyenLieuRepository nguyenLieuRepository,
        NhomNguyenLieuService nhomNguyenLieuService,
        NhaCungCapService nhaCungCapService
    ) {
        this.nguyenLieuService = nguyenLieuService;
        this.nguyenLieuRepository = nguyenLieuRepository;
        this.nhomNguyenLieuService = nhomNguyenLieuService;
        this.nhaCungCapService = nhaCungCapService;
    }

    public static String getAbsolutePath(String relativePath) {
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(relativePath)).getFile());
        return file.getAbsolutePath();
    }

    /**
     * {@code POST  /nguyen-lieus} : Create a new nguyenLieu.
     *
     * @param nguyenLieuRequest the nguyenLieuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nguyenLieuDTO, or with status {@code 400 (Bad Request)} if the nguyenLieu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nguyen-lieu")
    public ResponseEntity<?> createNguyenLieu(@ModelAttribute NguyenLieuRequest nguyenLieuRequest) throws Exception {
        //        log.debug("REST request to save NguyenLieu : {}", nguyenLieuDTO);

        String fileNameToSave = "nguyenlieu_" + UUID.randomUUID() + ".jpg";
        //        String path = "/web/rest/uploads/";
        MultipartFile file = nguyenLieuRequest.getFile();
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String uploadDir = "vue_fe/public/assets/images"; // Đường dẫn tương đối đến thư mục bên ngoài mã nguồn
            String fileName = StringUtils.cleanPath(fileNameToSave);

            // Lấy đường dẫn thư mục gốc của ứng dụng
            Path appPath = Paths.get("").toAbsolutePath().normalize();

            // Tạo đường dẫn tới thư mục lưu trữ bên ngoài mã nguồn
            Path uploadPath = appPath.resolveSibling(uploadDir);

            // Tạo thư mục lưu trữ nếu chưa tồn tại
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lưu file vào thư mục lưu trữ
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            //            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }

        Optional<NhomNguyenLieuDTO> nhomNguyenLieuDTO = nhomNguyenLieuService.findOne(nguyenLieuRequest.getIdNhom());
        NhomNguyenLieuDTO nhomNguyenLieu;
        if (nhomNguyenLieuDTO.isPresent()) {
            nhomNguyenLieu = nhomNguyenLieuDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhóm nguyên liệu có ID " + nguyenLieuRequest.getIdNhom());
        }
        Optional<NhaCungCapDTO> nhaCungCapDTO = nhaCungCapService.findOne(nguyenLieuRequest.getIdNCC());
        NhaCungCapDTO nhaCungCap;
        if (nhaCungCapDTO.isPresent()) {
            nhaCungCap = nhaCungCapDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhà cung cấp có ID " + nguyenLieuRequest.getIdNCC());
        }

        NhomNguyenLieuDTO nhomNguyenLieuDTO1 = new NhomNguyenLieuDTO();
        nhomNguyenLieuDTO1.setId(nhomNguyenLieu.getId());
        nhomNguyenLieuDTO1.setTenNhom(nhomNguyenLieu.getTenNhom());

        NhaCungCapDTO nhaCungCapDTO1 = new NhaCungCapDTO();
        nhaCungCapDTO1.setId(nhaCungCap.getId());
        nhaCungCapDTO1.setEmail(nhaCungCap.getEmail());
        nhaCungCapDTO1.setDiaChi(nhaCungCap.getDiaChi());
        nhaCungCapDTO1.setSoDT(nhaCungCap.getSoDT());
        nhaCungCapDTO1.setTenNCC(nhaCungCap.getTenNCC());
        nhaCungCapDTO1.setGhiChu(nhaCungCap.getGhiChu());

        NguyenLieuDTO nguyenLieuDTO = new NguyenLieuDTO();
        nguyenLieuDTO.setTenNguyenLieu(nguyenLieuRequest.getTenNguyenLieu());
        nguyenLieuDTO.setHinhAnh(fileNameToSave);
        nguyenLieuDTO.setDonViTinh(nguyenLieuRequest.getDonViTinh());
        nguyenLieuDTO.setGiaNhap(nguyenLieuRequest.getGiaNhap());
        nguyenLieuDTO.setMoTa(nguyenLieuRequest.getMoTa());
        nguyenLieuDTO.setvAT(nguyenLieuRequest.getvAT());
        nguyenLieuDTO.setNhomNguyenLieu(nhomNguyenLieuDTO1);
        nguyenLieuDTO.setNhaCungCap(nhaCungCapDTO1);

        log.debug("REST request to save NguyenLieu : {}", nguyenLieuDTO);

        if (nguyenLieuDTO.getId() != null) {
            throw new BadRequestAlertException("A new nguyenLieu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NguyenLieuDTO result = nguyenLieuService.save(nguyenLieuDTO);
        return ResponseEntity
            .created(new URI("/api/nguyen-lieu/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nguyen-lieus/:id} : Updates an existing nguyenLieu.
     *
     * @param id the id of the nguyenLieuDTO to save.
     * @param updateNguyenLieuRequest the nguyenLieuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nguyenLieuDTO,
     * or with status {@code 400 (Bad Request)} if the nguyenLieuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nguyenLieuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nguyen-lieu/{id}")
    public ResponseEntity<?> updateNguyenLieu(
        @PathVariable(value = "id", required = false) final Long id,
        @ModelAttribute UpdateNguyenLieuRequest updateNguyenLieuRequest
    ) throws Exception {
        //        log.debug("REST request to update NguyenLieu : {}, {}", id, nguyenLieuDTO);
        if (updateNguyenLieuRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, updateNguyenLieuRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nguyenLieuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        String fileNameToSave = "nguyenlieu_" + UUID.randomUUID() + ".jpg";
        //        String path = "/web/rest/uploads/";
        MultipartFile file = updateNguyenLieuRequest.getFile();
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String uploadDir = "vue_fe/public/assets/images"; // Đường dẫn tương đối đến thư mục bên ngoài mã nguồn
            String fileName = StringUtils.cleanPath(fileNameToSave);

            // Lấy đường dẫn thư mục gốc của ứng dụng
            Path appPath = Paths.get("").toAbsolutePath().normalize();

            // Tạo đường dẫn tới thư mục lưu trữ bên ngoài mã nguồn
            Path uploadPath = appPath.resolveSibling(uploadDir);

            // Tạo thư mục lưu trữ nếu chưa tồn tại
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lưu file vào thư mục lưu trữ
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            //            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }

        Optional<NhomNguyenLieuDTO> nhomNguyenLieuDTO = nhomNguyenLieuService.findOne(updateNguyenLieuRequest.getIdNhom());
        NhomNguyenLieuDTO nhomNguyenLieu;
        if (nhomNguyenLieuDTO.isPresent()) {
            nhomNguyenLieu = nhomNguyenLieuDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhóm nguyên liệu có ID " + updateNguyenLieuRequest.getIdNhom());
        }
        Optional<NhaCungCapDTO> nhaCungCapDTO = nhaCungCapService.findOne(updateNguyenLieuRequest.getIdNCC());
        NhaCungCapDTO nhaCungCap;
        if (nhaCungCapDTO.isPresent()) {
            nhaCungCap = nhaCungCapDTO.get();
        } else {
            throw new Exception("Không tìm thấy nhà cung cấp có ID " + updateNguyenLieuRequest.getIdNCC());
        }

        NhomNguyenLieuDTO nhomNguyenLieuDTO1 = new NhomNguyenLieuDTO();
        nhomNguyenLieuDTO1.setId(nhomNguyenLieu.getId());
        nhomNguyenLieuDTO1.setTenNhom(nhomNguyenLieu.getTenNhom());

        NhaCungCapDTO nhaCungCapDTO1 = new NhaCungCapDTO();
        nhaCungCapDTO1.setId(nhaCungCap.getId());
        nhaCungCapDTO1.setEmail(nhaCungCap.getEmail());
        nhaCungCapDTO1.setDiaChi(nhaCungCap.getDiaChi());
        nhaCungCapDTO1.setSoDT(nhaCungCap.getSoDT());
        nhaCungCapDTO1.setTenNCC(nhaCungCap.getTenNCC());
        nhaCungCapDTO1.setGhiChu(nhaCungCap.getGhiChu());

        NguyenLieuDTO nguyenLieuDTO = new NguyenLieuDTO();
        nguyenLieuDTO.setId(updateNguyenLieuRequest.getId());
        nguyenLieuDTO.setTenNguyenLieu(updateNguyenLieuRequest.getTenNguyenLieu());
        nguyenLieuDTO.setHinhAnh(fileNameToSave);
        nguyenLieuDTO.setDonViTinh(updateNguyenLieuRequest.getDonViTinh());
        nguyenLieuDTO.setGiaNhap(updateNguyenLieuRequest.getGiaNhap());
        nguyenLieuDTO.setMoTa(updateNguyenLieuRequest.getMoTa());
        nguyenLieuDTO.setvAT(updateNguyenLieuRequest.getvAT());
        nguyenLieuDTO.setNhomNguyenLieu(nhomNguyenLieuDTO1);
        nguyenLieuDTO.setNhaCungCap(nhaCungCapDTO1);

        NguyenLieuDTO result = nguyenLieuService.update(nguyenLieuDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nguyenLieuDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nguyen-lieus/:id} : Partial updates given fields of an existing nguyenLieu, field will ignore if it is null
     *
     * @param id the id of the nguyenLieuDTO to save.
     * @param updateNguyenLieuRequest the nguyenLieuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nguyenLieuDTO,
     * or with status {@code 400 (Bad Request)} if the nguyenLieuDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nguyenLieuDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nguyenLieuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nguyen-lieu/{id}") //, consumes = { "application/json", "application/merge-patch+json" }
    public ResponseEntity<?> partialUpdateNguyenLieu(
        @PathVariable(value = "id", required = false) final Long id,
        @ModelAttribute UpdateNguyenLieuRequest updateNguyenLieuRequest
    ) throws Exception {
        log.debug("ten:" + updateNguyenLieuRequest.getTenNguyenLieu());
        log.debug("idNhom:" + updateNguyenLieuRequest.getIdNhom());
        log.debug("idNCC:" + updateNguyenLieuRequest.getIdNCC());
        log.debug("moTa:" + updateNguyenLieuRequest.getMoTa());
        log.debug("id:" + updateNguyenLieuRequest.getId());
        log.debug("donViTinh:" + updateNguyenLieuRequest.getDonViTinh());
        log.debug("vat:" + updateNguyenLieuRequest.getvAT());
        log.debug("gia:" + updateNguyenLieuRequest.getGiaNhap());

        Optional<NguyenLieuDTO> nguyenLieuDTOCheck = nguyenLieuService.findOne(id);

        //        log.debug("REST request to partial update NguyenLieu partially : {}, {}", id, nguyenLieuDTO);
        if (updateNguyenLieuRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, updateNguyenLieuRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nguyenLieuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if (updateNguyenLieuRequest.getIdNhom() != null) {
            throw new BadRequestAlertException("Không thể thay đổi ID nhóm nguyên liệu trong chức năng này.", ENTITY_NAME, "idNhomnotnull");
        }

        if (updateNguyenLieuRequest.getIdNCC() != null) {
            throw new BadRequestAlertException("Không thể thay đổi ID nhà cung cấp trong chức năng này.", ENTITY_NAME, "idNCCnotnull");
        }

        String fileNameToSave = "nguyenlieu_" + UUID.randomUUID() + ".jpg";

        NguyenLieuDTO nguyenLieuDTO = new NguyenLieuDTO();

        if (updateNguyenLieuRequest.getFile() != null) {
            //        String path = "/web/rest/uploads/";
            MultipartFile file = updateNguyenLieuRequest.getFile();
            try {
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("File is empty");
                }

                String uploadDir = "vue_fe/public/assets/images"; // Đường dẫn tương đối đến thư mục bên ngoài mã nguồn
                String fileName = StringUtils.cleanPath(fileNameToSave);

                // Lấy đường dẫn thư mục gốc của ứng dụng
                Path appPath = Paths.get("").toAbsolutePath().normalize();

                // Tạo đường dẫn tới thư mục lưu trữ bên ngoài mã nguồn
                Path uploadPath = appPath.resolveSibling(uploadDir);

                // Tạo thư mục lưu trữ nếu chưa tồn tại
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Lưu file vào thư mục lưu trữ
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                nguyenLieuDTO.setHinhAnh(fileNameToSave);
                //            return ResponseEntity.ok("File uploaded successfully");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
            }
        } else nguyenLieuDTO.setHinhAnh(nguyenLieuDTOCheck.get().getHinhAnh());

        nguyenLieuDTO.setId(updateNguyenLieuRequest.getId());
        if (updateNguyenLieuRequest.getTenNguyenLieu() != null) {
            nguyenLieuDTO.setTenNguyenLieu(updateNguyenLieuRequest.getTenNguyenLieu());
        }

        if (updateNguyenLieuRequest.getDonViTinh() != null) {
            nguyenLieuDTO.setDonViTinh(updateNguyenLieuRequest.getDonViTinh());
        }
        if (updateNguyenLieuRequest.getGiaNhap() != null) {
            nguyenLieuDTO.setGiaNhap(updateNguyenLieuRequest.getGiaNhap());
            log.debug("hello");
        }
        if (updateNguyenLieuRequest.getMoTa() != null) {
            nguyenLieuDTO.setMoTa(updateNguyenLieuRequest.getMoTa());
        }
        if (updateNguyenLieuRequest.getvAT() != null) {
            nguyenLieuDTO.setvAT(updateNguyenLieuRequest.getvAT());
        }

        Optional<NguyenLieuDTO> result = nguyenLieuService.partialUpdate(nguyenLieuDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nguyenLieuDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nguyen-lieus} : get all the nguyenLieus.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nguyenLieus in body.
     */
    @GetMapping("/nguyen-lieu")
    public ResponseEntity<List<NguyenLieuDTO>> getAllNguyenLieus(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NguyenLieus");
        Page<NguyenLieuDTO> page = nguyenLieuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nguyen-lieus/:id} : get the "id" nguyenLieu.
     *
     * @param id the id of the nguyenLieuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nguyenLieuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nguyen-lieu/{id}")
    public ResponseEntity<NguyenLieuDTO> getNguyenLieu(@PathVariable Long id) {
        log.debug("REST request to get NguyenLieu : {}", id);
        Optional<NguyenLieuDTO> nguyenLieuDTO = nguyenLieuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nguyenLieuDTO);
    }

    /**
     * {@code DELETE  /nguyen-lieus/:id} : delete the "id" nguyenLieu.
     *
     * @param id the id of the nguyenLieuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nguyen-lieu/{id}")
    public ResponseEntity<Void> deleteNguyenLieu(@PathVariable Long id) {
        log.debug("REST request to delete NguyenLieu : {}", id);
        nguyenLieuService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
