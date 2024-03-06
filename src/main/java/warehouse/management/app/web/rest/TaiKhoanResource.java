package warehouse.management.app.web.rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.undertow.security.impl.AuthenticationInfoToken;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import warehouse.management.app.repository.TaiKhoanRepository;
import warehouse.management.app.security.SecurityUtils;
import warehouse.management.app.security.jwt.JWTToken;
import warehouse.management.app.security.jwt.TokenProvider;
import warehouse.management.app.service.TaiKhoanService;
import warehouse.management.app.service.dto.PhieuNhapDTO;
import warehouse.management.app.service.dto.TaiKhoanDTO;
import warehouse.management.app.web.rest.errors.BadRequestAlertException;
import warehouse.management.app.web.rest.vm.*;

/**
 * REST controller for managing {@link warehouse.management.app.domain.TaiKhoan}.
 */
@RestController
@RequestMapping("/api")
public class TaiKhoanResource {

    private final Logger log = LoggerFactory.getLogger(TaiKhoanResource.class);

    private static final String ENTITY_NAME = "lvProjectTaiKhoan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaiKhoanService taiKhoanService;

    private final TaiKhoanRepository taiKhoanRepository;

    private final TokenProvider tokenProvider;

    @Autowired
    private JavaMailSender javaMailSender;

    public TaiKhoanResource(TaiKhoanService taiKhoanService, TaiKhoanRepository taiKhoanRepository, TokenProvider tokenProvider) {
        this.taiKhoanService = taiKhoanService;
        this.taiKhoanRepository = taiKhoanRepository;
        this.tokenProvider = tokenProvider;
    }

