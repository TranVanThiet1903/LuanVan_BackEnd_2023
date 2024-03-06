package warehouse.management.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import warehouse.management.app.domain.DonXuat;
import warehouse.management.app.repository.DonXuatRepository;
import warehouse.management.app.service.dto.DonXuatDTO;
import warehouse.management.app.service.mapper.DonXuatMapper;

/**
 * Integration tests for the {@link DonXuatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DonXuatResourceIT {

    private static final Instant DEFAULT_NGAY_LAP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_LAP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_TONG_TIEN_HANG = 1L;
    private static final Long UPDATED_TONG_TIEN_HANG = 2L;

    private static final String DEFAULT_GHI_CHU = "AAAAAAAAAA";
    private static final String UPDATED_GHI_CHU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/don-xuats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DonXuatRepository donXuatRepository;

    @Autowired
    private DonXuatMapper donXuatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonXuatMockMvc;

    private DonXuat donXuat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonXuat createEntity(EntityManager em) {
        DonXuat donXuat = new DonXuat().ngayLap(DEFAULT_NGAY_LAP).tongTienHang(DEFAULT_TONG_TIEN_HANG).ghiChu(DEFAULT_GHI_CHU);
        return donXuat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonXuat createUpdatedEntity(EntityManager em) {
        DonXuat donXuat = new DonXuat().ngayLap(UPDATED_NGAY_LAP).tongTienHang(UPDATED_TONG_TIEN_HANG).ghiChu(UPDATED_GHI_CHU);
        return donXuat;
    }

    @BeforeEach
    public void initTest() {
        donXuat = createEntity(em);
    }

    @Test
    @Transactional
    void createDonXuat() throws Exception {
        int databaseSizeBeforeCreate = donXuatRepository.findAll().size();
        // Create the DonXuat
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(donXuat);
        restDonXuatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donXuatDTO)))
            .andExpect(status().isCreated());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeCreate + 1);
        DonXuat testDonXuat = donXuatList.get(donXuatList.size() - 1);
        assertThat(testDonXuat.getNgayLap()).isEqualTo(DEFAULT_NGAY_LAP);
        assertThat(testDonXuat.getTongTienHang()).isEqualTo(DEFAULT_TONG_TIEN_HANG);
        assertThat(testDonXuat.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    void createDonXuatWithExistingId() throws Exception {
        // Create the DonXuat with an existing ID
        donXuat.setId(1L);
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(donXuat);

        int databaseSizeBeforeCreate = donXuatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonXuatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donXuatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNgayLapIsRequired() throws Exception {
        int databaseSizeBeforeTest = donXuatRepository.findAll().size();
        // set the field null
        donXuat.setNgayLap(null);

        // Create the DonXuat, which fails.
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(donXuat);

        restDonXuatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donXuatDTO)))
            .andExpect(status().isBadRequest());

        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTongTienHangIsRequired() throws Exception {
        int databaseSizeBeforeTest = donXuatRepository.findAll().size();
        // set the field null
        donXuat.setTongTienHang(null);

        // Create the DonXuat, which fails.
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(donXuat);

        restDonXuatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donXuatDTO)))
            .andExpect(status().isBadRequest());

        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDonXuats() throws Exception {
        // Initialize the database
        donXuatRepository.saveAndFlush(donXuat);

        // Get all the donXuatList
        restDonXuatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donXuat.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngayLap").value(hasItem(DEFAULT_NGAY_LAP.toString())))
            .andExpect(jsonPath("$.[*].tongTienHang").value(hasItem(DEFAULT_TONG_TIEN_HANG.intValue())))
            .andExpect(jsonPath("$.[*].ghiChu").value(hasItem(DEFAULT_GHI_CHU)));
    }

    @Test
    @Transactional
    void getDonXuat() throws Exception {
        // Initialize the database
        donXuatRepository.saveAndFlush(donXuat);

        // Get the donXuat
        restDonXuatMockMvc
            .perform(get(ENTITY_API_URL_ID, donXuat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donXuat.getId().intValue()))
            .andExpect(jsonPath("$.ngayLap").value(DEFAULT_NGAY_LAP.toString()))
            .andExpect(jsonPath("$.tongTienHang").value(DEFAULT_TONG_TIEN_HANG.intValue()))
            .andExpect(jsonPath("$.ghiChu").value(DEFAULT_GHI_CHU));
    }

    @Test
    @Transactional
    void getNonExistingDonXuat() throws Exception {
        // Get the donXuat
        restDonXuatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDonXuat() throws Exception {
        // Initialize the database
        donXuatRepository.saveAndFlush(donXuat);

        int databaseSizeBeforeUpdate = donXuatRepository.findAll().size();

        // Update the donXuat
        DonXuat updatedDonXuat = donXuatRepository.findById(donXuat.getId()).get();
        // Disconnect from session so that the updates on updatedDonXuat are not directly saved in db
        em.detach(updatedDonXuat);
        updatedDonXuat.ngayLap(UPDATED_NGAY_LAP).tongTienHang(UPDATED_TONG_TIEN_HANG).ghiChu(UPDATED_GHI_CHU);
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(updatedDonXuat);

        restDonXuatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donXuatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donXuatDTO))
            )
            .andExpect(status().isOk());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeUpdate);
        DonXuat testDonXuat = donXuatList.get(donXuatList.size() - 1);
        assertThat(testDonXuat.getNgayLap()).isEqualTo(UPDATED_NGAY_LAP);
        assertThat(testDonXuat.getTongTienHang()).isEqualTo(UPDATED_TONG_TIEN_HANG);
        assertThat(testDonXuat.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void putNonExistingDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = donXuatRepository.findAll().size();
        donXuat.setId(count.incrementAndGet());

        // Create the DonXuat
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(donXuat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonXuatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donXuatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donXuatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = donXuatRepository.findAll().size();
        donXuat.setId(count.incrementAndGet());

        // Create the DonXuat
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(donXuat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonXuatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donXuatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = donXuatRepository.findAll().size();
        donXuat.setId(count.incrementAndGet());

        // Create the DonXuat
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(donXuat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonXuatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donXuatDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonXuatWithPatch() throws Exception {
        // Initialize the database
        donXuatRepository.saveAndFlush(donXuat);

        int databaseSizeBeforeUpdate = donXuatRepository.findAll().size();

        // Update the donXuat using partial update
        DonXuat partialUpdatedDonXuat = new DonXuat();
        partialUpdatedDonXuat.setId(donXuat.getId());

        partialUpdatedDonXuat.ngayLap(UPDATED_NGAY_LAP);

        restDonXuatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonXuat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonXuat))
            )
            .andExpect(status().isOk());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeUpdate);
        DonXuat testDonXuat = donXuatList.get(donXuatList.size() - 1);
        assertThat(testDonXuat.getNgayLap()).isEqualTo(UPDATED_NGAY_LAP);
        assertThat(testDonXuat.getTongTienHang()).isEqualTo(DEFAULT_TONG_TIEN_HANG);
        assertThat(testDonXuat.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    void fullUpdateDonXuatWithPatch() throws Exception {
        // Initialize the database
        donXuatRepository.saveAndFlush(donXuat);

        int databaseSizeBeforeUpdate = donXuatRepository.findAll().size();

        // Update the donXuat using partial update
        DonXuat partialUpdatedDonXuat = new DonXuat();
        partialUpdatedDonXuat.setId(donXuat.getId());

        partialUpdatedDonXuat.ngayLap(UPDATED_NGAY_LAP).tongTienHang(UPDATED_TONG_TIEN_HANG).ghiChu(UPDATED_GHI_CHU);

        restDonXuatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonXuat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonXuat))
            )
            .andExpect(status().isOk());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeUpdate);
        DonXuat testDonXuat = donXuatList.get(donXuatList.size() - 1);
        assertThat(testDonXuat.getNgayLap()).isEqualTo(UPDATED_NGAY_LAP);
        assertThat(testDonXuat.getTongTienHang()).isEqualTo(UPDATED_TONG_TIEN_HANG);
        assertThat(testDonXuat.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void patchNonExistingDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = donXuatRepository.findAll().size();
        donXuat.setId(count.incrementAndGet());

        // Create the DonXuat
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(donXuat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonXuatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donXuatDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donXuatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = donXuatRepository.findAll().size();
        donXuat.setId(count.incrementAndGet());

        // Create the DonXuat
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(donXuat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonXuatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donXuatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonXuat() throws Exception {
        int databaseSizeBeforeUpdate = donXuatRepository.findAll().size();
        donXuat.setId(count.incrementAndGet());

        // Create the DonXuat
        DonXuatDTO donXuatDTO = donXuatMapper.toDto(donXuat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonXuatMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(donXuatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonXuat in the database
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonXuat() throws Exception {
        // Initialize the database
        donXuatRepository.saveAndFlush(donXuat);

        int databaseSizeBeforeDelete = donXuatRepository.findAll().size();

        // Delete the donXuat
        restDonXuatMockMvc
            .perform(delete(ENTITY_API_URL_ID, donXuat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DonXuat> donXuatList = donXuatRepository.findAll();
        assertThat(donXuatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
