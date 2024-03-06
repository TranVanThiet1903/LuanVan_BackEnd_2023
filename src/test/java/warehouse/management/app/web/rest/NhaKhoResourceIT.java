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
import warehouse.management.app.domain.NhaKho;
import warehouse.management.app.repository.NhaKhoRepository;
import warehouse.management.app.service.dto.NhaKhoDTO;
import warehouse.management.app.service.mapper.NhaKhoMapper;

/**
 * Integration tests for the {@link NhaKhoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NhaKhoResourceIT {

    private static final String DEFAULT_TEN_KHO = "AAAAAAAAAA";
    private static final String UPDATED_TEN_KHO = "BBBBBBBBBB";

    private static final String DEFAULT_DIA_CHI = "AAAAAAAAAA";
    private static final String UPDATED_DIA_CHI = "BBBBBBBBBB";

    private static final String DEFAULT_LOAI = "AAAAAAAAAA";
    private static final String UPDATED_LOAI = "BBBBBBBBBB";

    private static final Instant DEFAULT_NGAY_TAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_TAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nha-khos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NhaKhoRepository nhaKhoRepository;

    @Autowired
    private NhaKhoMapper nhaKhoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNhaKhoMockMvc;

    private NhaKho nhaKho;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhaKho createEntity(EntityManager em) {
        NhaKho nhaKho = new NhaKho().tenKho(DEFAULT_TEN_KHO).diaChi(DEFAULT_DIA_CHI).loai(DEFAULT_LOAI).ngayTao(DEFAULT_NGAY_TAO);
        return nhaKho;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhaKho createUpdatedEntity(EntityManager em) {
        NhaKho nhaKho = new NhaKho().tenKho(UPDATED_TEN_KHO).diaChi(UPDATED_DIA_CHI).loai(UPDATED_LOAI).ngayTao(UPDATED_NGAY_TAO);
        return nhaKho;
    }

    @BeforeEach
    public void initTest() {
        nhaKho = createEntity(em);
    }

    @Test
    @Transactional
    void createNhaKho() throws Exception {
        int databaseSizeBeforeCreate = nhaKhoRepository.findAll().size();
        // Create the NhaKho
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);
        restNhaKhoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO)))
            .andExpect(status().isCreated());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeCreate + 1);
        NhaKho testNhaKho = nhaKhoList.get(nhaKhoList.size() - 1);
        assertThat(testNhaKho.getTenKho()).isEqualTo(DEFAULT_TEN_KHO);
        assertThat(testNhaKho.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
        assertThat(testNhaKho.getLoai()).isEqualTo(DEFAULT_LOAI);
        assertThat(testNhaKho.getNgayTao()).isEqualTo(DEFAULT_NGAY_TAO);
    }

    @Test
    @Transactional
    void createNhaKhoWithExistingId() throws Exception {
        // Create the NhaKho with an existing ID
        nhaKho.setId(1L);
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        int databaseSizeBeforeCreate = nhaKhoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhaKhoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTenKhoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhaKhoRepository.findAll().size();
        // set the field null
        nhaKho.setTenKho(null);

        // Create the NhaKho, which fails.
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        restNhaKhoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO)))
            .andExpect(status().isBadRequest());

        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDiaChiIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhaKhoRepository.findAll().size();
        // set the field null
        nhaKho.setDiaChi(null);

        // Create the NhaKho, which fails.
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        restNhaKhoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO)))
            .andExpect(status().isBadRequest());

        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLoaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhaKhoRepository.findAll().size();
        // set the field null
        nhaKho.setLoai(null);

        // Create the NhaKho, which fails.
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        restNhaKhoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO)))
            .andExpect(status().isBadRequest());

        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNgayTaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhaKhoRepository.findAll().size();
        // set the field null
        nhaKho.setNgayTao(null);

        // Create the NhaKho, which fails.
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        restNhaKhoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO)))
            .andExpect(status().isBadRequest());

        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNhaKhos() throws Exception {
        // Initialize the database
        nhaKhoRepository.saveAndFlush(nhaKho);

        // Get all the nhaKhoList
        restNhaKhoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhaKho.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenKho").value(hasItem(DEFAULT_TEN_KHO)))
            .andExpect(jsonPath("$.[*].diaChi").value(hasItem(DEFAULT_DIA_CHI)))
            .andExpect(jsonPath("$.[*].loai").value(hasItem(DEFAULT_LOAI)))
            .andExpect(jsonPath("$.[*].ngayTao").value(hasItem(DEFAULT_NGAY_TAO.toString())));
    }

    @Test
    @Transactional
    void getNhaKho() throws Exception {
        // Initialize the database
        nhaKhoRepository.saveAndFlush(nhaKho);

        // Get the nhaKho
        restNhaKhoMockMvc
            .perform(get(ENTITY_API_URL_ID, nhaKho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nhaKho.getId().intValue()))
            .andExpect(jsonPath("$.tenKho").value(DEFAULT_TEN_KHO))
            .andExpect(jsonPath("$.diaChi").value(DEFAULT_DIA_CHI))
            .andExpect(jsonPath("$.loai").value(DEFAULT_LOAI))
            .andExpect(jsonPath("$.ngayTao").value(DEFAULT_NGAY_TAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNhaKho() throws Exception {
        // Get the nhaKho
        restNhaKhoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNhaKho() throws Exception {
        // Initialize the database
        nhaKhoRepository.saveAndFlush(nhaKho);

        int databaseSizeBeforeUpdate = nhaKhoRepository.findAll().size();

        // Update the nhaKho
        NhaKho updatedNhaKho = nhaKhoRepository.findById(nhaKho.getId()).get();
        // Disconnect from session so that the updates on updatedNhaKho are not directly saved in db
        em.detach(updatedNhaKho);
        updatedNhaKho.tenKho(UPDATED_TEN_KHO).diaChi(UPDATED_DIA_CHI).loai(UPDATED_LOAI).ngayTao(UPDATED_NGAY_TAO);
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(updatedNhaKho);

        restNhaKhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhaKhoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO))
            )
            .andExpect(status().isOk());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeUpdate);
        NhaKho testNhaKho = nhaKhoList.get(nhaKhoList.size() - 1);
        assertThat(testNhaKho.getTenKho()).isEqualTo(UPDATED_TEN_KHO);
        assertThat(testNhaKho.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhaKho.getLoai()).isEqualTo(UPDATED_LOAI);
        assertThat(testNhaKho.getNgayTao()).isEqualTo(UPDATED_NGAY_TAO);
    }

    @Test
    @Transactional
    void putNonExistingNhaKho() throws Exception {
        int databaseSizeBeforeUpdate = nhaKhoRepository.findAll().size();
        nhaKho.setId(count.incrementAndGet());

        // Create the NhaKho
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhaKhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhaKhoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNhaKho() throws Exception {
        int databaseSizeBeforeUpdate = nhaKhoRepository.findAll().size();
        nhaKho.setId(count.incrementAndGet());

        // Create the NhaKho
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhaKhoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNhaKho() throws Exception {
        int databaseSizeBeforeUpdate = nhaKhoRepository.findAll().size();
        nhaKho.setId(count.incrementAndGet());

        // Create the NhaKho
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhaKhoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNhaKhoWithPatch() throws Exception {
        // Initialize the database
        nhaKhoRepository.saveAndFlush(nhaKho);

        int databaseSizeBeforeUpdate = nhaKhoRepository.findAll().size();

        // Update the nhaKho using partial update
        NhaKho partialUpdatedNhaKho = new NhaKho();
        partialUpdatedNhaKho.setId(nhaKho.getId());

        restNhaKhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhaKho.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhaKho))
            )
            .andExpect(status().isOk());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeUpdate);
        NhaKho testNhaKho = nhaKhoList.get(nhaKhoList.size() - 1);
        assertThat(testNhaKho.getTenKho()).isEqualTo(DEFAULT_TEN_KHO);
        assertThat(testNhaKho.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
        assertThat(testNhaKho.getLoai()).isEqualTo(DEFAULT_LOAI);
        assertThat(testNhaKho.getNgayTao()).isEqualTo(DEFAULT_NGAY_TAO);
    }

    @Test
    @Transactional
    void fullUpdateNhaKhoWithPatch() throws Exception {
        // Initialize the database
        nhaKhoRepository.saveAndFlush(nhaKho);

        int databaseSizeBeforeUpdate = nhaKhoRepository.findAll().size();

        // Update the nhaKho using partial update
        NhaKho partialUpdatedNhaKho = new NhaKho();
        partialUpdatedNhaKho.setId(nhaKho.getId());

        partialUpdatedNhaKho.tenKho(UPDATED_TEN_KHO).diaChi(UPDATED_DIA_CHI).loai(UPDATED_LOAI).ngayTao(UPDATED_NGAY_TAO);

        restNhaKhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhaKho.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhaKho))
            )
            .andExpect(status().isOk());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeUpdate);
        NhaKho testNhaKho = nhaKhoList.get(nhaKhoList.size() - 1);
        assertThat(testNhaKho.getTenKho()).isEqualTo(UPDATED_TEN_KHO);
        assertThat(testNhaKho.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testNhaKho.getLoai()).isEqualTo(UPDATED_LOAI);
        assertThat(testNhaKho.getNgayTao()).isEqualTo(UPDATED_NGAY_TAO);
    }

    @Test
    @Transactional
    void patchNonExistingNhaKho() throws Exception {
        int databaseSizeBeforeUpdate = nhaKhoRepository.findAll().size();
        nhaKho.setId(count.incrementAndGet());

        // Create the NhaKho
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhaKhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nhaKhoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNhaKho() throws Exception {
        int databaseSizeBeforeUpdate = nhaKhoRepository.findAll().size();
        nhaKho.setId(count.incrementAndGet());

        // Create the NhaKho
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhaKhoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNhaKho() throws Exception {
        int databaseSizeBeforeUpdate = nhaKhoRepository.findAll().size();
        nhaKho.setId(count.incrementAndGet());

        // Create the NhaKho
        NhaKhoDTO nhaKhoDTO = nhaKhoMapper.toDto(nhaKho);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhaKhoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nhaKhoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhaKho in the database
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNhaKho() throws Exception {
        // Initialize the database
        nhaKhoRepository.saveAndFlush(nhaKho);

        int databaseSizeBeforeDelete = nhaKhoRepository.findAll().size();

        // Delete the nhaKho
        restNhaKhoMockMvc
            .perform(delete(ENTITY_API_URL_ID, nhaKho.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NhaKho> nhaKhoList = nhaKhoRepository.findAll();
        assertThat(nhaKhoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
