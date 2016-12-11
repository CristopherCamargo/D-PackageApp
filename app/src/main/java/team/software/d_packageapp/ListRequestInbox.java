package team.software.d_packageapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import team.software.adapters.AdapterRequestInbox;
import team.software.connection.GetShipmentsInbox;
import team.software.models.RequestPackageModel;

public class ListRequestInbox extends Fragment{

    private View rootView;
    public AdapterRequestInbox request;
    public ListView listView;
    public ProgressBar circle_progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        rootView = inflater.inflate(R.layout.activity_list_resquest_inbox, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_request_inbox);
        circle_progress = (ProgressBar) rootView.findViewById(R.id.progress_request_inbox);
        circle_progress.setVisibility(View.VISIBLE);
        new GetShipmentsInbox(this.getContext(),this);
        request = new AdapterRequestInbox(getContext(),new ArrayList<RequestPackageModel>());
        listView.setAdapter(request);
        return rootView;
    }
}
