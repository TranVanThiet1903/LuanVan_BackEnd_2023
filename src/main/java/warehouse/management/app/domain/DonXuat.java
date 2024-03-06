package warehouse.management.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A DonXuat.
 */
@Entity
@Table(name = "don_xuat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonXuat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ngay_lap", nullable = false)
    private Instant ngayLap;

    @NotNull
    @Column(name = "tong_tien_hang", nullable = false)
    private Long tongTienHang;

    @Type(type = "uuid-char")
    @Column(name = "ghi_chu")
    private UUID nguoiXacNhan;

    //    @ManyToOne
    //    @JsonIgnoreProperties(value = { "nguyenLieus", "phieuNhaps", "donNhaps", "donXuats" }, allowSetters = true)
    //    private NhaCungCap nhaCungCap;

    @ManyToOne
    @JsonIgnoreProperties(value = { "chiTietKhos", "phieuNhaps", "donNhaps", "donXuats" }, allowSetters = true)
    private NhaKho nhaKho;

    @ManyToOne
    @JsonIgnoreProperties(value = { "phieuNhaps", "donNhaps", "donXuats" }, allowSetters = true)
    private TaiKhoan taiKhoan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DonXuat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getNgayLap() {
        return this.ngayLap;
    }

    public DonXuat ngayLap(Instant ngayLap) {
        this.setNgayLap(ngayLap);
        return this;
    }

    public void setNgayLap(Instant ngayLap) {
        this.ngayLap = ngayLap;
    }

    public Long getTongTienHang() {
        return this.tongTienHang;
    }

    public DonXuat tongTienHang(Long tongTienHang) {
        this.setTongTienHang(tongTienHang);
        return this;
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
    //        return this.ghiChu;
    //    }
    //
    //    public DonXuat ghiChu(String ghiChu) {
    //        this.setGhiChu(ghiChu);
    //        return this;
    //    }
    //
    //    public void setGhiChu(String ghiChu) {
    //        this.ghiChu = ghiChu;
    //    }

    //    public NhaCungCap getNhaCungCap() {
    //        return this.nhaCungCap;
    //    }
    //
    //    public void setNhaCungCap(NhaCungCap nhaCungCap) {
    //        this.nhaCungCap = nhaCungCap;
    //    }
    //
    //    public DonXuat nhaCungCap(NhaCungCap nhaCungCap) {
    //        this.setNhaCungCap(nhaCungCap);
    //        return this;
    //    }

    public NhaKho getNhaKho() {
        return this.nhaKho;
    }

    public void setNhaKho(NhaKho nhaKho) {
        this.nhaKho = nhaKho;
    }

    public DonXuat nhaKho(NhaKho nhaKho) {
        this.setNhaKho(nhaKho);
        return this;
    }

    public TaiKhoan getTaiKhoan() {
        return this.taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public DonXuat taiKhoan(TaiKhoan taiKhoan) {
        this.setTaiKhoan(taiKhoan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonXuat)) {
            return false;
        }
        return id != null && id.equals(((DonXuat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonXuat{" +
            "id=" + getId() +
            ", ngayLap='" + getNgayLap() + "'" +
            ", tongTienHang=" + getTongTienHang() +
            ", nguoiXacNhan='" + getNguoiXacNhan() + "'" +
            "}";
    }
}
