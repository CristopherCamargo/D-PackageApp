package team.software.d_packageapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import team.software.connection.PostDataRegisterProvider;
import team.software.connection.Useraccount;

public class RegisterProviderServiceOne extends AppCompatActivity {

    @BindView(R.id.imagePerfil)
    ImageView imagePerfil;
    @BindView(R.id.inputFirstName)
    EditText inputFirstName;
    @BindView(R.id.til_firstName)
    TextInputLayout tilFirstName;
    @BindView(R.id.inputLastName)
    EditText inputLastName;
    @BindView(R.id.til_lastName)
    TextInputLayout tilLastName;
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
    @BindView(R.id.inputDateOfBirth)
    EditText inputDateOfBirth;
    @BindView(R.id.til_dateOfBirth)
    TextInputLayout tilDateOfBirth;

    ValidatorInput validator = new ValidatorInput();
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
        boolean idnumber = validator.isValideNumber(inputIdNumber.getText().toString());
        boolean address = validator.isValideAddress(inputAddress.getText().toString());

        if (nombre && apellido && birthday && address) {
            tilFirstName.setError(null);
            tilLastName.setError(null);
            tilDateOfBirth.setError(null);
            tilIdNumber.setError(null);
            tilAddress.setError(null);
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

        Intent ActivityRegisterProviderServiceTwo = new Intent(this, RegisterProviderServiceTwo.class);
        ActivityRegisterProviderServiceTwo.putExtra("parametro", postDataRegisterProvider);
        startActivity(ActivityRegisterProviderServiceTwo);
    }

    @OnClick(R.id.imagePerfil)
    public void launchGallery() {
        final CharSequence[] options = {"Tomar Foto", "Elegir de Galeria", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProviderServiceOne.this);
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

}
