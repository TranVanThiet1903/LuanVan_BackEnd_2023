package warehouse.management.app.web.rest.vm;

import java.util.List;
import java.util.Optional;
import warehouse.management.app.domain.ChiTietKho;
import warehouse.management.app.service.dto.NhaKhoDTO;

public class NhaKhoVM {

    private Optional<NhaKhoDTO> nhaKhoDTO;

    private List<ChiTietKho> chiTietKhoDTOList;

    public Optional<NhaKhoDTO> getNhaKhoDTO() {
        return nhaKhoDTO;
    }

    public void setNhaKhoDTO(Optional<NhaKhoDTO> nhaKhoDTO) {
        this.nhaKhoDTO = nhaKhoDTO;
    }

    public List<ChiTietKho> getChiTietKhoDTOList() {
        return chiTietKhoDTOList;
    }

    public void setChiTietKhoDTOList(List<ChiTietKho> chiTietKhoDTOList) {
        this.chiTietKhoDTOList = chiTietKhoDTOList;
    }
}
