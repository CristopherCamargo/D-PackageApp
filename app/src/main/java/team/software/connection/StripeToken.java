package team.software.connection;


import com.google.gson.annotations.SerializedName;

public class StripeToken {
    @SerializedName("stripe_token") private String stripe_token;

    public StripeToken(String stripe_token) {
        this.stripe_token = stripe_token;
    }

    public String getStripe_token() {
        return stripe_token;
    }

    public void setStripe_token(String stripe_token) {
        this.stripe_token = stripe_token;
    }
}
