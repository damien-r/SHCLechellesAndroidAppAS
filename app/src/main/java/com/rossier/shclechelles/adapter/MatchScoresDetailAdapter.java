package com.rossier.shclechelles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rossier.data.Goal;
import com.rossier.shclechelles.R;

import java.util.List;

public class MatchScoresDetailAdapter extends BaseAdapter {

	private final Context context;
	private final List<Goal> objects;
	public MatchScoresDetailAdapter(Context context,List<Goal> objects) {
		this.context = context;
		this.objects = objects; 
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View row = convertView;
	    ViewHolder holder = new ViewHolder();
	    final Goal goal = objects.get(position);
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
	    
    	holder.scorer_jersey_num.setText(goal.getScorer().getJersey_num()+"");
        holder.scorer_name.setText(goal.getScorer().getName());
        if(goal.getAssitant()!=null){
	        holder.assit_jersey_num.setText(goal.getAssitant().getJersey_num()+"");
	        holder.assis_name.setText(goal.getAssitant().getName());
        }else{
        	holder.assit_jersey_num.setText("");
	        holder.assis_name.setText("");
        }
        holder.time.setText(goal.getTime());
    	holder.scorer_jersey_num.setText(goal.getScorer().getJersey_num()+"");
        holder.scorer_name.setText(goal.getScorer().getName());
        if(goal.getAssitant()!=null){
        	holder.assit_jersey_num.setText(goal.getAssitant().getJersey_num()+"");
        	holder.assis_name.setText(goal.getAssitant().getName());
        }else{
        	holder.assit_jersey_num.setText("");
        	holder.assis_name.setText("");
        }
        holder.time.setText(goal.getTime());
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
	public Goal getItem(int position) {
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
