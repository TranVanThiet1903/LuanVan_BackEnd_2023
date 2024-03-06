package warehouse.management.app.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.management.app.domain.TaiKhoan;
import warehouse.management.app.repository.TaiKhoanRepository;
import warehouse.management.app.service.dto.TaiKhoanDTO;
import warehouse.management.app.service.mapper.TaiKhoanMapper;
import warehouse.management.app.web.rest.vm.ChangePasswordVM;
import warehouse.management.app.web.rest.vm.DisableAccRequest;
import warehouse.management.app.web.rest.vm.LoginVM;

/**
 * Service Implementation for managing {@link TaiKhoan}.
 */
@Service
@Transactional
public class TaiKhoanService {

    private final Logger log = LoggerFactory.getLogger(TaiKhoanService.class);

    private final TaiKhoanRepository taiKhoanRepository;

    private final TaiKhoanMapper taiKhoanMapper;

    public TaiKhoanService(TaiKhoanRepository taiKhoanRepository, TaiKhoanMapper taiKhoanMapper) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.taiKhoanMapper = taiKhoanMapper;
    }

    /**
     * Save a taiKhoan.
     *
     * @param taiKhoanDTO the entity to save.
     * @return the persisted entity.
     */
    public TaiKhoanDTO save(TaiKhoanDTO taiKhoanDTO) {
        log.debug("Request to save TaiKhoan : {}", taiKhoanDTO);
        taiKhoanDTO.setId(UUID.randomUUID());
        taiKhoanDTO.setNgayTao(Instant.now());
        Long salt = taiKhoanDTO.getNgayTao().toEpochMilli();
        taiKhoanDTO.setSalt(salt);
        taiKhoanDTO.setPassword(hashPassword(taiKhoanDTO.getPassword(), salt));
        //        log.debug("Request to save TaiKhoan : {}", taiKhoanDTO);
        TaiKhoan taiKhoan = taiKhoanMapper.toEntity(taiKhoanDTO);
        taiKhoan = taiKhoanRepository.save(taiKhoan);
        return taiKhoanMapper.toDto(taiKhoan);
    }

    /**
     * Update a taiKhoan.
     *
     * @param taiKhoanDTO the entity to save.
     * @return the persisted entity.
     */
    public TaiKhoanDTO update(TaiKhoanDTO taiKhoanDTO) {
        log.debug("Request to update TaiKhoan : {}", taiKhoanDTO);
        TaiKhoan taiKhoan = taiKhoanMapper.toEntity(taiKhoanDTO);
        taiKhoan = taiKhoanRepository.save(taiKhoan);
        return taiKhoanMapper.toDto(taiKhoan);
    }

    /**
     * Partially update a taiKhoan.
     *
     * @param taiKhoanDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaiKhoanDTO> partialUpdate(TaiKhoanDTO taiKhoanDTO) {
        log.debug("Request to partially update TaiKhoan : {}", taiKhoanDTO);

        return taiKhoanRepository
            .findById(taiKhoanDTO.getId())
            .map(existingTaiKhoan -> {
                taiKhoanMapper.partialUpdate(existingTaiKhoan, taiKhoanDTO);

                return existingTaiKhoan;
            })
            .map(taiKhoanRepository::save)
            .map(taiKhoanMapper::toDto);
    }

    /**
     * Get all the taiKhoans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaiKhoanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaiKhoans");
        return taiKhoanRepository.findAll(pageable).map(taiKhoanMapper::toDto);
    }

    /**
     * Get one taiKhoan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaiKhoanDTO> findOne(UUID id) {
        log.debug("Request to get TaiKhoan : {}", id);
        return taiKhoanRepository.findById(id).map(taiKhoanMapper::toDto);
    }

    /**
     * Delete the taiKhoan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaiKhoan : {}", id);
        taiKhoanRepository.deleteById(id);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String hashPassword(String matKhau, Long salt) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String matKhauAndSalt = matKhau + salt;
        return bytesToHex(messageDigest.digest(matKhauAndSalt.getBytes()));
    }

    public Optional<TaiKhoanDTO> login(LoginVM loginVM) {
        Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findByUsername(loginVM.getUsername());
        if (optionalTaiKhoan.isPresent()) {
            TaiKhoan taiKhoan = optionalTaiKhoan.get();
            String currentPassword = taiKhoan.getPassword();
            Long salt = taiKhoan.getSalt();
            String inputPassword = loginVM.getPassword();
            String hashedPass = hashPassword(inputPassword, salt);
            if (currentPassword.equals(hashedPass)) {
                return optionalTaiKhoan.map(taiKhoanMapper::toDto);
            }
        }
        return Optional.empty();
    }

    public Optional<TaiKhoanDTO> changePassword(ChangePasswordVM changePasswordVM, String currentUserLogin) {
        Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findById(UUID.fromString(currentUserLogin));
        if (optionalTaiKhoan.isPresent()) {
            log.debug(optionalTaiKhoan.get().getHoTen());
            TaiKhoan taiKhoan = optionalTaiKhoan.get();
            String currentPassword = taiKhoan.getPassword();
            Long salt = taiKhoan.getSalt();
            String oldPass = changePasswordVM.getCurrentPassword();
            String newPass = changePasswordVM.getNewPassword();
            String inputPassword = hashPassword(oldPass, salt);
            if (currentPassword.equals(inputPassword)) {
                log.debug("checker");
                String hashNewPass = hashPassword(newPass, salt);
                taiKhoan.setPassword(hashNewPass);
                return optionalTaiKhoan.map(taiKhoanMapper::toDto);
            } else {
                log.debug("Mật khẩu cũ không đúng.");
            }
        }
        return Optional.empty();
    }

    public Optional<TaiKhoanDTO> disableAcc(DisableAccRequest disableAccRequest) {
        Optional<TaiKhoan> optionalTaiKhoan = taiKhoanRepository.findByUsername(disableAccRequest.getUsername());
        if (optionalTaiKhoan.isPresent()) {
            log.debug(optionalTaiKhoan.get().getHoTen());
            TaiKhoan taiKhoan = optionalTaiKhoan.get();
            String disablePass = "###";
            String disableUName = "###";
            taiKhoan.setPassword(disablePass);
            taiKhoan.setUsername(disableUName);
            return optionalTaiKhoan.map(taiKhoanMapper::toDto);
        }
        return Optional.empty();
    }

    public Optional<TaiKhoanDTO> findByUsername(String username) {
        return taiKhoanRepository.findByUsername(username).map(taiKhoanMapper::toDto);
    }
}
