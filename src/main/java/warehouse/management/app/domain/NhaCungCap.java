package warehouse.management.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NhaCungCap.
 */
@Entity
@Table(name = "nha_cung_cap")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NhaCungCap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ten_ncc", nullable = false)
    private String tenNCC;

    @NotNull
    @Column(name = "so_dt", nullable = false)
    private String soDT;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    //    @NotNull
    @Column(name = "ghi_chu", nullable = false)
    private String ghiChu;

    @OneToMany(mappedBy = "nhaCungCap")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "chiTietKhos", "chiTietPhieuNhaps", "chiTietDonNhaps", "chiTietDonXuats", "nhomNguyenLieu", "nhaCungCap" },
        allowSetters = true
    )
    private Set<NguyenLieu> nguyenLieus = new HashSet<>();

    @OneToMany(mappedBy = "nhaCungCap")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "chiTietPhieuNhaps", "donNhaps", "chiTietDonXuats", "nhaCungCap", "nhaKho", "taiKhoan" },
        allowSetters = true
    )
    private Set<PhieuNhap> phieuNhaps = new HashSet<>();

    @OneToMany(mappedBy = "nhaCungCap")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chiTietDonNhaps", "nhaCungCap", "phieuNhap", "nhaKho", "taiKhoan" }, allowSetters = true)
    private Set<DonNhap> donNhaps = new HashSet<>();

    //    @OneToMany(mappedBy = "nhaCungCap")
    //    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    //    @JsonIgnoreProperties(value = { "nhaCungCap", "nhaKho", "taiKhoan" }, allowSetters = true)
    //    private Set<DonXuat> donXuats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NhaCungCap id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenNCC() {
        return this.tenNCC;
    }

    public NhaCungCap tenNCC(String tenNCC) {
        this.setTenNCC(tenNCC);
        return this;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getSoDT() {
        return this.soDT;
    }

    public NhaCungCap soDT(String soDT) {
        this.setSoDT(soDT);
        return this;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return this.email;
    }

    public NhaCungCap email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return this.diaChi;
    }

    public NhaCungCap diaChi(String diaChi) {
        this.setDiaChi(diaChi);
        return this;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGhiChu() {
        return this.ghiChu;
    }

    public NhaCungCap ghiChu(String ghiChu) {
        this.setGhiChu(ghiChu);
        return this;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Set<NguyenLieu> getNguyenLieus() {
        return this.nguyenLieus;
    }

    public void setNguyenLieus(Set<NguyenLieu> nguyenLieus) {
        if (this.nguyenLieus != null) {
            this.nguyenLieus.forEach(i -> i.setNhaCungCap(null));
        }
        if (nguyenLieus != null) {
            nguyenLieus.forEach(i -> i.setNhaCungCap(this));
        }
        this.nguyenLieus = nguyenLieus;
    }

    public NhaCungCap nguyenLieus(Set<NguyenLieu> nguyenLieus) {
        this.setNguyenLieus(nguyenLieus);
        return this;
    }

    public NhaCungCap addNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieus.add(nguyenLieu);
        nguyenLieu.setNhaCungCap(this);
        return this;
    }

    public NhaCungCap removeNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieus.remove(nguyenLieu);
        nguyenLieu.setNhaCungCap(null);
        return this;
    }

    public Set<PhieuNhap> getPhieuNhaps() {
        return this.phieuNhaps;
    }

    public void setPhieuNhaps(Set<PhieuNhap> phieuNhaps) {
        if (this.phieuNhaps != null) {
            this.phieuNhaps.forEach(i -> i.setNhaCungCap(null));
        }
        if (phieuNhaps != null) {
            phieuNhaps.forEach(i -> i.setNhaCungCap(this));
        }
        this.phieuNhaps = phieuNhaps;
    }

    public NhaCungCap phieuNhaps(Set<PhieuNhap> phieuNhaps) {
        this.setPhieuNhaps(phieuNhaps);
        return this;
    }

    public NhaCungCap addPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhaps.add(phieuNhap);
        phieuNhap.setNhaCungCap(this);
        return this;
    }

    public NhaCungCap removePhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhaps.remove(phieuNhap);
        phieuNhap.setNhaCungCap(null);
        return this;
    }

    public Set<DonNhap> getDonNhaps() {
        return this.donNhaps;
    }

    public void setDonNhaps(Set<DonNhap> donNhaps) {
        if (this.donNhaps != null) {
            this.donNhaps.forEach(i -> i.setNhaCungCap(null));
        }
        if (donNhaps != null) {
            donNhaps.forEach(i -> i.setNhaCungCap(this));
        }
        this.donNhaps = donNhaps;
    }

    public NhaCungCap donNhaps(Set<DonNhap> donNhaps) {
        this.setDonNhaps(donNhaps);
        return this;
    }

    public NhaCungCap addDonNhap(DonNhap donNhap) {
        this.donNhaps.add(donNhap);
        donNhap.setNhaCungCap(this);
        return this;
    }

    public NhaCungCap removeDonNhap(DonNhap donNhap) {
        this.donNhaps.remove(donNhap);
        donNhap.setNhaCungCap(null);
        return this;
    }

    //    public Set<DonXuat> getDonXuats() {
    //        return this.donXuats;
    //    }
    //
    //    public void setDonXuats(Set<DonXuat> donXuats) {
    //        if (this.donXuats != null) {
    //            this.donXuats.forEach(i -> i.setNhaCungCap(null));
    //        }
    //        if (donXuats != null) {
    //            donXuats.forEach(i -> i.setNhaCungCap(this));
    //        }
    //        this.donXuats = donXuats;
    //    }

    //    public NhaCungCap donXuats(Set<DonXuat> donXuats) {
    //        this.setDonXuats(donXuats);
    //        return this;
    //    }

    //    public NhaCungCap addDonXuat(DonXuat donXuat) {
    //        this.donXuats.add(donXuat);
    //        donXuat.setNhaCungCap(this);
    //        return this;
    //    }

    //    public NhaCungCap removeDonXuat(DonXuat donXuat) {
    //        this.donXuats.remove(donXuat);
    //        donXuat.setNhaCungCap(null);
    //        return this;
    //    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhaCungCap)) {
            return false;
        }
        return id != null && id.equals(((NhaCungCap) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhaCungCap{" +
            "id=" + getId() +
            ", tenNCC='" + getTenNCC() + "'" +
            ", soDT='" + getSoDT() + "'" +
            ", email='" + getEmail() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            ", ghiChu='" + getGhiChu() + "'" +
            "}";
    }
}
