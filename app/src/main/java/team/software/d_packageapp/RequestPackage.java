package team.software.d_packageapp;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import team.software.connection.PostRequestPackage;

public class RequestPackage extends AppCompatActivity {

    String typePackage="",tag="",destPackage="",origPackage="", shipmentType="",receiver="";
    Boolean insurePackage;
    Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_package);

        //Cargo el dropDown del tipo de paquete
        final Spinner dropdown = (Spinner)findViewById(R.id.spinner_package);
        String[] items = new String[]{"Small", "Medium", "Large"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        final Spinner insure = (Spinner)findViewById(R.id.spinner_insure);
        String[] items2 = new String[]{"Yes", "No"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        insure.setAdapter(adapter2);

        ImageButton loadPhoto = (ImageButton) findViewById(R.id.load_photo_package);
        final Button newRequest = (Button) findViewById(R.id.newRequest);
        Button calculate = (Button) findViewById(R.id.calculate_price);
        final RadioButton expressPackage = (RadioButton) findViewById(R.id.express_package);
        final RadioButton standarPackage = (RadioButton) findViewById(R.id.standar_package);
        final EditText tagText = (EditText) findViewById(R.id.tag_package);
        final EditText receiverText = (EditText) findViewById(R.id.receiver_text);
        final EditText destination = (EditText) findViewById(R.id.destination_package_text);
        final EditText origin = (EditText) findViewById(R.id.origin_package_text);

        standarPackage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                expressPackage.setChecked(false);
                Log.d("Mensaje: ","Standar");
                typePackage = "2";
            }
        });

        expressPackage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                standarPackage.setChecked(false);
                Log.d("Mensaje: ","Express");
                typePackage = "1";
            }
        });

        loadPhoto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Selecciona una imagen"),200);
            }
        });


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tag = tagText.getText().toString();
                receiver = receiverText.getText().toString();
                destPackage = destination.getText().toString();
                origPackage = origin.getText().toString();

                if(dropdown.getSelectedItem().toString() == "Small")
                    shipmentType = "1";
                else if(dropdown.getSelectedItem().toString() == "Medium")
                    shipmentType = "2";
                else
                    shipmentType = "3";

                Log.d("Mensaje: ",tag+" "+destPackage+" "+origPackage+" "+shipmentType+" "+insurePackage);

                if(tag.isEmpty() || destPackage.isEmpty() || origPackage.isEmpty()){
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),"ERROR: Campos Vacios", Toast.LENGTH_SHORT);

                    toast1.show();
                }
                else{
                    newRequest.setVisibility(View.VISIBLE);
                    if(insure.getSelectedItem().toString() == "Yes") {
                        insurePackage = true;
                        new PostRequestPackage(getApplicationContext(),typePackage,tag,receiver,origPackage,destPackage,shipmentType,insurePackage);
                    }
                    else{
                        insurePackage = false;
                        new PostRequestPackage(getApplicationContext(),typePackage,tag,receiver,origPackage,destPackage,shipmentType,insurePackage);
                    }
                }



            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 200:
                if (resultCode == RESULT_OK) {
                    path = data.getData();
                    //imageVehicleOne.setImageURI(path);
                }
                break;
        }
    }




}
