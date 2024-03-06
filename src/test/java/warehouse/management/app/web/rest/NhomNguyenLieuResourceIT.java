package warehouse.management.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.IntegrationTest;
import warehouse.management.app.domain.NhomNguyenLieu;
import warehouse.management.app.repository.NhomNguyenLieuRepository;
import warehouse.management.app.service.dto.NhomNguyenLieuDTO;
import warehouse.management.app.service.mapper.NhomNguyenLieuMapper;

/**
 * Integration tests for the {@link NhomNguyenLieuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NhomNguyenLieuResourceIT {

    private static final String DEFAULT_TEN_NHOM = "AAAAAAAAAA";
    private static final String UPDATED_TEN_NHOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nhom-nguyen-lieus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NhomNguyenLieuRepository nhomNguyenLieuRepository;

    @Autowired
    private NhomNguyenLieuMapper nhomNguyenLieuMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNhomNguyenLieuMockMvc;

    private NhomNguyenLieu nhomNguyenLieu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhomNguyenLieu createEntity(EntityManager em) {
        NhomNguyenLieu nhomNguyenLieu = new NhomNguyenLieu().tenNhom(DEFAULT_TEN_NHOM);
        return nhomNguyenLieu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhomNguyenLieu createUpdatedEntity(EntityManager em) {
        NhomNguyenLieu nhomNguyenLieu = new NhomNguyenLieu().tenNhom(UPDATED_TEN_NHOM);
        return nhomNguyenLieu;
    }

    @BeforeEach
    public void initTest() {
        nhomNguyenLieu = createEntity(em);
    }

    @Test
    @Transactional
    void createNhomNguyenLieu() throws Exception {
        int databaseSizeBeforeCreate = nhomNguyenLieuRepository.findAll().size();
        // Create the NhomNguyenLieu
        NhomNguyenLieuDTO nhomNguyenLieuDTO = nhomNguyenLieuMapper.toDto(nhomNguyenLieu);
        restNhomNguyenLieuMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhomNguyenLieuDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeCreate + 1);
        NhomNguyenLieu testNhomNguyenLieu = nhomNguyenLieuList.get(nhomNguyenLieuList.size() - 1);
        assertThat(testNhomNguyenLieu.getTenNhom()).isEqualTo(DEFAULT_TEN_NHOM);
    }

    @Test
    @Transactional
    void createNhomNguyenLieuWithExistingId() throws Exception {
        // Create the NhomNguyenLieu with an existing ID
        nhomNguyenLieu.setId(1L);
        NhomNguyenLieuDTO nhomNguyenLieuDTO = nhomNguyenLieuMapper.toDto(nhomNguyenLieu);

        int databaseSizeBeforeCreate = nhomNguyenLieuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhomNguyenLieuMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhomNguyenLieuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenNhomIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhomNguyenLieuRepository.findAll().size();
        // set the field null
        nhomNguyenLieu.setTenNhom(null);

        // Create the NhomNguyenLieu, which fails.
        NhomNguyenLieuDTO nhomNguyenLieuDTO = nhomNguyenLieuMapper.toDto(nhomNguyenLieu);

        restNhomNguyenLieuMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhomNguyenLieuDTO))
            )
            .andExpect(status().isBadRequest());

        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNhomNguyenLieus() throws Exception {
        // Initialize the database
        nhomNguyenLieuRepository.saveAndFlush(nhomNguyenLieu);

        // Get all the nhomNguyenLieuList
        restNhomNguyenLieuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhomNguyenLieu.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenNhom").value(hasItem(DEFAULT_TEN_NHOM)));
    }

    @Test
    @Transactional
    void getNhomNguyenLieu() throws Exception {
        // Initialize the database
        nhomNguyenLieuRepository.saveAndFlush(nhomNguyenLieu);

        // Get the nhomNguyenLieu
        restNhomNguyenLieuMockMvc
            .perform(get(ENTITY_API_URL_ID, nhomNguyenLieu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nhomNguyenLieu.getId().intValue()))
            .andExpect(jsonPath("$.tenNhom").value(DEFAULT_TEN_NHOM));
    }

    @Test
    @Transactional
    void getNonExistingNhomNguyenLieu() throws Exception {
        // Get the nhomNguyenLieu
        restNhomNguyenLieuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNhomNguyenLieu() throws Exception {
        // Initialize the database
        nhomNguyenLieuRepository.saveAndFlush(nhomNguyenLieu);

        int databaseSizeBeforeUpdate = nhomNguyenLieuRepository.findAll().size();

        // Update the nhomNguyenLieu
        NhomNguyenLieu updatedNhomNguyenLieu = nhomNguyenLieuRepository.findById(nhomNguyenLieu.getId()).get();
        // Disconnect from session so that the updates on updatedNhomNguyenLieu are not directly saved in db
        em.detach(updatedNhomNguyenLieu);
        updatedNhomNguyenLieu.tenNhom(UPDATED_TEN_NHOM);
        NhomNguyenLieuDTO nhomNguyenLieuDTO = nhomNguyenLieuMapper.toDto(updatedNhomNguyenLieu);

        restNhomNguyenLieuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhomNguyenLieuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhomNguyenLieuDTO))
            )
            .andExpect(status().isOk());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeUpdate);
        NhomNguyenLieu testNhomNguyenLieu = nhomNguyenLieuList.get(nhomNguyenLieuList.size() - 1);
        assertThat(testNhomNguyenLieu.getTenNhom()).isEqualTo(UPDATED_TEN_NHOM);
    }

    @Test
    @Transactional
    void putNonExistingNhomNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nhomNguyenLieuRepository.findAll().size();
        nhomNguyenLieu.setId(count.incrementAndGet());

        // Create the NhomNguyenLieu
        NhomNguyenLieuDTO nhomNguyenLieuDTO = nhomNguyenLieuMapper.toDto(nhomNguyenLieu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhomNguyenLieuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhomNguyenLieuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhomNguyenLieuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNhomNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nhomNguyenLieuRepository.findAll().size();
        nhomNguyenLieu.setId(count.incrementAndGet());

        // Create the NhomNguyenLieu
        NhomNguyenLieuDTO nhomNguyenLieuDTO = nhomNguyenLieuMapper.toDto(nhomNguyenLieu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhomNguyenLieuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhomNguyenLieuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNhomNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nhomNguyenLieuRepository.findAll().size();
        nhomNguyenLieu.setId(count.incrementAndGet());

        // Create the NhomNguyenLieu
        NhomNguyenLieuDTO nhomNguyenLieuDTO = nhomNguyenLieuMapper.toDto(nhomNguyenLieu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhomNguyenLieuMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhomNguyenLieuDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNhomNguyenLieuWithPatch() throws Exception {
        // Initialize the database
        nhomNguyenLieuRepository.saveAndFlush(nhomNguyenLieu);

        int databaseSizeBeforeUpdate = nhomNguyenLieuRepository.findAll().size();

        // Update the nhomNguyenLieu using partial update
        NhomNguyenLieu partialUpdatedNhomNguyenLieu = new NhomNguyenLieu();
        partialUpdatedNhomNguyenLieu.setId(nhomNguyenLieu.getId());

        partialUpdatedNhomNguyenLieu.tenNhom(UPDATED_TEN_NHOM);

        restNhomNguyenLieuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhomNguyenLieu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhomNguyenLieu))
            )
            .andExpect(status().isOk());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeUpdate);
        NhomNguyenLieu testNhomNguyenLieu = nhomNguyenLieuList.get(nhomNguyenLieuList.size() - 1);
        assertThat(testNhomNguyenLieu.getTenNhom()).isEqualTo(UPDATED_TEN_NHOM);
    }

    @Test
    @Transactional
    void fullUpdateNhomNguyenLieuWithPatch() throws Exception {
        // Initialize the database
        nhomNguyenLieuRepository.saveAndFlush(nhomNguyenLieu);

        int databaseSizeBeforeUpdate = nhomNguyenLieuRepository.findAll().size();

        // Update the nhomNguyenLieu using partial update
        NhomNguyenLieu partialUpdatedNhomNguyenLieu = new NhomNguyenLieu();
        partialUpdatedNhomNguyenLieu.setId(nhomNguyenLieu.getId());

        partialUpdatedNhomNguyenLieu.tenNhom(UPDATED_TEN_NHOM);

        restNhomNguyenLieuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhomNguyenLieu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhomNguyenLieu))
            )
            .andExpect(status().isOk());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeUpdate);
        NhomNguyenLieu testNhomNguyenLieu = nhomNguyenLieuList.get(nhomNguyenLieuList.size() - 1);
        assertThat(testNhomNguyenLieu.getTenNhom()).isEqualTo(UPDATED_TEN_NHOM);
    }

    @Test
    @Transactional
    void patchNonExistingNhomNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nhomNguyenLieuRepository.findAll().size();
        nhomNguyenLieu.setId(count.incrementAndGet());

        // Create the NhomNguyenLieu
        NhomNguyenLieuDTO nhomNguyenLieuDTO = nhomNguyenLieuMapper.toDto(nhomNguyenLieu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhomNguyenLieuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nhomNguyenLieuDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhomNguyenLieuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNhomNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nhomNguyenLieuRepository.findAll().size();
        nhomNguyenLieu.setId(count.incrementAndGet());

        // Create the NhomNguyenLieu
        NhomNguyenLieuDTO nhomNguyenLieuDTO = nhomNguyenLieuMapper.toDto(nhomNguyenLieu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhomNguyenLieuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhomNguyenLieuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNhomNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nhomNguyenLieuRepository.findAll().size();
        nhomNguyenLieu.setId(count.incrementAndGet());

        // Create the NhomNguyenLieu
        NhomNguyenLieuDTO nhomNguyenLieuDTO = nhomNguyenLieuMapper.toDto(nhomNguyenLieu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhomNguyenLieuMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhomNguyenLieuDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhomNguyenLieu in the database
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNhomNguyenLieu() throws Exception {
        // Initialize the database
        nhomNguyenLieuRepository.saveAndFlush(nhomNguyenLieu);

        int databaseSizeBeforeDelete = nhomNguyenLieuRepository.findAll().size();

        // Delete the nhomNguyenLieu
        restNhomNguyenLieuMockMvc
            .perform(delete(ENTITY_API_URL_ID, nhomNguyenLieu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NhomNguyenLieu> nhomNguyenLieuList = nhomNguyenLieuRepository.findAll();
        assertThat(nhomNguyenLieuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
