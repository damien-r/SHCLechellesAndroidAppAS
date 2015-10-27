package com.rossier.shclechelles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rossier.data.Penality;
import com.rossier.shclechelles.R;

import java.util.List;

public class MatchPenaltyDetailAdapter extends BaseAdapter {

	private final Context context;
	private final List<Penality> objects;
	public MatchPenaltyDetailAdapter(Context context,List<Penality> objects) {
		this.context = context;
		this.objects = objects; 
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View row = convertView;
	    ViewHolder holder = new ViewHolder();
	    final Penality penalty = objects.get(position);
	    int type = getItemViewType(position);
	    if(type==0&&row==null&&penalty!=null){
	    	LayoutInflater inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        row = inflater.inflate(R.layout.match_detail_penalty_home_player_layout, parent, false);
	        holder.time = (TextView) row.findViewById(R.id.match_detail_penalty_home_time);
	        holder.name = (TextView) row.findViewById(R.id.match_detail_penalty_home_name);
	        holder.duration = (TextView) row.findViewById(R.id.match_detail_penalty_home_duration);
	        row.setTag(holder);
	    }else if(type==1&&row==null&&penalty!=null){
	    	LayoutInflater inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        row = inflater.inflate(R.layout.match_detail_penalty_away_player_layout, parent, false);
	        holder.time = (TextView) row.findViewById(R.id.match_detail_penalty_away_time);
	        holder.name = (TextView) row.findViewById(R.id.match_detail_penalty_away_name);
	        holder.duration = (TextView) row.findViewById(R.id.match_detail_penalty_away_duration);
	        
	        row.setTag(holder);
	    }else{
	    	holder=(ViewHolder) row.getTag();
	    }
	    holder.time.setText(penalty.getStart());
        holder.name.setText(penalty.getPlayer().getName());
        holder.duration.setText(penalty.getDuration());
	    return row;
	}

	public static class ViewHolder {
	    TextView time; // view1
	    TextView name; // view1
	    TextView duration; // view1
	}

	@Override
	public int getCount() {
	    return objects.size();
	}

	@Override
	public Penality getItem(int position) {
	    // TODO Auto-generated method stub
	    return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		 return position;
	}

	@Override
	public int getItemViewType(int position) {
		return objects.get(position).isHome()?0:1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	
	
	

}
