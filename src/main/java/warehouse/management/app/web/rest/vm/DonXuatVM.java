package warehouse.management.app.web.rest.vm;

import java.util.List;
import javax.validation.constraints.NotNull;
import warehouse.management.app.domain.ChiTietDonXuat;
import warehouse.management.app.service.dto.DonXuatDTO;

public class DonXuatVM {

    @NotNull
    private DonXuatDTO donXuatDTO;

    @NotNull
    private List<ChiTietDonXuat> chiTietDonXuatList;

    public DonXuatDTO getDonXuatDTO() {
        return donXuatDTO;
    }

    public void setChiTietDonXuatList(List<ChiTietDonXuat> chiTietDonXuatList) {
        this.chiTietDonXuatList = chiTietDonXuatList;
    }

    public List<ChiTietDonXuat> getChiTietDonXuatList() {
        return chiTietDonXuatList;
    }

    public void setDonXuatDTO(DonXuatDTO donXuatDTO) {
        this.donXuatDTO = donXuatDTO;
    }
}
