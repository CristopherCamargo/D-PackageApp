package team.software.connection;

import com.google.gson.annotations.SerializedName;

import team.software.d_packageapp.RegisterClient;

public class PostDataRegisterClient {
    @SerializedName("useraccount") Useraccount useraccount;
    @SerializedName("phone") String phone;

    public PostDataRegisterClient(Useraccount user, String ph) {
        this.useraccount = user;
        this.phone = ph;
    }

    public Useraccount getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(Useraccount useraccount) {
        this.useraccount = useraccount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}