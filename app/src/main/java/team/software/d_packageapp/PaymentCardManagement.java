package team.software.d_packageapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import team.software.connection.API;
import team.software.connection.GetDataCards;

public class PaymentCardManagement extends AppCompatActivity {

    @BindView(R.id.addCard)
    FloatingActionButton addCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_card_management);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        loadCards();

    }

    private void loadCards() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("D-package", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("user_token",null);
        Call<GetDataCards> response = getInstance().getCards("Token "+token);
        response.enqueue(new Callback<GetDataCards>() {
            @Override
            public void onResponse(Call<GetDataCards> call, Response<GetDataCards> response) {
                if (response.code() ==200) {
                    GetDataCards obj = response.body();
                    if(obj.getCard() != null && obj.getCard().size() > 0){
                        Toast.makeText(PaymentCardManagement.this, ""+obj.getCard().get(0).getId(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetDataCards> call, Throwable t) {

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @OnClick(R.id.addCard)
    public void launchAddCard() {
        Intent intent = new Intent(this, RegisterPaymentMethod.class);
        startActivity(intent);
    }
}
