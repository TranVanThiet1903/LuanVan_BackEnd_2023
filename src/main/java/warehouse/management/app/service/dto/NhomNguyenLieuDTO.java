package warehouse.management.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link warehouse.management.app.domain.NhomNguyenLieu} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NhomNguyenLieuDTO implements Serializable {

    private Long id;

    @NotNull
    private String tenNhom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhomNguyenLieuDTO)) {
            return false;
        }

        NhomNguyenLieuDTO nhomNguyenLieuDTO = (NhomNguyenLieuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nhomNguyenLieuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhomNguyenLieuDTO{" +
            "id=" + getId() +
            ", tenNhom='" + getTenNhom() + "'" +
            "}";
    }
}
