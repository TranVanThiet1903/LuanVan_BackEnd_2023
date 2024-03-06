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
import warehouse.management.app.repository.ChiTietKhoRepository;
import warehouse.management.app.service.ChiTietKhoService;
import warehouse.management.app.service.dto.ChiTietKhoDTO;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link warehouse.management.app.domain.ChiTietKho}.
 */
@RestController
@RequestMapping("/api")
public class ChiTietKhoResource {

    private final Logger log = LoggerFactory.getLogger(ChiTietKhoResource.class);

    private static final String ENTITY_NAME = "lvProjectChiTietKho";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChiTietKhoService chiTietKhoService;

    private final ChiTietKhoRepository chiTietKhoRepository;

    public ChiTietKhoResource(ChiTietKhoService chiTietKhoService, ChiTietKhoRepository chiTietKhoRepository) {
        this.chiTietKhoService = chiTietKhoService;
        this.chiTietKhoRepository = chiTietKhoRepository;
    }

    /**
     * {@code POST  /chi-tiet-khos} : Create a new chiTietKho.
     *
     * @param chiTietKhoDTO the chiTietKhoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chiTietKhoDTO, or with status {@code 400 (Bad Request)} if the chiTietKho has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chi-tiet-khos")
    public ResponseEntity<ChiTietKhoDTO> createChiTietKho(@Valid @RequestBody ChiTietKhoDTO chiTietKhoDTO) throws URISyntaxException {
        log.debug("REST request to save ChiTietKho : {}", chiTietKhoDTO);
        if (chiTietKhoDTO.getId() != null) {
            throw new BadRequestAlertException("A new chiTietKho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChiTietKhoDTO result = chiTietKhoService.save(chiTietKhoDTO);
        return ResponseEntity
            .created(new URI("/api/chi-tiet-khos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chi-tiet-khos/:id} : Updates an existing chiTietKho.
     *
     * @param id the id of the chiTietKhoDTO to save.
     * @param chiTietKhoDTO the chiTietKhoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiTietKhoDTO,
     * or with status {@code 400 (Bad Request)} if the chiTietKhoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chiTietKhoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chi-tiet-khos/{id}")
    public ResponseEntity<ChiTietKhoDTO> updateChiTietKho(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChiTietKhoDTO chiTietKhoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChiTietKho : {}, {}", id, chiTietKhoDTO);
        if (chiTietKhoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiTietKhoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiTietKhoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChiTietKhoDTO result = chiTietKhoService.update(chiTietKhoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chiTietKhoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chi-tiet-khos/:id} : Partial updates given fields of an existing chiTietKho, field will ignore if it is null
     *
     * @param id the id of the chiTietKhoDTO to save.
     * @param chiTietKhoDTO the chiTietKhoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiTietKhoDTO,
     * or with status {@code 400 (Bad Request)} if the chiTietKhoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chiTietKhoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chiTietKhoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chi-tiet-khos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChiTietKhoDTO> partialUpdateChiTietKho(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChiTietKhoDTO chiTietKhoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChiTietKho partially : {}, {}", id, chiTietKhoDTO);
        if (chiTietKhoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiTietKhoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiTietKhoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChiTietKhoDTO> result = chiTietKhoService.partialUpdate(chiTietKhoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chiTietKhoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /chi-tiet-khos} : get all the chiTietKhos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chiTietKhos in body.
     */
    @GetMapping("/chi-tiet-khos")
    public ResponseEntity<List<ChiTietKhoDTO>> getAllChiTietKhos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ChiTietKhos");
        Page<ChiTietKhoDTO> page = chiTietKhoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chi-tiet-khos/:id} : get the "id" chiTietKho.
     *
     * @param id the id of the chiTietKhoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chiTietKhoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chi-tiet-khos/{id}")
    public ResponseEntity<ChiTietKhoDTO> getChiTietKho(@PathVariable Long id) {
        log.debug("REST request to get ChiTietKho : {}", id);
        Optional<ChiTietKhoDTO> chiTietKhoDTO = chiTietKhoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chiTietKhoDTO);
    }

    /**
     * {@code DELETE  /chi-tiet-khos/:id} : delete the "id" chiTietKho.
     *
     * @param id the id of the chiTietKhoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chi-tiet-khos/{id}")
    public ResponseEntity<Void> deleteChiTietKho(@PathVariable Long id) {
        log.debug("REST request to delete ChiTietKho : {}", id);
        chiTietKhoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
