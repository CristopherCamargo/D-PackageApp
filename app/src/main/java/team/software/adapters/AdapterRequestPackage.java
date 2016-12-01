package team.software.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import team.software.d_packageapp.R;
import team.software.models.RequestPackageModel;

public class AdapterRequestPackage extends ArrayAdapter<RequestPackageModel> {


    public AdapterRequestPackage(Context context, ArrayList<RequestPackageModel> requests) {
        super(context,0,requests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RequestPackageModel requestItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter_request_package, parent, false);
        }

        TextView identificador = (TextView) convertView.findViewById(R.id.codeRequest);
        TextView tag = (TextView) convertView.findViewById(R.id.tag_request);
        TextView destine = (TextView) convertView.findViewById(R.id.request_destine);
        TextView provider_service = (TextView) convertView.findViewById(R.id.provider_service_request_client);
        TextView type = (TextView) convertView.findViewById(R.id.type_request_package);
        TextView status = (TextView) convertView.findViewById(R.id.status_request);

        identificador.setText(requestItem.getIndentificador());
        tag.setText(requestItem.getTag());
        destine.setText(requestItem.getDestine());
        provider_service.setText(requestItem.getProvider_service());
        type.setText(requestItem.getType());
        status.setText(requestItem.getStatus());

        return convertView;
    }
}
