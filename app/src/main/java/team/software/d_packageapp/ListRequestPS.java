package team.software.d_packageapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import team.software.adapters.AdapterRequestPS;
import team.software.connection.GetShipmentsPS;
import team.software.models.RequestPackageModel;

public class ListRequestPS extends Fragment{

    private View rootView;
    public AdapterRequestPS request;
    public ListView listView;
    public ProgressBar circle_progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        rootView = inflater.inflate(R.layout.activity_list_request_ps, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_request_ps);
        circle_progress = (ProgressBar) rootView.findViewById(R.id.progress_request_ps);
        circle_progress.setVisibility(View.VISIBLE);
        new GetShipmentsPS(this.getContext(),this);
        request = new AdapterRequestPS(getContext(),new ArrayList<RequestPackageModel>());
        listView.setAdapter(request);

        return rootView;
    }
}
