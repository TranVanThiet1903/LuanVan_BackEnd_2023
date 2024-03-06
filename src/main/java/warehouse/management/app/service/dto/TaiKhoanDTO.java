package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.TaiKhoan} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaiKhoanDTO implements Serializable {

    private UUID id;

    @NotNull
    private String hoTen;

    @NotNull
    private String username;

    @NotNull
    private String password;

    //    @NotNull
    private Instant ngayTao;

    //    @NotNull
    private Long salt;

    //    @NotNull
    //    private String viTri;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Instant ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Long getSalt() {
        return salt;
    }

    public void setSalt(Long salt) {
        this.salt = salt;
    }

    //    public String getViTri() {
    //        return viTri;
    //    }
    //
    //    public void setViTri(String viTri) {
    //        this.viTri = viTri;
    //    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaiKhoanDTO)) {
            return false;
        }

        TaiKhoanDTO taiKhoanDTO = (TaiKhoanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taiKhoanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaiKhoanDTO{" +
            "id=" + getId() +
            ", hoTen='" + getHoTen() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", ngayTao='" + getNgayTao() + "'" +
            ", salt=" + getSalt() +
//            ", viTri=" + getViTri() +
            "}";
    }
}
