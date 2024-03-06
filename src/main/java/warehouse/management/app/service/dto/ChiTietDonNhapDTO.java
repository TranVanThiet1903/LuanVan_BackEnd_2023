package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.ChiTietDonNhap} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChiTietDonNhapDTO implements Serializable {

    private Long id;

    @NotNull
    private Long soLuong;

    @NotNull
    private Long thanhTien;

    private DonNhapDTO donNhap;

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

    public DonNhapDTO getDonNhap() {
        return donNhap;
    }

    public void setDonNhap(DonNhapDTO donNhap) {
        this.donNhap = donNhap;
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
        if (!(o instanceof ChiTietDonNhapDTO)) {
            return false;
        }

        ChiTietDonNhapDTO chiTietDonNhapDTO = (ChiTietDonNhapDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chiTietDonNhapDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChiTietDonNhapDTO{" +
            "id=" + getId() +
            ", soLuong=" + getSoLuong() +
            ", thanhTien=" + getThanhTien() +
            ", donNhap=" + getDonNhap() +
            ", nguyenLieu=" + getNguyenLieu() +
            "}";
    }
}
