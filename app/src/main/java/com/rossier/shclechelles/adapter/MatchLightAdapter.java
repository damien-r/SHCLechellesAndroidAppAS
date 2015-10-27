package com.rossier.shclechelles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rossier.data.MatchLight;
import com.rossier.shclechelles.R;
import com.rossier.shclechelles.utils.Utils;

import java.util.List;

public class MatchLightAdapter extends ArrayAdapter<MatchLight> {

	private final Context context;
	private final List<MatchLight> objects;
	public MatchLightAdapter(Context context,int resource,List<MatchLight> objects) {
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
	        row = inflater.inflate(R.layout.match_row_layout, parent, false);
	        holder.name_loc = (TextView) row.findViewById(R.id.team_home);
	        holder.name_away = (TextView) row.findViewById(R.id.team_away);
	        holder.score_loc = (TextView) row.findViewById(R.id.result_home);
	        holder.score_away = (TextView) row.findViewById(R.id.result_away);
	        holder.loc = (TextView) row.findViewById(R.id.loc);
	        holder.logo_loc = (ImageView) row.findViewById(R.id.team_loc_logo);
	        holder.logo_away = (ImageView) row.findViewById(R.id.team_away_logo);
	        holder.vs = (TextView) row.findViewById(R.id.vs);
	        holder.ligue = (TextView)row.findViewById(R.id.ligue);
	        row.setTag(holder);
	    } else {
	        holder = (ViewHolder) row.getTag();
	    }
    	final MatchLight match = (MatchLight)objects.get(position);
	    if (match != null) {     
	        holder.name_loc.setText(match.getTeam_home().getName());
	        holder.name_away.setText(match.getTeam_away().getName());
	        holder.ligue.setText(match.getLigue());
	        if(match.getScore_home()==-1&&match.getScore_away()==-1){
	        	holder.score_loc.setText("");
		        holder.score_away.setText("");
		        holder.vs.setText("VS");
	        }else{
	        	holder.score_loc.setText(match.getScore_home()+"");
		        holder.score_away.setText(match.getScore_away()+"");
		        holder.vs.setText(" - ");
		        
	        }
	        holder.loc.setText(match.getDate_loc());
	        
	        holder.logo_loc.setImageResource(Utils.getLogo(match.getTeam_home().getName()));
	        holder.logo_away.setImageResource(Utils.getLogo(match.getTeam_away().getName()));
	         
	        
	        // Log.d("Adapter", "holder.v1.getText(): " + holder.v1.getText());
	    }
	    
	    return row;
	}

	public static class ViewHolder {
		ImageView logo_loc;
		ImageView logo_away;
	    TextView name_loc; // view1
	    TextView name_away; // view1
	    TextView score_loc; // view1
	    TextView score_away; // view1
	    TextView vs;
	    TextView loc;
	    TextView ligue;
	}

	@Override
	public int getCount() {
	    return objects.size();
	}

	@Override
	public MatchLight getItem(int position) {
	    // TODO Auto-generated method stub
	    return objects.get(position);
	}

}
