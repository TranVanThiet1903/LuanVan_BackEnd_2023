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
 * A NhomNguyenLieu.
 */
@Entity
@Table(name = "nhom_nguyen_lieu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NhomNguyenLieu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ten_nhom", nullable = false)
    private String tenNhom;

    @OneToMany(mappedBy = "nhomNguyenLieu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "chiTietKhos", "chiTietPhieuNhaps", "chiTietDonNhaps", "chiTietDonXuats", "nhomNguyenLieu", "nhaCungCap" },
        allowSetters = true
    )
    private Set<NguyenLieu> nguyenLieus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NhomNguyenLieu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenNhom() {
        return this.tenNhom;
    }

    public NhomNguyenLieu tenNhom(String tenNhom) {
        this.setTenNhom(tenNhom);
        return this;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public Set<NguyenLieu> getNguyenLieus() {
        return this.nguyenLieus;
    }

    public void setNguyenLieus(Set<NguyenLieu> nguyenLieus) {
        if (this.nguyenLieus != null) {
            this.nguyenLieus.forEach(i -> i.setNhomNguyenLieu(null));
        }
        if (nguyenLieus != null) {
            nguyenLieus.forEach(i -> i.setNhomNguyenLieu(this));
        }
        this.nguyenLieus = nguyenLieus;
    }

    public NhomNguyenLieu nguyenLieus(Set<NguyenLieu> nguyenLieus) {
        this.setNguyenLieus(nguyenLieus);
        return this;
    }

    public NhomNguyenLieu addNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieus.add(nguyenLieu);
        nguyenLieu.setNhomNguyenLieu(this);
        return this;
    }

    public NhomNguyenLieu removeNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieus.remove(nguyenLieu);
        nguyenLieu.setNhomNguyenLieu(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhomNguyenLieu)) {
            return false;
        }
        return id != null && id.equals(((NhomNguyenLieu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhomNguyenLieu{" +
            "id=" + getId() +
            ", tenNhom='" + getTenNhom() + "'" +
            "}";
    }
}