    /**
     * {@code POST  /tai-khoans} : Create a new taiKhoan.
     *
     * @param taiKhoanRequest the taiKhoanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taiKhoanDTO, or with status {@code 400 (Bad Request)} if the taiKhoan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tai-khoan")
    public ResponseEntity<TaiKhoanDTO> createTaiKhoan(@Valid @RequestBody TaiKhoanRequest taiKhoanRequest)
        throws URISyntaxException, MessagingException {
        TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();
        taiKhoanDTO.setHoTen(taiKhoanRequest.getHoTen());
        log.debug("TaiKhoanRequest: " + taiKhoanRequest.getSelectedRole());
        log.debug("TaiKhoanRequest: " + taiKhoanRequest.getEmail());
        log.debug("TaiKhoanRequest: " + taiKhoanRequest.getHoTen());
        if (taiKhoanRequest.getSelectedRole().equals("kiemHang")) {
            Random random = new Random();
            int randomNumber = random.nextInt(900000) + 100000;
            String randomNum = String.valueOf(randomNumber);
            taiKhoanDTO.setUsername("user@" + randomNum);
        } else if (taiKhoanRequest.getSelectedRole().equals("quanKho")) {
            Random random = new Random();
            int randomNumber = random.nextInt(900000) + 100000;
            String randomNum = String.valueOf(randomNumber);
            taiKhoanDTO.setUsername("manager@" + randomNum);
        } else {
            Random random = new Random();
            int randomNumber = random.nextInt(900000) + 100000;
            String randomNum = String.valueOf(randomNumber);
            taiKhoanDTO.setUsername("owner@" + randomNum);
        }
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        taiKhoanDTO.setPassword(generatedString);
        log.debug(generatedString);
        log.debug("REST request to save TaiKhoan : {}", taiKhoanDTO);

        if (taiKhoanDTO.getId() != null) {
            throw new BadRequestAlertException("A new taiKhoan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaiKhoanDTO result = taiKhoanService.save(taiKhoanDTO);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(taiKhoanRequest.getEmail());
        helper.setSubject("V/v CẤP TÀI KHOẢN CHO " + taiKhoanRequest.getHoTen() + " TỪ SERPENT RESTAURANT.");

        String mailData1 =
            "<html>" +
            "<body>" +
            "<h2>Tên Tài Khoản:</h2>" +
            "<h4>" +
            taiKhoanDTO.getUsername() +
            "</h4>" +
            "<br>" +
            "<h2>Mật Khẩu:</h2>" +
            "<h4>" +
            generatedString +
            "</h4>" +
            "</body></html>";

        helper.setText(mailData1, true);

        javaMailSender.send(message);

        return ResponseEntity
            .created(new URI("/api/tai-khoan/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    //    /**
    //     * {@code PUT  /tai-khoans/:id} : Updates an existing taiKhoan.
    //     *
    //     * @param id the id of the taiKhoanDTO to save.
    //     * @param taiKhoanDTO the taiKhoanDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taiKhoanDTO,
    //     * or with status {@code 400 (Bad Request)} if the taiKhoanDTO is not valid,
    //     * or with status {@code 500 (Internal Server Error)} if the taiKhoanDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PutMapping("/tai-khoan/{id}")
    //    public ResponseEntity<TaiKhoanDTO> updateTaiKhoan(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @Valid @RequestBody TaiKhoanDTO taiKhoanDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to update TaiKhoan : {}, {}", id, taiKhoanDTO);
    //        if (taiKhoanDTO.getId() == null) {
    //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, taiKhoanDTO.getId())) {
    //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!taiKhoanRepository.existsById(id)) {
    //            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        TaiKhoanDTO result = taiKhoanService.update(taiKhoanDTO);
    //        return ResponseEntity
    //            .ok()
    //            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taiKhoanDTO.getId().toString()))
    //            .body(result);
    //    }
    //
    //    /**
    //     * {@code PATCH  /tai-khoans/:id} : Partial updates given fields of an existing taiKhoan, field will ignore if it is null
    //     *
    //     * @param id the id of the taiKhoanDTO to save.
    //     * @param taiKhoanDTO the taiKhoanDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taiKhoanDTO,
    //     * or with status {@code 400 (Bad Request)} if the taiKhoanDTO is not valid,
    //     * or with status {@code 404 (Not Found)} if the taiKhoanDTO is not found,
    //     * or with status {@code 500 (Internal Server Error)} if the taiKhoanDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PatchMapping(value = "/tai-khoan/{id}", consumes = { "application/json", "application/merge-patch+json" })
    //    public ResponseEntity<TaiKhoanDTO> partialUpdateTaiKhoan(
    //        @PathVariable(value = "id", required = false) final UUID id,
    //        @NotNull @RequestBody TaiKhoanDTO taiKhoanDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to partial update TaiKhoan partially : {}, {}", id, taiKhoanDTO);
    //        if (taiKhoanDTO.getId() == null) {
    //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, taiKhoanDTO.getId())) {
    //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!taiKhoanRepository.existsById(id)) {
    //            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        Optional<TaiKhoanDTO> result = taiKhoanService.partialUpdate(taiKhoanDTO);
    //
    //        return ResponseUtil.wrapOrNotFound(
    //            result,
    //            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taiKhoanDTO.getId().toString())
    //        );
    //    }

    /**
     * {@code GET  /tai-khoans} : get all the taiKhoans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taiKhoans in body.
     */
    @GetMapping("/tai-khoan")
    public ResponseEntity<List<TaiKhoanDTO>> getAllTaiKhoans(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TaiKhoans");
        Page<TaiKhoanDTO> page = taiKhoanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tai-khoans/:id} : get the "id" taiKhoan.
     *
     * @param id the id of the taiKhoanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taiKhoanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tai-khoan/{id}")
    public ResponseEntity<TaiKhoanDTO> getTaiKhoan(@PathVariable UUID id) {
        log.debug("REST request to get TaiKhoan : {}", id);
        Optional<TaiKhoanDTO> taiKhoanDTO = taiKhoanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taiKhoanDTO);
    }

    /**
     * {@code DELETE  /tai-khoans/:id} : delete the "id" taiKhoan.
     *
     * @param id the id of the taiKhoanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tai-khoan/{id}")
    public ResponseEntity<Void> deleteTaiKhoan(@PathVariable Long id) {
        log.debug("REST request to delete TaiKhoan : {}", id);
        taiKhoanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginVM loginVM) {
        Optional<TaiKhoanDTO> taiKhoanDTO = taiKhoanService.login(loginVM);
        if (taiKhoanDTO.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "Đăng nhập thất bại!");
        }
        Optional<TaiKhoanDTO> taiKhoan = taiKhoanService.findByUsername(loginVM.getUsername());
        TaiKhoanDTO loginAccount = taiKhoan.get();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginAccount.getId(),
            loginVM.getUsername()
        );
        String jwt = tokenProvider.createToken(authenticationToken, true);
        TaiKhoanVM taiKhoanVM = new TaiKhoanVM();
        taiKhoanVM.setToken(jwt);
        taiKhoanVM.setUsername(loginVM.getUsername());
        return ResponseEntity.ok().body(taiKhoanVM);
    }

    @PatchMapping("/tai-khoan")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordVM changePasswordVM) {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().get();
        log.debug(currentUserLogin);
        Optional<TaiKhoanDTO> taiKhoanDTO = taiKhoanService.changePassword(changePasswordVM, currentUserLogin);
        if (taiKhoanDTO.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "Thay đổi mật khẩu thất bại");
        }
        return ResponseEntity.ok().body(taiKhoanDTO);
    }

    @PatchMapping("/tai-khoan/vhh")
    public ResponseEntity<?> disableAccount(@RequestBody DisableAccRequest disableAccRequest) {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().get();

        Optional<TaiKhoanDTO> taiKhoanDTO = taiKhoanService.findOne(UUID.fromString(currentUserLogin));
        String hashPass = taiKhoanService.hashPassword(disableAccRequest.getConfirmPass(), taiKhoanDTO.get().getSalt());

        if (!taiKhoanDTO.get().getPassword().equals(hashPass)) {
            throw new BadRequestAlertException("Bad Request", ENTITY_NAME, "Mật khẩu xác nhận sai.");
        }

        Optional<TaiKhoanDTO> taiKhoanDTO1 = taiKhoanService.disableAcc(disableAccRequest);
        if (taiKhoanDTO1.isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "Vô hiệu hóa tài khoản thất bại");
        }

        return ResponseEntity.ok().body(taiKhoanDTO1);
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getUUID(@RequestHeader("Authorization") String jwt) {
        String token = jwt.replace("Bearer ", "");
        GetUserID getUserID = new GetUserID();
        UUID id = getUserID.getIDFromToken(token);
        Optional<TaiKhoanDTO> currentUser = taiKhoanService.findOne(id);
        return ResponseEntity.ok(currentUser.get().getHoTen());
    }
}
