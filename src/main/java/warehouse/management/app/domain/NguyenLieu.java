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
 * A NguyenLieu.
 */
@Entity
@Table(name = "nguyen_lieu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NguyenLieu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "hinh_anh", nullable = false)
    private String hinhAnh;

    @NotNull
    @Column(name = "ten_nguyen_lieu", nullable = false)
    private String tenNguyenLieu;

    @NotNull
    @Column(name = "gia_nhap", nullable = false)
    private Long giaNhap;

    @NotNull
    @Column(name = "mo_ta", nullable = false)
    private String moTa;

    @NotNull
    @Column(name = "don_vi_tinh", nullable = false)
    private String donViTinh;

    @NotNull
    @Column(name = "v_at", nullable = false)
    private Long vAT;

    @OneToMany(mappedBy = "nguyenLieu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nhaKho", "nguyenLieu" }, allowSetters = true)
    private Set<ChiTietKho> chiTietKhos = new HashSet<>();

    @OneToMany(mappedBy = "nguyenLieu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "phieuNhap", "nguyenLieu" }, allowSetters = true)
    private Set<ChiTietPhieuNhap> chiTietPhieuNhaps = new HashSet<>();

    @OneToMany(mappedBy = "nguyenLieu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "donNhap", "nguyenLieu" }, allowSetters = true)
    private Set<ChiTietDonNhap> chiTietDonNhaps = new HashSet<>();

    @OneToMany(mappedBy = "nguyenLieu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "phieuNhap", "nguyenLieu" }, allowSetters = true)
    private Set<ChiTietDonXuat> chiTietDonXuats = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "nguyenLieus" }, allowSetters = true)
    private NhomNguyenLieu nhomNguyenLieu;

    @ManyToOne
    @JsonIgnoreProperties(value = { "nguyenLieus", "phieuNhaps", "donNhaps", "donXuats" }, allowSetters = true)
    private NhaCungCap nhaCungCap;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NguyenLieu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHinhAnh() {
        return this.hinhAnh;
    }

    public NguyenLieu hinhAnh(String hinhAnh) {
        this.setHinhAnh(hinhAnh);
        return this;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenNguyenLieu() {
        return this.tenNguyenLieu;
    }

    public NguyenLieu tenNguyenLieu(String tenNguyenLieu) {
        this.setTenNguyenLieu(tenNguyenLieu);
        return this;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public Long getGiaNhap() {
        return this.giaNhap;
    }

    public NguyenLieu giaNhap(Long giaNhap) {
        this.setGiaNhap(giaNhap);
        return this;
    }

    public void setGiaNhap(Long giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getMoTa() {
        return this.moTa;
    }

    public NguyenLieu moTa(String moTa) {
        this.setMoTa(moTa);
        return this;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getDonViTinh() {
        return this.donViTinh;
    }

    public NguyenLieu donViTinh(String donViTinh) {
        this.setDonViTinh(donViTinh);
        return this;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public Long getvAT() {
        return this.vAT;
    }

    public NguyenLieu vAT(Long vAT) {
        this.setvAT(vAT);
        return this;
    }

    public void setvAT(Long vAT) {
        this.vAT = vAT;
    }

    public Set<ChiTietKho> getChiTietKhos() {
        return this.chiTietKhos;
    }

    public void setChiTietKhos(Set<ChiTietKho> chiTietKhos) {
        if (this.chiTietKhos != null) {
            this.chiTietKhos.forEach(i -> i.setNguyenLieu(null));
        }
        if (chiTietKhos != null) {
            chiTietKhos.forEach(i -> i.setNguyenLieu(this));
        }
        this.chiTietKhos = chiTietKhos;
    }

    public NguyenLieu chiTietKhos(Set<ChiTietKho> chiTietKhos) {
        this.setChiTietKhos(chiTietKhos);
        return this;
    }

    public NguyenLieu addChiTietKho(ChiTietKho chiTietKho) {
        this.chiTietKhos.add(chiTietKho);
        chiTietKho.setNguyenLieu(this);
        return this;
    }

    public NguyenLieu removeChiTietKho(ChiTietKho chiTietKho) {
        this.chiTietKhos.remove(chiTietKho);
        chiTietKho.setNguyenLieu(null);
        return this;
    }

    public Set<ChiTietPhieuNhap> getChiTietPhieuNhaps() {
        return this.chiTietPhieuNhaps;
    }

    public void setChiTietPhieuNhaps(Set<ChiTietPhieuNhap> chiTietPhieuNhaps) {
        if (this.chiTietPhieuNhaps != null) {
            this.chiTietPhieuNhaps.forEach(i -> i.setNguyenLieu(null));
        }
        if (chiTietPhieuNhaps != null) {
            chiTietPhieuNhaps.forEach(i -> i.setNguyenLieu(this));
        }
        this.chiTietPhieuNhaps = chiTietPhieuNhaps;
    }

    public NguyenLieu chiTietPhieuNhaps(Set<ChiTietPhieuNhap> chiTietPhieuNhaps) {
        this.setChiTietPhieuNhaps(chiTietPhieuNhaps);
        return this;
    }

    public NguyenLieu addChiTietPhieuNhap(ChiTietPhieuNhap chiTietPhieuNhap) {
        this.chiTietPhieuNhaps.add(chiTietPhieuNhap);
        chiTietPhieuNhap.setNguyenLieu(this);
        return this;
    }

    public NguyenLieu removeChiTietPhieuNhap(ChiTietPhieuNhap chiTietPhieuNhap) {
        this.chiTietPhieuNhaps.remove(chiTietPhieuNhap);
        chiTietPhieuNhap.setNguyenLieu(null);
        return this;
    }

    public Set<ChiTietDonNhap> getChiTietDonNhaps() {
        return this.chiTietDonNhaps;
    }

    public void setChiTietDonNhaps(Set<ChiTietDonNhap> chiTietDonNhaps) {
        if (this.chiTietDonNhaps != null) {
            this.chiTietDonNhaps.forEach(i -> i.setNguyenLieu(null));
        }
        if (chiTietDonNhaps != null) {
            chiTietDonNhaps.forEach(i -> i.setNguyenLieu(this));
        }
        this.chiTietDonNhaps = chiTietDonNhaps;
    }

    public NguyenLieu chiTietDonNhaps(Set<ChiTietDonNhap> chiTietDonNhaps) {
        this.setChiTietDonNhaps(chiTietDonNhaps);
        return this;
    }

    public NguyenLieu addChiTietDonNhap(ChiTietDonNhap chiTietDonNhap) {
        this.chiTietDonNhaps.add(chiTietDonNhap);
        chiTietDonNhap.setNguyenLieu(this);
        return this;
    }

    public NguyenLieu removeChiTietDonNhap(ChiTietDonNhap chiTietDonNhap) {
        this.chiTietDonNhaps.remove(chiTietDonNhap);
        chiTietDonNhap.setNguyenLieu(null);
        return this;
    }

    public Set<ChiTietDonXuat> getChiTietDonXuats() {
        return this.chiTietDonXuats;
    }

    public void setChiTietDonXuats(Set<ChiTietDonXuat> chiTietDonXuats) {
        if (this.chiTietDonXuats != null) {
            this.chiTietDonXuats.forEach(i -> i.setNguyenLieu(null));
        }
        if (chiTietDonXuats != null) {
            chiTietDonXuats.forEach(i -> i.setNguyenLieu(this));
        }
        this.chiTietDonXuats = chiTietDonXuats;
    }

    public NguyenLieu chiTietDonXuats(Set<ChiTietDonXuat> chiTietDonXuats) {
        this.setChiTietDonXuats(chiTietDonXuats);
        return this;
    }

    public NguyenLieu addChiTietDonXuat(ChiTietDonXuat chiTietDonXuat) {
        this.chiTietDonXuats.add(chiTietDonXuat);
        chiTietDonXuat.setNguyenLieu(this);
        return this;
    }

    public NguyenLieu removeChiTietDonXuat(ChiTietDonXuat chiTietDonXuat) {
        this.chiTietDonXuats.remove(chiTietDonXuat);
        chiTietDonXuat.setNguyenLieu(null);
        return this;
    }

    public NhomNguyenLieu getNhomNguyenLieu() {
        return this.nhomNguyenLieu;
    }

    public void setNhomNguyenLieu(NhomNguyenLieu nhomNguyenLieu) {
        this.nhomNguyenLieu = nhomNguyenLieu;
    }

    public NguyenLieu nhomNguyenLieu(NhomNguyenLieu nhomNguyenLieu) {
        this.setNhomNguyenLieu(nhomNguyenLieu);
        return this;
    }

    public NhaCungCap getNhaCungCap() {
        return this.nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public NguyenLieu nhaCungCap(NhaCungCap nhaCungCap) {
        this.setNhaCungCap(nhaCungCap);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NguyenLieu)) {
            return false;
        }
        return id != null && id.equals(((NguyenLieu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NguyenLieu{" +
            "id=" + getId() +
            ", hinhAnh='" + getHinhAnh() + "'" +
            ", tenNguyenLieu='" + getTenNguyenLieu() + "'" +
            ", giaNhap=" + getGiaNhap() +
            ", moTa='" + getMoTa() + "'" +
            ", donViTinh='" + getDonViTinh() + "'" +
            ", vAT=" + getvAT() +
            "}";
    }
}
