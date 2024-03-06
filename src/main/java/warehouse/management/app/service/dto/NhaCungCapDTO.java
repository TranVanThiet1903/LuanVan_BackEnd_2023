package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.NhaCungCap} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NhaCungCapDTO implements Serializable {

    private Long id;

    @NotNull
    private String tenNCC;

    @NotNull
    private String soDT;

    @NotNull
    private String email;

    @NotNull
    private String diaChi;

    //    @NotNull
    private String ghiChu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhaCungCapDTO)) {
            return false;
        }

        NhaCungCapDTO nhaCungCapDTO = (NhaCungCapDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nhaCungCapDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhaCungCapDTO{" +
            "id=" + getId() +
            ", tenNCC='" + getTenNCC() + "'" +
            ", soDT='" + getSoDT() + "'" +
            ", email='" + getEmail() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            ", ghiChu='" + getGhiChu() + "'" +
            "}";
    }
}
