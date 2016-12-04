package team.software.d_packageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import team.software.connection.API;
import team.software.connection.PostDataRegisterClient;

public class EditProfileCliente extends AppCompatActivity {

    @BindView(R.id.imageProfileClient)
    ImageView imageProfileClient;
    @BindView(R.id.inputFirstName)
    EditText inputFirstName;
    @BindView(R.id.til_firstName)
    TextInputLayout tilFirstName;
    @BindView(R.id.inputLastName)
    EditText inputLastName;
    @BindView(R.id.til_lastName)
    TextInputLayout tilLastName;
    @BindView(R.id.inputDateOfBirth)
    EditText inputDateOfBirth;
    @BindView(R.id.til_dateOfBirth)
    TextInputLayout tilDateOfBirth;
    @BindView(R.id.inputNumContact)
    EditText inputNumContact;
    @BindView(R.id.til_NumContact)
    TextInputLayout tilNumContact;
    @BindView(R.id.buttonSave)
    Button buttonSave;

    ValidatorInput validator = new ValidatorInput();
    SharedPreferences sharedPref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_cliente);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadFields();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void loadFields() {
        sharedPref =  getApplicationContext().getSharedPreferences("D-package", Context.MODE_PRIVATE);
        String token = sharedPref.getString("user_token","");
        int pk = sharedPref.getInt("user_id",0);

        Call<PostDataRegisterClient> response = getInstance().getProfileClient("Token "+token, pk);
        response.enqueue(new Callback<PostDataRegisterClient>() {
            @Override
            public void onResponse(Call<PostDataRegisterClient> call, Response<PostDataRegisterClient> response) {
                if (response.code() == 200) {
                    inputFirstName.setText(response.body().getUseraccount().getFirst_name());
                    inputLastName.setText(response.body().getUseraccount().getLast_name());
                    inputNumContact.setText(response.body().getPhone());
                } else {
                    try {
                        Log.e("Error: ", response.errorBody().string());
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

    private API getInstance() {
        String BASE_URL = "http://api.d-packagebackend.edwarbaron.me/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        return api;
    }

    @OnClick(R.id.buttonSave)
    public void checkFields() {
        boolean firstName = validator.isValideName(inputFirstName.getText().toString());
        boolean lastName = validator.isValideName(inputLastName.getText().toString());
        boolean birthDay = validator.isValideDate(inputDateOfBirth.getText().toString(),"dd/MM/yyyy");
        boolean numContact = validator.isValideNumber(inputNumContact.getText().toString());

        if (firstName && lastName && birthDay && numContact) {
            tilFirstName.setError(null);
            tilLastName.setError(null);
            tilDateOfBirth.setError(null);
            tilNumContact.setError(null);
            launchSave();
        } else {
            if (!firstName) {
                tilFirstName.setError(getString(R.string.invalid_fisrt_name));
            } else {
                tilFirstName.setError(null);
            }

            if (!lastName) {
                tilLastName.setError(getString(R.string.invalid_last_name));
            } else {
                tilLastName.setError(null);
            }

            if (!birthDay) {
                tilDateOfBirth.setError(getString(R.string.invalid_birtday));
            } else {
                tilDateOfBirth.setError(null);
            }

            if (!numContact) {
                tilNumContact.setError(getString(R.string.invalid_contac));
            } else {
                tilNumContact.setError(null);
            }
        }
    }

    private void launchSave() {
        Toast.makeText(this, "Guarda los datos", Toast.LENGTH_SHORT).show();
    }
}
