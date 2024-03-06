package warehouse.management.app.web.rest.vm;

import java.util.List;
import javax.validation.constraints.NotNull;

public class DonNhapRequest {

    @NotNull
    private Long idPhieuNhap;

    @NotNull
    private Long tongTienThanhToan;

    @NotNull
    private Long tienNo;

    public Long getIdPhieuNhap() {
        return idPhieuNhap;
    }

    public void setIdPhieuNhap(Long idPhieuNhap) {
        this.idPhieuNhap = idPhieuNhap;
    }

    public Long getTongTienThanhToan() {
        return tongTienThanhToan;
    }

    public void setTongTienThanhToan(Long tongTienThanhToan) {
        this.tongTienThanhToan = tongTienThanhToan;
    }

    public Long getTienNo() {
        return tienNo;
    }

    public void setTienNo(Long tienNo) {
        this.tienNo = tienNo;
    }
}
