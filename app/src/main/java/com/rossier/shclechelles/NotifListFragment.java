package com.rossier.shclechelles;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.StringUtil;
import com.rossier.data.Data;
import com.rossier.data.NotifState;
import com.rossier.data.Team;
import com.rossier.data.TeamLight;
import com.rossier.shclechelles.adapter.TeamAdapter;
import com.rossier.shclechelles.adapter.TeamNotifAdapter;
import com.rossier.shclechelles.service.RegistrationIntentService;
import com.rossier.shclechelles.utils.ListFragmentSwipeRefreshLayout;
import com.rossier.shclechelles.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotifListFragment extends ListFragment {
	OnTeamSelectedListener mCallback;
	private static String URL = "http://1-dot-shcrestservice.appspot.com/rest/teams/all";
	private static int userID;
	List<TeamLight> teamsList;
	ListView teamView;
	private ProgressDialog pDialog;
	private LayoutInflater inflater;
	private Gson gson = new Gson();
	private SwipeRefreshLayout layout;
	private boolean first = true;
	private Context context;
	private NotifListFragment current;
	public interface OnTeamSelectedListener {
	    /** Called by HeadlinesFragment when a list item is selected */
	    public void onTeamSelected(int position);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); 
	    setRetainInstance(true);
	    initLayout();
	    context = getContext();
		current = this;


	    
	    if(Data.getInstance().getTeamsLightData()==null){
	    	new TeamLoadTask().execute();
	    }else{

	    	teamsList = Data.getInstance().getTeamsLightData();
	    	first = false;
	    }
	    setListAdapter(new TeamNotifAdapter(getActivity().getApplicationContext(), R.layout.param_layout, teamsList, this));

	}




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//View view = inflater.inflate(R.layout.fragment_player, container,false);
		View view = super.onCreateView(inflater, container, savedInstanceState);
		layout = new ListFragmentSwipeRefreshLayout(container.getContext(),this);
		layout.addView(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
	    layout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new TeamLoadTask().execute();
			}
		});
	    return layout;
	}
	
	private void initLayout() {         
	    if(getActivity().getIntent()!=null) {
	        userID = getActivity().getIntent().getIntExtra("id", 0);
	    } else {
	        return;
	    }
	    teamView = getListView();
	    teamsList = new ArrayList<TeamLight>();

	}
	
	@Override
	public void onStart() {
	    super.onStart();

	    // When in two-pane layout, set the listview to highlight the selected
	    // list item
	    // (We do this during onStart because at the point the listview is
	    // available.)
	    if (getFragmentManager().findFragmentById(R.id.notif_fragment) != null) {
	    	teamView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	    }
	}

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
		  Log.i("NotifListFragment","Test something");
	  }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
	private static final String ARG_SECTION_NUMBER = "section_number";
    public static NotifListFragment newInstance(int id) {
    	NotifListFragment fragment = new NotifListFragment();
    	Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
    
    class TeamLoadTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
        	if(first){
        		pDialog = ProgressDialog.show(getActivity(), "",
            		getResources().getString(R.string.loading), true);
        		first=!first;
        	}
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(URL);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                // if this is null the web service returned an empty page
                if (httpEntity == null) // response is empty so exit out
                    return null;

                String jsonString = EntityUtils.toString(httpEntity);

                if (jsonString != null && jsonString.length() != 0) 
                {
                	Type listType = new TypeToken<List<TeamLight>>() {}.getType();
                	teamsList = gson.fromJson(jsonString, listType);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("ERROR SOMEWHERE!!!! ", e.toString());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String file_url) {
			if (pDialog != null && pDialog.isShowing())
				pDialog.dismiss();
			if (teamsList.size() == 0) {
				Toast.makeText(getActivity(), "No teams in a list",
						Toast.LENGTH_SHORT).show();

			} else if (teamView != null) {
				setListAdapter(new TeamNotifAdapter(getActivity().getApplicationContext(), R.layout.param_layout, teamsList, current));
				Data.getInstance().setTeamsLightData(teamsList);
				layout.setRefreshing(false);
				Toast.makeText(getActivity(), "Mise à jour réussie",
						Toast.LENGTH_SHORT).show();
			}
		}
        
	}
}
