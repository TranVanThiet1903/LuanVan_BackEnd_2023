package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.DonNhap} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonNhapDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant ngayLap;

    @NotNull
    private Long tongTienHang;

    //    @NotNull
    private UUID nguoiXacNhan;

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

    private PhieuNhapDTO phieuNhap;

    private NhaKhoDTO nhaKho;

    private TaiKhoanDTO taiKhoan;

    //    private UUID nguoiXacNhanHang;

    //    @NotNull
    //    private String trangThai;

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

    //    public Long getvAT() {
    //        return vAT;
    //    }
    //
    //    public void setvAT(Long vAT) {
    //        this.vAT = vAT;
    //    }

    public UUID getNguoiXacNhan() {
        return nguoiXacNhan;
    }

    public void setNguoiXacNhan(UUID nguoiXacNhan) {
        this.nguoiXacNhan = nguoiXacNhan;
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

    public PhieuNhapDTO getPhieuNhap() {
        return phieuNhap;
    }

    public void setPhieuNhap(PhieuNhapDTO phieuNhap) {
        this.phieuNhap = phieuNhap;
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

    //    public UUID getNguoiXacNhanHang() {
    //        return nguoiXacNhanHang;
    //    }
    //
    //    public void setNguoiXacNhanHang(UUID nguoiXacNhanHang) {
    //        this.nguoiXacNhanHang = nguoiXacNhanHang;
    //    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonNhapDTO)) {
            return false;
        }

        DonNhapDTO donNhapDTO = (DonNhapDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donNhapDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonNhapDTO{" +
            "id=" + getId() +
            ", ngayLap='" + getNgayLap() + "'" +
            ", tongTienHang=" + getTongTienHang() +
            ", nguoiXacNhan=" + getNguoiXacNhan() +
            ", phiShip=" + getPhiShip() +
            ", giamGia=" + getGiamGia() +
            ", tongTienThanhToan=" + getTongTienThanhToan() +
            ", tienNo=" + getTienNo() +
            ", ghiChu='" + getGhiChu() + "'" +
            ", nhaCungCap=" + getNhaCungCap() +
            ", phieuNhap=" + getPhieuNhap() +
            ", nhaKho=" + getNhaKho() +
            ", taiKhoan=" + getTaiKhoan() +
            "}";
    }
}
