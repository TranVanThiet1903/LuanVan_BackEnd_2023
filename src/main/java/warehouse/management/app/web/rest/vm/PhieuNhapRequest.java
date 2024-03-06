package warehouse.management.app.web.rest.vm;

import java.util.List;
import javax.validation.constraints.NotNull;
import warehouse.management.app.service.dto.ChiTietPhieuNhapDTO;

public class PhieuNhapRequest {

    @NotNull
    private Long phiShip;

    @NotNull
    private Long giamGia;

    @NotNull
    private Long tongTienThanhToan;

    //    @NotNull
    //    private Long tienNo;

    @NotNull
    private String ghiChu;

    @NotNull
    private Long idNCC;

    @NotNull
    private Long idKho;

    @NotNull
    private List<ChiTietPhieuNhapRequest> chiTietPhieuNhapList;

    public Long getIdNCC() {
        return idNCC;
    }

    public void setIdNCC(Long idNCC) {
        this.idNCC = idNCC;
    }

    public List<ChiTietPhieuNhapRequest> getChiTietPhieuNhapList() {
        return chiTietPhieuNhapList;
    }

    public void setChiTietPhieuNhapList(List<ChiTietPhieuNhapRequest> chiTietPhieuNhapList) {
        this.chiTietPhieuNhapList = chiTietPhieuNhapList;
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

    public Long getPhiShip() {
        return phiShip;
    }

    public void setPhiShip(Long phiShip) {
        this.phiShip = phiShip;
    }

    //    public Long getTienNo() {
    //        return tienNo;
    //    }
    //
    //    public void setTienNo(Long tienNo) {
    //        this.tienNo = tienNo;
    //    }

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
}
