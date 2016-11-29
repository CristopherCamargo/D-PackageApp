package team.software.d_packageapp;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.BindView;

public class Recover_Password extends AppCompatActivity {

    @BindView(R.id.inputEmail)
    TextView inputEmail;
    @BindView(R.id.buttonRecover)
    Button buttonRecover;
    @BindView(R.id.til_email)
    TextInputLayout til_email;

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
            launchRecoverPassword();
            til_email.setError(null);
        } else {
            til_email.setError(getString(R.string.invalid_email));
        }
    }

    private void launchRecoverPassword() {
        Toast.makeText(this, "Recupero la clave", Toast.LENGTH_SHORT).show();
    }
}
