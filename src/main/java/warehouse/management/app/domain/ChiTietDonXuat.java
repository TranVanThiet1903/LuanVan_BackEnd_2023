package warehouse.management.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ChiTietDonXuat.
 */
@Entity
@Table(name = "chi_tiet_don_xuat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChiTietDonXuat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "so_luong", nullable = false)
    private Long soLuong;

    @NotNull
    @Column(name = "thanh_tien", nullable = false)
    private Long thanhTien;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "chiTietPhieuNhaps", "donNhaps", "chiTietDonXuats", "nhaCungCap", "nhaKho", "taiKhoan" },
        allowSetters = true
    )
    private DonXuat donXuat;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "chiTietKhos", "chiTietPhieuNhaps", "chiTietDonNhaps", "chiTietDonXuats", "nhomNguyenLieu", "nhaCungCap" },
        allowSetters = true
    )
    private NguyenLieu nguyenLieu;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChiTietDonXuat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSoLuong() {
        return this.soLuong;
    }

    public ChiTietDonXuat soLuong(Long soLuong) {
        this.setSoLuong(soLuong);
        return this;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }

    public Long getThanhTien() {
        return this.thanhTien;
    }

    public ChiTietDonXuat thanhTien(Long thanhTien) {
        this.setThanhTien(thanhTien);
        return this;
    }

    public void setThanhTien(Long thanhTien) {
        this.thanhTien = thanhTien;
    }

    //    public PhieuNhap getPhieuNhap() {
    //        return this.phieuNhap;
    //    }
    //
    //    public void setPhieuNhap(PhieuNhap phieuNhap) {
    //        this.phieuNhap = phieuNhap;
    //    }
    //
    //    public ChiTietDonXuat phieuNhap(PhieuNhap phieuNhap) {
    //        this.setPhieuNhap(phieuNhap);
    //        return this;
    //    }

    public DonXuat getDonXuat() {
        return this.donXuat;
    }

    public void setDonXuat(DonXuat donXuat) {
        this.donXuat = donXuat;
    }

    public ChiTietDonXuat phieuNhap(DonXuat donXuat) {
        this.setDonXuat(donXuat);
        return this;
    }

    public NguyenLieu getNguyenLieu() {
        return this.nguyenLieu;
    }

    public void setNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }

    public ChiTietDonXuat nguyenLieu(NguyenLieu nguyenLieu) {
        this.setNguyenLieu(nguyenLieu);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChiTietDonXuat)) {
            return false;
        }
        return id != null && id.equals(((ChiTietDonXuat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChiTietDonXuat{" +
            "id=" + getId() +
            ", soLuong=" + getSoLuong() +
            ", thanhTien=" + getThanhTien() +
            "}";
    }
}
