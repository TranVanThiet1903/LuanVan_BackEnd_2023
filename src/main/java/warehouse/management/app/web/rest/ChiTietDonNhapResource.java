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
import warehouse.management.app.repository.ChiTietDonNhapRepository;
import warehouse.management.app.service.ChiTietDonNhapService;
import warehouse.management.app.service.dto.ChiTietDonNhapDTO;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link warehouse.management.app.domain.ChiTietDonNhap}.
 */
@RestController
@RequestMapping("/api")
public class ChiTietDonNhapResource {

    private final Logger log = LoggerFactory.getLogger(ChiTietDonNhapResource.class);

    private static final String ENTITY_NAME = "lvProjectChiTietDonNhap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChiTietDonNhapService chiTietDonNhapService;

    private final ChiTietDonNhapRepository chiTietDonNhapRepository;

    public ChiTietDonNhapResource(ChiTietDonNhapService chiTietDonNhapService, ChiTietDonNhapRepository chiTietDonNhapRepository) {
        this.chiTietDonNhapService = chiTietDonNhapService;
        this.chiTietDonNhapRepository = chiTietDonNhapRepository;
    }

    /**
     * {@code POST  /chi-tiet-don-nhaps} : Create a new chiTietDonNhap.
     *
     * @param chiTietDonNhapDTO the chiTietDonNhapDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chiTietDonNhapDTO, or with status {@code 400 (Bad Request)} if the chiTietDonNhap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chi-tiet-don-nhaps")
    public ResponseEntity<ChiTietDonNhapDTO> createChiTietDonNhap(@Valid @RequestBody ChiTietDonNhapDTO chiTietDonNhapDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChiTietDonNhap : {}", chiTietDonNhapDTO);
        if (chiTietDonNhapDTO.getId() != null) {
            throw new BadRequestAlertException("A new chiTietDonNhap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChiTietDonNhapDTO result = chiTietDonNhapService.save(chiTietDonNhapDTO);
        return ResponseEntity
            .created(new URI("/api/chi-tiet-don-nhaps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chi-tiet-don-nhaps/:id} : Updates an existing chiTietDonNhap.
     *
     * @param id the id of the chiTietDonNhapDTO to save.
     * @param chiTietDonNhapDTO the chiTietDonNhapDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiTietDonNhapDTO,
     * or with status {@code 400 (Bad Request)} if the chiTietDonNhapDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chiTietDonNhapDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chi-tiet-don-nhaps/{id}")
    public ResponseEntity<ChiTietDonNhapDTO> updateChiTietDonNhap(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChiTietDonNhapDTO chiTietDonNhapDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChiTietDonNhap : {}, {}", id, chiTietDonNhapDTO);
        if (chiTietDonNhapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiTietDonNhapDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiTietDonNhapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChiTietDonNhapDTO result = chiTietDonNhapService.update(chiTietDonNhapDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chiTietDonNhapDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chi-tiet-don-nhaps/:id} : Partial updates given fields of an existing chiTietDonNhap, field will ignore if it is null
     *
     * @param id the id of the chiTietDonNhapDTO to save.
     * @param chiTietDonNhapDTO the chiTietDonNhapDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiTietDonNhapDTO,
     * or with status {@code 400 (Bad Request)} if the chiTietDonNhapDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chiTietDonNhapDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chiTietDonNhapDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chi-tiet-don-nhaps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChiTietDonNhapDTO> partialUpdateChiTietDonNhap(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChiTietDonNhapDTO chiTietDonNhapDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChiTietDonNhap partially : {}, {}", id, chiTietDonNhapDTO);
        if (chiTietDonNhapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiTietDonNhapDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiTietDonNhapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChiTietDonNhapDTO> result = chiTietDonNhapService.partialUpdate(chiTietDonNhapDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chiTietDonNhapDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /chi-tiet-don-nhaps} : get all the chiTietDonNhaps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chiTietDonNhaps in body.
     */
    @GetMapping("/chi-tiet-don-nhaps")
    public ResponseEntity<List<ChiTietDonNhapDTO>> getAllChiTietDonNhaps(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ChiTietDonNhaps");
        Page<ChiTietDonNhapDTO> page = chiTietDonNhapService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chi-tiet-don-nhaps/:id} : get the "id" chiTietDonNhap.
     *
     * @param id the id of the chiTietDonNhapDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chiTietDonNhapDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chi-tiet-don-nhaps/{id}")
    public ResponseEntity<ChiTietDonNhapDTO> getChiTietDonNhap(@PathVariable Long id) {
        log.debug("REST request to get ChiTietDonNhap : {}", id);
        Optional<ChiTietDonNhapDTO> chiTietDonNhapDTO = chiTietDonNhapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chiTietDonNhapDTO);
    }

    /**
     * {@code DELETE  /chi-tiet-don-nhaps/:id} : delete the "id" chiTietDonNhap.
     *
     * @param id the id of the chiTietDonNhapDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chi-tiet-don-nhaps/{id}")
    public ResponseEntity<Void> deleteChiTietDonNhap(@PathVariable Long id) {
        log.debug("REST request to delete ChiTietDonNhap : {}", id);
        chiTietDonNhapService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
