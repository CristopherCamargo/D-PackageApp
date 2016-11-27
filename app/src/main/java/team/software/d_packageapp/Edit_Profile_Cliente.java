package team.software.d_packageapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Edit_Profile_Cliente extends AppCompatActivity {

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

    validatorInput validator = new validatorInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_cliente);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonSave)
    public void checkFields() {
        boolean firstName = validator.isValideName(inputFirstName.getText().toString());
        boolean lastName = validator.isValideName(inputLastName.getText().toString());
        boolean birthDay = validator.isValideDate(inputDateOfBirth.getText().toString(),"dd/MM/yyyy");
        boolean numContact = validator.isValideNumber(inputNumContact.getText().toString());

        if (firstName && lastName && birthDay && numContact) {
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
