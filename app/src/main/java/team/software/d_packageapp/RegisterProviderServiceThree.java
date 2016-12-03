package team.software.d_packageapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import net.margaritov.preference.colorpicker.ColorPickerPreference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import team.software.connection.API;
import team.software.connection.GetDataVehicleCategory;
import team.software.connection.GetDataVehicleModel;
import team.software.connection.PostDataRegisterProvider;
import team.software.connection.Vehicle;
import team.software.connection.VehicleCategory;
import team.software.connection.VehicleModel;

public class RegisterProviderServiceThree extends AppCompatActivity {

    @BindView(R.id.image_vehicle_one)
    ImageView imageVehicleOne;
    @BindView(R.id.inputPlaca)
    EditText inputPlaca;
    @BindView(R.id.til_placa)
    TextInputLayout tilPlaca;
    @BindView(R.id.inputColor)
    EditText inputColor;
    @BindView(R.id.til_color)
    TextInputLayout tilColor;
    @BindView(R.id.spinnerModelo)
    Spinner spinnerModelo;
    @BindView(R.id.spinnerCategoria)
    Spinner spinnerCategoria;
    @BindView(R.id.checkBoxTermCondition)
    CheckBox checkBoxTermCondition;
    @BindView(R.id.buttonRegistrar)
    Button buttonRegistrar;
    @BindView(R.id.tilModel)
    TextView tilModel;
    @BindView(R.id.tilCategory)
    TextView tilCategory;


    private String APP_DIRECTORY = "D-packageApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    String category;
    String model;

    private PostDataRegisterProvider postDataRegisterProvider;

    ValidatorInput validator = new ValidatorInput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider_service_three);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        postDataRegisterProvider = (PostDataRegisterProvider) getIntent().getExtras().getSerializable("parametro");
        loadCategory();
        loadModel();

    }

    private API getInstance() {
        String BASE_URL = "http://api.d-packagebackend.edwarbaron.me/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        return api;
    }

    private void loadCategory() {

        Call<GetDataVehicleCategory> response = getInstance().vehicleCategory();
        response.enqueue(new Callback<GetDataVehicleCategory>() {
            @Override
            public void onResponse(Call<GetDataVehicleCategory> call, Response<GetDataVehicleCategory> response) {
                if (response.code() == 200) {
                    List<VehicleCategory> listCategorias = response.body().getVehiclecategory();
                    String[] categorias = new String[listCategorias.size() + 1];
                    categorias[0] = "Ninguna";
                    for (int i = 0; i < listCategorias.size(); i++) {
                        categorias[i + 1] = listCategorias.get(i).getValue();
                    }

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(RegisterProviderServiceThree.this, android.R.layout.simple_spinner_item, categorias);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategoria.setAdapter(spinnerAdapter);
                    listenerSpinnerCategory();
                }
            }

            @Override
            public void onFailure(Call<GetDataVehicleCategory> call, Throwable t) {
                Log.e("body: ", t.getLocalizedMessage());
            }
        });
    }

    private void loadModel() {
        Call<GetDataVehicleModel> response = getInstance().vehicleModel();
        response.enqueue(new Callback<GetDataVehicleModel>() {
            @Override
            public void onResponse(Call<GetDataVehicleModel> call, Response<GetDataVehicleModel> response) {
                if (response.code() == 200) {
                    List<VehicleModel> listModels = response.body().getModel();
                    String[] models = new String[listModels.size() + 1];
                    models[0] = "Ninguna";
                    for (int i = 0; i < listModels.size(); i++) {
                        models[i + 1] = listModels.get(i).getValue();
                    }

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(RegisterProviderServiceThree.this, android.R.layout.simple_spinner_item, models);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerModelo.setAdapter(spinnerAdapter);
                    listenerSpinnerModel();
                }
            }

            @Override
            public void onFailure(Call<GetDataVehicleModel> call, Throwable t) {
                Log.e("body: ", t.getLocalizedMessage());
            }
        });
    }

    private void listenerSpinnerCategory() {
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = "" + id;
                Toast.makeText(RegisterProviderServiceThree.this, "" + id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void listenerSpinnerModel() {
        spinnerModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model = "" + id;
                Toast.makeText(RegisterProviderServiceThree.this, "" + id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @OnClick(R.id.inputColor)
    public void launchColors() {
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(RegisterProviderServiceThree.this, 255);
        colorPickerDialog.setAlphaSliderVisible(true);
        colorPickerDialog.show();

        colorPickerDialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                inputColor.setText(ColorPickerPreference.convertToRGB(color));
            }
        });


    }

    @OnClick(R.id.buttonRegistrar)
    public void checkCampos() {
        boolean plateNumber = validator.isValidePlateNumber(inputPlaca.getText().toString());
        boolean year = validator.isValideColor(inputColor.getText().toString());
        boolean check = checkBoxTermCondition.isChecked();
        boolean modelos = !model.equals("0"); //spinnerModelo;
        boolean categorias = !category.equals("0"); //spinnerCategoria;

        if (plateNumber && year && check && modelos && categorias) {
            tilPlaca.setError(null);
            tilColor.setError(null);
            tilCategory.setText(null);
            tilModel.setText(null);
            launchRegister();
        } else {
            if (!plateNumber) {
                tilPlaca.setError(getString(R.string.invalid_plateNumber));
            } else {
                tilPlaca.setError(null);
            }

            if (!year) {
                tilColor.setError(getString(R.string.invalid_year));
            } else {
                tilColor.setError(null);
            }

            if (!categorias) {
                tilCategory.setText(getText(R.string.select_catetegory));
            } else {
                tilCategory.setText(null);
            }

            if (!modelos) {
                tilModel.setText(getText(R.string.select_model));
            } else {
                tilModel.setText(null);
            }

            if (!check) {
                Toast.makeText(this, getString(R.string.term_condition), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void launchRegister() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(inputPlaca.getText().toString());
        vehicle.setColor(inputColor.getText().toString());
        vehicle.setCategory(category);
        vehicle.setModel(model);
        postDataRegisterProvider.setVehicle(vehicle);

        Call<PostDataRegisterProvider> response = getInstance().registerProvider(postDataRegisterProvider);
        response.enqueue(new Callback<PostDataRegisterProvider>() {
            @Override
            public void onResponse(Call<PostDataRegisterProvider> call, Response<PostDataRegisterProvider> response) {
                if (response.code() == 201) {
                    Toast.makeText(RegisterProviderServiceThree.this, getString(R.string.register_completed), Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(RegisterProviderServiceThree.this, Login.class);
                    startActivity(login);
                } else {

//                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                    try {
                        Log.e("Result", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<PostDataRegisterProvider> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.image_vehicle_one)
    public void launchGallery() {
        final CharSequence[] options = {"Tomar Foto", "Elegir de Galeria", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProviderServiceThree.this);
        builder.setTitle("Elije una opcion");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selection) {
                if (selection == 0) {
                    openCamera();
                } else if (selection == 1) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Abrir con"), SELECT_PICTURE);
                } else
                    dialog.dismiss();
            }
        });
        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdir();

        String path = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
        File newfile = new File(path);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newfile));
        startActivityForResult(intent, PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    String dir = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                    decodeBitmap(dir);
                }
                break;

            case SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri path = data.getData();
                    imageVehicleOne.setImageURI(path);
                }
                break;
        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        imageVehicleOne.setImageBitmap(bitmap);
    }
}
