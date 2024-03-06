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
import warehouse.management.app.domain.NguyenLieu;
import warehouse.management.app.repository.NguyenLieuRepository;
import warehouse.management.app.service.dto.NguyenLieuDTO;
import warehouse.management.app.service.mapper.NguyenLieuMapper;

/**
 * Integration tests for the {@link NguyenLieuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NguyenLieuResourceIT {

    private static final String DEFAULT_HINH_ANH = "AAAAAAAAAA";
    private static final String UPDATED_HINH_ANH = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_NGUYEN_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_TEN_NGUYEN_LIEU = "BBBBBBBBBB";

    private static final Long DEFAULT_GIA_NHAP = 1L;
    private static final Long UPDATED_GIA_NHAP = 2L;

    private static final String DEFAULT_MO_TA = "AAAAAAAAAA";
    private static final String UPDATED_MO_TA = "BBBBBBBBBB";

    private static final String DEFAULT_DON_VI_TINH = "AAAAAAAAAA";
    private static final String UPDATED_DON_VI_TINH = "BBBBBBBBBB";

    private static final Long DEFAULT_V_AT = 1L;
    private static final Long UPDATED_V_AT = 2L;

    private static final String ENTITY_API_URL = "/api/nguyen-lieus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NguyenLieuRepository nguyenLieuRepository;

    @Autowired
    private NguyenLieuMapper nguyenLieuMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNguyenLieuMockMvc;

    private NguyenLieu nguyenLieu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NguyenLieu createEntity(EntityManager em) {
        NguyenLieu nguyenLieu = new NguyenLieu()
            .hinhAnh(DEFAULT_HINH_ANH)
            .tenNguyenLieu(DEFAULT_TEN_NGUYEN_LIEU)
            .giaNhap(DEFAULT_GIA_NHAP)
            .moTa(DEFAULT_MO_TA)
            .donViTinh(DEFAULT_DON_VI_TINH)
            .vAT(DEFAULT_V_AT);
        return nguyenLieu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NguyenLieu createUpdatedEntity(EntityManager em) {
        NguyenLieu nguyenLieu = new NguyenLieu()
            .hinhAnh(UPDATED_HINH_ANH)
            .tenNguyenLieu(UPDATED_TEN_NGUYEN_LIEU)
            .giaNhap(UPDATED_GIA_NHAP)
            .moTa(UPDATED_MO_TA)
            .donViTinh(UPDATED_DON_VI_TINH)
            .vAT(UPDATED_V_AT);
        return nguyenLieu;
    }

    @BeforeEach
    public void initTest() {
        nguyenLieu = createEntity(em);
    }

    @Test
    @Transactional
    void createNguyenLieu() throws Exception {
        int databaseSizeBeforeCreate = nguyenLieuRepository.findAll().size();
        // Create the NguyenLieu
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);
        restNguyenLieuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO)))
            .andExpect(status().isCreated());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeCreate + 1);
        NguyenLieu testNguyenLieu = nguyenLieuList.get(nguyenLieuList.size() - 1);
        assertThat(testNguyenLieu.getHinhAnh()).isEqualTo(DEFAULT_HINH_ANH);
        assertThat(testNguyenLieu.getTenNguyenLieu()).isEqualTo(DEFAULT_TEN_NGUYEN_LIEU);
        assertThat(testNguyenLieu.getGiaNhap()).isEqualTo(DEFAULT_GIA_NHAP);
        assertThat(testNguyenLieu.getMoTa()).isEqualTo(DEFAULT_MO_TA);
        assertThat(testNguyenLieu.getDonViTinh()).isEqualTo(DEFAULT_DON_VI_TINH);
        assertThat(testNguyenLieu.getvAT()).isEqualTo(DEFAULT_V_AT);
    }

    @Test
    @Transactional
    void createNguyenLieuWithExistingId() throws Exception {
        // Create the NguyenLieu with an existing ID
        nguyenLieu.setId(1L);
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        int databaseSizeBeforeCreate = nguyenLieuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNguyenLieuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHinhAnhIsRequired() throws Exception {
        int databaseSizeBeforeTest = nguyenLieuRepository.findAll().size();
        // set the field null
        nguyenLieu.setHinhAnh(null);

        // Create the NguyenLieu, which fails.
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        restNguyenLieuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO)))
            .andExpect(status().isBadRequest());

        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTenNguyenLieuIsRequired() throws Exception {
        int databaseSizeBeforeTest = nguyenLieuRepository.findAll().size();
        // set the field null
        nguyenLieu.setTenNguyenLieu(null);

        // Create the NguyenLieu, which fails.
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        restNguyenLieuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO)))
            .andExpect(status().isBadRequest());

        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGiaNhapIsRequired() throws Exception {
        int databaseSizeBeforeTest = nguyenLieuRepository.findAll().size();
        // set the field null
        nguyenLieu.setGiaNhap(null);

        // Create the NguyenLieu, which fails.
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        restNguyenLieuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO)))
            .andExpect(status().isBadRequest());

        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoTaIsRequired() throws Exception {
        int databaseSizeBeforeTest = nguyenLieuRepository.findAll().size();
        // set the field null
        nguyenLieu.setMoTa(null);

        // Create the NguyenLieu, which fails.
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        restNguyenLieuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO)))
            .andExpect(status().isBadRequest());

        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDonViTinhIsRequired() throws Exception {
        int databaseSizeBeforeTest = nguyenLieuRepository.findAll().size();
        // set the field null
        nguyenLieu.setDonViTinh(null);

        // Create the NguyenLieu, which fails.
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        restNguyenLieuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO)))
            .andExpect(status().isBadRequest());

        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkvATIsRequired() throws Exception {
        int databaseSizeBeforeTest = nguyenLieuRepository.findAll().size();
        // set the field null
        nguyenLieu.setvAT(null);

        // Create the NguyenLieu, which fails.
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        restNguyenLieuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO)))
            .andExpect(status().isBadRequest());

        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNguyenLieus() throws Exception {
        // Initialize the database
        nguyenLieuRepository.saveAndFlush(nguyenLieu);

        // Get all the nguyenLieuList
        restNguyenLieuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nguyenLieu.getId().intValue())))
            .andExpect(jsonPath("$.[*].hinhAnh").value(hasItem(DEFAULT_HINH_ANH)))
            .andExpect(jsonPath("$.[*].tenNguyenLieu").value(hasItem(DEFAULT_TEN_NGUYEN_LIEU)))
            .andExpect(jsonPath("$.[*].giaNhap").value(hasItem(DEFAULT_GIA_NHAP.intValue())))
            .andExpect(jsonPath("$.[*].moTa").value(hasItem(DEFAULT_MO_TA)))
            .andExpect(jsonPath("$.[*].donViTinh").value(hasItem(DEFAULT_DON_VI_TINH)))
            .andExpect(jsonPath("$.[*].vAT").value(hasItem(DEFAULT_V_AT.intValue())));
    }

    @Test
    @Transactional
    void getNguyenLieu() throws Exception {
        // Initialize the database
        nguyenLieuRepository.saveAndFlush(nguyenLieu);

        // Get the nguyenLieu
        restNguyenLieuMockMvc
            .perform(get(ENTITY_API_URL_ID, nguyenLieu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nguyenLieu.getId().intValue()))
            .andExpect(jsonPath("$.hinhAnh").value(DEFAULT_HINH_ANH))
            .andExpect(jsonPath("$.tenNguyenLieu").value(DEFAULT_TEN_NGUYEN_LIEU))
            .andExpect(jsonPath("$.giaNhap").value(DEFAULT_GIA_NHAP.intValue()))
            .andExpect(jsonPath("$.moTa").value(DEFAULT_MO_TA))
            .andExpect(jsonPath("$.donViTinh").value(DEFAULT_DON_VI_TINH))
            .andExpect(jsonPath("$.vAT").value(DEFAULT_V_AT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingNguyenLieu() throws Exception {
        // Get the nguyenLieu
        restNguyenLieuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNguyenLieu() throws Exception {
        // Initialize the database
        nguyenLieuRepository.saveAndFlush(nguyenLieu);

        int databaseSizeBeforeUpdate = nguyenLieuRepository.findAll().size();

        // Update the nguyenLieu
        NguyenLieu updatedNguyenLieu = nguyenLieuRepository.findById(nguyenLieu.getId()).get();
        // Disconnect from session so that the updates on updatedNguyenLieu are not directly saved in db
        em.detach(updatedNguyenLieu);
        updatedNguyenLieu
            .hinhAnh(UPDATED_HINH_ANH)
            .tenNguyenLieu(UPDATED_TEN_NGUYEN_LIEU)
            .giaNhap(UPDATED_GIA_NHAP)
            .moTa(UPDATED_MO_TA)
            .donViTinh(UPDATED_DON_VI_TINH)
            .vAT(UPDATED_V_AT);
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(updatedNguyenLieu);

        restNguyenLieuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nguyenLieuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO))
            )
            .andExpect(status().isOk());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeUpdate);
        NguyenLieu testNguyenLieu = nguyenLieuList.get(nguyenLieuList.size() - 1);
        assertThat(testNguyenLieu.getHinhAnh()).isEqualTo(UPDATED_HINH_ANH);
        assertThat(testNguyenLieu.getTenNguyenLieu()).isEqualTo(UPDATED_TEN_NGUYEN_LIEU);
        assertThat(testNguyenLieu.getGiaNhap()).isEqualTo(UPDATED_GIA_NHAP);
        assertThat(testNguyenLieu.getMoTa()).isEqualTo(UPDATED_MO_TA);
        assertThat(testNguyenLieu.getDonViTinh()).isEqualTo(UPDATED_DON_VI_TINH);
        assertThat(testNguyenLieu.getvAT()).isEqualTo(UPDATED_V_AT);
    }

    @Test
    @Transactional
    void putNonExistingNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nguyenLieuRepository.findAll().size();
        nguyenLieu.setId(count.incrementAndGet());

        // Create the NguyenLieu
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNguyenLieuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nguyenLieuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nguyenLieuRepository.findAll().size();
        nguyenLieu.setId(count.incrementAndGet());

        // Create the NguyenLieu
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNguyenLieuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nguyenLieuRepository.findAll().size();
        nguyenLieu.setId(count.incrementAndGet());

        // Create the NguyenLieu
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNguyenLieuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNguyenLieuWithPatch() throws Exception {
        // Initialize the database
        nguyenLieuRepository.saveAndFlush(nguyenLieu);

        int databaseSizeBeforeUpdate = nguyenLieuRepository.findAll().size();

        // Update the nguyenLieu using partial update
        NguyenLieu partialUpdatedNguyenLieu = new NguyenLieu();
        partialUpdatedNguyenLieu.setId(nguyenLieu.getId());

        partialUpdatedNguyenLieu.tenNguyenLieu(UPDATED_TEN_NGUYEN_LIEU).giaNhap(UPDATED_GIA_NHAP).moTa(UPDATED_MO_TA).vAT(UPDATED_V_AT);

        restNguyenLieuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNguyenLieu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNguyenLieu))
            )
            .andExpect(status().isOk());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeUpdate);
        NguyenLieu testNguyenLieu = nguyenLieuList.get(nguyenLieuList.size() - 1);
        assertThat(testNguyenLieu.getHinhAnh()).isEqualTo(DEFAULT_HINH_ANH);
        assertThat(testNguyenLieu.getTenNguyenLieu()).isEqualTo(UPDATED_TEN_NGUYEN_LIEU);
        assertThat(testNguyenLieu.getGiaNhap()).isEqualTo(UPDATED_GIA_NHAP);
        assertThat(testNguyenLieu.getMoTa()).isEqualTo(UPDATED_MO_TA);
        assertThat(testNguyenLieu.getDonViTinh()).isEqualTo(DEFAULT_DON_VI_TINH);
        assertThat(testNguyenLieu.getvAT()).isEqualTo(UPDATED_V_AT);
    }

    @Test
    @Transactional
    void fullUpdateNguyenLieuWithPatch() throws Exception {
        // Initialize the database
        nguyenLieuRepository.saveAndFlush(nguyenLieu);

        int databaseSizeBeforeUpdate = nguyenLieuRepository.findAll().size();

        // Update the nguyenLieu using partial update
        NguyenLieu partialUpdatedNguyenLieu = new NguyenLieu();
        partialUpdatedNguyenLieu.setId(nguyenLieu.getId());

        partialUpdatedNguyenLieu
            .hinhAnh(UPDATED_HINH_ANH)
            .tenNguyenLieu(UPDATED_TEN_NGUYEN_LIEU)
            .giaNhap(UPDATED_GIA_NHAP)
            .moTa(UPDATED_MO_TA)
            .donViTinh(UPDATED_DON_VI_TINH)
            .vAT(UPDATED_V_AT);

        restNguyenLieuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNguyenLieu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNguyenLieu))
            )
            .andExpect(status().isOk());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeUpdate);
        NguyenLieu testNguyenLieu = nguyenLieuList.get(nguyenLieuList.size() - 1);
        assertThat(testNguyenLieu.getHinhAnh()).isEqualTo(UPDATED_HINH_ANH);
        assertThat(testNguyenLieu.getTenNguyenLieu()).isEqualTo(UPDATED_TEN_NGUYEN_LIEU);
        assertThat(testNguyenLieu.getGiaNhap()).isEqualTo(UPDATED_GIA_NHAP);
        assertThat(testNguyenLieu.getMoTa()).isEqualTo(UPDATED_MO_TA);
        assertThat(testNguyenLieu.getDonViTinh()).isEqualTo(UPDATED_DON_VI_TINH);
        assertThat(testNguyenLieu.getvAT()).isEqualTo(UPDATED_V_AT);
    }

    @Test
    @Transactional
    void patchNonExistingNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nguyenLieuRepository.findAll().size();
        nguyenLieu.setId(count.incrementAndGet());

        // Create the NguyenLieu
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNguyenLieuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nguyenLieuDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nguyenLieuRepository.findAll().size();
        nguyenLieu.setId(count.incrementAndGet());

        // Create the NguyenLieu
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNguyenLieuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNguyenLieu() throws Exception {
        int databaseSizeBeforeUpdate = nguyenLieuRepository.findAll().size();
        nguyenLieu.setId(count.incrementAndGet());

        // Create the NguyenLieu
        NguyenLieuDTO nguyenLieuDTO = nguyenLieuMapper.toDto(nguyenLieu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNguyenLieuMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nguyenLieuDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NguyenLieu in the database
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNguyenLieu() throws Exception {
        // Initialize the database
        nguyenLieuRepository.saveAndFlush(nguyenLieu);

        int databaseSizeBeforeDelete = nguyenLieuRepository.findAll().size();

        // Delete the nguyenLieu
        restNguyenLieuMockMvc
            .perform(delete(ENTITY_API_URL_ID, nguyenLieu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
        assertThat(nguyenLieuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
