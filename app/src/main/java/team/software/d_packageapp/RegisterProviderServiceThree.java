package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterProviderServiceThree extends AppCompatActivity {

    @BindView(R.id.image_vehicle_one)
    ImageView imageVehicleOne;
    @BindView(R.id.image_vehicle_two)
    ImageView imageVehicleTwo;
    @BindView(R.id.image_vehicle_three)
    ImageView imageVehicleThree;
    @BindView(R.id.inputPlaca)
    EditText inputPlaca;
    @BindView(R.id.til_placa)
    TextInputLayout tilPlaca;
    @BindView(R.id.inputAno)
    EditText inputAno;
    @BindView(R.id.til_ano)
    TextInputLayout tilAno;
    @BindView(R.id.spinnerModelo)
    Spinner spinnerModelo;
    @BindView(R.id.spinnerCategoria)
    Spinner spinnerCategoria;
    @BindView(R.id.checkBoxTermCondition)
    CheckBox checkBoxTermCondition;
    @BindView(R.id.buttonRegistrar)
    Button buttonRegistrar;

    validatorInput validator = new validatorInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider_service_three);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
    }

    @OnClick(R.id.buttonRegistrar)
    public void checkCampos() {
        boolean plateNumber = validator.isValidePlateNumber(inputPlaca.getText().toString());
        boolean year = validator.isValideYear(inputAno.getText().toString());
        boolean check = checkBoxTermCondition.isChecked();
        boolean model = true; //spinnerModelo;
        boolean category = true; //spinnerCategoria;

        if (plateNumber && year && check && model && category) {
            tilPlaca.setError(null);
            tilAno.setError(null);
            launchRegister();
        } else {
            if (!plateNumber) {
                tilPlaca.setError(getString(R.string.invalid_plateNumber));
            } else {
                tilPlaca.setError(null);
            }

            if (!year) {
                tilAno.setError(getString(R.string.invalid_year));
            } else {
                tilAno.setError(null);
            }

            if (!check) {
                Toast.makeText(this, getString(R.string.term_condition), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchRegister() {
        Toast.makeText(this, "Aqui Intento registrar", Toast.LENGTH_SHORT).show();
    }
}
