package warehouse.management.app.web.rest.vm;

import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class DonXuatExcelRequest {

    //    @NotNull
    private String ghiChu;

    @NotNull
    private Long idKho;

    @NotNull
    private MultipartFile file;

    public String getGhiChu() {
        return ghiChu;
    }

    public void setIdKho(Long idKho) {
        this.idKho = idKho;
    }

    public Long getIdKho() {
        return idKho;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
