package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.ChiTietKho} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChiTietKhoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long soLuong;

    private NhaKhoDTO nhaKho;

    private NguyenLieuDTO nguyenLieu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }

    public NhaKhoDTO getNhaKho() {
        return nhaKho;
    }

    public void setNhaKho(NhaKhoDTO nhaKho) {
        this.nhaKho = nhaKho;
    }

    public NguyenLieuDTO getNguyenLieu() {
        return nguyenLieu;
    }

    public void setNguyenLieu(NguyenLieuDTO nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChiTietKhoDTO)) {
            return false;
        }

        ChiTietKhoDTO chiTietKhoDTO = (ChiTietKhoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chiTietKhoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChiTietKhoDTO{" +
            "id=" + getId() +
            ", soLuong=" + getSoLuong() +
            ", nhaKho=" + getNhaKho() +
            ", nguyenLieu=" + getNguyenLieu() +
            "}";
    }
}
