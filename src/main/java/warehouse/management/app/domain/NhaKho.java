package warehouse.management.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NhaKho.
 */
@Entity
@Table(name = "nha_kho")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NhaKho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ten_kho", nullable = false)
    private String tenKho;

    @NotNull
    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @NotNull
    @Column(name = "loai", nullable = false)
    private String loai;

    @NotNull
    @Column(name = "ngay_tao", nullable = false)
    private Instant ngayTao;

    @OneToMany(mappedBy = "nhaKho")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nhaKho", "nguyenLieu" }, allowSetters = true)
    private Set<ChiTietKho> chiTietKhos = new HashSet<>();

    @OneToMany(mappedBy = "nhaKho")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "chiTietPhieuNhaps", "donNhaps", "chiTietDonXuats", "nhaCungCap", "nhaKho", "taiKhoan" },
        allowSetters = true
    )
    private Set<PhieuNhap> phieuNhaps = new HashSet<>();

    @OneToMany(mappedBy = "nhaKho")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chiTietDonNhaps", "nhaCungCap", "phieuNhap", "nhaKho", "taiKhoan" }, allowSetters = true)
    private Set<DonNhap> donNhaps = new HashSet<>();

    @OneToMany(mappedBy = "nhaKho")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nhaCungCap", "nhaKho", "taiKhoan" }, allowSetters = true)
    private Set<DonXuat> donXuats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    //    @OneToMany(mappedBy = "nhaKho", cascade = CascadeType.ALL, orphanRemoval = true)
    //    private List<ChiTietKho> chiTietKhoList;
    //
    //    // Constructors, getters, setters và các phương thức khác
    //
    //    public List<ChiTietKho> getChiTietKhoList() {
    //        return chiTietKhoList;
    //    }

    public Long getId() {
        return this.id;
    }

    public NhaKho id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenKho() {
        return this.tenKho;
    }

    public NhaKho tenKho(String tenKho) {
        this.setTenKho(tenKho);
        return this;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }

    public String getDiaChi() {
        return this.diaChi;
    }

    public NhaKho diaChi(String diaChi) {
        this.setDiaChi(diaChi);
        return this;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getLoai() {
        return this.loai;
    }

    public NhaKho loai(String loai) {
        this.setLoai(loai);
        return this;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public Instant getNgayTao() {
        return this.ngayTao;
    }

    public NhaKho ngayTao(Instant ngayTao) {
        this.setNgayTao(ngayTao);
        return this;
    }

    public void setNgayTao(Instant ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Set<ChiTietKho> getChiTietKhos() {
        return this.chiTietKhos;
    }

    public void setChiTietKhos(Set<ChiTietKho> chiTietKhos) {
        if (this.chiTietKhos != null) {
            this.chiTietKhos.forEach(i -> i.setNhaKho(null));
        }
        if (chiTietKhos != null) {
            chiTietKhos.forEach(i -> i.setNhaKho(this));
        }
        this.chiTietKhos = chiTietKhos;
    }

    public NhaKho chiTietKhos(Set<ChiTietKho> chiTietKhos) {
        this.setChiTietKhos(chiTietKhos);
        return this;
    }

    public NhaKho addChiTietKho(ChiTietKho chiTietKho) {
        this.chiTietKhos.add(chiTietKho);
        chiTietKho.setNhaKho(this);
        return this;
    }

    public NhaKho removeChiTietKho(ChiTietKho chiTietKho) {
        this.chiTietKhos.remove(chiTietKho);
        chiTietKho.setNhaKho(null);
        return this;
    }

    public Set<PhieuNhap> getPhieuNhaps() {
        return this.phieuNhaps;
    }

    public void setPhieuNhaps(Set<PhieuNhap> phieuNhaps) {
        if (this.phieuNhaps != null) {
            this.phieuNhaps.forEach(i -> i.setNhaKho(null));
        }
        if (phieuNhaps != null) {
            phieuNhaps.forEach(i -> i.setNhaKho(this));
        }
        this.phieuNhaps = phieuNhaps;
    }

    public NhaKho phieuNhaps(Set<PhieuNhap> phieuNhaps) {
        this.setPhieuNhaps(phieuNhaps);
        return this;
    }

    public NhaKho addPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhaps.add(phieuNhap);
        phieuNhap.setNhaKho(this);
        return this;
    }

    public NhaKho removePhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhaps.remove(phieuNhap);
        phieuNhap.setNhaKho(null);
        return this;
    }

    public Set<DonNhap> getDonNhaps() {
        return this.donNhaps;
    }

    public void setDonNhaps(Set<DonNhap> donNhaps) {
        if (this.donNhaps != null) {
            this.donNhaps.forEach(i -> i.setNhaKho(null));
        }
        if (donNhaps != null) {
            donNhaps.forEach(i -> i.setNhaKho(this));
        }
        this.donNhaps = donNhaps;
    }

    public NhaKho donNhaps(Set<DonNhap> donNhaps) {
        this.setDonNhaps(donNhaps);
        return this;
    }

    public NhaKho addDonNhap(DonNhap donNhap) {
        this.donNhaps.add(donNhap);
        donNhap.setNhaKho(this);
        return this;
    }

    public NhaKho removeDonNhap(DonNhap donNhap) {
        this.donNhaps.remove(donNhap);
        donNhap.setNhaKho(null);
        return this;
    }

    public Set<DonXuat> getDonXuats() {
        return this.donXuats;
    }

    public void setDonXuats(Set<DonXuat> donXuats) {
        if (this.donXuats != null) {
            this.donXuats.forEach(i -> i.setNhaKho(null));
        }
        if (donXuats != null) {
            donXuats.forEach(i -> i.setNhaKho(this));
        }
        this.donXuats = donXuats;
    }

    public NhaKho donXuats(Set<DonXuat> donXuats) {
        this.setDonXuats(donXuats);
        return this;
    }

    public NhaKho addDonXuat(DonXuat donXuat) {
        this.donXuats.add(donXuat);
        donXuat.setNhaKho(this);
        return this;
    }

    public NhaKho removeDonXuat(DonXuat donXuat) {
        this.donXuats.remove(donXuat);
        donXuat.setNhaKho(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhaKho)) {
            return false;
        }
        return id != null && id.equals(((NhaKho) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhaKho{" +
            "id=" + getId() +
            ", tenKho='" + getTenKho() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            ", loai='" + getLoai() + "'" +
            ", ngayTao='" + getNgayTao() + "'" +
            "}";
    }
}
