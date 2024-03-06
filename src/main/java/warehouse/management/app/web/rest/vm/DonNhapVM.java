package warehouse.management.app.web.rest.vm;

import java.util.List;
import javax.validation.constraints.NotNull;
import warehouse.management.app.domain.ChiTietDonNhap;
import warehouse.management.app.service.dto.DonNhapDTO;

public class DonNhapVM {

    @NotNull
    private DonNhapDTO donNhapDTO;

    @NotNull
    private List<ChiTietDonNhap> chiTietDonNhapList;

    public DonNhapDTO getDonNhapDTO() {
        return donNhapDTO;
    }

    public void setChiTietDonNhapList(List<ChiTietDonNhap> chiTietDonNhapList) {
        this.chiTietDonNhapList = chiTietDonNhapList;
    }

    public List<ChiTietDonNhap> getChiTietDonNhapList() {
        return chiTietDonNhapList;
    }

    public void setDonNhapDTO(DonNhapDTO donNhapDTO) {
        this.donNhapDTO = donNhapDTO;
    }
}
