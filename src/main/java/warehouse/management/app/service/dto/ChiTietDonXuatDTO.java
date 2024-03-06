package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.ChiTietDonXuat} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChiTietDonXuatDTO implements Serializable {

    private Long id;

    @NotNull
    private Long soLuong;

    @NotNull
    private Long thanhTien;

    private DonXuatDTO donXuat;

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

    public Long getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Long thanhTien) {
        this.thanhTien = thanhTien;
    }

    public DonXuatDTO getDonXuat() {
        return donXuat;
    }

    public void setDonXuat(DonXuatDTO donXuat) {
        this.donXuat = donXuat;
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
        if (!(o instanceof ChiTietDonXuatDTO)) {
            return false;
        }

        ChiTietDonXuatDTO chiTietDonXuatDTO = (ChiTietDonXuatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chiTietDonXuatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChiTietDonXuatDTO{" +
            "id=" + getId() +
            ", soLuong=" + getSoLuong() +
            ", thanhTien=" + getThanhTien() +
            ", donXuat=" + getDonXuat() +
            ", nguyenLieu=" + getNguyenLieu() +
            "}";
    }
}
