package warehouse.management.app.web.rest.vm;

import javax.validation.constraints.NotNull;
import warehouse.management.app.service.dto.DonXuatDTO;

public class DonXuatVM2 {

    @NotNull
    private DonXuatDTO donXuatDTO;

    //    @NotNull
    private String nguoiXacNhanDon;

    public String getNguoiXacNhanDon() {
        return nguoiXacNhanDon;
    }

    public void setNguoiXacNhanDon(String nguoiXacNhanDon) {
        this.nguoiXacNhanDon = nguoiXacNhanDon;
    }

    public DonXuatDTO getDonXuatDTO() {
        return donXuatDTO;
    }

    public void setDonXuatDTO(DonXuatDTO donXuatDTO) {
        this.donXuatDTO = donXuatDTO;
    }
}
