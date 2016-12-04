package team.software.connection;

import com.google.gson.annotations.SerializedName;

public class PostDataChangePassword {
    @SerializedName("new_password") private String new_password;
    @SerializedName("old_password") private String old_password;

    public PostDataChangePassword(String new_password, String old_password) {
        this.new_password = new_password;
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }
}
