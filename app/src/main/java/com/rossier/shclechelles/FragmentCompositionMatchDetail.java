package com.rossier.shclechelles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rossier.data.Match;
import com.rossier.data.PlayerLight;
import com.rossier.shclechelles.utils.Utils;

public class FragmentCompositionMatchDetail extends Fragment {

	private Match match;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        match = new Gson().fromJson(getArguments().getString("match"),Match.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.match_detail_penality_tab_layout, container, false);
        TextView team_home = (TextView) v.findViewById(R.id.match_detail_team_home);
        TextView team_away = (TextView) v.findViewById(R.id.match_detail_team_away);
        TextView team_home_result = (TextView) v.findViewById(R.id.match_detail_result_home);
        TextView team_away_result = (TextView) v.findViewById(R.id.match_detail_result_away);
        TextView vs = (TextView) v.findViewById(R.id.match_detail__vs);
        TextView tier = (TextView) v.findViewById(R.id.match_detail_tiers);
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
        logo_home.setImageResource(Utils.getLogo(match.getTeam_home()));
        logo_away.setImageResource(Utils.getLogo(match.getTeam_away()));


        /*ListView tv = (ListView) v.findViewById(R.id.match_detail_scores_list);
        long id =0;
        if(match!=null){
            for(PlayerLight player : match.get){
                player.setHome(true);
                player.setId(id++);
            }
            for(PlayerLight player : match.getScorers_away()){
                player.setId(id++);
            }
        }
        ArrayList<Goal> listPlayer = new ArrayList<Goal>();
        listGoal.addAll(match.getScorers_home());
        listGoal.addAll(match.getScorers_away());
        Collections.sort(listGoal);
        tv.setAdapter(new MatchScoresDetailAdapter(getActivity(), listGoal));*/

        return v;
    }
}