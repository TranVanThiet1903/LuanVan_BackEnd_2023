package warehouse.management.app.web.rest.vm;

import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class NguyenLieuRequest {

    @NotNull
    private String tenNguyenLieu;

    @NotNull
    private Long idNhom;

    @NotNull
    private Long idNCC;

    @NotNull
    private Long giaNhap;

    @NotNull
    private String moTa;

    @NotNull
    private String donViTinh;

    @NotNull
    private Long vAT;

    @NotNull
    private MultipartFile file;

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public Long getIdNCC() {
        return idNCC;
    }

    public void setIdNCC(Long idNCC) {
        this.idNCC = idNCC;
    }

    public Long getIdNhom() {
        return idNhom;
    }

    public void setIdNhom(Long idNhom) {
        this.idNhom = idNhom;
    }

    public Long getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(Long giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Long getvAT() {
        return vAT;
    }

    public void setvAT(Long vAT) {
        this.vAT = vAT;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
