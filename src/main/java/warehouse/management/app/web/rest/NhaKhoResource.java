package warehouse.management.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import warehouse.management.app.domain.ChiTietKho;
import warehouse.management.app.domain.NhaKho;
import warehouse.management.app.repository.ChiTietKhoRepository;
import warehouse.management.app.repository.NhaKhoRepository;
import warehouse.management.app.service.ChiTietKhoService;
import warehouse.management.app.service.NguyenLieuService;
import warehouse.management.app.service.NhaKhoService;
import warehouse.management.app.service.dto.ChiTietKhoDTO;
import warehouse.management.app.service.dto.NhaKhoDTO;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;
import warehouse.management.app.web.rest.vm.NhaKhoVM;

/**
 * REST controller for managing {@link warehouse.management.app.domain.NhaKho}.
 */
@RestController
@RequestMapping("/api")
public class NhaKhoResource {

    private final Logger log = LoggerFactory.getLogger(NhaKhoResource.class);

    private static final String ENTITY_NAME = "lvProjectNhaKho";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhaKhoService nhaKhoService;

    private final NhaKhoRepository nhaKhoRepository;

    private final ChiTietKhoService chiTietKhoService;

    private final ChiTietKhoRepository chiTietKhoRepository;

    private final NguyenLieuService nguyenLieuService;

    public NhaKhoResource(
        NhaKhoService nhaKhoService,
        NhaKhoRepository nhaKhoRepository,
        ChiTietKhoService chiTietKhoService,
        ChiTietKhoRepository chiTietKhoRepository,
        NguyenLieuService nguyenLieuService
    ) {
        this.nhaKhoService = nhaKhoService;
        this.nhaKhoRepository = nhaKhoRepository;
        this.chiTietKhoService = chiTietKhoService;
        this.chiTietKhoRepository = chiTietKhoRepository;
        this.nguyenLieuService = nguyenLieuService;
    }

    /**
     * {@code POST  /nha-khos} : Create a new nhaKho.
     *
     * @param nhaKhoDTO the nhaKhoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhaKhoDTO, or with status {@code 400 (Bad Request)} if the nhaKho has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    @PostMapping("/nha-kho")
    public ResponseEntity<NhaKhoDTO> createNhaKho(@Valid @RequestBody NhaKhoDTO nhaKhoDTO) throws URISyntaxException {
        log.debug("REST request to save NhaKho : {}", nhaKhoDTO);
        nhaKhoDTO.setNgayTao(Instant.now());
        if (nhaKhoDTO.getId() != null) {
            throw new BadRequestAlertException("A new nhaKho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhaKhoDTO result = nhaKhoService.save(nhaKhoDTO);
        return ResponseEntity
            .created(new URI("/api/nha-kho/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nha-khos/:id} : Updates an existing nhaKho.
     *
     * @param id the id of the nhaKhoDTO to save.
     * @param nhaKhoDTO the nhaKhoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhaKhoDTO,
     * or with status {@code 400 (Bad Request)} if the nhaKhoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhaKhoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nha-kho/{id}")
    public ResponseEntity<NhaKhoDTO> updateNhaKho(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NhaKhoDTO nhaKhoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NhaKho : {}, {}", id, nhaKhoDTO);
        if (nhaKhoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhaKhoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhaKhoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NhaKhoDTO result = nhaKhoService.update(nhaKhoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhaKhoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nha-khos/:id} : Partial updates given fields of an existing nhaKho, field will ignore if it is null
     *
     * @param id the id of the nhaKhoDTO to save.
     * @param nhaKhoDTO the nhaKhoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhaKhoDTO,
     * or with status {@code 400 (Bad Request)} if the nhaKhoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nhaKhoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nhaKhoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nha-kho/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NhaKhoDTO> partialUpdateNhaKho(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NhaKhoDTO nhaKhoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NhaKho partially : {}, {}", id, nhaKhoDTO);
        if (nhaKhoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhaKhoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhaKhoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NhaKhoDTO> result = nhaKhoService.partialUpdate(nhaKhoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhaKhoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nha-khos} : get all the nhaKhos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhaKhos in body.
     */
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    @GetMapping("/nha-kho")
    public ResponseEntity<List<NhaKhoDTO>> getAllNhaKhos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NhaKhos");
        Page<NhaKhoDTO> page = nhaKhoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nha-khos/:id} : get the "id" nhaKho.
     *
     * @param id the id of the nhaKhoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhaKhoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nha-kho/{id}")
    public ResponseEntity<?> getNhaKho(@PathVariable Long id) {
        log.debug("REST request to get NhaKho : {}", id);
        Optional<NhaKhoDTO> nhaKhoDTO = nhaKhoService.findOne(id);
        List<ChiTietKho> chiTietKhoDTOList = chiTietKhoService.findByNhaKhoId(id);
        NhaKhoVM nhaKhoVM = new NhaKhoVM();
        nhaKhoVM.setNhaKhoDTO(nhaKhoDTO);
        nhaKhoVM.setChiTietKhoDTOList(chiTietKhoDTOList);
        return ResponseEntity.ok(nhaKhoVM);
    }

    //    @GetMapping("/{id}/chitietkho")
    //    public ResponseEntity<Set<ChiTietKho>> getChiTietKhoByNhaKho(@PathVariable Long id) {
    //        NhaKho nhaKho = nhaKhoService.getNhaKhoById(id);
    //        if (nhaKho != null) {
    //            Set<ChiTietKho> chiTietKhoList = nhaKhoService.getChiTietKhoByNhaKho(nhaKho);
    //            return new ResponseEntity<>(chiTietKhoList, HttpStatus.OK);
    //        } else {
    //            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //        }
    //    }

    /**
     * {@code DELETE  /nha-khos/:id} : delete the "id" nhaKho.
     *
     * @param id the id of the nhaKhoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nha-kho/{id}")
    public ResponseEntity<Void> deleteNhaKho(@PathVariable Long id) {
        log.debug("REST request to delete NhaKho : {}", id);
        nhaKhoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
