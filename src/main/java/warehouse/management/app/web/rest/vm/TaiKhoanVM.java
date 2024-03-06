package warehouse.management.app.web.rest.vm;

import javax.validation.constraints.NotNull;

public class TaiKhoanVM {

    @NotNull
    private String username;

    @NotNull
    private String token;

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
