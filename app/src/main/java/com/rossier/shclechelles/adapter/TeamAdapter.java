package com.rossier.shclechelles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rossier.data.TeamLight;
import com.rossier.shclechelles.R;

import java.util.List;

public class TeamAdapter extends ArrayAdapter<TeamLight> {

    private final Context context;
    private final List<TeamLight> objects;

    public TeamAdapter(Context context, int resource, List<TeamLight> objects) {
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
            row = inflater.inflate(R.layout.team_row_layout, parent, false);
            holder.v1 = (TextView) row.findViewById(R.id.team_layout_name);
            holder.v2 = (TextView) row.findViewById(R.id.team_layout_division);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        final TeamLight team = objects.get(position);
        if (team != null) {
            holder.v1.setText(team.getName());
            holder.v2.setText(team.getLigue());
            // Log.d("Adapter", "holder.v1.getText(): " + holder.v1.getText());
        }
        return row;
    }

    public static class ViewHolder {
        TextView v1; // view1
        TextView v2; // view1
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
