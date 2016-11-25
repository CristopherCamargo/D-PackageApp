package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterProviderServiceTwo extends AppCompatActivity {

    @BindView(R.id.inputLicence)
    EditText inputLicence;
    @BindView(R.id.til_licence)
    TextInputLayout tilLicence;
    @BindView(R.id.inputNumContact)
    EditText inputNumContact;
    @BindView(R.id.til_NumContact)
    TextInputLayout tilNumContact;
    @BindView(R.id.inputEmail)
    EditText inputEmail;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.inputPassword)
    EditText inputPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.buttonContinue)
    Button buttonContinue;

    validatorInput validator = new validatorInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider_service_two);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

    }

    @OnClick(R.id.buttonContinue)
    public void checkCampos() {
        boolean nLicencse = validator.isValideLicense(inputLicence.getText().toString());
        boolean nContacto = validator.isValideNumber(inputNumContact.getText().toString());
        boolean email = validator.isvalideMail(inputEmail.getText().toString());
        boolean password = validator.isValidePassword(inputPassword.getText().toString());

        if (nLicencse && nContacto && email && password) {
            tilLicence.setError(null);
            tilNumContact.setError(null);
            tilEmail.setError(null);
            tilPassword.setError(null);
            launchActivityRegisterProviderServiceThree();
        } else {
            if (!nLicencse) {
                tilLicence.setError(getText(R.string.invalid_license));
            } else {
                tilLicence.setError(null);
            }

            if (!nContacto) {
                tilNumContact.setError(getString(R.string.invalid_contac));
            } else {
                tilNumContact.setError(null);
            }

            if (!email) {
                tilEmail.setError(getString(R.string.invalid_email));
            } else {
                tilEmail.setError(null);
            }

            if (!password) {
                tilPassword.setError(getString(R.string.invalid_password));
            } else {
                tilPassword.setError(null);
            }
        }

    }

    private void launchActivityRegisterProviderServiceThree() {
        Intent ActivityRegisterProviderServiceThree = new Intent(this, RegisterProviderServiceThree.class);
        startActivity(ActivityRegisterProviderServiceThree);
    }
}
