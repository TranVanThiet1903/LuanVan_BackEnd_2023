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
import warehouse.management.app.domain.ChiTietDonXuat;
import warehouse.management.app.repository.ChiTietDonXuatRepository;
import warehouse.management.app.service.dto.ChiTietDonXuatDTO;
import warehouse.management.app.service.mapper.ChiTietDonXuatMapper;

/**
 * Integration tests for the {@link ChiTietDonXuatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChiTietDonXuatResourceIT {

    private static final Long DEFAULT_SO_LUONG = 1L;
    private static final Long UPDATED_SO_LUONG = 2L;

    private static final Long DEFAULT_THANH_TIEN = 1L;
    private static final Long UPDATED_THANH_TIEN = 2L;

    private static final String ENTITY_API_URL = "/api/chi-tiet-don-xuats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChiTietDonXuatRepository chiTietDonXuatRepository;

    @Autowired
    private ChiTietDonXuatMapper chiTietDonXuatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChiTietDonXuatMockMvc;

    private ChiTietDonXuat chiTietDonXuat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiTietDonXuat createEntity(EntityManager em) {
        ChiTietDonXuat chiTietDonXuat = new ChiTietDonXuat().soLuong(DEFAULT_SO_LUONG).thanhTien(DEFAULT_THANH_TIEN);
        return chiTietDonXuat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiTietDonXuat createUpdatedEntity(EntityManager em) {
        ChiTietDonXuat chiTietDonXuat = new ChiTietDonXuat().soLuong(UPDATED_SO_LUONG).thanhTien(UPDATED_THANH_TIEN);
        return chiTietDonXuat;
    }

    @BeforeEach
    public void initTest() {
        chiTietDonXuat = createEntity(em);
    }

    @Test
    @Transactional
    void createChiTietDonXuat() throws Exception {
        int databaseSizeBeforeCreate = chiTietDonXuatRepository.findAll().size();
        // Create the ChiTietDonXuat
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(chiTietDonXuat);
        restChiTietDonXuatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeCreate + 1);
        ChiTietDonXuat testChiTietDonXuat = chiTietDonXuatList.get(chiTietDonXuatList.size() - 1);
        assertThat(testChiTietDonXuat.getSoLuong()).isEqualTo(DEFAULT_SO_LUONG);
        assertThat(testChiTietDonXuat.getThanhTien()).isEqualTo(DEFAULT_THANH_TIEN);
    }

    @Test
    @Transactional
    void createChiTietDonXuatWithExistingId() throws Exception {
        // Create the ChiTietDonXuat with an existing ID
        chiTietDonXuat.setId(1L);
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(chiTietDonXuat);

        int databaseSizeBeforeCreate = chiTietDonXuatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChiTietDonXuatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSoLuongIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTietDonXuatRepository.findAll().size();
        // set the field null
        chiTietDonXuat.setSoLuong(null);

        // Create the ChiTietDonXuat, which fails.
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(chiTietDonXuat);

        restChiTietDonXuatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThanhTienIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTietDonXuatRepository.findAll().size();
        // set the field null
        chiTietDonXuat.setThanhTien(null);

        // Create the ChiTietDonXuat, which fails.
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(chiTietDonXuat);

        restChiTietDonXuatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChiTietDonXuats() throws Exception {
        // Initialize the database
        chiTietDonXuatRepository.saveAndFlush(chiTietDonXuat);

        // Get all the chiTietDonXuatList
        restChiTietDonXuatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chiTietDonXuat.getId().intValue())))
            .andExpect(jsonPath("$.[*].soLuong").value(hasItem(DEFAULT_SO_LUONG.intValue())))
            .andExpect(jsonPath("$.[*].thanhTien").value(hasItem(DEFAULT_THANH_TIEN.intValue())));
    }

    @Test
    @Transactional
    void getChiTietDonXuat() throws Exception {
        // Initialize the database
        chiTietDonXuatRepository.saveAndFlush(chiTietDonXuat);

        // Get the chiTietDonXuat
        restChiTietDonXuatMockMvc
            .perform(get(ENTITY_API_URL_ID, chiTietDonXuat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chiTietDonXuat.getId().intValue()))
            .andExpect(jsonPath("$.soLuong").value(DEFAULT_SO_LUONG.intValue()))
            .andExpect(jsonPath("$.thanhTien").value(DEFAULT_THANH_TIEN.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingChiTietDonXuat() throws Exception {
        // Get the chiTietDonXuat
        restChiTietDonXuatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChiTietDonXuat() throws Exception {
        // Initialize the database
        chiTietDonXuatRepository.saveAndFlush(chiTietDonXuat);

        int databaseSizeBeforeUpdate = chiTietDonXuatRepository.findAll().size();

        // Update the chiTietDonXuat
        ChiTietDonXuat updatedChiTietDonXuat = chiTietDonXuatRepository.findById(chiTietDonXuat.getId()).get();
        // Disconnect from session so that the updates on updatedChiTietDonXuat are not directly saved in db
        em.detach(updatedChiTietDonXuat);
        updatedChiTietDonXuat.soLuong(UPDATED_SO_LUONG).thanhTien(UPDATED_THANH_TIEN);
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(updatedChiTietDonXuat);

        restChiTietDonXuatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiTietDonXuatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeUpdate);
        ChiTietDonXuat testChiTietDonXuat = chiTietDonXuatList.get(chiTietDonXuatList.size() - 1);
        assertThat(testChiTietDonXuat.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
        assertThat(testChiTietDonXuat.getThanhTien()).isEqualTo(UPDATED_THANH_TIEN);
    }

    @Test
    @Transactional
    void putNonExistingChiTietDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonXuatRepository.findAll().size();
        chiTietDonXuat.setId(count.incrementAndGet());

        // Create the ChiTietDonXuat
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(chiTietDonXuat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTietDonXuatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiTietDonXuatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChiTietDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonXuatRepository.findAll().size();
        chiTietDonXuat.setId(count.incrementAndGet());

        // Create the ChiTietDonXuat
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(chiTietDonXuat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietDonXuatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChiTietDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonXuatRepository.findAll().size();
        chiTietDonXuat.setId(count.incrementAndGet());

        // Create the ChiTietDonXuat
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(chiTietDonXuat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietDonXuatMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChiTietDonXuatWithPatch() throws Exception {
        // Initialize the database
        chiTietDonXuatRepository.saveAndFlush(chiTietDonXuat);

        int databaseSizeBeforeUpdate = chiTietDonXuatRepository.findAll().size();

        // Update the chiTietDonXuat using partial update
        ChiTietDonXuat partialUpdatedChiTietDonXuat = new ChiTietDonXuat();
        partialUpdatedChiTietDonXuat.setId(chiTietDonXuat.getId());

        restChiTietDonXuatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiTietDonXuat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiTietDonXuat))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeUpdate);
        ChiTietDonXuat testChiTietDonXuat = chiTietDonXuatList.get(chiTietDonXuatList.size() - 1);
        assertThat(testChiTietDonXuat.getSoLuong()).isEqualTo(DEFAULT_SO_LUONG);
        assertThat(testChiTietDonXuat.getThanhTien()).isEqualTo(DEFAULT_THANH_TIEN);
    }

    @Test
    @Transactional
    void fullUpdateChiTietDonXuatWithPatch() throws Exception {
        // Initialize the database
        chiTietDonXuatRepository.saveAndFlush(chiTietDonXuat);

        int databaseSizeBeforeUpdate = chiTietDonXuatRepository.findAll().size();

        // Update the chiTietDonXuat using partial update
        ChiTietDonXuat partialUpdatedChiTietDonXuat = new ChiTietDonXuat();
        partialUpdatedChiTietDonXuat.setId(chiTietDonXuat.getId());

        partialUpdatedChiTietDonXuat.soLuong(UPDATED_SO_LUONG).thanhTien(UPDATED_THANH_TIEN);

        restChiTietDonXuatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiTietDonXuat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiTietDonXuat))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeUpdate);
        ChiTietDonXuat testChiTietDonXuat = chiTietDonXuatList.get(chiTietDonXuatList.size() - 1);
        assertThat(testChiTietDonXuat.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
        assertThat(testChiTietDonXuat.getThanhTien()).isEqualTo(UPDATED_THANH_TIEN);
    }

    @Test
    @Transactional
    void patchNonExistingChiTietDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonXuatRepository.findAll().size();
        chiTietDonXuat.setId(count.incrementAndGet());

        // Create the ChiTietDonXuat
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(chiTietDonXuat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTietDonXuatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chiTietDonXuatDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChiTietDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonXuatRepository.findAll().size();
        chiTietDonXuat.setId(count.incrementAndGet());

        // Create the ChiTietDonXuat
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(chiTietDonXuat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietDonXuatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChiTietDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonXuatRepository.findAll().size();
        chiTietDonXuat.setId(count.incrementAndGet());

        // Create the ChiTietDonXuat
        ChiTietDonXuatDTO chiTietDonXuatDTO = chiTietDonXuatMapper.toDto(chiTietDonXuat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietDonXuatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonXuatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiTietDonXuat in the database
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChiTietDonXuat() throws Exception {
        // Initialize the database
        chiTietDonXuatRepository.saveAndFlush(chiTietDonXuat);

        int databaseSizeBeforeDelete = chiTietDonXuatRepository.findAll().size();

        // Delete the chiTietDonXuat
        restChiTietDonXuatMockMvc
            .perform(delete(ENTITY_API_URL_ID, chiTietDonXuat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChiTietDonXuat> chiTietDonXuatList = chiTietDonXuatRepository.findAll();
        assertThat(chiTietDonXuatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
