package com.rossier.shclechelles.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.rossier.data.NotifState;
import com.rossier.data.TeamLight;
import com.rossier.shclechelles.NotifListFragment;
import com.rossier.shclechelles.R;
import com.rossier.shclechelles.service.RegistrationIntentService;

import java.util.List;

public class TeamNotifAdapter extends ArrayAdapter<TeamLight> {

    private final Context context;
    private final List<TeamLight> objects;
    private NotifListFragment fragment;

    public TeamNotifAdapter(Context context, int resource, List<TeamLight> objects, NotifListFragment fragment) {
        super(context, resource);
        this.context = context;
        this.objects = objects;
        this.fragment = fragment;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.param_layout, parent, false);
            holder.v1 = (TextView) row.findViewById(R.id.notif_layout_name);
            holder.v2 = (TextView) row.findViewById(R.id.notif_layout_division);
            holder.b1 = (CheckBox) row.findViewById(R.id.notif_notif_state);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        final TeamLight team = objects.get(position);
        if (team != null) {
            holder.v1.setText(team.getName());
            holder.v2.setText(team.getLigue());
            List<NotifState> states = NotifState.find(NotifState.class, "name = ?", team.getTopicId());
            if(states != null && states.size()==1){
                //holder.b1.setSelected(states.get(0).isState());
                holder.b1.setChecked(states.get(0).isState());
                holder.b1.setTag(R.id.TAG_ONLINE_ID, team.getTopicId());
            }
        }
        holder.b1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckBox b1 = (CheckBox)buttonView;
                String name = (String) b1.getTag(R.id.TAG_ONLINE_ID);
                List<NotifState> states = NotifState.find(NotifState.class, "name = ?", name);
                if(states != null && states.size()==1){
                    NotifState state = states.get(0);
                    state.setState(isChecked);
                    state.save();
                    if (checkPlayServices()) {
                        // Start IntentService to register this application with GCM.
                        Intent intent = new Intent(fragment.getActivity(), RegistrationIntentService.class);
                        fragment.getActivity().startService(intent);
                    }
                }
            }
        });
        return row;
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(fragment.getActivity(), resultCode, 9000).show();
            } else {
                Log.i("MainActivity", "This device is not supported.");
                fragment.getActivity().finish();
            }
            return false;
        }
        return true;
    }

    public static class ViewHolder {
        TextView v1; // view1
        TextView v2; // view1
        CheckBox b1;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public TeamLight getItem(int position) {
        // TODO Auto-generated method stub
        return objects.get(position);
    }

}
