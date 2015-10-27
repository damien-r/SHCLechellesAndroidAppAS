package com.rossier.shclechelles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.rossier.data.Team;
import com.rossier.shclechelles.adapter.MatchLightAdapter;

public class FragmentNextMatchsTeamDetail extends Fragment {

	Team team;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        team = new Gson().fromJson(getArguments().getString("team"),Team.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.team_detail_next_tab_layout, container, false);
        
        //ranking fill data
        ListView ranking = (ListView) v.findViewById(R.id.team_detail_next_matchs);
        ranking.setAdapter(new MatchLightAdapter(getActivity(),R.layout.match_row_layout,team.getNext_matchs()));
        
        
        return v;
    }
}