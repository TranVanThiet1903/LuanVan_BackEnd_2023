package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.NhaKho} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NhaKhoDTO implements Serializable {

    private Long id;

    @NotNull
    private String tenKho;

    @NotNull
    private String diaChi;

    @NotNull
    private String loai;

    //    @NotNull
    private Instant ngayTao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public Instant getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Instant ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhaKhoDTO)) {
            return false;
        }

        NhaKhoDTO nhaKhoDTO = (NhaKhoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nhaKhoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhaKhoDTO{" +
            "id=" + getId() +
            ", tenKho='" + getTenKho() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            ", loai='" + getLoai() + "'" +
            ", ngayTao='" + getNgayTao() + "'" +
            "}";
    }
}
