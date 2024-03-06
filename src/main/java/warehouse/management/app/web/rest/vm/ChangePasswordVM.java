package warehouse.management.app.web.rest.vm;

import javax.validation.constraints.NotNull;

public class ChangePasswordVM {

    @NotNull
    private String currentPassword;

    @NotNull
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
