package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.DonXuat} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonXuatDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant ngayLap;

    @NotNull
    private Long tongTienHang;

    private UUID nguoiXacNhan;

    //    private NhaCungCapDTO nhaCungCap;

    private NhaKhoDTO nhaKho;

    private TaiKhoanDTO taiKhoan;

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

    public UUID getNguoiXacNhan() {
        return nguoiXacNhan;
    }

    public void setNguoiXacNhan(UUID nguoiXacNhan) {
        this.nguoiXacNhan = nguoiXacNhan;
    }

    //    public String getGhiChu() {
    //        return ghiChu;
    //    }
    //
    //    public void setGhiChu(String ghiChu) {
    //        this.ghiChu = ghiChu;
    //    }

    //    public NhaCungCapDTO getNhaCungCap() {
    //        return nhaCungCap;
    //    }
    //
    //    public void setNhaCungCap(NhaCungCapDTO nhaCungCap) {
    //        this.nhaCungCap = nhaCungCap;
    //    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonXuatDTO)) {
            return false;
        }

        DonXuatDTO donXuatDTO = (DonXuatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donXuatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonXuatDTO{" +
            "id=" + getId() +
            ", ngayLap='" + getNgayLap() + "'" +
            ", tongTienHang=" + getTongTienHang() +
            ", nguoiXacNhan='" + getNguoiXacNhan() + "'" +
//            ", nhaCungCap=" + getNhaCungCap() +
            ", nhaKho=" + getNhaKho() +
            ", taiKhoan=" + getTaiKhoan() +
            "}";
    }
}
