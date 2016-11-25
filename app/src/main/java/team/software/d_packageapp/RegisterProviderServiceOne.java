package team.software.d_packageapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterProviderServiceOne extends AppCompatActivity {

    @BindView(R.id.imagePerfil)
    ImageView imagePerfil;
    @BindView(R.id.inputFirstName)
    EditText inputFirstName;
    @BindView(R.id.til_firstName)
    TextInputLayout tilFirstName;
    @BindView(R.id.inputLastName)
    EditText inputLastName;
    @BindView(R.id.til_lastName)
    TextInputLayout tilLastName;
    @BindView(R.id.inputIdNumber)
    EditText inputIdNumber;
    @BindView(R.id.til_idNumber)
    TextInputLayout tilIdNumber;
    @BindView(R.id.inputAddress)
    EditText inputAddress;
    @BindView(R.id.til_address)
    TextInputLayout tilAddress;
    @BindView(R.id.buttonContinue)
    Button buttonContinue;
    @BindView(R.id.inputDateOfBirth)
    EditText inputDateOfBirth;
    @BindView(R.id.til_dateOfBirth)
    TextInputLayout tilDateOfBirth;

    validatorInput validator = new validatorInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider_service_one);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.buttonContinue)
    public void launchStepTwo() {
        checkCampos();
    }

    @OnClick(R.id.imagePerfil)
    public void launchGellery() {
        Toast.makeText(this, "Seleccionar Imagen", Toast.LENGTH_SHORT).show();
    }

    private void checkCampos() {
        boolean nombre = validator.isValideName(inputFirstName.getText().toString());
        boolean apellido = validator.isValideName(inputLastName.getText().toString());
        boolean birthday = validator.isValideDate(inputDateOfBirth.getText().toString(),"dd/MM/yyyy");
        boolean idnumber = validator.isValideNumber(inputIdNumber.getText().toString());
        boolean address = validator.isValideAddress(inputAddress.getText().toString());

        if (nombre && apellido && birthday && address) {
            launchActivityRegisterProviderServiceTwo();
        } else {
            if (!nombre)
                tilFirstName.setError(getString(R.string.invalid_fisrt_name));
            else
                tilFirstName.setError(null);

            if (!apellido)
                tilLastName.setError(getString(R.string.invalid_last_name));
            else
                tilLastName.setError(null);

            if (!birthday) {
                tilDateOfBirth.setError(getString(R.string.invalid_birtday));
            } else {
                tilDateOfBirth.setError(null);
            }

            if (!idnumber) {
                tilIdNumber.setError(getText(R.string.invalid_idnumber));
            } else {
                tilIdNumber.setError(null);
            }

            if (!address) {
                tilAddress.setError(getText(R.string.invalid_address));
            } else {
                tilAddress.setError(null);
            }
        }

    }

    private void launchActivityRegisterProviderServiceTwo() {
        Intent ActivityRegisterProviderServiceTwo = new Intent(this, RegisterProviderServiceTwo.class);
        startActivity(ActivityRegisterProviderServiceTwo);
    }


}
