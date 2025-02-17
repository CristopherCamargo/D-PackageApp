package team.software.d_packageapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import team.software.adapters.AdapterRequestPackage;
import team.software.connection.GetShipmentsClient;
import team.software.models.RequestPackageModel;

public class ListRequestPackage extends Fragment {

    private View rootView;
    public AdapterRequestPackage request;
    public ListView listView;
    public ProgressBar circle_progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        setRetainInstance(true);
        rootView = inflater.inflate(R.layout.activity_list_request_package, container, false);
        circle_progress = (ProgressBar) rootView.findViewById(R.id.progress_request_client);
        circle_progress.setVisibility(View.VISIBLE);
        listView = (ListView) rootView.findViewById(R.id.list_request_client);
        new GetShipmentsClient(this.getContext(),this);
        request = new AdapterRequestPackage(rootView.getContext(),new ArrayList<RequestPackageModel>());
        listView.setAdapter(request);

        return rootView;
    }
}
