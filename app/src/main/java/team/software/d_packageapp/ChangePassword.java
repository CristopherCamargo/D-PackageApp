package team.software.d_packageapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
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
import team.software.connection.PostDataChangePassword;

public class ChangePassword extends AppCompatActivity {

    @BindView(R.id.inputCurrentPassword)
    EditText inputCurrentPassword;
    @BindView(R.id.til_CurrentPassword)
    TextInputLayout tilCurrentPassword;
    @BindView(R.id.inputNewPassword)
    EditText inputNewPassword;
    @BindView(R.id.til_NewPassword)
    TextInputLayout tilNewPassword;
    @BindView(R.id.inputVerifyPassword)
    EditText inputVerifyPassword;
    @BindView(R.id.til_VerifyPassword)
    TextInputLayout tilVerifyPassword;
    @BindView(R.id.buttonChange)
    Button buttonChange;

    ValidatorInput validator = new ValidatorInput();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @OnClick(R.id.buttonChange)
    public void checkFields() {
        boolean currentPassword = validator.isValidePassword(inputCurrentPassword.getText().toString());
        boolean newPassword = validator.isValidePassword(inputNewPassword.getText().toString());
        boolean verifyPassword = validator.isValidePassword(inputVerifyPassword.getText().toString());

        if (currentPassword && newPassword && verifyPassword) {
            boolean passwordEquals = validator.isValidaEqualPassword(inputNewPassword.getText().toString(),inputVerifyPassword.getText().toString());
            if (passwordEquals) {
                tilCurrentPassword.setError(null);
                tilNewPassword.setError(null);
                tilVerifyPassword.setError(null);
                launchChangePassword();
            } else {
                Toast.makeText(this, getString(R.string.password_not_matches), Toast.LENGTH_LONG).show();
                inputNewPassword.setText("");
                inputVerifyPassword.setText("");
                inputNewPassword.requestFocus();
            }

        } else {
            if (!currentPassword) {
                tilCurrentPassword.setError(getString(R.string.invalid_password));
            } else {
                tilCurrentPassword.setError(null);
            }

            if (!newPassword) {
                tilNewPassword.setError(getString(R.string.invalid_password));
            } else {
                tilNewPassword.setError(null);
            }

            if (!verifyPassword) {
                tilVerifyPassword.setError(getString(R.string.invalid_password));
            } else {
                tilVerifyPassword.setError(null);
            }
        }
    }

    private void launchChangePassword() {
        PostDataChangePassword changePassword = new PostDataChangePassword(inputNewPassword.getText().toString(), inputCurrentPassword.getText().toString());
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("D-package",Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("user_token",null);
        int id = sharedPreferences.getInt("user_id", 0);
        Toast.makeText(this, "Token "+token, Toast.LENGTH_SHORT).show();
        Call<ResponseBody> response = getInstance().changePassword("Token "+token, changePassword, id);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 204) {
                    finish();
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ChangePassword.this, jsonError.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
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
