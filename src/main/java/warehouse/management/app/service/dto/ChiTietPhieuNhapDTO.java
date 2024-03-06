package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.ChiTietPhieuNhap} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChiTietPhieuNhapDTO implements Serializable {

    private Long id;

    @NotNull
    private Long soLuong;

    @NotNull
    private Long thanhTien;

    private PhieuNhapDTO phieuNhap;

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

    public PhieuNhapDTO getPhieuNhap() {
        return phieuNhap;
    }

    public void setPhieuNhap(PhieuNhapDTO phieuNhap) {
        this.phieuNhap = phieuNhap;
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
        if (!(o instanceof ChiTietPhieuNhapDTO)) {
            return false;
        }

        ChiTietPhieuNhapDTO chiTietPhieuNhapDTO = (ChiTietPhieuNhapDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chiTietPhieuNhapDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChiTietPhieuNhapDTO{" +
            "id=" + getId() +
            ", soLuong=" + getSoLuong() +
            ", thanhTien=" + getThanhTien() +
            ", phieuNhap=" + getPhieuNhap() +
            ", nguyenLieu=" + getNguyenLieu() +
            "}";
    }
}
