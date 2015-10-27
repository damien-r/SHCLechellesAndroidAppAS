package com.rossier.shclechelles;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rossier.data.Data;
import com.rossier.data.Player;
import com.rossier.data.PlayerLight;
import com.rossier.shclechelles.adapter.PlayerLightAdapter;
import com.rossier.shclechelles.utils.ListFragmentSwipeRefreshLayout;

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

public class PlayerListFragment extends ListFragment {
	OnPlayerSelectedListener mCallback;
	private static String URL = "http://1-dot-shcrestservice.appspot.com/rest/players/all";
	private static int userID;
	List<PlayerLight> playerList;
	ListView playerView;
	private ProgressDialog pDialog;
	private LayoutInflater inflater;
	private Gson gson = new Gson();
	private SwipeRefreshLayout layout;
	private boolean first = true;
	public interface OnPlayerSelectedListener {
	    /** Called by HeadlinesFragment when a list item is selected */
	    public void onPlayerSelected(int position);
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); 
	    setRetainInstance(true);
	    initLayout();

	    if(Data.getInstance().getPlayersLightData()==null){
	    	new PlayerLoadTask().execute();
	    }else{
	    	playerList =  Data.getInstance().getPlayersLightData();
	    	first = false;
	    }
	    setListAdapter(new PlayerLightAdapter(getActivity().getApplicationContext(), R.layout.player_row_layout, playerList));
	    

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
				new PlayerLoadTask().execute();
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
	    playerView = getListView();
	    playerList = new ArrayList<PlayerLight>();

	}
	
	@Override
	public void onStart() {
	    super.onStart();
	    if (getFragmentManager().findFragmentById(R.id.team_fragment) != null) {
	    	playerView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	    }
	}

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
		// Notify the parent activity of selected item
		  int id_player = playerList.get(position).getId();
		  getFragmentManager().beginTransaction()
          .replace(R.id.container, PlayerFragment.newInstance(position+1,id_player))
          .addToBackStack("BASE")
          .commit();
	  }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
	private static final String ARG_SECTION_NUMBER = "section_number";
    public static PlayerListFragment newInstance(int id) {
    	PlayerListFragment fragment = new PlayerListFragment();
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
    
    class PlayerLoadTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
        	if(first){
        		pDialog = ProgressDialog.show(getActivity(), "",
            		getResources().getString(R.string.loading), true);
        		first = !first;
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
                	Type listType = new TypeToken<List<PlayerLight>>() {}.getType();
                	playerList = gson.fromJson(jsonString, listType);
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
            if (pDialog!=null&&pDialog.isShowing())
                pDialog.dismiss();
            if (playerList.size() == 0) {
                Toast.makeText(getActivity(), "No player in a list",
                        Toast.LENGTH_SHORT).show();

            } else if(playerView != null) {
            	setListAdapter(new PlayerLightAdapter(getActivity().getApplicationContext(), R.layout.player_row_layout, playerList));
            	Data.getInstance().setPlayersLightData(playerList);
            	Data.getInstance().setPlayersData(new ArrayList<Player>());
            	Toast.makeText(getActivity(), "Mise à jour réussie",
                        Toast.LENGTH_SHORT).show();
            	layout.setRefreshing(false);
            }
        }
}     
}
