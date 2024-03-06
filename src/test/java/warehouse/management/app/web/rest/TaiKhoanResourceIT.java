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
import warehouse.management.app.domain.TaiKhoan;
import warehouse.management.app.repository.TaiKhoanRepository;
import warehouse.management.app.service.dto.TaiKhoanDTO;
import warehouse.management.app.service.mapper.TaiKhoanMapper;

/**
 * Integration tests for the {@link TaiKhoanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaiKhoanResourceIT {

    private static final String DEFAULT_HO_TEN = "AAAAAAAAAA";
    private static final String UPDATED_HO_TEN = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Instant DEFAULT_NGAY_TAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_TAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_SALT = 1L;
    private static final Long UPDATED_SALT = 2L;

    private static final String ENTITY_API_URL = "/api/tai-khoans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private TaiKhoanMapper taiKhoanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaiKhoanMockMvc;

    private TaiKhoan taiKhoan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaiKhoan createEntity(EntityManager em) {
        TaiKhoan taiKhoan = new TaiKhoan()
            .hoTen(DEFAULT_HO_TEN)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .ngayTao(DEFAULT_NGAY_TAO)
            .salt(DEFAULT_SALT);
        return taiKhoan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaiKhoan createUpdatedEntity(EntityManager em) {
        TaiKhoan taiKhoan = new TaiKhoan()
            .hoTen(UPDATED_HO_TEN)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .ngayTao(UPDATED_NGAY_TAO)
            .salt(UPDATED_SALT);
        return taiKhoan;
    }

    @BeforeEach
    public void initTest() {
        taiKhoan = createEntity(em);
    }

    @Test
    @Transactional
    void createTaiKhoan() throws Exception {
        int databaseSizeBeforeCreate = taiKhoanRepository.findAll().size();
        // Create the TaiKhoan
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);
        restTaiKhoanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO)))
            .andExpect(status().isCreated());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeCreate + 1);
        TaiKhoan testTaiKhoan = taiKhoanList.get(taiKhoanList.size() - 1);
        assertThat(testTaiKhoan.getHoTen()).isEqualTo(DEFAULT_HO_TEN);
        assertThat(testTaiKhoan.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testTaiKhoan.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testTaiKhoan.getNgayTao()).isEqualTo(DEFAULT_NGAY_TAO);
        assertThat(testTaiKhoan.getSalt()).isEqualTo(DEFAULT_SALT);
    }

    @Test
    @Transactional
    void createTaiKhoanWithExistingId() throws Exception {
        // Create the TaiKhoan with an existing ID
        taiKhoan.setId(1L);
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        int databaseSizeBeforeCreate = taiKhoanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaiKhoanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHoTenIsRequired() throws Exception {
        int databaseSizeBeforeTest = taiKhoanRepository.findAll().size();
        // set the field null
        taiKhoan.setHoTen(null);

        // Create the TaiKhoan, which fails.
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        restTaiKhoanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO)))
            .andExpect(status().isBadRequest());

        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taiKhoanRepository.findAll().size();
        // set the field null
        taiKhoan.setUsername(null);

        // Create the TaiKhoan, which fails.
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        restTaiKhoanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO)))
            .andExpect(status().isBadRequest());

        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = taiKhoanRepository.findAll().size();
        // set the field null
        taiKhoan.setPassword(null);

        // Create the TaiKhoan, which fails.
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        restTaiKhoanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO)))
            .andExpect(status().isBadRequest());

        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNgayTaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = taiKhoanRepository.findAll().size();
        // set the field null
        taiKhoan.setNgayTao(null);

        // Create the TaiKhoan, which fails.
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        restTaiKhoanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO)))
            .andExpect(status().isBadRequest());

        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSaltIsRequired() throws Exception {
        int databaseSizeBeforeTest = taiKhoanRepository.findAll().size();
        // set the field null
        taiKhoan.setSalt(null);

        // Create the TaiKhoan, which fails.
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        restTaiKhoanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO)))
            .andExpect(status().isBadRequest());

        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaiKhoans() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        // Get all the taiKhoanList
        restTaiKhoanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taiKhoan.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoTen").value(hasItem(DEFAULT_HO_TEN)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].ngayTao").value(hasItem(DEFAULT_NGAY_TAO.toString())))
            .andExpect(jsonPath("$.[*].salt").value(hasItem(DEFAULT_SALT.intValue())));
    }

    @Test
    @Transactional
    void getTaiKhoan() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        // Get the taiKhoan
        restTaiKhoanMockMvc
            .perform(get(ENTITY_API_URL_ID, taiKhoan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taiKhoan.getId().intValue()))
            .andExpect(jsonPath("$.hoTen").value(DEFAULT_HO_TEN))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.ngayTao").value(DEFAULT_NGAY_TAO.toString()))
            .andExpect(jsonPath("$.salt").value(DEFAULT_SALT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTaiKhoan() throws Exception {
        // Get the taiKhoan
        restTaiKhoanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaiKhoan() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();

        // Update the taiKhoan
        TaiKhoan updatedTaiKhoan = taiKhoanRepository.findById(taiKhoan.getId()).get();
        // Disconnect from session so that the updates on updatedTaiKhoan are not directly saved in db
        em.detach(updatedTaiKhoan);
        updatedTaiKhoan
            .hoTen(UPDATED_HO_TEN)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .ngayTao(UPDATED_NGAY_TAO)
            .salt(UPDATED_SALT);
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(updatedTaiKhoan);

        restTaiKhoanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taiKhoanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
        TaiKhoan testTaiKhoan = taiKhoanList.get(taiKhoanList.size() - 1);
        assertThat(testTaiKhoan.getHoTen()).isEqualTo(UPDATED_HO_TEN);
        assertThat(testTaiKhoan.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testTaiKhoan.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testTaiKhoan.getNgayTao()).isEqualTo(UPDATED_NGAY_TAO);
        assertThat(testTaiKhoan.getSalt()).isEqualTo(UPDATED_SALT);
    }

    @Test
    @Transactional
    void putNonExistingTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // Create the TaiKhoan
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taiKhoanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // Create the TaiKhoan
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // Create the TaiKhoan
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaiKhoanWithPatch() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();

        // Update the taiKhoan using partial update
        TaiKhoan partialUpdatedTaiKhoan = new TaiKhoan();
        partialUpdatedTaiKhoan.setId(taiKhoan.getId());

        partialUpdatedTaiKhoan.hoTen(UPDATED_HO_TEN).password(UPDATED_PASSWORD).salt(UPDATED_SALT);

        restTaiKhoanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaiKhoan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaiKhoan))
            )
            .andExpect(status().isOk());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
        TaiKhoan testTaiKhoan = taiKhoanList.get(taiKhoanList.size() - 1);
        assertThat(testTaiKhoan.getHoTen()).isEqualTo(UPDATED_HO_TEN);
        assertThat(testTaiKhoan.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testTaiKhoan.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testTaiKhoan.getNgayTao()).isEqualTo(DEFAULT_NGAY_TAO);
        assertThat(testTaiKhoan.getSalt()).isEqualTo(UPDATED_SALT);
    }

    @Test
    @Transactional
    void fullUpdateTaiKhoanWithPatch() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();

        // Update the taiKhoan using partial update
        TaiKhoan partialUpdatedTaiKhoan = new TaiKhoan();
        partialUpdatedTaiKhoan.setId(taiKhoan.getId());

        partialUpdatedTaiKhoan
            .hoTen(UPDATED_HO_TEN)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .ngayTao(UPDATED_NGAY_TAO)
            .salt(UPDATED_SALT);

        restTaiKhoanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaiKhoan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaiKhoan))
            )
            .andExpect(status().isOk());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
        TaiKhoan testTaiKhoan = taiKhoanList.get(taiKhoanList.size() - 1);
        assertThat(testTaiKhoan.getHoTen()).isEqualTo(UPDATED_HO_TEN);
        assertThat(testTaiKhoan.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testTaiKhoan.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testTaiKhoan.getNgayTao()).isEqualTo(UPDATED_NGAY_TAO);
        assertThat(testTaiKhoan.getSalt()).isEqualTo(UPDATED_SALT);
    }

    @Test
    @Transactional
    void patchNonExistingTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // Create the TaiKhoan
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taiKhoanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // Create the TaiKhoan
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // Create the TaiKhoan
        TaiKhoanDTO taiKhoanDTO = taiKhoanMapper.toDto(taiKhoan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taiKhoanDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaiKhoan() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        int databaseSizeBeforeDelete = taiKhoanRepository.findAll().size();

        // Delete the taiKhoan
        restTaiKhoanMockMvc
            .perform(delete(ENTITY_API_URL_ID, taiKhoan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
