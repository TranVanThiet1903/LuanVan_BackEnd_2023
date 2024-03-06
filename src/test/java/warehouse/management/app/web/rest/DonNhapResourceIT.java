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
import warehouse.management.app.domain.DonNhap;
import warehouse.management.app.repository.DonNhapRepository;
import warehouse.management.app.service.dto.DonNhapDTO;
import warehouse.management.app.service.mapper.DonNhapMapper;

/**
 * Integration tests for the {@link DonNhapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DonNhapResourceIT {

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

    private static final String ENTITY_API_URL = "/api/don-nhaps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DonNhapRepository donNhapRepository;

    @Autowired
    private DonNhapMapper donNhapMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonNhapMockMvc;

    private DonNhap donNhap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonNhap createEntity(EntityManager em) {
        DonNhap donNhap = new DonNhap()
            .ngayLap(DEFAULT_NGAY_LAP)
            .tongTienHang(DEFAULT_TONG_TIEN_HANG)
            .vAT(DEFAULT_V_AT)
            .phiShip(DEFAULT_PHI_SHIP)
            .giamGia(DEFAULT_GIAM_GIA)
            .tongTienThanhToan(DEFAULT_TONG_TIEN_THANH_TOAN)
            .tienNo(DEFAULT_TIEN_NO)
            .ghiChu(DEFAULT_GHI_CHU);
        return donNhap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonNhap createUpdatedEntity(EntityManager em) {
        DonNhap donNhap = new DonNhap()
            .ngayLap(UPDATED_NGAY_LAP)
            .tongTienHang(UPDATED_TONG_TIEN_HANG)
            .vAT(UPDATED_V_AT)
            .phiShip(UPDATED_PHI_SHIP)
            .giamGia(UPDATED_GIAM_GIA)
            .tongTienThanhToan(UPDATED_TONG_TIEN_THANH_TOAN)
            .tienNo(UPDATED_TIEN_NO)
            .ghiChu(UPDATED_GHI_CHU);
        return donNhap;
    }

    @BeforeEach
    public void initTest() {
        donNhap = createEntity(em);
    }

    @Test
    @Transactional
    void createDonNhap() throws Exception {
        int databaseSizeBeforeCreate = donNhapRepository.findAll().size();
        // Create the DonNhap
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);
        restDonNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donNhapDTO)))
            .andExpect(status().isCreated());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeCreate + 1);
        DonNhap testDonNhap = donNhapList.get(donNhapList.size() - 1);
        assertThat(testDonNhap.getNgayLap()).isEqualTo(DEFAULT_NGAY_LAP);
        assertThat(testDonNhap.getTongTienHang()).isEqualTo(DEFAULT_TONG_TIEN_HANG);
        assertThat(testDonNhap.getvAT()).isEqualTo(DEFAULT_V_AT);
        assertThat(testDonNhap.getPhiShip()).isEqualTo(DEFAULT_PHI_SHIP);
        assertThat(testDonNhap.getGiamGia()).isEqualTo(DEFAULT_GIAM_GIA);
        assertThat(testDonNhap.getTongTienThanhToan()).isEqualTo(DEFAULT_TONG_TIEN_THANH_TOAN);
        assertThat(testDonNhap.getTienNo()).isEqualTo(DEFAULT_TIEN_NO);
        assertThat(testDonNhap.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    void createDonNhapWithExistingId() throws Exception {
        // Create the DonNhap with an existing ID
        donNhap.setId(1L);
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        int databaseSizeBeforeCreate = donNhapRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donNhapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNgayLapIsRequired() throws Exception {
        int databaseSizeBeforeTest = donNhapRepository.findAll().size();
        // set the field null
        donNhap.setNgayLap(null);

        // Create the DonNhap, which fails.
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        restDonNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donNhapDTO)))
            .andExpect(status().isBadRequest());

        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTongTienHangIsRequired() throws Exception {
        int databaseSizeBeforeTest = donNhapRepository.findAll().size();
        // set the field null
        donNhap.setTongTienHang(null);

        // Create the DonNhap, which fails.
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        restDonNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donNhapDTO)))
            .andExpect(status().isBadRequest());

        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkvATIsRequired() throws Exception {
        int databaseSizeBeforeTest = donNhapRepository.findAll().size();
        // set the field null
        donNhap.setvAT(null);

        // Create the DonNhap, which fails.
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        restDonNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donNhapDTO)))
            .andExpect(status().isBadRequest());

        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhiShipIsRequired() throws Exception {
        int databaseSizeBeforeTest = donNhapRepository.findAll().size();
        // set the field null
        donNhap.setPhiShip(null);

        // Create the DonNhap, which fails.
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        restDonNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donNhapDTO)))
            .andExpect(status().isBadRequest());

        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGiamGiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = donNhapRepository.findAll().size();
        // set the field null
        donNhap.setGiamGia(null);

        // Create the DonNhap, which fails.
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        restDonNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donNhapDTO)))
            .andExpect(status().isBadRequest());

        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTongTienThanhToanIsRequired() throws Exception {
        int databaseSizeBeforeTest = donNhapRepository.findAll().size();
        // set the field null
        donNhap.setTongTienThanhToan(null);

        // Create the DonNhap, which fails.
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        restDonNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donNhapDTO)))
            .andExpect(status().isBadRequest());

        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTienNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = donNhapRepository.findAll().size();
        // set the field null
        donNhap.setTienNo(null);

        // Create the DonNhap, which fails.
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        restDonNhapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donNhapDTO)))
            .andExpect(status().isBadRequest());

        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDonNhaps() throws Exception {
        // Initialize the database
        donNhapRepository.saveAndFlush(donNhap);

        // Get all the donNhapList
        restDonNhapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donNhap.getId().intValue())))
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
    void getDonNhap() throws Exception {
        // Initialize the database
        donNhapRepository.saveAndFlush(donNhap);

        // Get the donNhap
        restDonNhapMockMvc
            .perform(get(ENTITY_API_URL_ID, donNhap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donNhap.getId().intValue()))
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
    void getNonExistingDonNhap() throws Exception {
        // Get the donNhap
        restDonNhapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDonNhap() throws Exception {
        // Initialize the database
        donNhapRepository.saveAndFlush(donNhap);

        int databaseSizeBeforeUpdate = donNhapRepository.findAll().size();

        // Update the donNhap
        DonNhap updatedDonNhap = donNhapRepository.findById(donNhap.getId()).get();
        // Disconnect from session so that the updates on updatedDonNhap are not directly saved in db
        em.detach(updatedDonNhap);
        updatedDonNhap
            .ngayLap(UPDATED_NGAY_LAP)
            .tongTienHang(UPDATED_TONG_TIEN_HANG)
            .vAT(UPDATED_V_AT)
            .phiShip(UPDATED_PHI_SHIP)
            .giamGia(UPDATED_GIAM_GIA)
            .tongTienThanhToan(UPDATED_TONG_TIEN_THANH_TOAN)
            .tienNo(UPDATED_TIEN_NO)
            .ghiChu(UPDATED_GHI_CHU);
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(updatedDonNhap);

        restDonNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donNhapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donNhapDTO))
            )
            .andExpect(status().isOk());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeUpdate);
        DonNhap testDonNhap = donNhapList.get(donNhapList.size() - 1);
        assertThat(testDonNhap.getNgayLap()).isEqualTo(UPDATED_NGAY_LAP);
        assertThat(testDonNhap.getTongTienHang()).isEqualTo(UPDATED_TONG_TIEN_HANG);
        assertThat(testDonNhap.getvAT()).isEqualTo(UPDATED_V_AT);
        assertThat(testDonNhap.getPhiShip()).isEqualTo(UPDATED_PHI_SHIP);
        assertThat(testDonNhap.getGiamGia()).isEqualTo(UPDATED_GIAM_GIA);
        assertThat(testDonNhap.getTongTienThanhToan()).isEqualTo(UPDATED_TONG_TIEN_THANH_TOAN);
        assertThat(testDonNhap.getTienNo()).isEqualTo(UPDATED_TIEN_NO);
        assertThat(testDonNhap.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void putNonExistingDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = donNhapRepository.findAll().size();
        donNhap.setId(count.incrementAndGet());

        // Create the DonNhap
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donNhapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = donNhapRepository.findAll().size();
        donNhap.setId(count.incrementAndGet());

        // Create the DonNhap
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonNhapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = donNhapRepository.findAll().size();
        donNhap.setId(count.incrementAndGet());

        // Create the DonNhap
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonNhapMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donNhapDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonNhapWithPatch() throws Exception {
        // Initialize the database
        donNhapRepository.saveAndFlush(donNhap);

        int databaseSizeBeforeUpdate = donNhapRepository.findAll().size();

        // Update the donNhap using partial update
        DonNhap partialUpdatedDonNhap = new DonNhap();
        partialUpdatedDonNhap.setId(donNhap.getId());

        partialUpdatedDonNhap.tongTienHang(UPDATED_TONG_TIEN_HANG).vAT(UPDATED_V_AT).phiShip(UPDATED_PHI_SHIP).giamGia(UPDATED_GIAM_GIA);

        restDonNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonNhap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonNhap))
            )
            .andExpect(status().isOk());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeUpdate);
        DonNhap testDonNhap = donNhapList.get(donNhapList.size() - 1);
        assertThat(testDonNhap.getNgayLap()).isEqualTo(DEFAULT_NGAY_LAP);
        assertThat(testDonNhap.getTongTienHang()).isEqualTo(UPDATED_TONG_TIEN_HANG);
        assertThat(testDonNhap.getvAT()).isEqualTo(UPDATED_V_AT);
        assertThat(testDonNhap.getPhiShip()).isEqualTo(UPDATED_PHI_SHIP);
        assertThat(testDonNhap.getGiamGia()).isEqualTo(UPDATED_GIAM_GIA);
        assertThat(testDonNhap.getTongTienThanhToan()).isEqualTo(DEFAULT_TONG_TIEN_THANH_TOAN);
        assertThat(testDonNhap.getTienNo()).isEqualTo(DEFAULT_TIEN_NO);
        assertThat(testDonNhap.getGhiChu()).isEqualTo(DEFAULT_GHI_CHU);
    }

    @Test
    @Transactional
    void fullUpdateDonNhapWithPatch() throws Exception {
        // Initialize the database
        donNhapRepository.saveAndFlush(donNhap);

        int databaseSizeBeforeUpdate = donNhapRepository.findAll().size();

        // Update the donNhap using partial update
        DonNhap partialUpdatedDonNhap = new DonNhap();
        partialUpdatedDonNhap.setId(donNhap.getId());

        partialUpdatedDonNhap
            .ngayLap(UPDATED_NGAY_LAP)
            .tongTienHang(UPDATED_TONG_TIEN_HANG)
            .vAT(UPDATED_V_AT)
            .phiShip(UPDATED_PHI_SHIP)
            .giamGia(UPDATED_GIAM_GIA)
            .tongTienThanhToan(UPDATED_TONG_TIEN_THANH_TOAN)
            .tienNo(UPDATED_TIEN_NO)
            .ghiChu(UPDATED_GHI_CHU);

        restDonNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonNhap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonNhap))
            )
            .andExpect(status().isOk());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeUpdate);
        DonNhap testDonNhap = donNhapList.get(donNhapList.size() - 1);
        assertThat(testDonNhap.getNgayLap()).isEqualTo(UPDATED_NGAY_LAP);
        assertThat(testDonNhap.getTongTienHang()).isEqualTo(UPDATED_TONG_TIEN_HANG);
        assertThat(testDonNhap.getvAT()).isEqualTo(UPDATED_V_AT);
        assertThat(testDonNhap.getPhiShip()).isEqualTo(UPDATED_PHI_SHIP);
        assertThat(testDonNhap.getGiamGia()).isEqualTo(UPDATED_GIAM_GIA);
        assertThat(testDonNhap.getTongTienThanhToan()).isEqualTo(UPDATED_TONG_TIEN_THANH_TOAN);
        assertThat(testDonNhap.getTienNo()).isEqualTo(UPDATED_TIEN_NO);
        assertThat(testDonNhap.getGhiChu()).isEqualTo(UPDATED_GHI_CHU);
    }

    @Test
    @Transactional
    void patchNonExistingDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = donNhapRepository.findAll().size();
        donNhap.setId(count.incrementAndGet());

        // Create the DonNhap
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donNhapDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = donNhapRepository.findAll().size();
        donNhap.setId(count.incrementAndGet());

        // Create the DonNhap
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonNhapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donNhapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonNhap() throws Exception {
        int databaseSizeBeforeUpdate = donNhapRepository.findAll().size();
        donNhap.setId(count.incrementAndGet());

        // Create the DonNhap
        DonNhapDTO donNhapDTO = donNhapMapper.toDto(donNhap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonNhapMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(donNhapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonNhap in the database
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonNhap() throws Exception {
        // Initialize the database
        donNhapRepository.saveAndFlush(donNhap);

        int databaseSizeBeforeDelete = donNhapRepository.findAll().size();

        // Delete the donNhap
        restDonNhapMockMvc
            .perform(delete(ENTITY_API_URL_ID, donNhap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DonNhap> donNhapList = donNhapRepository.findAll();
        assertThat(donNhapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
