package com.rossier.shclechelles;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rossier.data.Data;
import com.rossier.data.Player;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class PlayerFragment extends Fragment {
	private static String URL = "http://1-dot-shcrestservice.appspot.com/rest/players/player/";
	private static int userID;
	private ProgressDialog pDialog;
	private LayoutInflater inflater;
	private Gson gson = new Gson();
	Player player;
	private int player_id;
	private View v;
	public interface OnPlayerSelectedListener {
	    /** Called by HeadlinesFragment when a list item is selected */
	    public void onPlayerSelected(int position);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); 
	    setRetainInstance(true);
	    initLayout();
	    Player player_tmp = Data.getInstance().getPlayer(player_id);
	    if(player_tmp==null){
		    PlayerLoadTask loader = new PlayerLoadTask(new FragmentCallback() {
				
				@Override
				public void onTaskDone() {
					filData();
					
				}
			});
		    loader.execute();
	    }else{
	    	player = player_tmp;
	    	filData();
	    }
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_player_detail, container,false);
		this.inflater = inflater;
		return v;
	}



	private void initLayout() {         
	    if(getActivity().getIntent()!=null) {
	        userID = getActivity().getIntent().getIntExtra("id", 0);
	    } else {
	        return;
	    }
	}
	
	@Override
	public void onStart() {
	    super.onStart();
	}
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String PLAYER_ID = "player_id";
    public static PlayerFragment newInstance(int id,int player_id) {
    	PlayerFragment fragment = new PlayerFragment();
    	Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, id);
        args.putInt(PLAYER_ID, player_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        player_id = getArguments().getInt(PLAYER_ID);
    }
    
    public void filData(){
    	if(player != null){
    		TextView name = (TextView)v.findViewById(R.id.name);
        	TextView pos = (TextView)v.findViewById(R.id.position);
        	TextView habi = (TextView)v.findViewById(R.id.habilite);
        	TextView natio = (TextView)v.findViewById(R.id.nationalite);
        	TextView age = (TextView)v.findViewById(R.id.age);
        	TextView height = (TextView)v.findViewById(R.id.height);
        	TextView weight = (TextView)v.findViewById(R.id.weight);
        	TextView desc = (TextView)v.findViewById(R.id.desc);
        	TextView mj = (TextView)v.findViewById(R.id.mj);
        	TextView mg = (TextView)v.findViewById(R.id.mg);
        	TextView mp = (TextView)v.findViewById(R.id.mp);
        	TextView goal = (TextView)v.findViewById(R.id.g);
        	TextView assis = (TextView)v.findViewById(R.id.a);
        	TextView point = (TextView)v.findViewById(R.id.p);
        	name.setText(player.getName());
        	pos.setText(player.getPosition());
        	habi.setText(player.getHabilite());
        	natio.setText(player.getNationality());
        	age.setText(player.getAge()+"");
        	height.setText(player.getHeight()+"");
        	weight.setText(player.getWeight()+"");
        	if(player.getDescription().startsWith("Stefanos Kapino")||player.getDescription().equals("")){
        		desc.setText("Une description n'a pas encore été fournie pour ce joueur");
        	}else{
        		desc.setText(player.getDescription());
        	}
        	mj.setText(player.getCurrent_match_played()+"");
        	mg.setText(player.getCurrent_match_won()+"");
        	mp.setText(player.getCurrent_match_lose()+"");
        	goal.setText(player.getCurrent_goal()+"");
        	assis.setText(player.getCurrent_assistance()+"");
        	point.setText(player.getCurrent_point()+"");
    	}
    }
    
    public interface FragmentCallback{
    	public void onTaskDone();
    }
    
    class PlayerLoadTask extends AsyncTask<String, String, String> {
    	private FragmentCallback callback;
    	
    	public PlayerLoadTask(FragmentCallback callback){
    		this.callback = callback;
    	}
        @Override
        protected void onPreExecute() {
            pDialog = ProgressDialog.show(getActivity(), "",
            		getResources().getString(R.string.loading), true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(URL+player_id);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                // if this is null the web service returned an empty page
                if (httpEntity == null) // response is empty so exit out
                    return null;

                String jsonString = EntityUtils.toString(httpEntity);
                if (jsonString != null && jsonString.length() != 0) 
                {
                	player = gson.fromJson(jsonString, Player.class);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            callback.onTaskDone();
            if(Data.getInstance().getPlayersData()==null){
            	Data.getInstance().setPlayersData(new ArrayList<Player>());
            }
            Data.getInstance().getPlayersData().add(player);
        }
}
}
