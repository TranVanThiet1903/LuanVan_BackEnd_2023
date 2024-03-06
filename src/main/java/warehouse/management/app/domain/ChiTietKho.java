package warehouse.management.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ChiTietKho.
 */
@Entity
@Table(name = "chi_tiet_kho")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChiTietKho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "so_luong", nullable = false)
    private Long soLuong;

    @ManyToOne
    @JsonIgnoreProperties(value = { "chiTietKhos", "phieuNhaps", "donNhaps", "donXuats" }, allowSetters = true)
    private NhaKho nhaKho;

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

    public ChiTietKho id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSoLuong() {
        return this.soLuong;
    }

    public ChiTietKho soLuong(Long soLuong) {
        this.setSoLuong(soLuong);
        return this;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }

    public NhaKho getNhaKho() {
        return this.nhaKho;
    }

    public void setNhaKho(NhaKho nhaKho) {
        this.nhaKho = nhaKho;
    }

    public ChiTietKho nhaKho(NhaKho nhaKho) {
        this.setNhaKho(nhaKho);
        return this;
    }

    public NguyenLieu getNguyenLieu() {
        return this.nguyenLieu;
    }

    public void setNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }

    public ChiTietKho nguyenLieu(NguyenLieu nguyenLieu) {
        this.setNguyenLieu(nguyenLieu);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChiTietKho)) {
            return false;
        }
        return id != null && id.equals(((ChiTietKho) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChiTietKho{" +
            "id=" + getId() +
            ", soLuong=" + getSoLuong() +
            "}";
    }
}
