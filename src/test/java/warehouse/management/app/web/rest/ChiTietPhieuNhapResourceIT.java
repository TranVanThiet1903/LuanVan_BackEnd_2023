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
import warehouse.management.app.domain.ChiTietPhieuNhap;
import warehouse.management.app.repository.ChiTietPhieuNhapRepository;
import warehouse.management.app.service.dto.ChiTietPhieuNhapDTO;
import warehouse.management.app.service.mapper.ChiTietPhieuNhapMapper;

/**
 * Integration tests for the {@link ChiTietPhieuNhapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChiTietPhieuNhapResourceIT {

    private static final Long DEFAULT_SO_LUONG = 1L;
    private static final Long UPDATED_SO_LUONG = 2L;

    private static final Long DEFAULT_THANH_TIEN = 1L;
    private static final Long UPDATED_THANH_TIEN = 2L;

    private static final String ENTITY_API_URL = "/api/chi-tiet-phieu-nhaps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChiTietPhieuNhapRepository chiTietPhieuNhapRepository;

    @Autowired
    private ChiTietPhieuNhapMapper chiTietPhieuNhapMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChiTietPhieuNhapMockMvc;

    private ChiTietPhieuNhap chiTietPhieuNhap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiTietPhieuNhap createEntity(EntityManager em) {
        ChiTietPhieuNhap chiTietPhieuNhap = new ChiTietPhieuNhap().soLuong(DEFAULT_SO_LUONG).thanhTien(DEFAULT_THANH_TIEN);
        return chiTietPhieuNhap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiTietPhieuNhap createUpdatedEntity(EntityManager em) {
        ChiTietPhieuNhap chiTietPhieuNhap = new ChiTietPhieuNhap().soLuong(UPDATED_SO_LUONG).thanhTien(UPDATED_THANH_TIEN);
        return chiTietPhieuNhap;
    }

    @BeforeEach
    public void initTest() {
        chiTietPhieuNhap = createEntity(em);
    }

    @Test
    @Transactional
    void createChiTietPhieuNhap() throws Exception {
        int databaseSizeBeforeCreate = chiTietPhieuNhapRepository.findAll().size();
        // Create the ChiTietPhieuNhap
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);
        restChiTietPhieuNhapMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeCreate + 1);
        ChiTietPhieuNhap testChiTietPhieuNhap = chiTietPhieuNhapList.get(chiTietPhieuNhapList.size() - 1);
        assertThat(testChiTietPhieuNhap.getSoLuong()).isEqualTo(DEFAULT_SO_LUONG);
        assertThat(testChiTietPhieuNhap.getThanhTien()).isEqualTo(DEFAULT_THANH_TIEN);
    }

    @Test
    @Transactional
    void createChiTietPhieuNhapWithExistingId() throws Exception {
        // Create the ChiTietPhieuNhap with an existing ID
        chiTietPhieuNhap.setId(1L);
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);

        int databaseSizeBeforeCreate = chiTietPhieuNhapRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChiTietPhieuNhapMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSoLuongIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTietPhieuNhapRepository.findAll().size();
        // set the field null
        chiTietPhieuNhap.setSoLuong(null);

        // Create the ChiTietPhieuNhap, which fails.
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);

        restChiTietPhieuNhapMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThanhTienIsRequired() throws Exception {
        int databaseSizeBeforeTest = chiTietPhieuNhapRepository.findAll().size();
        // set the field null
        chiTietPhieuNhap.setThanhTien(null);

        // Create the ChiTietPhieuNhap, which fails.
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);

        restChiTietPhieuNhapMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChiTietPhieuNhaps() throws Exception {
        // Initialize the database
        chiTietPhieuNhapRepository.saveAndFlush(chiTietPhieuNhap);

        // Get all the chiTietPhieuNhapList
        restChiTietPhieuNhapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chiTietPhieuNhap.getId().intValue())))
            .andExpect(jsonPath("$.[*].soLuong").value(hasItem(DEFAULT_SO_LUONG.intValue())))
            .andExpect(jsonPath("$.[*].thanhTien").value(hasItem(DEFAULT_THANH_TIEN.intValue())));
    }

    @Test
    @Transactional
    void getChiTietPhieuNhap() throws Exception {
        // Initialize the database
        chiTietPhieuNhapRepository.saveAndFlush(chiTietPhieuNhap);

        // Get the chiTietPhieuNhap
        restChiTietPhieuNhapMockMvc
            .perform(get(ENTITY_API_URL_ID, chiTietPhieuNhap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chiTietPhieuNhap.getId().intValue()))
            .andExpect(jsonPath("$.soLuong").value(DEFAULT_SO_LUONG.intValue()))
            .andExpect(jsonPath("$.thanhTien").value(DEFAULT_THANH_TIEN.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingChiTietPhieuNhap() throws Exception {
        // Get the chiTietPhieuNhap
        restChiTietPhieuNhapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChiTietPhieuNhap() throws Exception {
        // Initialize the database
        chiTietPhieuNhapRepository.saveAndFlush(chiTietPhieuNhap);

        int databaseSizeBeforeUpdate = chiTietPhieuNhapRepository.findAll().size();

        // Update the chiTietPhieuNhap
        ChiTietPhieuNhap updatedChiTietPhieuNhap = chiTietPhieuNhapRepository.findById(chiTietPhieuNhap.getId()).get();
        // Disconnect from session so that the updates on updatedChiTietPhieuNhap are not directly saved in db
        em.detach(updatedChiTietPhieuNhap);
        updatedChiTietPhieuNhap.soLuong(UPDATED_SO_LUONG).thanhTien(UPDATED_THANH_TIEN);
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(updatedChiTietPhieuNhap);

        restChiTietPhieuNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiTietPhieuNhapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeUpdate);
        ChiTietPhieuNhap testChiTietPhieuNhap = chiTietPhieuNhapList.get(chiTietPhieuNhapList.size() - 1);
        assertThat(testChiTietPhieuNhap.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
        assertThat(testChiTietPhieuNhap.getThanhTien()).isEqualTo(UPDATED_THANH_TIEN);
    }

    @Test
    @Transactional
    void putNonExistingChiTietPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietPhieuNhapRepository.findAll().size();
        chiTietPhieuNhap.setId(count.incrementAndGet());

        // Create the ChiTietPhieuNhap
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTietPhieuNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiTietPhieuNhapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChiTietPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietPhieuNhapRepository.findAll().size();
        chiTietPhieuNhap.setId(count.incrementAndGet());

        // Create the ChiTietPhieuNhap
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietPhieuNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChiTietPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietPhieuNhapRepository.findAll().size();
        chiTietPhieuNhap.setId(count.incrementAndGet());

        // Create the ChiTietPhieuNhap
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietPhieuNhapMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChiTietPhieuNhapWithPatch() throws Exception {
        // Initialize the database
        chiTietPhieuNhapRepository.saveAndFlush(chiTietPhieuNhap);

        int databaseSizeBeforeUpdate = chiTietPhieuNhapRepository.findAll().size();

        // Update the chiTietPhieuNhap using partial update
        ChiTietPhieuNhap partialUpdatedChiTietPhieuNhap = new ChiTietPhieuNhap();
        partialUpdatedChiTietPhieuNhap.setId(chiTietPhieuNhap.getId());

        partialUpdatedChiTietPhieuNhap.soLuong(UPDATED_SO_LUONG);

        restChiTietPhieuNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiTietPhieuNhap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiTietPhieuNhap))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeUpdate);
        ChiTietPhieuNhap testChiTietPhieuNhap = chiTietPhieuNhapList.get(chiTietPhieuNhapList.size() - 1);
        assertThat(testChiTietPhieuNhap.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
        assertThat(testChiTietPhieuNhap.getThanhTien()).isEqualTo(DEFAULT_THANH_TIEN);
    }

    @Test
    @Transactional
    void fullUpdateChiTietPhieuNhapWithPatch() throws Exception {
        // Initialize the database
        chiTietPhieuNhapRepository.saveAndFlush(chiTietPhieuNhap);

        int databaseSizeBeforeUpdate = chiTietPhieuNhapRepository.findAll().size();

        // Update the chiTietPhieuNhap using partial update
        ChiTietPhieuNhap partialUpdatedChiTietPhieuNhap = new ChiTietPhieuNhap();
        partialUpdatedChiTietPhieuNhap.setId(chiTietPhieuNhap.getId());

        partialUpdatedChiTietPhieuNhap.soLuong(UPDATED_SO_LUONG).thanhTien(UPDATED_THANH_TIEN);

        restChiTietPhieuNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiTietPhieuNhap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiTietPhieuNhap))
            )
            .andExpect(status().isOk());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeUpdate);
        ChiTietPhieuNhap testChiTietPhieuNhap = chiTietPhieuNhapList.get(chiTietPhieuNhapList.size() - 1);
        assertThat(testChiTietPhieuNhap.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
        assertThat(testChiTietPhieuNhap.getThanhTien()).isEqualTo(UPDATED_THANH_TIEN);
    }

    @Test
    @Transactional
    void patchNonExistingChiTietPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietPhieuNhapRepository.findAll().size();
        chiTietPhieuNhap.setId(count.incrementAndGet());

        // Create the ChiTietPhieuNhap
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiTietPhieuNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chiTietPhieuNhapDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChiTietPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietPhieuNhapRepository.findAll().size();
        chiTietPhieuNhap.setId(count.incrementAndGet());

        // Create the ChiTietPhieuNhap
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietPhieuNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChiTietPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = chiTietPhieuNhapRepository.findAll().size();
        chiTietPhieuNhap.setId(count.incrementAndGet());

        // Create the ChiTietPhieuNhap
        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = chiTietPhieuNhapMapper.toDto(chiTietPhieuNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiTietPhieuNhapMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiTietPhieuNhapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiTietPhieuNhap in the database
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChiTietPhieuNhap() throws Exception {
        // Initialize the database
        chiTietPhieuNhapRepository.saveAndFlush(chiTietPhieuNhap);

        int databaseSizeBeforeDelete = chiTietPhieuNhapRepository.findAll().size();

        // Delete the chiTietPhieuNhap
        restChiTietPhieuNhapMockMvc
            .perform(delete(ENTITY_API_URL_ID, chiTietPhieuNhap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPhieuNhapRepository.findAll();
        assertThat(chiTietPhieuNhapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
