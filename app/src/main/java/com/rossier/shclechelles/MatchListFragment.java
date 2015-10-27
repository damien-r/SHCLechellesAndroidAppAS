package com.rossier.shclechelles;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.rossier.data.Match;
import com.rossier.data.MatchLight;
import com.rossier.shclechelles.adapter.MatchAdapter;
import com.rossier.shclechelles.adapter.MatchLightAdapter;
import com.rossier.shclechelles.utils.ListFragmentSwipeRefreshLayout;
import com.rossier.shclechelles.utils.Utils;

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

public class MatchListFragment extends ListFragment {
	OnMatchSelectedListener mCallback;
	private static String URL_CURRENT = "http://1-dot-shcrestservice.appspot.com/rest/matchs/current";
	private static String URL_LAST = "http://1-dot-shcrestservice.appspot.com/rest/matchs/last/";
	private static String URL_NEXT = "http://1-dot-shcrestservice.appspot.com/rest/matchs/next/";
	private static String URL_MATCH_DETAIL = "http://1-dot-shcrestservice.appspot.com/rest/matchs/match/";
	private static int userID;
	List<MatchLight> matchList;
	List<Match> currentMatchList;
	ListView matchView;
	private LayoutInflater inflater;
	private Gson gson = new Gson();
	private ProgressDialog pDialog;
	private String type;
	private int nb;
	private boolean isData= true;
	private SwipeRefreshLayout layout;
	private OnRefreshListener listener;
	private boolean first = true;
	public interface OnMatchSelectedListener {
	    /** Called by HeadlinesFragment when a list item is selected */
	    public void onMatchSelected(int position);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); 
	    setRetainInstance(true);
	    initLayout();
	    if(!isData){
	    		new MatchsLoadTask().execute();
        }
	    if(!isData)setListAdapter(new MatchLightAdapter(getActivity().getApplicationContext(), R.layout.match_row_layout, matchList));

	}
	
	private void initLayout() {         
	    if(getActivity().getIntent()!=null) {
	        userID = getActivity().getIntent().getIntExtra("id", 0);
	    } else {
	        return;
	    }
	    matchView = getListView();
	    if(matchList==null)matchList = new ArrayList<MatchLight>();
	    if(currentMatchList==null)currentMatchList = new ArrayList<Match>();


	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//View view = inflater.inflate(R.layout.fragment_player, container,false);
		View view = super.onCreateView(inflater, container, savedInstanceState);
		layout = new ListFragmentSwipeRefreshLayout(container.getContext(),this);
		layout.addView(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
	    listener = new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new MatchsLoadTask().execute();
				
			}
		};
		layout.setOnRefreshListener(listener);
	    return layout;
	}
	
	@Override
	public void onStart() {
	    super.onStart();

	    // When in two-pane layout, set the listview to highlight the selected
	    // list item
	    // (We do this during onStart because at the point the listview is
	    // available.)
	    if (getFragmentManager().findFragmentById(R.id.match_lastMatch_fragment) != null) {
	    	matchView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	    }
	}

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
		// Notify the parent activity of selected item
		  if(type.equals("current")){
			  Intent firstpage= new Intent(getActivity(),MatchDetailFragment.class);
			  Match match = null;
			  firstpage.putExtra("match", new Gson().toJson(currentMatchList.get(position)));
			  getActivity().startActivity(firstpage);
		  }else{
			  new MatchsDetailLoadTask(matchList.get(position).getId()).execute();
		  }
	  }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ARG_MATCH_TYPE = "match_type";
	private static final String ARG_MATCH_NB = "match_nb";
    public static MatchListFragment newInstance(int id,String type,int nb) {
    	MatchListFragment fragment = new MatchListFragment();
    	Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, id);
        args.putString(ARG_MATCH_TYPE, type);
        args.putInt(ARG_MATCH_NB, nb);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        type = getArguments().getString(ARG_MATCH_TYPE);
        nb = getArguments().getInt(ARG_MATCH_NB);
        if(type.equals("last")&&Data.getInstance().getLast_club_matchs()!=null){
        	matchList = Data.getInstance().getLast_club_matchs();
        	setListAdapter(new MatchLightAdapter(getActivity().getApplicationContext(), R.layout.match_row_layout, matchList));
        	first = false;
        }else if(type.equals("next")&&Data.getInstance().getNext_club_matchs()!=null){
        	matchList = Data.getInstance().getNext_club_matchs();
        	setListAdapter(new MatchLightAdapter(getActivity().getApplicationContext(), R.layout.match_row_layout, matchList));
        	first = false;
        }else if(type.equals("current")&&Data.getInstance().getCurrent_matchs()!=null){
        	ArrayList<Match> list = (ArrayList<Match>) Data.getInstance().getCurrent_matchs();
        	currentMatchList = (List<Match>) list.clone();
        	setListAdapter(new MatchAdapter(getActivity().getApplicationContext(), R.layout.match_row_layout, currentMatchList));
        	first = false;
        }else{
        	isData = !isData;
        }
    }
    
    class MatchsLoadTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
        	if(!Utils.isNetworkAvailable(getActivity())){
        		Toast.makeText(getActivity(), "Aucune connexion n'est disponible, Veuillez réessayer plus tard",
                    Toast.LENGTH_SHORT).show();
        		cancel(true);
        		layout.setRefreshing(false);
        	}else{
        		if(first){
        			pDialog = ProgressDialog.show(getActivity(), "",
        					getResources().getString(R.string.loading), true);
        			first =!first;
        		}
        	}
        }

        @Override
        protected String doInBackground(String... params) {
              String url = "";       
            if(type.equals("current"))url = URL_CURRENT;
            else if(type.equals("last"))url = URL_LAST+nb;
            else if(type.equals("next"))url=URL_NEXT+nb;
        	try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                // if this is null the web service returned an empty page
                if (httpEntity == null) // response is empty so exit out
                    return null;

                String jsonString = EntityUtils.toString(httpEntity);

                if (jsonString != null && jsonString.length() != 0) 
                {
                	if(type.equals("current")){
                		Type listType = new TypeToken<List<Match>>() {}.getType();
                		currentMatchList = gson.fromJson(jsonString, listType);
                	}else{
                		Type listType = new TypeToken<List<MatchLight>>() {}.getType();
                    	matchList = gson.fromJson(jsonString, listType);
                	}
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
            if (matchList.size() == 0&&currentMatchList.size() == 0) {
                Toast.makeText(getActivity(), "No match in the list",
                        Toast.LENGTH_SHORT).show();

            } else if(matchView != null) {
            	if(type.equals("current")){
            		setListAdapter(new MatchAdapter(getActivity().getApplicationContext(), R.layout.match_row_layout, currentMatchList));
            		Data.getInstance().setCurrent_matchs(currentMatchList);
            	}else{
            		setListAdapter(new MatchLightAdapter(getActivity().getApplicationContext(), R.layout.match_row_layout, matchList));
            		if(type.equals("last"))Data.getInstance().setLast_club_matchs(matchList);
            		else Data.getInstance().setNext_club_matchs(matchList);
            		Data.getInstance().setMatchs(new ArrayList<Match>());
            	}
            	layout.setRefreshing(false);
            	Toast.makeText(getActivity(), "Mise à jour réussie",
                        Toast.LENGTH_SHORT).show();
            }
        }
        
        
}
    class MatchsDetailLoadTask extends AsyncTask<String, String, String> {
    	int id;
    	Match match;
    	
        public MatchsDetailLoadTask(int id) {
			super();
			this.id = id;
		}

		@Override
        protected void onPreExecute() {
        	if(!Utils.isNetworkAvailable(getActivity())){
        		Toast.makeText(getActivity(), "Aucune connexion n'est disponible, Veuillez réessayer plus tard",
                    Toast.LENGTH_SHORT).show();
        		cancel(true);
        		layout.setRefreshing(false);
        	}else{
    			pDialog = ProgressDialog.show(getActivity(), "",
    					getResources().getString(R.string.loading), true);
        	}
        }

        @Override
        protected String doInBackground(String... params) {    
        	try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(URL_MATCH_DETAIL+id);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                // if this is null the web service returned an empty page
                if (httpEntity == null) // response is empty so exit out
                    return null;

                String jsonString = EntityUtils.toString(httpEntity);

                if (jsonString != null && jsonString.length() != 0) 
                {
                	match = gson.fromJson(jsonString, Match.class);
                	
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
            if (match == null) {
                Toast.makeText(getActivity(), "Je ne peux pas trouver ce match, dommage",
                        Toast.LENGTH_SHORT).show();

            } else if(matchView != null) {
            	Data.getInstance().getMatchs().add(match);
            	Intent firstpage= new Intent(getActivity(),MatchDetailFragment.class);
  			  	firstpage.putExtra("match", new Gson().toJson(match));
  			  	getActivity().startActivity(firstpage);
            }
        }
        
        
}
}
