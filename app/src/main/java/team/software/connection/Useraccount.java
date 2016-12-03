package team.software.connection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Useraccount implements Serializable{
    @SerializedName("first_name") String first_name;
    @SerializedName("last_name") String last_name;
    @SerializedName("email") String email;
    @SerializedName("password") String password;

    public Useraccount(String fn, String ls, String em, String pass) {
        this.first_name = fn;
        this.last_name = ls;
        this.email = em;
        this.password = pass;
    }

    public Useraccount(){}

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}