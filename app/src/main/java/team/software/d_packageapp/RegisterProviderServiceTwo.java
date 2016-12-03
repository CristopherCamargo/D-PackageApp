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

public class RegisterProviderServiceTwo extends AppCompatActivity {

    @BindView(R.id.photoLicence)
    ImageView photoLicence;
    @BindView(R.id.inputNumContact)
    EditText inputNumContact;
    @BindView(R.id.til_NumContact)
    TextInputLayout tilNumContact;
    @BindView(R.id.inputEmail)
    EditText inputEmail;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.inputPassword)
    EditText inputPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.buttonContinue)
    Button buttonContinue;
    @BindView(R.id.til_UploadLicense)
    TextView tilUploadLicense;

    Uri path;
    private final Context mContext = this;
    ValidatorInput validator = new ValidatorInput();


    private String APP_DIRECTORY = "D-packageApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    private PostDataRegisterProvider postDataRegisterProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider_service_two);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        postDataRegisterProvider =
                (PostDataRegisterProvider) getIntent().getExtras().getSerializable("parametro");


    }

    @OnClick(R.id.buttonContinue)
    public void checkCampos() {

        boolean nContacto = validator.isValideNumber(inputNumContact.getText().toString());
        boolean email = validator.isvalideMail(inputEmail.getText().toString());
        boolean password = validator.isValidePassword(inputPassword.getText().toString());

        if (nContacto && email && password && path != null) {

            tilNumContact.setError(null);
            tilEmail.setError(null);
            tilPassword.setError(null);
            tilUploadLicense.setText(null);
            launchActivityRegisterProviderServiceThree();
        } else {

            if (!nContacto) {
                tilNumContact.setError(getString(R.string.invalid_contac));
            } else {
                tilNumContact.setError(null);
            }

            if (!email) {
                tilEmail.setError(getString(R.string.invalid_email));
            } else {
                tilEmail.setError(null);
            }

            if (!password) {
                tilPassword.setError(getString(R.string.invalid_password));
            } else {
                tilPassword.setError(null);
            }

            if (path == null) {
                tilUploadLicense.setText(getString(R.string.add_image_license));
            } else {
                tilUploadLicense.setText(null);
            }
        }

    }

    private void launchActivityRegisterProviderServiceThree() {

        postDataRegisterProvider.setPhone(inputNumContact.getText().toString());
        postDataRegisterProvider.getUseraccount().setEmail(inputEmail.getText().toString());
        postDataRegisterProvider.getUseraccount().setPassword(inputPassword.getText().toString());
        if (path != null) {
            postDataRegisterProvider.setDriver_license(getRealPathFromUri(path));
        }
        Intent ActivityRegisterProviderServiceThree = new Intent(this, RegisterProviderServiceThree.class);
        ActivityRegisterProviderServiceThree.putExtra("parametro", postDataRegisterProvider);
        startActivity(ActivityRegisterProviderServiceThree);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @OnClick(R.id.photoLicence)
    public void launchGallery() {
        final CharSequence[] options = { "Elegir de Galeria", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProviderServiceTwo.this);
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
                    photoLicence.setImageURI(path);
                }
                break;
        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        photoLicence.setImageBitmap(bitmap);
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
