package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.NguyenLieu} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NguyenLieuDTO implements Serializable {

    private Long id;

    @NotNull
    private String hinhAnh;

    @NotNull
    private String tenNguyenLieu;

    @NotNull
    private Long giaNhap;

    //    @NotNull
    private String moTa;

    @NotNull
    private String donViTinh;

    @NotNull
    private Long vAT;

    private NhomNguyenLieuDTO nhomNguyenLieu;

    private NhaCungCapDTO nhaCungCap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public Long getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(Long giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public Long getvAT() {
        return vAT;
    }

    public void setvAT(Long vAT) {
        this.vAT = vAT;
    }

    public NhomNguyenLieuDTO getNhomNguyenLieu() {
        return nhomNguyenLieu;
    }

    public void setNhomNguyenLieu(NhomNguyenLieuDTO nhomNguyenLieu) {
        this.nhomNguyenLieu = nhomNguyenLieu;
    }

    public NhaCungCapDTO getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCapDTO nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NguyenLieuDTO)) {
            return false;
        }

        NguyenLieuDTO nguyenLieuDTO = (NguyenLieuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nguyenLieuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NguyenLieuDTO{" +
            "id=" + getId() +
            ", hinhAnh='" + getHinhAnh() + "'" +
            ", tenNguyenLieu='" + getTenNguyenLieu() + "'" +
            ", giaNhap=" + getGiaNhap() +
            ", moTa='" + getMoTa() + "'" +
            ", donViTinh='" + getDonViTinh() + "'" +
            ", vAT=" + getvAT() +
            ", nhomNguyenLieu=" + getNhomNguyenLieu() +
            ", nhaCungCap=" + getNhaCungCap() +
            "}";
    }
}
