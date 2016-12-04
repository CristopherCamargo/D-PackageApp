package team.software.connection;

import com.google.gson.annotations.SerializedName;

public class logout {
    @SerializedName("token") private String token;

    public logout(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
