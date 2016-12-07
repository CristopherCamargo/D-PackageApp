package team.software.d_packageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.stripe.android.*;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import team.software.connection.API;
import team.software.connection.StripeToken;

import static java.security.AccessController.getContext;

public class RegisterPaymentMethod extends AppCompatActivity {

    @BindView(R.id.input_car_number)
    EditText inputCarNumber;
    @BindView(R.id.til_car_number)
    TextInputLayout tilCarNumber;
    @BindView(R.id.input_expire_day)
    EditText inputExpireDay;
    @BindView(R.id.til_expire_date)
    TextInputLayout tilExpireDate;
    @BindView(R.id.input_name_on_card)
    EditText inputNameOnCard;
    @BindView(R.id.til_name_on_card)
    TextInputLayout tilNameOnCard;
    @BindView(R.id.input_card_code)
    EditText inputCardCode;
    @BindView(R.id.til_card_code)
    TextInputLayout tilCardCode;
    @BindView(R.id.addCard)
    Button addCard;

    ValidatorInput validator = new ValidatorInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_payment_method);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @OnClick(R.id.addCard)
    public void checkFields() {

        boolean name = validator.isValideName(inputNameOnCard.getText().toString());
        boolean numberCard = validator.isValideNumberCard(inputCarNumber.getText().toString());
        boolean codeCard = validator.isValideCodeCard(inputCardCode.getText().toString());
        boolean expireCard = validator.isValideExpireDate(inputExpireDay.getText().toString());

        if (name && numberCard && codeCard && expireCard) {

            tilNameOnCard.setError(null);
            tilCardCode.setError(null);
            tilCarNumber.setError(null);
            tilExpireDate.setError(null);
            launchRegisterCard();
        } else {
            if (!name) {
                tilNameOnCard.setError(getString(R.string.invalid_fisrt_name));
            } else {
                tilNameOnCard.setError(null);
            }

            if (!codeCard) {
                tilCardCode.setError(getString(R.string.invalid_code_card));
            } else {
                tilCardCode.setError(null);
            }

            if (!numberCard) {
                tilCarNumber.setError(getString(R.string.invalid_number_card));
            } else {
                tilCarNumber.setError(null);
            }

            if (!expireCard) {
                tilExpireDate.setError(getString(R.string.invalid_expire_date));
            } else {
                tilExpireDate.setError(null);
            }
        }
    }

    private int formatExpireDate(String month, int i, int f) {
        if (i == 0)
            return Integer.parseInt(month.substring(i,f));
        else
            return Integer.parseInt("20"+month.substring(i,f));
    }

    private String formatNumberCard(String number){
        return  number.substring(0,4)+"-"+
                number.substring(4,8)+"-"+
                number.substring(8,12)+"-"+
                number.substring(12, number.length());
    }

    private void launchRegisterCard() {

        Card card = new Card(
                formatNumberCard(inputCarNumber.getText().toString()),
                formatExpireDate(inputExpireDay.getText().toString(),0,2),
                formatExpireDate(inputExpireDay.getText().toString(),3, inputExpireDay.getText().toString().length()),
                inputCardCode.getText().toString(),
                inputNameOnCard.getText().toString(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (!card.validateNumber()) {
            Toast.makeText(this, "Numero de tarjeta invalida", Toast.LENGTH_SHORT).show();
        };

        if (!card.validateCVC()) {
            Toast.makeText(this, "Codigo cvc invalido", Toast.LENGTH_SHORT).show();
        };

        if(!card.validateExpiryDate()) {
            Toast.makeText(this, "Fecha Experiracion Invalida", Toast.LENGTH_SHORT).show();
        }

        try {
            Stripe stripe = new Stripe("pk_test_Np2jMupIe57b2X3tJEye4lS6");
            stripe.createToken(card, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    Toast.makeText(RegisterPaymentMethod.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(Token token) {
                    sendTokenAPI(token.getId());
                }
            });
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }


    private void sendTokenAPI(String token) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("D-package", Context.MODE_PRIVATE);
        String user_token = sharedPreferences.getString("user_token",null);
        Log.e("token",user_token);
        Call<ResponseBody> response = getInstance().SaveToken("Token "+user_token, new StripeToken(token));
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(RegisterPaymentMethod.this, "Tarjeta Registrada Exitosamente", Toast.LENGTH_LONG).show();
                    try {
                        Log.e("body", response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Log.e("error", response.errorBody().string());
                        Toast.makeText(RegisterPaymentMethod.this, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private API getInstance() {
        String BASE_URL = "http://api.d-packagebackend.edwarbaron.me/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        return api;
    }

}
