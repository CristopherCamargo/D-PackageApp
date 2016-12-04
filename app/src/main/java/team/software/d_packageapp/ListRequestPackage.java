package team.software.d_packageapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import team.software.adapters.AdapterRequestPackage;
import team.software.models.RequestPackageModel;

public class ListRequestPackage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_list_request_package, container, false);

        RequestPackageModel model = new RequestPackageModel("A000","Express","Televisor","Peribeca","Juan","En camino");

        ListView listView = (ListView) rootView.findViewById(R.id.list_request_client);
        ArrayList<RequestPackageModel> requestItems = new ArrayList<RequestPackageModel>();
        requestItems.add(model);
        requestItems.add(model);
        AdapterRequestPackage requestPackage = new AdapterRequestPackage(rootView.getContext(),requestItems);
        listView.setAdapter(requestPackage);

        return rootView;
    }
}
