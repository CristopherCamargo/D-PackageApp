package team.software.connection;

import com.google.gson.annotations.SerializedName;

public class Cards {

    @SerializedName("id") private String id;
    @SerializedName("cvc_check") private String cvc_check;
    @SerializedName("brand") private String brand;
    @SerializedName("exp_month") private int exp_month;
    @SerializedName("exp_year") private int exp_year;
    @SerializedName("funding") private String funding;
    @SerializedName("last4") private String last4;
    @SerializedName("name") private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCvc_check() {
        return cvc_check;
    }

    public void setCvc_check(String cvc_check) {
        this.cvc_check = cvc_check;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getExp_month() {
        return exp_month;
    }

    public void setExp_month(int exp_month) {
        this.exp_month = exp_month;
    }

    public int getExp_year() {
        return exp_year;
    }

    public void setExp_year(int exp_year) {
        this.exp_year = exp_year;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}