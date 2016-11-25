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

public class RegisterBankDetails extends AppCompatActivity {

    @BindView(R.id.inputNameBank)
    EditText inputNameBank;
    @BindView(R.id.til_NameBank)
    TextInputLayout tilNameBank;
    @BindView(R.id.inputTypeAccount)
    EditText inputTypeAccount;
    @BindView(R.id.til_TypeAccount)
    TextInputLayout tilTypeAccount;
    @BindView(R.id.inputNumberAccount)
    EditText inputNumberAccount;
    @BindView(R.id.til_NumberAccount)
    TextInputLayout tilNumberAccount;
    @BindView(R.id.inputAccountOwner)
    EditText inputAccountOwner;
    @BindView(R.id.til_AccountOwner)
    TextInputLayout tilAccountOwner;
    @BindView(R.id.inputIdNumber)
    EditText inputIdNumber;
    @BindView(R.id.til_idNumber)
    TextInputLayout tilIdNumber;
    @BindView(R.id.buttonRegistrar)
    Button buttonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bank_details);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonRegistrar)
    public void checkFields() {

    }

    private void launchRegisterAccountBanck() {
        Toast.makeText(this, "Registro mi Cuenta", Toast.LENGTH_SHORT).show();
    }
}
