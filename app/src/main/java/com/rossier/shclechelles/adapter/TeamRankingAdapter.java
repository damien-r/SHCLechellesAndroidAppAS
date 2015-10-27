package com.rossier.shclechelles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rossier.data.TeamRanking;
import com.rossier.shclechelles.R;
import com.rossier.shclechelles.utils.Utils;

import java.util.List;

public class TeamRankingAdapter extends ArrayAdapter<TeamRanking> {

	private final Context context;
	private final List<TeamRanking> objects;
	public TeamRankingAdapter(Context context,int resource,List<TeamRanking> objects) {
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
	        row = inflater.inflate(R.layout.team_ranking_row_layout, parent, false);
	        holder.rank = (TextView) row.findViewById(R.id.team_ranking_rank);
	        holder.team_name = (TextView) row.findViewById(R.id.team_ranking_name);
	        //holder.match_played = (TextView) row.findViewById(R.id.team_ranking_mj);
	        //holder.match_won = (TextView) row.findViewById(R.id.team_ranking_mg);
	        //holder.match_loss = (TextView) row.findViewById(R.id.team_ranking_mp);
	        holder.goal_diff = (TextView) row.findViewById(R.id.team_ranking_goal);
	        holder.points = (TextView) row.findViewById(R.id.team_ranking_points);
	        holder.logo = (ImageView) row.findViewById(R.id.team_ranking_logo);
	        row.setTag(holder);
	    } else {
	        holder = (ViewHolder) row.getTag();
	    }
	    final TeamRanking team = objects.get(position);
	    if (team != null) {
	        holder.rank.setText(position+1+"");
	        holder.team_name.setText(team.getName()+" ("+team.getMatch_played()+")");
	       // holder.match_played.setText(team.getMatch_played()+"");
	        //holder.match_won.setText(team.getMatch_won()+"");
	        //holder.match_loss.setText(team.getMatch_loss()+"");
	        holder.goal_diff.setText(team.getGoal_scored()+"/"+team.getGoal_conceded()+"("+team.getGoal_diff()+")");
	        holder.points.setText(team.getPoints()+"");
	        holder.logo.setImageResource(Utils.getLogo(team.getName()));
	    }
	    return row;
	}

	public static class ViewHolder {
	    TextView rank; // view1
	    TextView team_name; // view1
	    //TextView match_played;
	    //TextView match_won;
	    //TextView match_loss;
	    TextView goal_diff;
	    TextView points;
	    ImageView logo;
	}

	@Override
	public int getCount() {
	    return objects.size();
	}

	@Override
	public TeamRanking getItem(int position) {
	    // TODO Auto-generated method stub
	    return objects.get(position);
	}

}
