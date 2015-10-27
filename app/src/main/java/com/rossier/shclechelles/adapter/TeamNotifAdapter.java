package com.rossier.shclechelles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rossier.data.NotifState;
import com.rossier.data.TeamLight;
import com.rossier.shclechelles.R;

import java.util.List;

public class TeamNotifAdapter extends ArrayAdapter<TeamLight> {

    private final Context context;
    private final List<TeamLight> objects;

    public TeamNotifAdapter(Context context, int resource, List<TeamLight> objects) {
        super(context, resource);
        this.context = context;
        this.objects = objects;
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
            String id = team.getName()+team.getLigue();
            id = id.replace(" ","").trim();
            List<NotifState> states = NotifState.find(NotifState.class, "name = ?", id);
            if(states != null && states.size()==1){
                holder.b1.setSelected(states.get(0).isState());
            }
        }
        return row;
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
