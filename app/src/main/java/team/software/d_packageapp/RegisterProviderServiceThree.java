package team.software.d_packageapp;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
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

import com.google.gson.JsonElement;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import net.margaritov.preference.colorpicker.ColorPickerPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    Uri path;
    private final Context mContext = this;
    @BindView(R.id.til_imageVehicle)
    TextView tilImageVehicle;

    private String APP_DIRECTORY = "D-packageApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    String category = "0";
    String model = "0";

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
        boolean color = validator.isValideColor(inputColor.getText().toString());
        boolean check = checkBoxTermCondition.isChecked();
        boolean modelos = !model.equals("0"); //spinnerModelo;
        boolean categorias = !category.equals("0"); //spinnerCategoria;

        if (plateNumber && color && check && modelos && categorias && path != null) {
            tilPlaca.setError(null);
            tilColor.setError(null);
            tilCategory.setText(null);
            tilModel.setText(null);
            tilImageVehicle.setText(null);
            launchRegister();
        } else {
            if (!plateNumber) {
                tilPlaca.setError(getString(R.string.invalid_plateNumber));
            } else {
                tilPlaca.setError(null);
            }

            if (!color) {
                tilColor.setError(getString(R.string.select_color));
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

            if (path == null) {
                tilImageVehicle.setText(getString(R.string.add_car));
            } else {
                tilImageVehicle.setText(null);
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

        if (path != null) {
            vehicle.setPhoto1(getRealPathFromUri(path));
        }

        postDataRegisterProvider.setVehicle(vehicle);

        RequestBody useraccount_first_name = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getUseraccount().getFirst_name());
        RequestBody useraccount_last_name = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getUseraccount().getLast_name());
        RequestBody useraccount_email = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getUseraccount().getEmail());
        RequestBody useraccount_password = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getUseraccount().getPassword());
        RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getPhone());
        //photo
        RequestBody photoFile = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getPhoto());
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", postDataRegisterProvider.getPhoto().getName(), photoFile);
        //
        RequestBody birthDate = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getBirthdate());
        RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getAddress());
        RequestBody identity_card = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getIdentity_card());
        // drive Licences
        RequestBody drive_licenseFile = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getDriver_license());
        MultipartBody.Part drive_license = MultipartBody.Part.createFormData("driver_license", postDataRegisterProvider.getDriver_license().getName(), drive_licenseFile);
        //
        RequestBody vehicle_license_plate = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getVehicle().getLicensePlate());
        RequestBody vehicle_model = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getVehicle().getModel());
        RequestBody vehicle_category = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getVehicle().getCategory());
        RequestBody vehicle_color = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getVehicle().getColor());
        //photo1
        RequestBody photo1File = RequestBody.create(MediaType.parse("multipart/form-data"), postDataRegisterProvider.getVehicle().getPhoto1());
        MultipartBody.Part photo1 = MultipartBody.Part.createFormData("vehicle.photo1", postDataRegisterProvider.getVehicle().getPhoto1().getName(), photo1File);
        //


        Call<ResponseBody> call = getInstance().registerProvider(
                useraccount_first_name,
                useraccount_last_name,
                useraccount_email,
                useraccount_password,
                phone,
                photo,
                birthDate,
                address,
                identity_card,
                drive_license,
                vehicle_license_plate,
                vehicle_model,
                vehicle_category,
                vehicle_color,
                photo1
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() != 201) {
                    try {
                        JSONObject jsonError = new JSONObject(response.errorBody().string());
                        if (jsonError.getJSONObject("useraccount").get("email") != null) {
                            Toast.makeText(RegisterProviderServiceThree.this, jsonError.getJSONObject("useraccount").get("email").toString() , Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(RegisterProviderServiceThree.this, getString(R.string.register_completed), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterProviderServiceThree.this, Login.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.image_vehicle_one)
    public void launchGallery() {
        final CharSequence[] options = {"Elegir de Galeria", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProviderServiceThree.this);
        builder.setTitle("Elije una opcion");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selection) {
//                if (selection == 0) {
//                    openCamera();
//                } else
                if (selection == 0) {
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
                    path = data.getData();
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

    public String getRealPathFromUri(final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(mContext, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(mContext, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(mContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(mContext, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
