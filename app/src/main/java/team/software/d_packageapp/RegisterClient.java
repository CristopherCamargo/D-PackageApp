package team.software.d_packageapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
    public void onClick() {
        checkCampos();
    }

    private void checkCampos() {
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
            Toast.makeText(this, "Aqui registro", Toast.LENGTH_SHORT).show();
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
}
