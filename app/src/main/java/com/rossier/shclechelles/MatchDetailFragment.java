package com.rossier.shclechelles;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.MenuItem;

import com.rossier.data.Match;

public class MatchDetailFragment extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private Match match;
    private int id_match;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.
        setContentView(R.layout.match_detail_layout);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        Intent intent = getIntent();
        String match = intent.getExtras().getString("match");
        Bundle arg1 = new Bundle();
        arg1.putString("match", match);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Général", null),
                FragmentGeneralMatchDetail.class, arg1);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Buts", null),
                FragmentScoresMatchDetail.class, arg1);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Penalités", null),
                FragmentPenalityMatchDetail.class, arg1);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("Composition", null),
                FragmentCompositionMatchDetail.class, arg1);
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
