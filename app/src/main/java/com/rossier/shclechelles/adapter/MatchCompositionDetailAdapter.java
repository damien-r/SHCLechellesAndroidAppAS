package com.rossier.shclechelles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rossier.data.Goal;
import com.rossier.data.PlayerLight;
import com.rossier.shclechelles.R;

import java.util.List;

public class MatchCompositionDetailAdapter extends BaseAdapter {

	private final Context context;
	private final List<PlayerLight> objects;
	public MatchCompositionDetailAdapter(Context context, List<PlayerLight> objects) {
		this.context = context;
		this.objects = objects; 
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View row = convertView;
	    ViewHolder holder = new ViewHolder();
	    final PlayerLight player = objects.get(position);
	    int type = getItemViewType(position);
	    if(type==0&&row==null){
	    	LayoutInflater inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        row = inflater.inflate(R.layout.match_detail_score_home_player_layout, parent, false);
	        holder.scorer_jersey_num = (TextView) row.findViewById(R.id.match_detail_score_home_num);
	        holder.scorer_name = (TextView) row.findViewById(R.id.match_detail_score_home_name);
			holder.assit_jersey_num = (TextView) row.findViewById(R.id.match_detail_ass_home_num);
			holder.assis_name = (TextView) row.findViewById(R.id.match_detail_ass_home_name);
	        holder.time = (TextView) row.findViewById(R.id.match_detail_scores_home_time);
	        
	        
	        row.setTag(holder);
	    }else if(type==1&&row==null){
	    	LayoutInflater inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        row = inflater.inflate(R.layout.match_detail_score_away_player_layout, parent, false);
	        holder.scorer_jersey_num = (TextView) row.findViewById(R.id.match_detail_scores_away_scores_num);
	        holder.scorer_name = (TextView) row.findViewById(R.id.match_detail_scores_away_name);
			holder.assit_jersey_num = (TextView) row.findViewById(R.id.match_detail_scores_away_ass_num);
			holder.assis_name = (TextView) row.findViewById(R.id.match_detail_scores_away_ass_name);
	        holder.time = (TextView) row.findViewById(R.id.match_detail_score_away_time);
	        
	        row.setTag(holder);
	    }else{
	    	holder=(ViewHolder) row.getTag();
	    }
	    
    	holder.scorer_jersey_num.setText(player.getJersey_num()+"");
        holder.scorer_name.setText(player.getName());
        holder.time.setText(player.getPosition());
		holder.scorer_jersey_num.setText(player.getJersey_num()+"");
		holder.scorer_name.setText(player.getName());
		holder.time.setText(player.getPosition());
		holder.assis_name.setText("");
		holder.assit_jersey_num.setText("");
		holder.assis_name.setText("");
		holder.assit_jersey_num.setText("");
	    return row;
	}

	public static class ViewHolder {
	    TextView scorer_name; // view1
	    TextView scorer_jersey_num; // view1
		TextView assis_name; // view1
		TextView assit_jersey_num; // view1
	    TextView time;
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

	@Override
	public int getItemViewType(int position) {
		return objects.get(position).isHome()?0:1;
	}
	
	

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public long getItemId(int position) {
		return objects.get(position).getId();
	}
	
	

}
