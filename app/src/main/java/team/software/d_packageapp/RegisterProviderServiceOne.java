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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.software.connection.PostDataRegisterProvider;
import team.software.connection.Useraccount;

public class RegisterProviderServiceOne extends AppCompatActivity {


    ValidatorInput validator = new ValidatorInput();
    Uri path;

    private final Context mContext = this;
    @BindView(R.id.imagePerfil)
    ImageView imagePerfil;
    @BindView(R.id.til_ImagePerfil)
    TextView tilImagePerfil;
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
    @BindView(R.id.buttonContinue)
    Button buttonContinue;

    private String APP_DIRECTORY = "D-packageApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider_service_one);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.buttonContinue)
    public void checkCampos() {
        boolean nombre = validator.isValideName(inputFirstName.getText().toString());
        boolean apellido = validator.isValideName(inputLastName.getText().toString());
        boolean birthday = validator.isValideDate(inputDateOfBirth.getText().toString(), "yyyy-MM-dd");
        boolean idnumber = validator.isValideIdNumber(inputIdNumber.getText().toString());
        boolean address = validator.isValideAddress(inputAddress.getText().toString());

        if (nombre && apellido && birthday && idnumber && address && path != null) {
            tilFirstName.setError(null);
            tilLastName.setError(null);
            tilDateOfBirth.setError(null);
            tilIdNumber.setError(null);
            tilAddress.setError(null);
            tilImagePerfil.setText(null);
            launchActivityRegisterProviderServiceTwo();
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

            if (path == null) {
                tilImagePerfil.setText(getText(R.string.add_image));
            } else {
                tilImagePerfil.setText(null);
            }
        }

    }

    private void launchActivityRegisterProviderServiceTwo() {
        Useraccount useraccount = new Useraccount();
        useraccount.setFirst_name(inputFirstName.getText().toString());
        useraccount.setLast_name(inputLastName.getText().toString());

        PostDataRegisterProvider postDataRegisterProvider = new PostDataRegisterProvider();
        postDataRegisterProvider.setUseraccount(useraccount);
        postDataRegisterProvider.setBirthdate(inputDateOfBirth.getText().toString());
        postDataRegisterProvider.setIdentity_card(inputIdNumber.getText().toString());
        postDataRegisterProvider.setAddress(inputAddress.getText().toString());
        if (path != null)
            postDataRegisterProvider.setPhoto(getRealPathFromUri(path));

        Intent ActivityRegisterProviderServiceTwo = new Intent(this, RegisterProviderServiceTwo.class);
        ActivityRegisterProviderServiceTwo.putExtra("parametro", postDataRegisterProvider);
        startActivity(ActivityRegisterProviderServiceTwo);
    }

    @OnClick(R.id.imagePerfil)
    public void launchGallery() {
        final CharSequence[] options = {"Elegir de Galeria", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProviderServiceOne.this);
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
                    imagePerfil.setImageURI(path);
                }
                break;
        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        imagePerfil.setImageBitmap(bitmap);
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
