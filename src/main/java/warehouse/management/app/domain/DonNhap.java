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
 * A DonNhap.
 */
@Entity
@Table(name = "don_nhap")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonNhap implements Serializable {

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

    //    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "v_at")
    private UUID nguoiXacNhan;

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

    //    @Column(name = "nguoi_xac_nhan_hang")
    //    private UUID nguoiXacNhanHang;

    //    @NotNull
    //    @Column(name = "trang_thai", nullable = false)
    //    private String trangThai;

    @OneToMany(mappedBy = "donNhap")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "donNhap", "nguyenLieu" }, allowSetters = true)
    private Set<ChiTietDonNhap> chiTietDonNhaps = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "nguyenLieus", "phieuNhaps", "donNhaps", "donXuats" }, allowSetters = true)
    private NhaCungCap nhaCungCap;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "chiTietPhieuNhaps", "donNhaps", "chiTietDonXuats", "nhaCungCap", "nhaKho", "taiKhoan" },
        allowSetters = true
    )
    private PhieuNhap phieuNhap;

    @ManyToOne
    @JsonIgnoreProperties(value = { "chiTietKhos", "phieuNhaps", "donNhaps", "donXuats" }, allowSetters = true)
    private NhaKho nhaKho;

    @ManyToOne
    @JsonIgnoreProperties(value = { "phieuNhaps", "donNhaps", "donXuats" }, allowSetters = true)
    private TaiKhoan taiKhoan;

    //    @ManyToOne(fetch = FetchType.EAGER)
    ////    @JsonIgnoreProperties(value = { "phieuNhaps", "donNhaps", "donXuats" }, allowSetters = true)
    //    @JoinColumn(name = "tai_khoan_xac_nhan_id")
    //    private TaiKhoan taiKhoanXacNhan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DonNhap id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getNgayLap() {
        return this.ngayLap;
    }

    public DonNhap ngayLap(Instant ngayLap) {
        this.setNgayLap(ngayLap);
        return this;
    }

    public void setNgayLap(Instant ngayLap) {
        this.ngayLap = ngayLap;
    }

    public Long getTongTienHang() {
        return this.tongTienHang;
    }

    public DonNhap tongTienHang(Long tongTienHang) {
        this.setTongTienHang(tongTienHang);
        return this;
    }

    public void setTongTienHang(Long tongTienHang) {
        this.tongTienHang = tongTienHang;
    }

    //    public Long getvAT() {
    //        return this.vAT;
    //    }
    //
    //    public DonNhap vAT(Long vAT) {
    //        this.setvAT(vAT);
    //        return this;
    //    }
    //
    //    public void setvAT(Long vAT) {
    //        this.vAT = vAT;
    //    }

    public UUID getNguoiXacNhan() {
        return nguoiXacNhan;
    }

    public void setNguoiXacNhan(UUID nguoiXacNhan) {
        this.nguoiXacNhan = nguoiXacNhan;
    }

    public Long getPhiShip() {
        return this.phiShip;
    }

    public DonNhap phiShip(Long phiShip) {
        this.setPhiShip(phiShip);
        return this;
    }

    public void setPhiShip(Long phiShip) {
        this.phiShip = phiShip;
    }

    public Long getGiamGia() {
        return this.giamGia;
    }

    public DonNhap giamGia(Long giamGia) {
        this.setGiamGia(giamGia);
        return this;
    }

    public void setGiamGia(Long giamGia) {
        this.giamGia = giamGia;
    }

    public Long getTongTienThanhToan() {
        return this.tongTienThanhToan;
    }

    public DonNhap tongTienThanhToan(Long tongTienThanhToan) {
        this.setTongTienThanhToan(tongTienThanhToan);
        return this;
    }

    public void setTongTienThanhToan(Long tongTienThanhToan) {
        this.tongTienThanhToan = tongTienThanhToan;
    }

    public Long getTienNo() {
        return this.tienNo;
    }

    public DonNhap tienNo(Long tienNo) {
        this.setTienNo(tienNo);
        return this;
    }

    public void setTienNo(Long tienNo) {
        this.tienNo = tienNo;
    }

    public String getGhiChu() {
        return this.ghiChu;
    }

    public DonNhap ghiChu(String ghiChu) {
        this.setGhiChu(ghiChu);
        return this;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Set<ChiTietDonNhap> getChiTietDonNhaps() {
        return this.chiTietDonNhaps;
    }

    public void setChiTietDonNhaps(Set<ChiTietDonNhap> chiTietDonNhaps) {
        if (this.chiTietDonNhaps != null) {
            this.chiTietDonNhaps.forEach(i -> i.setDonNhap(null));
        }
        if (chiTietDonNhaps != null) {
            chiTietDonNhaps.forEach(i -> i.setDonNhap(this));
        }
        this.chiTietDonNhaps = chiTietDonNhaps;
    }

    public DonNhap chiTietDonNhaps(Set<ChiTietDonNhap> chiTietDonNhaps) {
        this.setChiTietDonNhaps(chiTietDonNhaps);
        return this;
    }

    public DonNhap addChiTietDonNhap(ChiTietDonNhap chiTietDonNhap) {
        this.chiTietDonNhaps.add(chiTietDonNhap);
        chiTietDonNhap.setDonNhap(this);
        return this;
    }

    public DonNhap removeChiTietDonNhap(ChiTietDonNhap chiTietDonNhap) {
        this.chiTietDonNhaps.remove(chiTietDonNhap);
        chiTietDonNhap.setDonNhap(null);
        return this;
    }

    public NhaCungCap getNhaCungCap() {
        return this.nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public DonNhap nhaCungCap(NhaCungCap nhaCungCap) {
        this.setNhaCungCap(nhaCungCap);
        return this;
    }

    public PhieuNhap getPhieuNhap() {
        return this.phieuNhap;
    }

    public void setPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhap = phieuNhap;
    }

    public DonNhap phieuNhap(PhieuNhap phieuNhap) {
        this.setPhieuNhap(phieuNhap);
        return this;
    }

    public NhaKho getNhaKho() {
        return this.nhaKho;
    }

    public void setNhaKho(NhaKho nhaKho) {
        this.nhaKho = nhaKho;
    }

    public DonNhap nhaKho(NhaKho nhaKho) {
        this.setNhaKho(nhaKho);
        return this;
    }

    public TaiKhoan getTaiKhoan() {
        return this.taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public DonNhap taiKhoan(TaiKhoan taiKhoan) {
        this.setTaiKhoan(taiKhoan);
        return this;
    }

    //    public UUID getNguoiXacNhanHang() {
    //        return nguoiXacNhanHang;
    //    }
    //
    //    public void setNguoiXacNhanHang(UUID nguoiXacNhanHang) {
    //        this.nguoiXacNhanHang = nguoiXacNhanHang;
    //    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonNhap)) {
            return false;
        }
        return id != null && id.equals(((DonNhap) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonNhap{" +
            "id=" + getId() +
            ", ngayLap='" + getNgayLap() + "'" +
            ", tongTienHang=" + getTongTienHang() +
            ", nguoiXacNhan=" + getNguoiXacNhan() +
            ", phiShip=" + getPhiShip() +
            ", giamGia=" + getGiamGia() +
            ", tongTienThanhToan=" + getTongTienThanhToan() +
            ", tienNo=" + getTienNo() +
            ", ghiChu='" + getGhiChu() + "'" +
            "}";
    }
}
