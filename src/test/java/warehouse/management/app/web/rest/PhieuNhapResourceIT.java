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
import warehouse.management.app.domain.PhieuNhap;
import warehouse.management.app.repository.PhieuNhapRepository;
import warehouse.management.app.service.dto.PhieuNhapDTO;
import warehouse.management.app.service.mapper.PhieuNhapMapper;

/**
 * Integration tests for the {@link PhieuNhapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhieuNhapResourceIT {

    private static final Instant DEFAULT_NGAY_LAP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_LAP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_TONG_TIEN_HANG = 1L;
    private static final Long UPDATED_TONG_TIEN_HANG = 2L;

    private static final Long DEFAULT_V_AT = 1L;
    private static final Long UPDATED_V_AT = 2L;

    private static final Long DEFAULT_PHI_SHIP = 1L;
    private static final Long UPDATED_PHI_SHIP = 2L;

    private static final Long DEFAULT_GIAM_GIA = 1L;
    private static final Long UPDATED_GIAM_GIA = 2L;

    private static final Long DEFAULT_TONG_TIEN_THANH_TOAN = 1L;
    private static final Long UPDATED_TONG_TIEN_THANH_TOAN = 2L;

    private static final Long DEFAULT_TIEN_NO = 1L;
    private static final Long UPDATED_TIEN_NO = 2L;

    private static final String DEFAULT_GHI_CHU = "AAAAAAAAAA";
    private static final String UPDATED_GHI_CHU = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phieu-nhaps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhieuNhapRepository phieuNhapRepository;

    @Autowired
    private PhieuNhapMapper phieuNhapMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhieuNhapMockMvc;

    private PhieuNhap phieuNhap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhieuNhap createEntity(EntityManager em) {
        PhieuNhap phieuNhap = new PhieuNhap()
            .ngayLap(DEFAULT_NGAY_LAP)
            .tongTienHang(DEFAULT_TONG_TIEN_HANG)
            .vAT(DEFAULT_V_AT)
            .phiShip(DEFAULT_PHI_SHIP)
            .giamGia(DEFAULT_GIAM_GIA)
            .tongTienThanhToan(DEFAULT_TONG_TIEN_THANH_TOAN)
            .tienNo(DEFAULT_TIEN_NO)
            .ghiChu(DEFAULT_GHI_CHU);
        return phieuNhap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhieuNhap createUpdatedEntity(EntityManager em) {
        PhieuNhap phieuNhap = new PhieuNhap()
            .ngayLap(UPDATED_NGAY_LAP)
            .tongTienHang(UPDATED_TONG_TIEN_HANG)
            .vAT(UPDATED_V_AT)
            .phiShip(UPDATED_PHI_SHIP)
            .giamGia(UPDATED_GIAM_GIA)
            .tongTienThanhToan(UPDATED_TONG_TIEN_THANH_TOAN)
            .tienNo(UPDATED_TIEN_NO)
            .ghiChu(UPDATED_GHI_CHU);
        return phieuNhap;
    }

    @BeforeEach
    public void initTest() {
        phieuNhap = createEntity(em);
    }

    @Test
    @Transactional
    void createPhieuNhap() throws Exception {
        int databaseSizeBeforeCreate = phieuNhapRepository.findAll().size();
        // Create the PhieuNhap
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);
        restPhieuNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO)))
            .andExpect(status().isCreated());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeCreate + 1);
        PhieuNhap testPhieuNhap = phieuNhapList.get(phieuNhapList.size() - 1);
        assertThat(testPhieuNhap.getNgayLap()).isEqualTo(DEFAULT_NGAY_LAP);
        assertThat(testPhieuNhap.getTongTienHang()).isEqualTo(DEFAULT_TONG_TIEN_HANG);
        assertThat(testPhieuNhap.getvAT()).isEqualTo(DEFAULT_V_AT);
        assertThat(testPhieuNhap.getPhiShip()).isEqualTo(DEFAULT_PHI_SHIP);
        assertThat(testPhieuNhap.getGiamGia()).isEqualTo(DEFAULT_GIAM_GIA);
        assertThat(testPhieuNhap.getTongTienThanhToan()).isEqualTo(DEFAULT_TONG_TIEN_THANH_TOAN);
        assertThat(testPhieuNhap.getTienNo()).isEqualTo(DEFAULT_TIEN_NO);
        assertThat(testPhieuNhap.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    void createPhieuNhapWithExistingId() throws Exception {
        // Create the PhieuNhap with an existing ID
        phieuNhap.setId(1L);
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        int databaseSizeBeforeCreate = phieuNhapRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhieuNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNgayLapIsRequired() throws Exception {
        int databaseSizeBeforeTest = phieuNhapRepository.findAll().size();
        // set the field null
        phieuNhap.setNgayLap(null);

        // Create the PhieuNhap, which fails.
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        restPhieuNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO)))
            .andExpect(status().isBadRequest());

        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTongTienHangIsRequired() throws Exception {
        int databaseSizeBeforeTest = phieuNhapRepository.findAll().size();
        // set the field null
        phieuNhap.setTongTienHang(null);

        // Create the PhieuNhap, which fails.
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        restPhieuNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO)))
            .andExpect(status().isBadRequest());

        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkvATIsRequired() throws Exception {
        int databaseSizeBeforeTest = phieuNhapRepository.findAll().size();
        // set the field null
        phieuNhap.setvAT(null);

        // Create the PhieuNhap, which fails.
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        restPhieuNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO)))
            .andExpect(status().isBadRequest());

        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhiShipIsRequired() throws Exception {
        int databaseSizeBeforeTest = phieuNhapRepository.findAll().size();
        // set the field null
        phieuNhap.setPhiShip(null);

        // Create the PhieuNhap, which fails.
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        restPhieuNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO)))
            .andExpect(status().isBadRequest());

        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGiamGiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = phieuNhapRepository.findAll().size();
        // set the field null
        phieuNhap.setGiamGia(null);

        // Create the PhieuNhap, which fails.
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        restPhieuNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO)))
            .andExpect(status().isBadRequest());

        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTongTienThanhToanIsRequired() throws Exception {
        int databaseSizeBeforeTest = phieuNhapRepository.findAll().size();
        // set the field null
        phieuNhap.setTongTienThanhToan(null);

        // Create the PhieuNhap, which fails.
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        restPhieuNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO)))
            .andExpect(status().isBadRequest());

        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTienNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = phieuNhapRepository.findAll().size();
        // set the field null
        phieuNhap.setTienNo(null);

        // Create the PhieuNhap, which fails.
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        restPhieuNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO)))
            .andExpect(status().isBadRequest());

        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPhieuNhaps() throws Exception {
        // Initialize the database
        phieuNhapRepository.saveAndFlush(phieuNhap);

        // Get all the phieuNhapList
        restPhieuNhapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phieuNhap.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngayLap").value(hasItem(DEFAULT_NGAY_LAP.toString())))
            .andExpect(jsonPath("$.[*].tongTienHang").value(hasItem(DEFAULT_TONG_TIEN_HANG.intValue())))
            .andExpect(jsonPath("$.[*].vAT").value(hasItem(DEFAULT_V_AT.intValue())))
            .andExpect(jsonPath("$.[*].phiShip").value(hasItem(DEFAULT_PHI_SHIP.intValue())))
            .andExpect(jsonPath("$.[*].giamGia").value(hasItem(DEFAULT_GIAM_GIA.intValue())))
            .andExpect(jsonPath("$.[*].tongTienThanhToan").value(hasItem(DEFAULT_TONG_TIEN_THANH_TOAN.intValue())))
            .andExpect(jsonPath("$.[*].tienNo").value(hasItem(DEFAULT_TIEN_NO.intValue())))
            .andExpect(jsonPath("$.[*].ghiChu").value(hasItem(DEFAULT_GHI_CHU)));
    }

    @Test
    @Transactional
    void getPhieuNhap() throws Exception {
        // Initialize the database
        phieuNhapRepository.saveAndFlush(phieuNhap);

        // Get the phieuNhap
        restPhieuNhapMockMvc
            .perform(get(ENTITY_API_URL_ID, phieuNhap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phieuNhap.getId().intValue()))
            .andExpect(jsonPath("$.ngayLap").value(DEFAULT_NGAY_LAP.toString()))
            .andExpect(jsonPath("$.tongTienHang").value(DEFAULT_TONG_TIEN_HANG.intValue()))
            .andExpect(jsonPath("$.vAT").value(DEFAULT_V_AT.intValue()))
            .andExpect(jsonPath("$.phiShip").value(DEFAULT_PHI_SHIP.intValue()))
            .andExpect(jsonPath("$.giamGia").value(DEFAULT_GIAM_GIA.intValue()))
            .andExpect(jsonPath("$.tongTienThanhToan").value(DEFAULT_TONG_TIEN_THANH_TOAN.intValue()))
            .andExpect(jsonPath("$.tienNo").value(DEFAULT_TIEN_NO.intValue()))
            .andExpect(jsonPath("$.ghiChu").value(DEFAULT_GHI_CHU));
    }

    @Test
    @Transactional
    void getNonExistingPhieuNhap() throws Exception {
        // Get the phieuNhap
        restPhieuNhapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPhieuNhap() throws Exception {
        // Initialize the database
        phieuNhapRepository.saveAndFlush(phieuNhap);

        int databaseSizeBeforeUpdate = phieuNhapRepository.findAll().size();

        // Update the phieuNhap
        PhieuNhap updatedPhieuNhap = phieuNhapRepository.findById(phieuNhap.getId()).get();
        // Disconnect from session so that the updates on updatedPhieuNhap are not directly saved in db
        em.detach(updatedPhieuNhap);
        updatedPhieuNhap
            .ngayLap(UPDATED_NGAY_LAP)
            .tongTienHang(UPDATED_TONG_TIEN_HANG)
            .vAT(UPDATED_V_AT)
            .phiShip(UPDATED_PHI_SHIP)
            .giamGia(UPDATED_GIAM_GIA)
            .tongTienThanhToan(UPDATED_TONG_TIEN_THANH_TOAN)
            .tienNo(UPDATED_TIEN_NO)
            .ghiChu(UPDATED_GHI_CHU);
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(updatedPhieuNhap);

        restPhieuNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phieuNhapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO))
            )
            .andExpect(status().isOk());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeUpdate);
        PhieuNhap testPhieuNhap = phieuNhapList.get(phieuNhapList.size() - 1);
        assertThat(testPhieuNhap.getNgayLap()).isEqualTo(UPDATED_NGAY_LAP);
        assertThat(testPhieuNhap.getTongTienHang()).isEqualTo(UPDATED_TONG_TIEN_HANG);
        assertThat(testPhieuNhap.getvAT()).isEqualTo(UPDATED_V_AT);
        assertThat(testPhieuNhap.getPhiShip()).isEqualTo(UPDATED_PHI_SHIP);
        assertThat(testPhieuNhap.getGiamGia()).isEqualTo(UPDATED_GIAM_GIA);
        assertThat(testPhieuNhap.getTongTienThanhToan()).isEqualTo(UPDATED_TONG_TIEN_THANH_TOAN);
        assertThat(testPhieuNhap.getTienNo()).isEqualTo(UPDATED_TIEN_NO);
        assertThat(testPhieuNhap.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void putNonExistingPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = phieuNhapRepository.findAll().size();
        phieuNhap.setId(count.incrementAndGet());

        // Create the PhieuNhap
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhieuNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phieuNhapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = phieuNhapRepository.findAll().size();
        phieuNhap.setId(count.incrementAndGet());

        // Create the PhieuNhap
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhieuNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = phieuNhapRepository.findAll().size();
        phieuNhap.setId(count.incrementAndGet());

        // Create the PhieuNhap
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhieuNhapMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhieuNhapWithPatch() throws Exception {
        // Initialize the database
        phieuNhapRepository.saveAndFlush(phieuNhap);

        int databaseSizeBeforeUpdate = phieuNhapRepository.findAll().size();

        // Update the phieuNhap using partial update
        PhieuNhap partialUpdatedPhieuNhap = new PhieuNhap();
        partialUpdatedPhieuNhap.setId(phieuNhap.getId());

        partialUpdatedPhieuNhap.vAT(UPDATED_V_AT).tienNo(UPDATED_TIEN_NO).ghiChu(UPDATED_GHI_CHU);

        restPhieuNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhieuNhap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhieuNhap))
            )
            .andExpect(status().isOk());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeUpdate);
        PhieuNhap testPhieuNhap = phieuNhapList.get(phieuNhapList.size() - 1);
        assertThat(testPhieuNhap.getNgayLap()).isEqualTo(DEFAULT_NGAY_LAP);
        assertThat(testPhieuNhap.getTongTienHang()).isEqualTo(DEFAULT_TONG_TIEN_HANG);
        assertThat(testPhieuNhap.getvAT()).isEqualTo(UPDATED_V_AT);
        assertThat(testPhieuNhap.getPhiShip()).isEqualTo(DEFAULT_PHI_SHIP);
        assertThat(testPhieuNhap.getGiamGia()).isEqualTo(DEFAULT_GIAM_GIA);
        assertThat(testPhieuNhap.getTongTienThanhToan()).isEqualTo(DEFAULT_TONG_TIEN_THANH_TOAN);
        assertThat(testPhieuNhap.getTienNo()).isEqualTo(UPDATED_TIEN_NO);
        assertThat(testPhieuNhap.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void fullUpdatePhieuNhapWithPatch() throws Exception {
        // Initialize the database
        phieuNhapRepository.saveAndFlush(phieuNhap);

        int databaseSizeBeforeUpdate = phieuNhapRepository.findAll().size();

        // Update the phieuNhap using partial update
        PhieuNhap partialUpdatedPhieuNhap = new PhieuNhap();
        partialUpdatedPhieuNhap.setId(phieuNhap.getId());

        partialUpdatedPhieuNhap
            .ngayLap(UPDATED_NGAY_LAP)
            .tongTienHang(UPDATED_TONG_TIEN_HANG)
            .vAT(UPDATED_V_AT)
            .phiShip(UPDATED_PHI_SHIP)
            .giamGia(UPDATED_GIAM_GIA)
            .tongTienThanhToan(UPDATED_TONG_TIEN_THANH_TOAN)
            .tienNo(UPDATED_TIEN_NO)
            .ghiChu(UPDATED_GHI_CHU);

        restPhieuNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhieuNhap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhieuNhap))
            )
            .andExpect(status().isOk());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeUpdate);
        PhieuNhap testPhieuNhap = phieuNhapList.get(phieuNhapList.size() - 1);
        assertThat(testPhieuNhap.getNgayLap()).isEqualTo(UPDATED_NGAY_LAP);
        assertThat(testPhieuNhap.getTongTienHang()).isEqualTo(UPDATED_TONG_TIEN_HANG);
        assertThat(testPhieuNhap.getvAT()).isEqualTo(UPDATED_V_AT);
        assertThat(testPhieuNhap.getPhiShip()).isEqualTo(UPDATED_PHI_SHIP);
        assertThat(testPhieuNhap.getGiamGia()).isEqualTo(UPDATED_GIAM_GIA);
        assertThat(testPhieuNhap.getTongTienThanhToan()).isEqualTo(UPDATED_TONG_TIEN_THANH_TOAN);
        assertThat(testPhieuNhap.getTienNo()).isEqualTo(UPDATED_TIEN_NO);
        assertThat(testPhieuNhap.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void patchNonExistingPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = phieuNhapRepository.findAll().size();
        phieuNhap.setId(count.incrementAndGet());

        // Create the PhieuNhap
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhieuNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phieuNhapDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = phieuNhapRepository.findAll().size();
        phieuNhap.setId(count.incrementAndGet());

        // Create the PhieuNhap
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhieuNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhieuNhap() throws Exception {
        int databaseSizeBeforeUpdate = phieuNhapRepository.findAll().size();
        phieuNhap.setId(count.incrementAndGet());

        // Create the PhieuNhap
        PhieuNhapDTO phieuNhapDTO = phieuNhapMapper.toDto(phieuNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhieuNhapMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(phieuNhapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhieuNhap in the database
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhieuNhap() throws Exception {
        // Initialize the database
        phieuNhapRepository.saveAndFlush(phieuNhap);

        int databaseSizeBeforeDelete = phieuNhapRepository.findAll().size();

        // Delete the phieuNhap
        restPhieuNhapMockMvc
            .perform(delete(ENTITY_API_URL_ID, phieuNhap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhieuNhap> phieuNhapList = phieuNhapRepository.findAll();
        assertThat(phieuNhapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
