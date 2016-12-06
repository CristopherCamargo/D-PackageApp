package team.software.d_packageapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.software.connection.GetShipmentsPS;
import team.software.models.RequestPSPackageModel;

public class ListRequestPS extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_list_request_package, container, false);

        GetShipmentsPS data = new GetShipmentsPS(this.getContext());
        RequestPSPackageModel[] requests = data.getData();
        //Log.i("com.prueba",requests[0].client);

        return rootView;
    }
}
