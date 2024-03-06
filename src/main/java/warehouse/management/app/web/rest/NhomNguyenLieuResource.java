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
import warehouse.management.app.repository.NhomNguyenLieuRepository;
import warehouse.management.app.service.NhomNguyenLieuService;
import warehouse.management.app.service.dto.NhomNguyenLieuDTO;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link warehouse.management.app.domain.NhomNguyenLieu}.
 */
@RestController
@RequestMapping("/api")
public class NhomNguyenLieuResource {

    private final Logger log = LoggerFactory.getLogger(NhomNguyenLieuResource.class);

    private static final String ENTITY_NAME = "lvProjectNhomNguyenLieu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhomNguyenLieuService nhomNguyenLieuService;

    private final NhomNguyenLieuRepository nhomNguyenLieuRepository;

    public NhomNguyenLieuResource(NhomNguyenLieuService nhomNguyenLieuService, NhomNguyenLieuRepository nhomNguyenLieuRepository) {
        this.nhomNguyenLieuService = nhomNguyenLieuService;
        this.nhomNguyenLieuRepository = nhomNguyenLieuRepository;
    }

    /**
     * {@code POST  /nhom-nguyen-lieus} : Create a new nhomNguyenLieu.
     *
     * @param nhomNguyenLieuDTO the nhomNguyenLieuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhomNguyenLieuDTO, or with status {@code 400 (Bad Request)} if the nhomNguyenLieu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nhom-nguyen-lieu")
    public ResponseEntity<NhomNguyenLieuDTO> createNhomNguyenLieu(@Valid @RequestBody NhomNguyenLieuDTO nhomNguyenLieuDTO)
        throws URISyntaxException {
        log.debug("REST request to save NhomNguyenLieu : {}", nhomNguyenLieuDTO);
        if (nhomNguyenLieuDTO.getId() != null) {
            throw new BadRequestAlertException("A new nhomNguyenLieu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhomNguyenLieuDTO result = nhomNguyenLieuService.save(nhomNguyenLieuDTO);
        return ResponseEntity
            .created(new URI("/api/nhom-nguyen-lieu/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nhom-nguyen-lieus/:id} : Updates an existing nhomNguyenLieu.
     *
     * @param id the id of the nhomNguyenLieuDTO to save.
     * @param nhomNguyenLieuDTO the nhomNguyenLieuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhomNguyenLieuDTO,
     * or with status {@code 400 (Bad Request)} if the nhomNguyenLieuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhomNguyenLieuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nhom-nguyen-lieu/{id}")
    public ResponseEntity<NhomNguyenLieuDTO> updateNhomNguyenLieu(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NhomNguyenLieuDTO nhomNguyenLieuDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NhomNguyenLieu : {}, {}", id, nhomNguyenLieuDTO);
        if (nhomNguyenLieuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhomNguyenLieuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhomNguyenLieuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NhomNguyenLieuDTO result = nhomNguyenLieuService.update(nhomNguyenLieuDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhomNguyenLieuDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nhom-nguyen-lieus/:id} : Partial updates given fields of an existing nhomNguyenLieu, field will ignore if it is null
     *
     * @param id the id of the nhomNguyenLieuDTO to save.
     * @param nhomNguyenLieuDTO the nhomNguyenLieuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhomNguyenLieuDTO,
     * or with status {@code 400 (Bad Request)} if the nhomNguyenLieuDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nhomNguyenLieuDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nhomNguyenLieuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nhom-nguyen-lieu/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NhomNguyenLieuDTO> partialUpdateNhomNguyenLieu(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NhomNguyenLieuDTO nhomNguyenLieuDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NhomNguyenLieu partially : {}, {}", id, nhomNguyenLieuDTO);
        if (nhomNguyenLieuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhomNguyenLieuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhomNguyenLieuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NhomNguyenLieuDTO> result = nhomNguyenLieuService.partialUpdate(nhomNguyenLieuDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhomNguyenLieuDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nhom-nguyen-lieus} : get all the nhomNguyenLieus.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhomNguyenLieus in body.
     */
    @GetMapping("/nhom-nguyen-lieu")
    public ResponseEntity<List<NhomNguyenLieuDTO>> getAllNhomNguyenLieus(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NhomNguyenLieus");
        Page<NhomNguyenLieuDTO> page = nhomNguyenLieuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nhom-nguyen-lieus/:id} : get the "id" nhomNguyenLieu.
     *
     * @param id the id of the nhomNguyenLieuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhomNguyenLieuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nhom-nguyen-lieu/{id}")
    public ResponseEntity<NhomNguyenLieuDTO> getNhomNguyenLieu(@PathVariable Long id) {
        log.debug("REST request to get NhomNguyenLieu : {}", id);
        Optional<NhomNguyenLieuDTO> nhomNguyenLieuDTO = nhomNguyenLieuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhomNguyenLieuDTO);
    }

    /**
     * {@code DELETE  /nhom-nguyen-lieus/:id} : delete the "id" nhomNguyenLieu.
     *
     * @param id the id of the nhomNguyenLieuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nhom-nguyen-lieu/{id}")
    public ResponseEntity<Void> deleteNhomNguyenLieu(@PathVariable Long id) {
        log.debug("REST request to delete NhomNguyenLieu : {}", id);
        nhomNguyenLieuService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
