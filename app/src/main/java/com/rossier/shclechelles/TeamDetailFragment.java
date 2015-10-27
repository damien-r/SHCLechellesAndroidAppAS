package com.rossier.shclechelles;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.MenuItem;

import com.rossier.data.Team;

public class TeamDetailFragment extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private Team team;
    private int id_team;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_detail_layout);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        Intent intent = getIntent();
        String team = intent.getExtras().getString("team");
        Bundle arg1 = new Bundle();
        arg1.putString("team", team);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Général", null),
                FragmentGeneralTeamDetail.class, arg1);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Class.", null),
                FragmentRankingTeamDetail.class, arg1);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Calen.", null),
                FragmentNextMatchsTeamDetail.class, arg1);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("Résul.", null),
                FragmentLastMatchsTeamDetail.class, arg1);
        /*mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Effectifs", null),
                FragmentPenalityMatchDetail.class, arg1);*/
        /*mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("Composition", null),
                FragmentCompositionMatchDetail.class, arg1);*/
        //setContentView(R.layout.match_detail_layout);
       
        /*for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++) {
        	mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF0000")); //unselected
        }
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#0000FF")); // selected*/

        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }

    
}
