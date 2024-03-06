package warehouse.management.app.web.rest.vm;

import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class PhieuNhapExcelRequest {

    @NotNull
    private Long phiShip;

    @NotNull
    private Long giamGia;

    @NotNull
    private Long tongTienThanhToan;

    @NotNull
    private Long tienNo;

    @NotNull
    private String ghiChu;

    @NotNull
    private Long idNCC;

    @NotNull
    private Long idKho;

    private MultipartFile file;

    public Long getTongTienThanhToan() {
        return tongTienThanhToan;
    }

    public void setTongTienThanhToan(Long tongTienThanhToan) {
        this.tongTienThanhToan = tongTienThanhToan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getTienNo() {
        return tienNo;
    }

    public void setTienNo(Long tienNo) {
        this.tienNo = tienNo;
    }

    public Long getPhiShip() {
        return phiShip;
    }

    public void setPhiShip(Long phiShip) {
        this.phiShip = phiShip;
    }

    public Long getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(Long giamGia) {
        this.giamGia = giamGia;
    }

    public Long getIdKho() {
        return idKho;
    }

    public void setIdKho(Long idKho) {
        this.idKho = idKho;
    }

    public Long getIdNCC() {
        return idNCC;
    }

    public void setIdNCC(Long idNCC) {
        this.idNCC = idNCC;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
