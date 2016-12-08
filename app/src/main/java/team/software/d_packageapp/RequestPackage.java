package team.software.d_packageapp;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import team.software.connection.GetDataPackageType;
import team.software.connection.GetDataShipmentType;
import team.software.connection.PackageType;
import team.software.connection.ShipmentType;

public class RequestPackage extends AppCompatActivity {

    @BindView(R.id.textView16)
    TextView textView16;
    @BindView(R.id.spinnerTypeRequest)
    Spinner spinnerTypeRequest;
    @BindView(R.id.spinnerTypePackage)
    Spinner spinnerTypePackage;
    @BindView(R.id.imageProductOne)
    ImageView imageProductOne;
    @BindView(R.id.imageProduct2)
    ImageView imageProduct2;
    @BindView(R.id.imageProduct3)
    ImageView imageProduct3;
    @BindView(R.id.inputTags)
    EditText inputTags;
    @BindView(R.id.inputReceiver)
    EditText inputReceiver;
    @BindView(R.id.inputPointOrigin)
    EditText inputPointOrigin;
    @BindView(R.id.inputPointDestine)
    EditText inputPointDestine;
    @BindView(R.id.buttonSave)
    Button buttonSave;
    @BindView(R.id.radio_yes)
    RadioButton radioYes;
    @BindView(R.id.radio_no)
    RadioButton radioNo;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    private String APP_DIRECTORY = "D-packageApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    ValidatorInput validator = new ValidatorInput();

    Uri path, path1, path2;
    private final Context mContext = this;

    SharedPreferences sharedPreferences;
    String token;
    String tipoPaquete = "0";
    String tipoEnvio = "0";

