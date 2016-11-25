package team.software.d_packageapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    validatorInput validator = new validatorInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonChange)
    public void checkFields() {
        boolean currentPassword = validator.isValidePassword(inputCurrentPassword.getText().toString());
        boolean newPassword = validator.isValidePassword(inputNewPassword.getText().toString());
        boolean verifyPassword = validator.isValidePassword(inputVerifyPassword.getText().toString());

        if (currentPassword && newPassword && verifyPassword) {
            boolean passwordEquals = validator.isValidaEqualPassword(inputNewPassword.getText().toString(),inputVerifyPassword.getText().toString());
            if (passwordEquals) {
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
        Toast.makeText(this, "Intento cambiar contraseña", Toast.LENGTH_SHORT).show();
    }
}
