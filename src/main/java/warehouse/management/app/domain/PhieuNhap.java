package warehouse.management.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PhieuNhap.
 */
@Entity
@Table(name = "phieu_nhap")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhieuNhap implements Serializable {

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

    @NotNull
    @Column(name = "v_at", nullable = false)
    private Long vAT;

    @NotNull
    @Column(name = "phi_ship", nullable = false)
    private Long phiShip;

    @NotNull
    @Column(name = "giam_gia", nullable = false)
    private Long giamGia;

    @NotNull
    @Column(name = "tong_tien_thanh_toan", nullable = false)
    private Long tongTienThanhToan;

    @NotNull
    @Column(name = "tien_no", nullable = false)
    private Long tienNo;

    @Column(name = "ghi_chu")
    private String ghiChu;

    //    @Column(name = "trang_thai")
    //    private String trangThai;

    @OneToMany(mappedBy = "phieuNhap")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "phieuNhap", "nguyenLieu" }, allowSetters = true)
    private Set<ChiTietPhieuNhap> chiTietPhieuNhaps = new HashSet<>();

    @OneToMany(mappedBy = "phieuNhap")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chiTietDonNhaps", "nhaCungCap", "phieuNhap", "nhaKho", "taiKhoan" }, allowSetters = true)
    private Set<DonNhap> donNhaps = new HashSet<>();

    //    @OneToMany(mappedBy = "phieuNhap")
    //    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    //    @JsonIgnoreProperties(value = { "phieuNhap", "nguyenLieu" }, allowSetters = true)
    //    private Set<ChiTietDonXuat> chiTietDonXuats = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "nguyenLieus", "phieuNhaps", "donNhaps", "donXuats" }, allowSetters = true)
    private NhaCungCap nhaCungCap;

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

    public PhieuNhap id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getNgayLap() {
        return this.ngayLap;
    }

    public PhieuNhap ngayLap(Instant ngayLap) {
        this.setNgayLap(ngayLap);
        return this;
    }

    public void setNgayLap(Instant ngayLap) {
        this.ngayLap = ngayLap;
    }

    public Long getTongTienHang() {
        return this.tongTienHang;
    }

    public PhieuNhap tongTienHang(Long tongTienHang) {
        this.setTongTienHang(tongTienHang);
        return this;
    }

    public void setTongTienHang(Long tongTienHang) {
        this.tongTienHang = tongTienHang;
    }

    public Long getvAT() {
        return this.vAT;
    }

    public PhieuNhap vAT(Long vAT) {
        this.setvAT(vAT);
        return this;
    }

    public void setvAT(Long vAT) {
        this.vAT = vAT;
    }

    public Long getPhiShip() {
        return this.phiShip;
    }

    public PhieuNhap phiShip(Long phiShip) {
        this.setPhiShip(phiShip);
        return this;
    }

    public void setPhiShip(Long phiShip) {
        this.phiShip = phiShip;
    }

    public Long getGiamGia() {
        return this.giamGia;
    }

    public PhieuNhap giamGia(Long giamGia) {
        this.setGiamGia(giamGia);
        return this;
    }

    public void setGiamGia(Long giamGia) {
        this.giamGia = giamGia;
    }

    public Long getTongTienThanhToan() {
        return this.tongTienThanhToan;
    }

    public PhieuNhap tongTienThanhToan(Long tongTienThanhToan) {
        this.setTongTienThanhToan(tongTienThanhToan);
        return this;
    }

    public void setTongTienThanhToan(Long tongTienThanhToan) {
        this.tongTienThanhToan = tongTienThanhToan;
    }

    public Long getTienNo() {
        return this.tienNo;
    }

    public PhieuNhap tienNo(Long tienNo) {
        this.setTienNo(tienNo);
        return this;
    }

    public void setTienNo(Long tienNo) {
        this.tienNo = tienNo;
    }

    public String getGhiChu() {
        return this.ghiChu;
    }

    public PhieuNhap ghiChu(String ghiChu) {
        this.setGhiChu(ghiChu);
        return this;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Set<ChiTietPhieuNhap> getChiTietPhieuNhaps() {
        return this.chiTietPhieuNhaps;
    }

    public void setChiTietPhieuNhaps(Set<ChiTietPhieuNhap> chiTietPhieuNhaps) {
        if (this.chiTietPhieuNhaps != null) {
            this.chiTietPhieuNhaps.forEach(i -> i.setPhieuNhap(null));
        }
        if (chiTietPhieuNhaps != null) {
            chiTietPhieuNhaps.forEach(i -> i.setPhieuNhap(this));
        }
        this.chiTietPhieuNhaps = chiTietPhieuNhaps;
    }

    public PhieuNhap chiTietPhieuNhaps(Set<ChiTietPhieuNhap> chiTietPhieuNhaps) {
        this.setChiTietPhieuNhaps(chiTietPhieuNhaps);
        return this;
    }

    public PhieuNhap addChiTietPhieuNhap(ChiTietPhieuNhap chiTietPhieuNhap) {
        this.chiTietPhieuNhaps.add(chiTietPhieuNhap);
        chiTietPhieuNhap.setPhieuNhap(this);
        return this;
    }

    public PhieuNhap removeChiTietPhieuNhap(ChiTietPhieuNhap chiTietPhieuNhap) {
        this.chiTietPhieuNhaps.remove(chiTietPhieuNhap);
        chiTietPhieuNhap.setPhieuNhap(null);
        return this;
    }

    public Set<DonNhap> getDonNhaps() {
        return this.donNhaps;
    }

    public void setDonNhaps(Set<DonNhap> donNhaps) {
        if (this.donNhaps != null) {
            this.donNhaps.forEach(i -> i.setPhieuNhap(null));
        }
        if (donNhaps != null) {
            donNhaps.forEach(i -> i.setPhieuNhap(this));
        }
        this.donNhaps = donNhaps;
    }

    public PhieuNhap donNhaps(Set<DonNhap> donNhaps) {
        this.setDonNhaps(donNhaps);
        return this;
    }

    public PhieuNhap addDonNhap(DonNhap donNhap) {
        this.donNhaps.add(donNhap);
        donNhap.setPhieuNhap(this);
        return this;
    }

    public PhieuNhap removeDonNhap(DonNhap donNhap) {
        this.donNhaps.remove(donNhap);
        donNhap.setPhieuNhap(null);
        return this;
    }

    //    public Set<ChiTietDonXuat> getChiTietDonXuats() {
    //        return this.chiTietDonXuats;
    //    }

    //    public void setChiTietDonXuats(Set<ChiTietDonXuat> chiTietDonXuats) {
    //        if (this.chiTietDonXuats != null) {
    //            this.chiTietDonXuats.forEach(i -> i.setPhieuNhap(null));
    //        }
    //        if (chiTietDonXuats != null) {
    //            chiTietDonXuats.forEach(i -> i.setPhieuNhap(this));
    //        }
    //        this.chiTietDonXuats = chiTietDonXuats;
    //    }

    //    public PhieuNhap chiTietDonXuats(Set<ChiTietDonXuat> chiTietDonXuats) {
    //        this.setChiTietDonXuats(chiTietDonXuats);
    //        return this;
    //    }
    //
    //    public PhieuNhap addChiTietDonXuat(ChiTietDonXuat chiTietDonXuat) {
    //        this.chiTietDonXuats.add(chiTietDonXuat);
    //        chiTietDonXuat.setPhieuNhap(this);
    //        return this;
    //    }
    //
    //    public PhieuNhap removeChiTietDonXuat(ChiTietDonXuat chiTietDonXuat) {
    //        this.chiTietDonXuats.remove(chiTietDonXuat);
    //        chiTietDonXuat.setPhieuNhap(null);
    //        return this;
    //    }

    public NhaCungCap getNhaCungCap() {
        return this.nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public PhieuNhap nhaCungCap(NhaCungCap nhaCungCap) {
        this.setNhaCungCap(nhaCungCap);
        return this;
    }

    public NhaKho getNhaKho() {
        return this.nhaKho;
    }

    public void setNhaKho(NhaKho nhaKho) {
        this.nhaKho = nhaKho;
    }

    public PhieuNhap nhaKho(NhaKho nhaKho) {
        this.setNhaKho(nhaKho);
        return this;
    }

    public TaiKhoan getTaiKhoan() {
        return this.taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public PhieuNhap taiKhoan(TaiKhoan taiKhoan) {
        this.setTaiKhoan(taiKhoan);
        return this;
    }

    //    public String getTrangThai() {
    //        return trangThai;
    //    }
    //
    //    public void setTrangThai(String trangThai) {
    //        this.trangThai = trangThai;
    //    }
    //
    //    public PhieuNhap trangThai(String trangThai) {
    //        this.setTrangThai(trangThai);
    //        return this;
    //    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhieuNhap)) {
            return false;
        }
        return id != null && id.equals(((PhieuNhap) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhieuNhap{" +
            "id=" + getId() +
            ", ngayLap='" + getNgayLap() + "'" +
            ", tongTienHang=" + getTongTienHang() +
            ", vAT=" + getvAT() +
            ", phiShip=" + getPhiShip() +
            ", giamGia=" + getGiamGia() +
            ", tongTienThanhToan=" + getTongTienThanhToan() +
            ", tienNo=" + getTienNo() +
            ", ghiChu='" + getGhiChu() + "'" +
//            ", trangThai=" + getTrangThai() +
            "}";
    }
}
