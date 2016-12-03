package team.software.d_packageapp;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import team.software.connection.API;
import team.software.connection.Useraccount;

public class Recover_Password extends AppCompatActivity {

    @BindView(R.id.inputEmail)
    EditText inputEmail;
    @BindView(R.id.til_email)
    TextInputLayout til_email;
    @BindView(R.id.buttonRecover)
    Button buttonRecover;

    ValidatorInput validator = new ValidatorInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.buttonRecover)
    public void checkField() {
        boolean email = validator.isvalideMail(inputEmail.getText().toString());
        if (email) {
            til_email.setError(null);
            launchRecoverPassword();
        } else {
            til_email.setError(getString(R.string.invalid_email));
        }
    }

    private void launchRecoverPassword() {
        Useraccount useraccount = new Useraccount();
        useraccount.setEmail(inputEmail.getText().toString());
        Call<Useraccount> response = getInstance().forgotPassword(useraccount);
        response.enqueue(new Callback<Useraccount>() {
            @Override
            public void onResponse(Call<Useraccount> call, Response<Useraccount> response) {

            }

            @Override
            public void onFailure(Call<Useraccount> call, Throwable t) {

            }
        });

    }

    private API getInstance(){
        String BASE_URL = "http://api.d-packagebackend.edwarbaron.me/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        return api;
    }
}
