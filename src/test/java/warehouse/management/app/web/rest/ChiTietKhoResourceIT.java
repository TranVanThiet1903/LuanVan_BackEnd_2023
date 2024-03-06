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
import warehouse.management.app.domain.ChiTietKho;
import warehouse.management.app.repository.ChiTietKhoRepository;
import warehouse.management.app.service.dto.ChiTietKhoDTO;
import warehouse.management.app.service.mapper.ChiTietKhoMapper;

/**
 * Integration tests for the {@link ChiTietKhoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChiTietKhoResourceIT {

    private static final Long DEFAULT_SO_LUONG = 1L;
    private static final Long UPDATED_SO_LUONG = 2L;

    private static final String ENTITY_API_URL = "/api/chi-tiet-khos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChiTietKhoRepository chiTietKhoRepository;

    @Autowired
    private ChiTietKhoMapper chiTietKhoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChiTietKhoMockMvc;

    private ChiTietKho chiTietKho;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiTietKho createEntity(EntityManager em) {
        ChiTietKho chiTietKho = new ChiTietKho().soLuong(DEFAULT_SO_LUONG);
        return chiTietKho;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiTietKho createUpdatedEntity(EntityManager em) {
        ChiTietKho chiTietKho = new ChiTietKho().soLuong(UPDATED_SO_LUONG);
        return chiTietKho;
    }

    @BeforeEach
    public void initTest() {
        chiTietKho = createEntity(em);
    }

    @Test
    @Transactional
    void createChiTietKho() throws Exception {
        int databaseSizeBeforeCreate = chiTietKhoRepository.findAll().size();
        // Create the ChiTietKho
        ChiTietKhoDTO chiTietKhoDTO = chiTietKhoMapper.toDto(chiTietKho);
        restChiTietKhoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietKhoDTO)))
            .andExpect(status().isCreated());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeCreate + 1);
        ChiTietKho testChiTietKho = chiTietKhoList.get(chiTietKhoList.size() - 1);
        assertThat(testChiTietKho.getSoLuong()).isEqualTo(DEFAULT_SO_LUONG);
    }

    @Test
    @Transactional
    void createChiTietKhoWithExistingId() throws Exception {
        // Create the ChiTietKho with an existing ID
        chiTietKho.setId(1L);
        ChiTietKhoDTO chiTietKhoDTO = chiTietKhoMapper.toDto(chiTietKho);

        int databaseSizeBeforeCreate = chiTietKhoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChiTietKhoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietKhoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSoLuongIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTietKhoRepository.findAll().size();
        // set the field null
        chiTietKho.setSoLuong(null);

        // Create the ChiTietKho, which fails.
        ChiTietKhoDTO chiTietKhoDTO = chiTietKhoMapper.toDto(chiTietKho);

        restChiTietKhoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietKhoDTO)))
            .andExpect(status().isBadRequest());

        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChiTietKhos() throws Exception {
        // Initialize the database
        chiTietKhoRepository.saveAndFlush(chiTietKho);

        // Get all the chiTietKhoList
        restChiTietKhoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chiTietKho.getId().intValue())))
            .andExpect(jsonPath("$.[*].soLuong").value(hasItem(DEFAULT_SO_LUONG.intValue())));
    }

    @Test
    @Transactional
    void getChiTietKho() throws Exception {
        // Initialize the database
        chiTietKhoRepository.saveAndFlush(chiTietKho);

        // Get the chiTietKho
        restChiTietKhoMockMvc
            .perform(get(ENTITY_API_URL_ID, chiTietKho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chiTietKho.getId().intValue()))
            .andExpect(jsonPath("$.soLuong").value(DEFAULT_SO_LUONG.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingChiTietKho() throws Exception {
        // Get the chiTietKho
        restChiTietKhoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChiTietKho() throws Exception {
        // Initialize the database
        chiTietKhoRepository.saveAndFlush(chiTietKho);

        int databaseSizeBeforeUpdate = chiTietKhoRepository.findAll().size();

        // Update the chiTietKho
        ChiTietKho updatedChiTietKho = chiTietKhoRepository.findById(chiTietKho.getId()).get();
        // Disconnect from session so that the updates on updatedChiTietKho are not directly saved in db
        em.detach(updatedChiTietKho);
        updatedChiTietKho.soLuong(UPDATED_SO_LUONG);
        ChiTietKhoDTO chiTietKhoDTO = chiTietKhoMapper.toDto(updatedChiTietKho);

        restChiTietKhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiTietKhoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietKhoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeUpdate);
        ChiTietKho testChiTietKho = chiTietKhoList.get(chiTietKhoList.size() - 1);
        assertThat(testChiTietKho.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
    }

    @Test
    @Transactional
    void putNonExistingChiTietKho() throws Exception {
        int databaseSizeBeforeUpdate = chiTietKhoRepository.findAll().size();
        chiTietKho.setId(count.incrementAndGet());

        // Create the ChiTietKho
        ChiTietKhoDTO chiTietKhoDTO = chiTietKhoMapper.toDto(chiTietKho);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTietKhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiTietKhoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietKhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChiTietKho() throws Exception {
        int databaseSizeBeforeUpdate = chiTietKhoRepository.findAll().size();
        chiTietKho.setId(count.incrementAndGet());

        // Create the ChiTietKho
        ChiTietKhoDTO chiTietKhoDTO = chiTietKhoMapper.toDto(chiTietKho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietKhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietKhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChiTietKho() throws Exception {
        int databaseSizeBeforeUpdate = chiTietKhoRepository.findAll().size();
        chiTietKho.setId(count.incrementAndGet());

        // Create the ChiTietKho
        ChiTietKhoDTO chiTietKhoDTO = chiTietKhoMapper.toDto(chiTietKho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietKhoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietKhoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChiTietKhoWithPatch() throws Exception {
        // Initialize the database
        chiTietKhoRepository.saveAndFlush(chiTietKho);

        int databaseSizeBeforeUpdate = chiTietKhoRepository.findAll().size();

        // Update the chiTietKho using partial update
        ChiTietKho partialUpdatedChiTietKho = new ChiTietKho();
        partialUpdatedChiTietKho.setId(chiTietKho.getId());

        partialUpdatedChiTietKho.soLuong(UPDATED_SO_LUONG);

        restChiTietKhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiTietKho.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiTietKho))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeUpdate);
        ChiTietKho testChiTietKho = chiTietKhoList.get(chiTietKhoList.size() - 1);
        assertThat(testChiTietKho.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
    }

    @Test
    @Transactional
    void fullUpdateChiTietKhoWithPatch() throws Exception {
        // Initialize the database
        chiTietKhoRepository.saveAndFlush(chiTietKho);

        int databaseSizeBeforeUpdate = chiTietKhoRepository.findAll().size();

        // Update the chiTietKho using partial update
        ChiTietKho partialUpdatedChiTietKho = new ChiTietKho();
        partialUpdatedChiTietKho.setId(chiTietKho.getId());

        partialUpdatedChiTietKho.soLuong(UPDATED_SO_LUONG);

        restChiTietKhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiTietKho.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiTietKho))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeUpdate);
        ChiTietKho testChiTietKho = chiTietKhoList.get(chiTietKhoList.size() - 1);
        assertThat(testChiTietKho.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
    }

    @Test
    @Transactional
    void patchNonExistingChiTietKho() throws Exception {
        int databaseSizeBeforeUpdate = chiTietKhoRepository.findAll().size();
        chiTietKho.setId(count.incrementAndGet());

        // Create the ChiTietKho
        ChiTietKhoDTO chiTietKhoDTO = chiTietKhoMapper.toDto(chiTietKho);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTietKhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chiTietKhoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietKhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChiTietKho() throws Exception {
        int databaseSizeBeforeUpdate = chiTietKhoRepository.findAll().size();
        chiTietKho.setId(count.incrementAndGet());

        // Create the ChiTietKho
        ChiTietKhoDTO chiTietKhoDTO = chiTietKhoMapper.toDto(chiTietKho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietKhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietKhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChiTietKho() throws Exception {
        int databaseSizeBeforeUpdate = chiTietKhoRepository.findAll().size();
        chiTietKho.setId(count.incrementAndGet());

        // Create the ChiTietKho
        ChiTietKhoDTO chiTietKhoDTO = chiTietKhoMapper.toDto(chiTietKho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietKhoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chiTietKhoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiTietKho in the database
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChiTietKho() throws Exception {
        // Initialize the database
        chiTietKhoRepository.saveAndFlush(chiTietKho);

        int databaseSizeBeforeDelete = chiTietKhoRepository.findAll().size();

        // Delete the chiTietKho
        restChiTietKhoMockMvc
            .perform(delete(ENTITY_API_URL_ID, chiTietKho.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChiTietKho> chiTietKhoList = chiTietKhoRepository.findAll();
        assertThat(chiTietKhoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
