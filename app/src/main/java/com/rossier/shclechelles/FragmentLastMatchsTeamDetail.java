package com.rossier.shclechelles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rossier.data.Data;
import com.rossier.data.Match;
import com.rossier.data.Team;
import com.rossier.shclechelles.adapter.MatchLightAdapter;
import com.rossier.shclechelles.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FragmentLastMatchsTeamDetail extends Fragment {

	Team team;
	private static String URL_MATCH_DETAIL = "http://1-dot-shcrestservice.appspot.com/rest/matchs/match/";
	private ProgressDialog pDialog;
	private Gson gson = new Gson();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        team = new Gson().fromJson(getArguments().getString("team"),Team.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.team_detail_last_tab_layout, container, false);
        
        //ranking fill data
        ListView ranking = (ListView) v.findViewById(R.id.team_detail_last_matchs);
        ranking.setAdapter(new MatchLightAdapter(getActivity(),R.layout.match_row_layout,team.getLast_matchs()));
        ranking.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				new MatchsDetailLoadTask(team.getLast_matchs().get(position).getId()).execute();
			}
		
        });
        
        return v;
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
			if (!Utils.isNetworkAvailable(getActivity())) {
				Toast.makeText(
						getActivity(),
						"Aucune connexion n'est disponible, Veuillez réessayer plus tard",
						Toast.LENGTH_SHORT).show();
				cancel(true);
			} else {
				pDialog = ProgressDialog.show(getActivity(), "", getResources()
						.getString(R.string.loading), true);
			}
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(URL_MATCH_DETAIL + id);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();

				// if this is null the web service returned an empty page
				if (httpEntity == null) // response is empty so exit out
					return null;

				String jsonString = EntityUtils.toString(httpEntity);

				if (jsonString != null && jsonString.length() != 0) {
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
			if (pDialog != null && pDialog.isShowing())
				pDialog.dismiss();
			if (match == null) {
				Toast.makeText(getActivity(),
						"Je ne peux pas trouver ce match, dommage",
						Toast.LENGTH_SHORT).show();

			} else {
				if(Data.getInstance().getMatchs()==null)Data.getInstance().setMatchs(new ArrayList<Match>());
				Data.getInstance().getMatchs().add(match);
				Intent firstpage = new Intent(getActivity(),
						MatchDetailFragment.class);
				firstpage.putExtra("match", new Gson().toJson(match));
				getActivity().startActivity(firstpage);
			}
		}
	}
}