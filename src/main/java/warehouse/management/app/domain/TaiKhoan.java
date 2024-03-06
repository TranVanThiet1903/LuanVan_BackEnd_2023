package warehouse.management.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A TaiKhoan.
 */
@Entity
@Table(name = "tai_khoan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaiKhoan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    //    @NotNull
    @Column(name = "ngay_tao", nullable = false)
    private Instant ngayTao;

    //    @NotNull
    @Column(name = "salt", nullable = false)
    private Long salt;

    //    @Column(name = "vi_tri", nullable = false)
    //    private String viTri;

    @OneToMany(mappedBy = "taiKhoan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "chiTietPhieuNhaps", "donNhaps", "chiTietDonXuats", "nhaCungCap", "nhaKho", "taiKhoan" },
        allowSetters = true
    )
    private Set<PhieuNhap> phieuNhaps = new HashSet<>();

    @OneToMany(mappedBy = "taiKhoan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chiTietDonNhaps", "nhaCungCap", "phieuNhap", "nhaKho", "taiKhoan" }, allowSetters = true)
    private Set<DonNhap> donNhaps = new HashSet<>();

    @OneToMany(mappedBy = "taiKhoan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nhaCungCap", "nhaKho", "taiKhoan" }, allowSetters = true)
    private Set<DonXuat> donXuats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public TaiKhoan id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHoTen() {
        return this.hoTen;
    }

    public TaiKhoan hoTen(String hoTen) {
        this.setHoTen(hoTen);
        return this;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getUsername() {
        return this.username;
    }

    public TaiKhoan username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public TaiKhoan password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getNgayTao() {
        return this.ngayTao;
    }

    public TaiKhoan ngayTao(Instant ngayTao) {
        this.setNgayTao(ngayTao);
        return this;
    }

    //    public void setViTri(String viTri) {
    //        this.viTri = viTri;
    //    }
    //
    //    public String getViTri() {
    //        return viTri;
    //    }
    //
    //    public TaiKhoan viTri(String viTri) {
    //        this.setViTri(viTri);
    //        return this;
    //    }

    public void setNgayTao(Instant ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Long getSalt() {
        return this.salt;
    }

    public TaiKhoan salt(Long salt) {
        this.setSalt(salt);
        return this;
    }

    public void setSalt(Long salt) {
        this.salt = salt;
    }

    public Set<PhieuNhap> getPhieuNhaps() {
        return this.phieuNhaps;
    }

    public void setPhieuNhaps(Set<PhieuNhap> phieuNhaps) {
        if (this.phieuNhaps != null) {
            this.phieuNhaps.forEach(i -> i.setTaiKhoan(null));
        }
        if (phieuNhaps != null) {
            phieuNhaps.forEach(i -> i.setTaiKhoan(this));
        }
        this.phieuNhaps = phieuNhaps;
    }

    public TaiKhoan phieuNhaps(Set<PhieuNhap> phieuNhaps) {
        this.setPhieuNhaps(phieuNhaps);
        return this;
    }

    public TaiKhoan addPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhaps.add(phieuNhap);
        phieuNhap.setTaiKhoan(this);
        return this;
    }

    public TaiKhoan removePhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhaps.remove(phieuNhap);
        phieuNhap.setTaiKhoan(null);
        return this;
    }

    public Set<DonNhap> getDonNhaps() {
        return this.donNhaps;
    }

    public void setDonNhaps(Set<DonNhap> donNhaps) {
        if (this.donNhaps != null) {
            this.donNhaps.forEach(i -> i.setTaiKhoan(null));
        }
        if (donNhaps != null) {
            donNhaps.forEach(i -> i.setTaiKhoan(this));
        }
        this.donNhaps = donNhaps;
    }

    public TaiKhoan donNhaps(Set<DonNhap> donNhaps) {
        this.setDonNhaps(donNhaps);
        return this;
    }

    public TaiKhoan addDonNhap(DonNhap donNhap) {
        this.donNhaps.add(donNhap);
        donNhap.setTaiKhoan(this);
        return this;
    }

    public TaiKhoan removeDonNhap(DonNhap donNhap) {
        this.donNhaps.remove(donNhap);
        donNhap.setTaiKhoan(null);
        return this;
    }

    public Set<DonXuat> getDonXuats() {
        return this.donXuats;
    }

    public void setDonXuats(Set<DonXuat> donXuats) {
        if (this.donXuats != null) {
            this.donXuats.forEach(i -> i.setTaiKhoan(null));
        }
        if (donXuats != null) {
            donXuats.forEach(i -> i.setTaiKhoan(this));
        }
        this.donXuats = donXuats;
    }

    public TaiKhoan donXuats(Set<DonXuat> donXuats) {
        this.setDonXuats(donXuats);
        return this;
    }

    public TaiKhoan addDonXuat(DonXuat donXuat) {
        this.donXuats.add(donXuat);
        donXuat.setTaiKhoan(this);
        return this;
    }

    public TaiKhoan removeDonXuat(DonXuat donXuat) {
        this.donXuats.remove(donXuat);
        donXuat.setTaiKhoan(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaiKhoan)) {
            return false;
        }
        return id != null && id.equals(((TaiKhoan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaiKhoan{" +
            "id=" + getId() +
            ", hoTen='" + getHoTen() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", ngayTao='" + getNgayTao() + "'" +
            ", salt=" + getSalt() +
            "}";
    }
}
