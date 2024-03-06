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
import warehouse.management.app.repository.ChiTietDonXuatRepository;
import warehouse.management.app.service.ChiTietDonXuatService;
import warehouse.management.app.service.dto.ChiTietDonXuatDTO;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link warehouse.management.app.domain.ChiTietDonXuat}.
 */
@RestController
@RequestMapping("/api")
public class ChiTietDonXuatResource {

    private final Logger log = LoggerFactory.getLogger(ChiTietDonXuatResource.class);

    private static final String ENTITY_NAME = "lvProjectChiTietDonXuat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChiTietDonXuatService chiTietDonXuatService;

    private final ChiTietDonXuatRepository chiTietDonXuatRepository;

    public ChiTietDonXuatResource(ChiTietDonXuatService chiTietDonXuatService, ChiTietDonXuatRepository chiTietDonXuatRepository) {
        this.chiTietDonXuatService = chiTietDonXuatService;
        this.chiTietDonXuatRepository = chiTietDonXuatRepository;
    }

    /**
     * {@code POST  /chi-tiet-don-xuats} : Create a new chiTietDonXuat.
     *
     * @param chiTietDonXuatDTO the chiTietDonXuatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chiTietDonXuatDTO, or with status {@code 400 (Bad Request)} if the chiTietDonXuat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chi-tiet-don-xuats")
    public ResponseEntity<ChiTietDonXuatDTO> createChiTietDonXuat(@Valid @RequestBody ChiTietDonXuatDTO chiTietDonXuatDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChiTietDonXuat : {}", chiTietDonXuatDTO);
        if (chiTietDonXuatDTO.getId() != null) {
            throw new BadRequestAlertException("A new chiTietDonXuat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChiTietDonXuatDTO result = chiTietDonXuatService.save(chiTietDonXuatDTO);
        return ResponseEntity
            .created(new URI("/api/chi-tiet-don-xuats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chi-tiet-don-xuats/:id} : Updates an existing chiTietDonXuat.
     *
     * @param id the id of the chiTietDonXuatDTO to save.
     * @param chiTietDonXuatDTO the chiTietDonXuatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiTietDonXuatDTO,
     * or with status {@code 400 (Bad Request)} if the chiTietDonXuatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chiTietDonXuatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chi-tiet-don-xuats/{id}")
    public ResponseEntity<ChiTietDonXuatDTO> updateChiTietDonXuat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChiTietDonXuatDTO chiTietDonXuatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChiTietDonXuat : {}, {}", id, chiTietDonXuatDTO);
        if (chiTietDonXuatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiTietDonXuatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiTietDonXuatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChiTietDonXuatDTO result = chiTietDonXuatService.update(chiTietDonXuatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chiTietDonXuatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chi-tiet-don-xuats/:id} : Partial updates given fields of an existing chiTietDonXuat, field will ignore if it is null
     *
     * @param id the id of the chiTietDonXuatDTO to save.
     * @param chiTietDonXuatDTO the chiTietDonXuatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiTietDonXuatDTO,
     * or with status {@code 400 (Bad Request)} if the chiTietDonXuatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chiTietDonXuatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chiTietDonXuatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chi-tiet-don-xuats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChiTietDonXuatDTO> partialUpdateChiTietDonXuat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChiTietDonXuatDTO chiTietDonXuatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChiTietDonXuat partially : {}, {}", id, chiTietDonXuatDTO);
        if (chiTietDonXuatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiTietDonXuatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiTietDonXuatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChiTietDonXuatDTO> result = chiTietDonXuatService.partialUpdate(chiTietDonXuatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chiTietDonXuatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /chi-tiet-don-xuats} : get all the chiTietDonXuats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chiTietDonXuats in body.
     */
    @GetMapping("/chi-tiet-don-xuats")
    public ResponseEntity<List<ChiTietDonXuatDTO>> getAllChiTietDonXuats(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ChiTietDonXuats");
        Page<ChiTietDonXuatDTO> page = chiTietDonXuatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chi-tiet-don-xuats/:id} : get the "id" chiTietDonXuat.
     *
     * @param id the id of the chiTietDonXuatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chiTietDonXuatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chi-tiet-don-xuats/{id}")
    public ResponseEntity<ChiTietDonXuatDTO> getChiTietDonXuat(@PathVariable Long id) {
        log.debug("REST request to get ChiTietDonXuat : {}", id);
        Optional<ChiTietDonXuatDTO> chiTietDonXuatDTO = chiTietDonXuatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chiTietDonXuatDTO);
    }

    /**
     * {@code DELETE  /chi-tiet-don-xuats/:id} : delete the "id" chiTietDonXuat.
     *
     * @param id the id of the chiTietDonXuatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chi-tiet-don-xuats/{id}")
    public ResponseEntity<Void> deleteChiTietDonXuat(@PathVariable Long id) {
        log.debug("REST request to delete ChiTietDonXuat : {}", id);
        chiTietDonXuatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
