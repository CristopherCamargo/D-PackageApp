package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import team.software.connection.API;
import team.software.connection.PostDataRegisterClient;
import team.software.connection.Useraccount;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterClient extends AppCompatActivity {

    @BindView(R.id.inputFirstName)
    EditText inputFirstName;
    @BindView(R.id.inputLastName)
    EditText inputLastName;
    @BindView(R.id.inputEmail)
    EditText inputEmail;
    @BindView(R.id.inputPassword)
    EditText inputPassword;
    @BindView(R.id.checkBoxTermCondition)
    CheckBox checkBoxTermCondition;
    @BindView(R.id.buttonRegistrar)
    Button buttonRegistrar;
    @BindView(R.id.til_firstName)
    TextInputLayout tilFirstName;
    @BindView(R.id.til_lastName)
    TextInputLayout tilLastName;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.inputPhone)
    EditText inputPhone;
    @BindView(R.id.til_phone)
    TextInputLayout tilPhone;

    ValidatorInput validator = new ValidatorInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.buttonRegistrar)
    public void checkCampos() {
        boolean nombre = validator.isValideName(inputFirstName.getText().toString());
        boolean apellido = validator.isValideName(inputLastName.getText().toString());
        boolean email = validator.isvalideMail(inputEmail.getText().toString());
        boolean password = validator.isValidePassword(inputPassword.getText().toString());
        boolean terminos = checkBoxTermCondition.isChecked();
        boolean phone = validator.isValideNumber(inputPhone.getText().toString());

        if (nombre && apellido && email && password && terminos) {
            tilFirstName.setError(null);
            tilLastName.setError(null);
            tilEmail.setError(null);
            tilPassword.setError(null);
            tilPhone.setError(null);
            launchRegisterClient();
        } else {
            if (!nombre)
                tilFirstName.setError(getString(R.string.invalid_fisrt_name));
            else
                tilFirstName.setError(null);

            if (!apellido)
                tilLastName.setError(getString(R.string.invalid_last_name));
            else
                tilLastName.setError(null);

            if (!email)
                tilEmail.setError(getString(R.string.invalid_email));
            else
                tilEmail.setError(null);

            if (!password)
                tilPassword.setError(getString(R.string.invalid_password));
            else
                tilPassword.setError(null);

            if (!phone)
                tilPhone.setError(getString(R.string.invalid_phone));
            else
                tilPhone.setError(null);

            if (!terminos) {
                Toast.makeText(this, getString(R.string.invalid_conditions), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchRegisterClient() {
        Useraccount useraccount =
                new Useraccount(
                        inputFirstName.getText().toString(),
                        inputLastName.getText().toString(),
                        inputEmail.getText().toString(),
                        inputPassword.getText().toString());

        PostDataRegisterClient postDataRegisterClient =
                new PostDataRegisterClient(
                        useraccount,
                        inputPhone.getText().toString()
                );

        String BASE_URL = "http://api.d-packagebackend.edwarbaron.me/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<PostDataRegisterClient> response = api.registerClient(postDataRegisterClient);
        response.enqueue(new Callback<PostDataRegisterClient>() {
            @Override
            public void onResponse(Call<PostDataRegisterClient> call, Response<PostDataRegisterClient> response) {
                if (response.code() == 201) {
                    Toast.makeText(RegisterClient.this, getString(R.string.register_completed), Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(RegisterClient.this, Login.class);
                    startActivity(login);
                } else {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());

                        if (jsonError.getJSONObject("useraccount").getString("email") != null) {
                            tilEmail.setError(getString(R.string.invalid_email_exist));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<PostDataRegisterClient> call, Throwable t) {

            }
        });

    }

}
