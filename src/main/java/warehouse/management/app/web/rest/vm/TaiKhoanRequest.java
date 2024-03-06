package warehouse.management.app.web.rest.vm;

import javax.validation.constraints.NotNull;

public class TaiKhoanRequest {

    @NotNull
    private String selectedRole;

    @NotNull
    private String hoTen;

    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }
}
