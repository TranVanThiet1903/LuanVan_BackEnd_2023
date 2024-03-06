package warehouse.management.app.web.rest.vm;

import javax.validation.constraints.NotNull;

public class DisableAccRequest {

    //    @NotNull
    //    private String hoTen;

    @NotNull
    private String username;

    @NotNull
    private String confirmPass;

    public String getUsername() {
        return username;
    }

    //    public String getHoTen() {
    //        return hoTen;
    //    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //    public void setHoTen(String hoTen) {
    //        this.hoTen = hoTen;
    //    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
}
