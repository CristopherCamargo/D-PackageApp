package team.software.d_packageapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBankDetails extends AppCompatActivity {

    @BindView(R.id.radio_savingsAccount)
    RadioButton radioSavingsAccount;
    @BindView(R.id.radio_currentAccount)
    RadioButton radioCurrentAccount;
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
    @BindView(R.id.spinnerBanks)
    AppCompatSpinner spinnerBanks;
    @BindView(R.id.textErrorBank)
    TextView textErrorBank;
    @BindView(R.id.radioErrorTypeAccount)
    TextView radioErrorTypeAccount;

    ValidatorInput validator = new ValidatorInput();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bank_details);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonRegistrar)
    public void checkFields() {
        boolean bank = validator.isValideBank(spinnerBanks.getSelectedItem().toString(), getString(R.string.selection));
        boolean currentAccount = radioCurrentAccount.isChecked();
        boolean savingAccount = radioSavingsAccount.isChecked();
        boolean numAccount = validator.isValideAccount(inputNumberAccount.getText().toString());
        boolean ownerAccount = validator.isValideName(inputAccountOwner.getText().toString());
        boolean idNumber = validator.isValideNumber(inputIdNumber.getText().toString());

        Log.e("Valores", bank + " " + currentAccount + " " + savingAccount + " " + numAccount + " " + ownerAccount + " " + idNumber);

        if (bank && (currentAccount || savingAccount) && numAccount && ownerAccount && idNumber) {
            textErrorBank.setVisibility(View.INVISIBLE);
            radioErrorTypeAccount.setVisibility(View.INVISIBLE);
            tilNumberAccount.setError(null);
            tilAccountOwner.setError(null);
            tilIdNumber.setError(null);
            launchRegisterAccountBank();
        } else {
            if (!bank) {
                textErrorBank.setVisibility(View.VISIBLE);
            } else {
                textErrorBank.setVisibility(View.INVISIBLE);
            }
            if (!currentAccount && !savingAccount) {
                radioErrorTypeAccount.setVisibility(View.VISIBLE);
            } else {
                radioErrorTypeAccount.setVisibility(View.INVISIBLE);
            }

            if (!numAccount) {
                tilNumberAccount.setError(getString(R.string.invalid_number_account));
            } else {
                tilNumberAccount.setError(null);
            }

            if (!ownerAccount) {
                tilAccountOwner.setError(getString(R.string.invalid_owner_account));
            } else {
                tilAccountOwner.setError(null);
            }

            if (!idNumber) {
                tilIdNumber.setError(getString(R.string.invalid_idnumber));
            } else {
                tilIdNumber.setError(null);
            }
        }
    }

    private void launchRegisterAccountBank() {
        Toast.makeText(this, "registro la cuenta", Toast.LENGTH_SHORT).show();
    }


}
