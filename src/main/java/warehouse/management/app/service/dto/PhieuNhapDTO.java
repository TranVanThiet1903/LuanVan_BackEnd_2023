package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.PhieuNhap} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhieuNhapDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant ngayLap;

    @NotNull
    private Long tongTienHang;

    @NotNull
    private Long vAT;

    @NotNull
    private Long phiShip;

    @NotNull
    private Long giamGia;

    @NotNull
    private Long tongTienThanhToan;

    @NotNull
    private Long tienNo;

    private String ghiChu;

    private NhaCungCapDTO nhaCungCap;

    private NhaKhoDTO nhaKho;

    private TaiKhoanDTO taiKhoan;

    private String trangThai;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Instant ngayLap) {
        this.ngayLap = ngayLap;
    }

    public Long getTongTienHang() {
        return tongTienHang;
    }

    public void setTongTienHang(Long tongTienHang) {
        this.tongTienHang = tongTienHang;
    }

    public Long getvAT() {
        return vAT;
    }

    public void setvAT(Long vAT) {
        this.vAT = vAT;
    }

    public Long getPhiShip() {
        return phiShip;
    }

    public void setPhiShip(Long phiShip) {
        this.phiShip = phiShip;
    }

    public Long getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(Long giamGia) {
        this.giamGia = giamGia;
    }

    public Long getTongTienThanhToan() {
        return tongTienThanhToan;
    }

    public void setTongTienThanhToan(Long tongTienThanhToan) {
        this.tongTienThanhToan = tongTienThanhToan;
    }

    public Long getTienNo() {
        return tienNo;
    }

    public void setTienNo(Long tienNo) {
        this.tienNo = tienNo;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public NhaCungCapDTO getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCapDTO nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public NhaKhoDTO getNhaKho() {
        return nhaKho;
    }

    public void setNhaKho(NhaKhoDTO nhaKho) {
        this.nhaKho = nhaKho;
    }

    public TaiKhoanDTO getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoanDTO taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    //    public String getTrangThai() {
    //        return trangThai;
    //    }
    //
    //    public void setTrangThai(String trangThai) {
    //        this.trangThai = trangThai;
    //    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhieuNhapDTO)) {
            return false;
        }

        PhieuNhapDTO phieuNhapDTO = (PhieuNhapDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, phieuNhapDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhieuNhapDTO{" +
            "id=" + getId() +
            ", ngayLap='" + getNgayLap() + "'" +
            ", tongTienHang=" + getTongTienHang() +
            ", vAT=" + getvAT() +
            ", phiShip=" + getPhiShip() +
            ", giamGia=" + getGiamGia() +
            ", tongTienThanhToan=" + getTongTienThanhToan() +
            ", tienNo=" + getTienNo() +
            ", ghiChu='" + getGhiChu() + "'" +
//            ", trangThai=" + getTrangThai() +
            ", nhaCungCap=" + getNhaCungCap() +
            ", nhaKho=" + getNhaKho() +
            ", taiKhoan=" + getTaiKhoan() +
            "}";
    }
}
