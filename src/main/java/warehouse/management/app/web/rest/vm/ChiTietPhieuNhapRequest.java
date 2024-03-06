package warehouse.management.app.web.rest.vm;

import javax.validation.constraints.NotNull;

public class ChiTietPhieuNhapRequest {

    @NotNull
    private Long idNguyenLieu;

    @NotNull
    private Long soLuong;

    public Long getIdNguyenLieu() {
        return idNguyenLieu;
    }

    public void setIdNguyenLieu(Long idNguyenLieu) {
        this.idNguyenLieu = idNguyenLieu;
    }

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }
}
