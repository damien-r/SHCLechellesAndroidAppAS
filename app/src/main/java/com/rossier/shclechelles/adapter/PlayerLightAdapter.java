package com.rossier.shclechelles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rossier.data.PlayerLight;
import com.rossier.shclechelles.R;

import java.util.List;

public class PlayerLightAdapter extends ArrayAdapter<PlayerLight> {

	private final Context context;
	private final List<PlayerLight> objects;
	public PlayerLightAdapter(Context context,int resource,List<PlayerLight> objects) {
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
	        row = inflater.inflate(R.layout.player_row_layout, parent, false);
	        holder.v1 = (TextView) row.findViewById(R.id.player_layout_license);
	        holder.v2 = (TextView) row.findViewById(R.id.player_layout_name);
	        row.setTag(holder);
	    } else {
	        holder = (ViewHolder) row.getTag();
	    }
	    final PlayerLight player = objects.get(position);
	    if (player != null) {
	        holder.v1.setText(player.getJersey_num());
	        holder.v2.setText(player.getName());
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
	public PlayerLight getItem(int position) {
	    // TODO Auto-generated method stub
	    return objects.get(position);
	}

}
