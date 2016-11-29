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

public class EditProfileProviderService extends AppCompatActivity {

    @BindView(R.id.imageProfileProvider)
    ImageView imageProfileProvider;
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
    @BindView(R.id.inputIdNumber)
    EditText inputIdNumber;
    @BindView(R.id.til_idNumber)
    TextInputLayout tilIdNumber;
    @BindView(R.id.inputAddress)
    EditText inputAddress;
    @BindView(R.id.til_address)
    TextInputLayout tilAddress;
    @BindView(R.id.inputLicence)
    EditText inputLicence;
    @BindView(R.id.til_licence)
    TextInputLayout tilLicence;
    @BindView(R.id.inputNumContact)
    EditText inputNumContact;
    @BindView(R.id.til_NumContact)
    TextInputLayout tilNumContact;
    @BindView(R.id.buttonSave)
    Button buttonSave;

    ValidatorInput validator = new ValidatorInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_provider_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonSave)
    public void checkFields(){
        boolean nombre = validator.isValideName(inputFirstName.getText().toString());
        boolean apellido = validator.isValideName(inputLastName.getText().toString());
        boolean birthday = validator.isValideDate(inputDateOfBirth.getText().toString(),"dd/MM/yyyy");
        boolean idnumber = validator.isValideNumber(inputIdNumber.getText().toString());
        boolean address = validator.isValideAddress(inputAddress.getText().toString());

        boolean nLicencse = validator.isValideLicense(inputLicence.getText().toString());
        boolean nContacto = validator.isValideNumber(inputNumContact.getText().toString());

        if (nombre && apellido && birthday && address && nLicencse && nContacto) {
            tilFirstName.setError(null);
            tilLastName.setError(null);
            tilLicence.setError(null);
            tilNumContact.setError(null);
            tilDateOfBirth.setError(null);
            tilIdNumber.setError(null);
            tilAddress.setError(null);
            launchSaveProfile();
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
        }
    }

    private void launchSaveProfile() {
        Toast.makeText(this, "Aqui Guardo", Toast.LENGTH_SHORT).show();
    }
}
