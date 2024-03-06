package warehouse.management.app.web.rest.vm;

import java.util.List;
import javax.validation.constraints.NotNull;

public class DonXuatRequest {

    //    @NotNull
    //    private String ghiChu;

    //    @NotNull
    //    private Long idNCC;

    @NotNull
    private Long idKho;

    @NotNull
    private List<ChiTietPhieuNhapRequest> chiTietPhieuNhapRequestList;

    //    public Long getIdNCC() {
    //        return idNCC;
    //    }
    //
    //    public void setIdNCC(Long idNCC) {
    //        this.idNCC = idNCC;
    //    }

    //    public String getGhiChu() {
    //        return ghiChu;
    //    }
    //
    //    public void setGhiChu(String ghiChu) {
    //        this.ghiChu = ghiChu;
    //    }

    public Long getIdKho() {
        return idKho;
    }

    public void setIdKho(Long idKho) {
        this.idKho = idKho;
    }

    public List<ChiTietPhieuNhapRequest> getChiTietPhieuNhapRequestList() {
        return chiTietPhieuNhapRequestList;
    }

    public void setChiTietPhieuNhapRequestList(List<ChiTietPhieuNhapRequest> chiTietPhieuNhapRequestList) {
        this.chiTietPhieuNhapRequestList = chiTietPhieuNhapRequestList;
    }
}
