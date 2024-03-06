package warehouse.management.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import warehouse.management.app.repository.ChiTietPhieuNhapRepository;
import warehouse.management.app.service.ChiTietPhieuNhapService;
import warehouse.management.app.service.dto.ChiTietPhieuNhapDTO;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link warehouse.management.app.domain.ChiTietPhieuNhap}.
 */
@RestController
@RequestMapping("/api")
public class ChiTietPhieuNhapResource {

    private final Logger log = LoggerFactory.getLogger(ChiTietPhieuNhapResource.class);

    private static final String ENTITY_NAME = "lvProjectChiTietPhieuNhap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChiTietPhieuNhapService chiTietPhieuNhapService;

    private final ChiTietPhieuNhapRepository chiTietPhieuNhapRepository;

    public ChiTietPhieuNhapResource(
        ChiTietPhieuNhapService chiTietPhieuNhapService,
        ChiTietPhieuNhapRepository chiTietPhieuNhapRepository
    ) {
        this.chiTietPhieuNhapService = chiTietPhieuNhapService;
        this.chiTietPhieuNhapRepository = chiTietPhieuNhapRepository;
    }

    /**
     * {@code POST  /chi-tiet-phieu-nhaps} : Create a new chiTietPhieuNhap.
     *
     * @param chiTietPhieuNhapDTO the chiTietPhieuNhapDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chiTietPhieuNhapDTO, or with status {@code 400 (Bad Request)} if the chiTietPhieuNhap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chi-tiet-phieu-nhaps")
    public ResponseEntity<ChiTietPhieuNhapDTO> createChiTietPhieuNhap(@Valid @RequestBody ChiTietPhieuNhapDTO chiTietPhieuNhapDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChiTietPhieuNhap : {}", chiTietPhieuNhapDTO);
        if (chiTietPhieuNhapDTO.getId() != null) {
            throw new BadRequestAlertException("A new chiTietPhieuNhap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChiTietPhieuNhapDTO result = chiTietPhieuNhapService.save(chiTietPhieuNhapDTO);
        return ResponseEntity
            .created(new URI("/api/chi-tiet-phieu-nhaps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chi-tiet-phieu-nhaps/:id} : Updates an existing chiTietPhieuNhap.
     *
     * @param id the id of the chiTietPhieuNhapDTO to save.
     * @param chiTietPhieuNhapDTO the chiTietPhieuNhapDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiTietPhieuNhapDTO,
     * or with status {@code 400 (Bad Request)} if the chiTietPhieuNhapDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chiTietPhieuNhapDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chi-tiet-phieu-nhaps/{id}")
    public ResponseEntity<ChiTietPhieuNhapDTO> updateChiTietPhieuNhap(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChiTietPhieuNhapDTO chiTietPhieuNhapDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChiTietPhieuNhap : {}, {}", id, chiTietPhieuNhapDTO);
        if (chiTietPhieuNhapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiTietPhieuNhapDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiTietPhieuNhapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChiTietPhieuNhapDTO result = chiTietPhieuNhapService.update(chiTietPhieuNhapDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chiTietPhieuNhapDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chi-tiet-phieu-nhaps/:id} : Partial updates given fields of an existing chiTietPhieuNhap, field will ignore if it is null
     *
     * @param id the id of the chiTietPhieuNhapDTO to save.
     * @param chiTietPhieuNhapDTO the chiTietPhieuNhapDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiTietPhieuNhapDTO,
     * or with status {@code 400 (Bad Request)} if the chiTietPhieuNhapDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chiTietPhieuNhapDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chiTietPhieuNhapDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chi-tiet-phieu-nhaps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChiTietPhieuNhapDTO> partialUpdateChiTietPhieuNhap(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChiTietPhieuNhapDTO chiTietPhieuNhapDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChiTietPhieuNhap partially : {}, {}", id, chiTietPhieuNhapDTO);
        if (chiTietPhieuNhapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiTietPhieuNhapDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiTietPhieuNhapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChiTietPhieuNhapDTO> result = chiTietPhieuNhapService.partialUpdate(chiTietPhieuNhapDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chiTietPhieuNhapDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /chi-tiet-phieu-nhaps} : get all the chiTietPhieuNhaps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chiTietPhieuNhaps in body.
     */
    @GetMapping("/chi-tiet-phieu-nhaps")
    public ResponseEntity<List<ChiTietPhieuNhapDTO>> getAllChiTietPhieuNhaps(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ChiTietPhieuNhaps");
        Page<ChiTietPhieuNhapDTO> page = chiTietPhieuNhapService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chi-tiet-phieu-nhaps/:id} : get the "id" chiTietPhieuNhap.
     *
     * @param id the id of the chiTietPhieuNhapDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chiTietPhieuNhapDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chi-tiet-phieu-nhaps/{id}")
    public ResponseEntity<ChiTietPhieuNhapDTO> getChiTietPhieuNhap(@PathVariable Long id) {
        log.debug("REST request to get ChiTietPhieuNhap : {}", id);
        Optional<ChiTietPhieuNhapDTO> chiTietPhieuNhapDTO = chiTietPhieuNhapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chiTietPhieuNhapDTO);
    }

    /**
     * {@code DELETE  /chi-tiet-phieu-nhaps/:id} : delete the "id" chiTietPhieuNhap.
     *
     * @param id the id of the chiTietPhieuNhapDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chi-tiet-phieu-nhaps/{id}")
    public ResponseEntity<Void> deleteChiTietPhieuNhap(@PathVariable Long id) {
        log.debug("REST request to delete ChiTietPhieuNhap : {}", id);
        chiTietPhieuNhapService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