    boolean image1 = false;
    boolean image2 = false;
    boolean image3 = false;
    boolean insure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_package);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadTypeShipment();
        loadTypePackage();
    }

    @OnClick(R.id.radio_yes)
    public void radio_yes(){
        insure = true;
    }

    @OnClick(R.id.radio_no)
    public void radio_no(){
        insure = false;
    }

    private void saveRequestPackage(String idPedido){
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), idPedido);
        RequestBody confirm = RequestBody.create(MediaType.parse("multipart/form-data"), "true");

        Call<ResponseBody> call = getInstance().saveRequestPackage(
                "Token 870a38885ad59dc9115b5cbe9656b9a976c305d9",
                id,
                confirm
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    Toast.makeText(RequestPackage.this, "Solicitud Guardada Correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RequestPackage.this, "Hubo Un Error, Intente Mas tarde", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.buttonSave)
    public void savePedido(){
        RequestBody shipmenttype = RequestBody.create(MediaType.parse("multipart/form-data"), tipoEnvio);
        RequestBody packagetype = RequestBody.create(MediaType.parse("multipart/form-data"), tipoPaquete);

        File p1 = new File(getRealPathFromUri(path));
        RequestBody photoFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), p1);
        MultipartBody.Part photo1 = MultipartBody.Part.createFormData("photo1", p1.getName(), photoFile1);

        //photo
        File p2 = new File(getRealPathFromUri(path1));
        RequestBody photoFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), p2);
        MultipartBody.Part photo2 = MultipartBody.Part.createFormData("photo2", p2.getName(), photoFile2);

        File p3 = new File(getRealPathFromUri(path2));
        RequestBody photoFile3 = RequestBody.create(MediaType.parse("multipart/form-data"),p3);
        MultipartBody.Part photo3 = MultipartBody.Part.createFormData("photo3", p3.getName(), photoFile3);


        RequestBody tags = RequestBody.create(MediaType.parse("multipart/form-data"), inputTags.getText().toString() );
        RequestBody receiver = RequestBody.create(MediaType.parse("multipart/form-data"),inputReceiver.getText().toString() );
        RequestBody origin = RequestBody.create(MediaType.parse("multipart/form-data"), "POINT("+inputPointOrigin.getText().toString()+")");
        RequestBody destination = RequestBody.create(MediaType.parse("multipart/form-data"),"POINT("+inputPointDestine.getText().toString()+")" );
        RequestBody insured = RequestBody.create(MediaType.parse("multipart/form-data"), ""+insure);




        Call<ResponseBody> call = getInstance().requestPackage(
                "Token 870a38885ad59dc9115b5cbe9656b9a976c305d9",
                shipmenttype,
                packagetype,
                photo1,
                photo2,
                photo3,
                tags,
                receiver,
                origin,
                destination,
                insured
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        final String price = json.get("price").toString();
                        final String idPedido = json.get("id").toString();

                        AlertDialog.Builder builder = new AlertDialog.Builder(RequestPackage.this);
                        builder.setMessage("Su Solicitud Tiene Un Costo de "+price)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        saveRequestPackage(idPedido);
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(RequestPackage.this, "Solicitud Cancelada", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        Log.e("Error: ", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void loadTypeShipment() {

        sharedPreferences = getApplicationContext().getSharedPreferences("D-package", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("user_token", null);

        Call<GetDataShipmentType> response = getInstance().getShipmentType("Token 870a38885ad59dc9115b5cbe9656b9a976c305d9");
        response.enqueue(new Callback<GetDataShipmentType>() {
            @Override
            public void onResponse(Call<GetDataShipmentType> call, Response<GetDataShipmentType> response) {
                if (response.code() == 200) {
                    List<ShipmentType> listCategorias = response.body().getShipmenttype();
                    String[] typeRequest = new String[listCategorias.size() + 1];
                    typeRequest[0] = "Ninguna";
                    for (int i = 0; i < listCategorias.size(); i++) {
                        typeRequest[i + 1] = listCategorias.get(i).getValue();
                    }

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(RequestPackage.this, android.R.layout.simple_spinner_item, typeRequest);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTypeRequest.setAdapter(spinnerAdapter);
                    listenerSpinnerTypeRequest();
                } else {
                    try {
                        Log.e("Error shitment", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetDataShipmentType> call, Throwable t) {

            }
        });
    }

    private void loadTypePackage() {
        Call<GetDataPackageType> response = getInstance().getPackageType("Token 870a38885ad59dc9115b5cbe9656b9a976c305d9");
        response.enqueue(new Callback<GetDataPackageType>() {
            @Override
            public void onResponse(Call<GetDataPackageType> call, Response<GetDataPackageType> response) {
                if (response.code() == 200) {
                    List<PackageType> listModels = response.body().getPackagetype();
                    String[] typePackage = new String[listModels.size() + 1];
                    typePackage[0] = "Ninguna";
                    for (int i = 0; i < listModels.size(); i++) {
                        typePackage[i + 1] = listModels.get(i).getValue();
                    }

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(RequestPackage.this, android.R.layout.simple_spinner_item, typePackage);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTypePackage.setAdapter(spinnerAdapter);
                    listenerSpinnerTypePackage();
                } else {
                    try {
                        Log.e("Error shitment", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetDataPackageType> call, Throwable t) {

            }
        });
    }

    private void listenerSpinnerTypeRequest() {
        spinnerTypeRequest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoEnvio = "" + id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void listenerSpinnerTypePackage() {
        spinnerTypePackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoPaquete = "" + id;
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

    private API getInstance() {
        String BASE_URL = "http://api.d-packagebackend.edwarbaron.me/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        return api;
    }

    @OnClick(R.id.imageProductOne)
    public void click() {
        launchGallery(imageProductOne.getId());
    }

    @OnClick(R.id.imageProduct2)
    public void click2() {
        launchGallery(imageProduct2.getId());
    }

    @OnClick(R.id.imageProduct3)
    public void click3() {
        launchGallery(imageProduct3.getId());
    }

    public void launchGallery(final int id) {
        final CharSequence[] options = {"Elegir de Galeria", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(RequestPackage.this);
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

                    if (id == imageProductOne.getId())
                        image1 = true;
                    else
                        image1 = false;

                    if (id == imageProduct2.getId())
                        image2 = true;
                    else
                        image2 = false;
                    if (id == imageProduct3.getId())
                        image3 = true;
                    else
                        image3 = false;

                    startActivityForResult(intent.createChooser(intent, "Abrir con"), SELECT_PICTURE);
                } else
                    dialog.dismiss();
            }
        });
        builder.show();
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

                    if (image1) {
                        path = data.getData();
                        imageProductOne.setImageURI(path);
                        image1 = false;
                    }

                    if (image2) {
                        path1 = data.getData();
                        imageProduct2.setImageURI(path1);
                        image2 = false;
                    }

                    if (image3) {
                        path2 = data.getData();
                        imageProduct3.setImageURI(path2);
                        image3 = false;
                    }

                }
                break;
        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
//        imageVehicleOne.setImageBitmap(bitmap);
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
