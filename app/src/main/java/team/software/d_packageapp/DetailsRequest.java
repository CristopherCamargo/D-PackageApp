package team.software.d_packageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import team.software.connection.PostAcceptRequest;
import team.software.connection.PostUpdateRequest;
import team.software.models.RequestPackageModel;

public class DetailsRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_request);

        Gson gson = new Gson();
        String jsonObject = getIntent().getExtras().getString("requestObject");
        final RequestPackageModel request = gson.fromJson(jsonObject,RequestPackageModel.class);
        final SharedPreferences sharedPref = getSharedPreferences("D-package", Context.MODE_PRIVATE);

        TextView cost = (TextView) findViewById(R.id.details_request_cost);
        cost.setText(String.valueOf(request.price));

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        switch (request.status){
            case 2:
                seekBar.setProgress(0);
                break;
            case 3:
                seekBar.setProgress(35);
                break;
            case 4:
                seekBar.setProgress(70);
                break;
            case 5:
                seekBar.setProgress(100);
                break;
            default:
                seekBar.setProgress(0);
                break;
        }
        seekBar.setEnabled(false);

        ImageView image_ps = (ImageView) findViewById(R.id.details_photo_ps);
        image_ps.setImageURI(Uri.parse(request.service.photo));

        ImageView image1 = (ImageView) findViewById(R.id.details_photo_one);
        image1.setImageURI(Uri.parse(request.photo1));
        ImageView image2 = (ImageView) findViewById(R.id.details_photo_two);
        image2.setImageURI(Uri.parse(request.photo2));
        ImageView image3 = (ImageView) findViewById(R.id.details_photo_three);
        image3.setImageURI(Uri.parse(request.photo3));

        TextView status = (TextView) findViewById(R.id.details_request_status);
        status.setText(sharedPref.getString("status_request_"+request.status,"null"));

        if(request.service==null || sharedPref.getString("type_user","null").compareTo("service")==0){
            LinearLayout service = (LinearLayout) findViewById(R.id.details_layout_service);
            service.setVisibility(View.INVISIBLE);
        }else{
            LinearLayout service = (LinearLayout) findViewById(R.id.details_layout_service);
            service.setVisibility(View.VISIBLE);

            TextView provider_name = (TextView) findViewById(R.id.details_provider_name);
            provider_name.setText(request.service.useraccount.first_name);
        }

        if(sharedPref.getString("type_user","null").compareTo("client")==0){
            LinearLayout service = (LinearLayout) findViewById(R.id.details_layout_client);
            service.setVisibility(View.INVISIBLE);
        }else{
            TextView client_name = (TextView) findViewById(R.id.details_client_name);
            client_name.setText(request.client.useraccount.first_name);
        }

        TextView tag = (TextView) findViewById(R.id.details_tag);
        tag.setText(request.tags);

        TextView type_request = (TextView) findViewById(R.id.details_type_shipment);
        type_request.setText(sharedPref.getString("type_shipment_"+request.shipmenttype,"null"));

        TextView type_package= (TextView) findViewById(R.id.details_type_package);
        type_package.setText(sharedPref.getString("type_package_"+request.packagetype,"null"));

        TextView receiver = (TextView) findViewById(R.id.details_receiver);
        receiver.setText(request.receiver);

        TextView origin = (TextView) findViewById(R.id.details_origin);
        origin.setText(request.origin);

        TextView destine = (TextView) findViewById(R.id.details_destine);
        destine.setText(request.destination);

        Button button = (Button) findViewById(R.id.details_button);
        if(sharedPref.getString("type_user","null").compareTo("client")==0) {
            switch (request.status) {
                case 3:
                    button.setText(R.string.status_3_client);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new PostUpdateRequest(getApplicationContext(),(request.status+1),request.id);
                        }
                    });
                    break;
                default:
                    button.setVisibility(View.INVISIBLE);
                    break;
            }
        }else{
            switch (request.status) {
                case 2:
                    button.setText(R.string.status_2_provider);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new PostAcceptRequest(getApplicationContext(),sharedPref.getInt("user_id",0),request.id);
                        }
                    });
                    break;
                case 4:
                    button.setText(R.string.status_4_provider);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new PostUpdateRequest(getApplicationContext(),(request.status+1),request.id);
                        }
                    });
                    break;
                default:
                    button.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }
}
