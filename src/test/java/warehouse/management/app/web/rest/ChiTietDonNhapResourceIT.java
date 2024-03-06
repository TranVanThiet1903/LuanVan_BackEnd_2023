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
import warehouse.management.app.domain.ChiTietDonNhap;
import warehouse.management.app.repository.ChiTietDonNhapRepository;
import warehouse.management.app.service.dto.ChiTietDonNhapDTO;
import warehouse.management.app.service.mapper.ChiTietDonNhapMapper;

/**
 * Integration tests for the {@link ChiTietDonNhapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChiTietDonNhapResourceIT {

    private static final Long DEFAULT_SO_LUONG = 1L;
    private static final Long UPDATED_SO_LUONG = 2L;

    private static final Long DEFAULT_THANH_TIEN = 1L;
    private static final Long UPDATED_THANH_TIEN = 2L;

    private static final String ENTITY_API_URL = "/api/chi-tiet-don-nhaps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChiTietDonNhapRepository chiTietDonNhapRepository;

    @Autowired
    private ChiTietDonNhapMapper chiTietDonNhapMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChiTietDonNhapMockMvc;

    private ChiTietDonNhap chiTietDonNhap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiTietDonNhap createEntity(EntityManager em) {
        ChiTietDonNhap chiTietDonNhap = new ChiTietDonNhap().soLuong(DEFAULT_SO_LUONG).thanhTien(DEFAULT_THANH_TIEN);
        return chiTietDonNhap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiTietDonNhap createUpdatedEntity(EntityManager em) {
        ChiTietDonNhap chiTietDonNhap = new ChiTietDonNhap().soLuong(UPDATED_SO_LUONG).thanhTien(UPDATED_THANH_TIEN);
        return chiTietDonNhap;
    }

    @BeforeEach
    public void initTest() {
        chiTietDonNhap = createEntity(em);
    }

    @Test
    @Transactional
    void createChiTietDonNhap() throws Exception {
        int databaseSizeBeforeCreate = chiTietDonNhapRepository.findAll().size();
        // Create the ChiTietDonNhap
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(chiTietDonNhap);
        restChiTietDonNhapMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeCreate + 1);
        ChiTietDonNhap testChiTietDonNhap = chiTietDonNhapList.get(chiTietDonNhapList.size() - 1);
        assertThat(testChiTietDonNhap.getSoLuong()).isEqualTo(DEFAULT_SO_LUONG);
        assertThat(testChiTietDonNhap.getThanhTien()).isEqualTo(DEFAULT_THANH_TIEN);
    }

    @Test
    @Transactional
    void createChiTietDonNhapWithExistingId() throws Exception {
        // Create the ChiTietDonNhap with an existing ID
        chiTietDonNhap.setId(1L);
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(chiTietDonNhap);

        int databaseSizeBeforeCreate = chiTietDonNhapRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChiTietDonNhapMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSoLuongIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTietDonNhapRepository.findAll().size();
        // set the field null
        chiTietDonNhap.setSoLuong(null);

        // Create the ChiTietDonNhap, which fails.
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(chiTietDonNhap);

        restChiTietDonNhapMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThanhTienIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTietDonNhapRepository.findAll().size();
        // set the field null
        chiTietDonNhap.setThanhTien(null);

        // Create the ChiTietDonNhap, which fails.
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(chiTietDonNhap);

        restChiTietDonNhapMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChiTietDonNhaps() throws Exception {
        // Initialize the database
        chiTietDonNhapRepository.saveAndFlush(chiTietDonNhap);

        // Get all the chiTietDonNhapList
        restChiTietDonNhapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chiTietDonNhap.getId().intValue())))
            .andExpect(jsonPath("$.[*].soLuong").value(hasItem(DEFAULT_SO_LUONG.intValue())))
            .andExpect(jsonPath("$.[*].thanhTien").value(hasItem(DEFAULT_THANH_TIEN.intValue())));
    }

    @Test
    @Transactional
    void getChiTietDonNhap() throws Exception {
        // Initialize the database
        chiTietDonNhapRepository.saveAndFlush(chiTietDonNhap);

        // Get the chiTietDonNhap
        restChiTietDonNhapMockMvc
            .perform(get(ENTITY_API_URL_ID, chiTietDonNhap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chiTietDonNhap.getId().intValue()))
            .andExpect(jsonPath("$.soLuong").value(DEFAULT_SO_LUONG.intValue()))
            .andExpect(jsonPath("$.thanhTien").value(DEFAULT_THANH_TIEN.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingChiTietDonNhap() throws Exception {
        // Get the chiTietDonNhap
        restChiTietDonNhapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChiTietDonNhap() throws Exception {
        // Initialize the database
        chiTietDonNhapRepository.saveAndFlush(chiTietDonNhap);

        int databaseSizeBeforeUpdate = chiTietDonNhapRepository.findAll().size();

        // Update the chiTietDonNhap
        ChiTietDonNhap updatedChiTietDonNhap = chiTietDonNhapRepository.findById(chiTietDonNhap.getId()).get();
        // Disconnect from session so that the updates on updatedChiTietDonNhap are not directly saved in db
        em.detach(updatedChiTietDonNhap);
        updatedChiTietDonNhap.soLuong(UPDATED_SO_LUONG).thanhTien(UPDATED_THANH_TIEN);
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(updatedChiTietDonNhap);

        restChiTietDonNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiTietDonNhapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeUpdate);
        ChiTietDonNhap testChiTietDonNhap = chiTietDonNhapList.get(chiTietDonNhapList.size() - 1);
        assertThat(testChiTietDonNhap.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
        assertThat(testChiTietDonNhap.getThanhTien()).isEqualTo(UPDATED_THANH_TIEN);
    }

    @Test
    @Transactional
    void putNonExistingChiTietDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonNhapRepository.findAll().size();
        chiTietDonNhap.setId(count.incrementAndGet());

        // Create the ChiTietDonNhap
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(chiTietDonNhap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTietDonNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiTietDonNhapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChiTietDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonNhapRepository.findAll().size();
        chiTietDonNhap.setId(count.incrementAndGet());

        // Create the ChiTietDonNhap
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(chiTietDonNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietDonNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChiTietDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonNhapRepository.findAll().size();
        chiTietDonNhap.setId(count.incrementAndGet());

        // Create the ChiTietDonNhap
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(chiTietDonNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietDonNhapMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChiTietDonNhapWithPatch() throws Exception {
        // Initialize the database
        chiTietDonNhapRepository.saveAndFlush(chiTietDonNhap);

        int databaseSizeBeforeUpdate = chiTietDonNhapRepository.findAll().size();

        // Update the chiTietDonNhap using partial update
        ChiTietDonNhap partialUpdatedChiTietDonNhap = new ChiTietDonNhap();
        partialUpdatedChiTietDonNhap.setId(chiTietDonNhap.getId());

        restChiTietDonNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiTietDonNhap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiTietDonNhap))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeUpdate);
        ChiTietDonNhap testChiTietDonNhap = chiTietDonNhapList.get(chiTietDonNhapList.size() - 1);
        assertThat(testChiTietDonNhap.getSoLuong()).isEqualTo(DEFAULT_SO_LUONG);
        assertThat(testChiTietDonNhap.getThanhTien()).isEqualTo(DEFAULT_THANH_TIEN);
    }

    @Test
    @Transactional
    void fullUpdateChiTietDonNhapWithPatch() throws Exception {
        // Initialize the database
        chiTietDonNhapRepository.saveAndFlush(chiTietDonNhap);

        int databaseSizeBeforeUpdate = chiTietDonNhapRepository.findAll().size();

        // Update the chiTietDonNhap using partial update
        ChiTietDonNhap partialUpdatedChiTietDonNhap = new ChiTietDonNhap();
        partialUpdatedChiTietDonNhap.setId(chiTietDonNhap.getId());

        partialUpdatedChiTietDonNhap.soLuong(UPDATED_SO_LUONG).thanhTien(UPDATED_THANH_TIEN);

        restChiTietDonNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiTietDonNhap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiTietDonNhap))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeUpdate);
        ChiTietDonNhap testChiTietDonNhap = chiTietDonNhapList.get(chiTietDonNhapList.size() - 1);
        assertThat(testChiTietDonNhap.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
        assertThat(testChiTietDonNhap.getThanhTien()).isEqualTo(UPDATED_THANH_TIEN);
    }

    @Test
    @Transactional
    void patchNonExistingChiTietDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonNhapRepository.findAll().size();
        chiTietDonNhap.setId(count.incrementAndGet());

        // Create the ChiTietDonNhap
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(chiTietDonNhap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTietDonNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chiTietDonNhapDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChiTietDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonNhapRepository.findAll().size();
        chiTietDonNhap.setId(count.incrementAndGet());

        // Create the ChiTietDonNhap
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(chiTietDonNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietDonNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChiTietDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietDonNhapRepository.findAll().size();
        chiTietDonNhap.setId(count.incrementAndGet());

        // Create the ChiTietDonNhap
        ChiTietDonNhapDTO chiTietDonNhapDTO = chiTietDonNhapMapper.toDto(chiTietDonNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietDonNhapMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietDonNhapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiTietDonNhap in the database
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChiTietDonNhap() throws Exception {
        // Initialize the database
        chiTietDonNhapRepository.saveAndFlush(chiTietDonNhap);

        int databaseSizeBeforeDelete = chiTietDonNhapRepository.findAll().size();

        // Delete the chiTietDonNhap
        restChiTietDonNhapMockMvc
            .perform(delete(ENTITY_API_URL_ID, chiTietDonNhap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChiTietDonNhap> chiTietDonNhapList = chiTietDonNhapRepository.findAll();
        assertThat(chiTietDonNhapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
