package team.software.d_packageapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import team.software.adapters.AdapterRequestPackage;
import team.software.models.RequestPackageModel;

public class ListRequestPackage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_request_package);

        RequestPackageModel model = new RequestPackageModel("A000","Express","Televisor","Peribeca","Juan","En camino");

        ListView listView = (ListView) findViewById(R.id.list_request_client);
        ArrayList<RequestPackageModel> requestItems = new ArrayList<RequestPackageModel>();
        requestItems.add(model);
        requestItems.add(model);
        AdapterRequestPackage requestPackage = new AdapterRequestPackage(this,requestItems);
        listView.setAdapter(requestPackage);
    }
}
