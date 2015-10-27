package com.rossier.shclechelles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rossier.data.Match;
import com.rossier.shclechelles.utils.Utils;

public class FragmentGeneralMatchDetail extends Fragment {

	private Match match;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        match = new Gson().fromJson(getArguments().getString("match"),Match.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.match_detail_general_tab_layout, container, false);
        TextView team_home = (TextView) v.findViewById(R.id.match_detail_team_home);
        TextView team_away = (TextView) v.findViewById(R.id.match_detail_team_away);
        TextView team_home_result = (TextView) v.findViewById(R.id.match_detail_result_home);
        TextView team_away_result = (TextView) v.findViewById(R.id.match_detail_result_away);
        TextView vs = (TextView) v.findViewById(R.id.match_detail__vs);
        TextView tier = (TextView) v.findViewById(R.id.match_detail_tiers);
        TextView arbi = (TextView) v.findViewById(R.id.match_detail_arbitre);
        TextView assis = (TextView) v.findViewById(R.id.match_detail_assistant);
        TextView chrono = (TextView) v.findViewById(R.id.match_detail_chrono);
        TextView marquer = (TextView) v.findViewById(R.id.match_detail_marquer);
        TextView spec = (TextView) v.findViewById(R.id.match_detail_spec);
        ImageView logo_home = (ImageView) v.findViewById(R.id.match_detail_team_loc_logo);
        ImageView logo_away = (ImageView) v.findViewById(R.id.match_detail_team_away_logo);
        
        team_home.setText(match.getTeam_home());
        team_away.setText(match.getTeam_away());
        if(match.getResult_away()==-1&&match.getResult_home()==-1){
        	vs.setText("VS");
        	team_home_result.setText("");
        	team_away_result.setText("");
        	tier.setText(match.getDate_loc());
        }else{
        	team_home_result.setText(match.getResult_home()+"");
        	team_away_result.setText(match.getResult_away()+"");
        	String[] result_home = match.getStats_home();
        	String[] result_away = match.getStats_away();
        	String text="("+result_home[0]+"-"+result_away[0]+")";
        	text+="("+result_home[1]+"-"+result_away[1]+")";
        	text+="("+result_home[2]+"-"+result_away[2]+")";
        	tier.setText(text);
        }
        
        
    	
    	
    	arbi.setText(match.getArbitre_princ());
    	assis.setText(match.getArbitre_assistant());
    	chrono.setText(match.getTimer());
    	marquer.setText(match.getMarker());
    	spec.setText(match.getNb_viewer());
    	logo_home.setImageResource(Utils.getLogo(match.getTeam_home()));
    	logo_away.setImageResource(Utils.getLogo(match.getTeam_away()));
        return v;
    }
}