package com.rossier.shclechelles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rossier.data.Data;
import com.rossier.data.Match;
import com.rossier.data.MatchLight;
import com.rossier.data.Team;
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

public class FragmentGeneralTeamDetail extends Fragment {

	Team team;
	MatchLight last_match;
	MatchLight next_match;
	private static String URL_MATCH_DETAIL = "http://1-dot-shcrestservice.appspot.com/rest/matchs/match/";
	private ProgressDialog pDialog;
	private Gson gson = new Gson();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		team = new Gson()
				.fromJson(getArguments().getString("team"), Team.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.team_detail_general_tab_layout,
				container, false);

		// last match fill data
		RelativeLayout last_layout = (RelativeLayout) v
				.findViewById(R.id.last_match_container);
		last_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new MatchsDetailLoadTask(last_match.getId()).execute();

			}
		});
		
		TextView team_home_last = (TextView) v
				.findViewById(R.id.team_detail_general_last_home);
		TextView team_away_last = (TextView) v
				.findViewById(R.id.team_detail_general_last_away);
		TextView team_home_result_last = (TextView) v
				.findViewById(R.id.team_detail_general_last_result_home);
		TextView team_away_result_last = (TextView) v
				.findViewById(R.id.team_detail_general_last_result_away);
		TextView vs_last = (TextView) v
				.findViewById(R.id.team_detail_general_vs);
		TextView tier_last = (TextView) v
				.findViewById(R.id.team_detail_general_last_tier);
		ImageView logo_home_last = (ImageView) v
				.findViewById(R.id.team_detail_general_last_loc_logo);
		ImageView logo_away_last = (ImageView) v
				.findViewById(R.id.team_detail_general_last_away_logo);
		TextView ligue_last = (TextView) v.findViewById(R.id.liguelast);

		last_match = team.getLast_matchs().size() == 0 ? null : team
				.getLast_matchs().get(0);
		if (last_match != null) {
			team_home_last.setText(last_match.getTeam_home().getName());
			team_away_last.setText(last_match.getTeam_away().getName());
			ligue_last.setText(last_match.getLigue());
			if (last_match.getScore_away() == -1
					&& last_match.getScore_home() == -1) {
				vs_last.setText("VS");
				team_home_result_last.setText("");
				team_away_result_last.setText("");
				tier_last.setText(last_match.getDate_loc());
			} else {
				team_home_result_last.setText(last_match.getScore_home() + "");
				team_away_result_last.setText(last_match.getScore_away() + "");
				tier_last.setText(last_match.getDate_loc());
			}
			logo_home_last.setImageResource(Utils.getLogo(last_match
					.getTeam_home().getName()));
			logo_away_last.setImageResource(Utils.getLogo(last_match
					.getTeam_away().getName()));
		} else {
			team_home_last.setText("");
			team_away_last.setText("");
			vs_last.setText("Aucune données");
			team_home_result_last.setText("");
			team_away_result_last.setText("");
			tier_last.setText("");
			logo_home_last.setImageResource(Utils.getLogo(""));
			logo_away_last.setImageResource(Utils.getLogo(""));
		}

		RelativeLayout next_layout = (RelativeLayout) v
				.findViewById(R.id.next_match_container);
		// next match fill data
		TextView team_home_next = (TextView) v
				.findViewById(R.id.team_detail_general_next_home);
		TextView team_away_next = (TextView) v
				.findViewById(R.id.team_detail_general_next_away);
		TextView vs_next = (TextView) v.findViewById(R.id.team_detail__vs);
		TextView tier_next = (TextView) v.findViewById(R.id.team_detail_tiers);
		ImageView logo_home_next = (ImageView) v
				.findViewById(R.id.team_detail_general_next_loc_logo);
		ImageView logo_away_next = (ImageView) v
				.findViewById(R.id.team_detail_general_next_away_logo);
		TextView ligue_next = (TextView) v.findViewById(R.id.liguenext);
		next_match = team.getNext_matchs().size() == 0 ? null : team
				.getNext_matchs().get(0);
		if (next_match != null) {
			ligue_next.setText(next_match.getLigue());
			team_home_next.setText(next_match.getTeam_home().getName());
			team_away_next.setText(next_match.getTeam_away().getName());
			logo_home_next.setImageResource(Utils.getLogo(next_match
					.getTeam_home().getName()));
			logo_away_next.setImageResource(Utils.getLogo(next_match
					.getTeam_away().getName()));
			tier_next.setText(next_match.getDate_loc());
		} else {
			team_home_next.setText("");
			team_away_next.setText("");
			vs_next.setText("Aucune données");
			tier_next.setText("");
			logo_home_next.setImageResource(Utils.getLogo(""));
			logo_away_next.setImageResource(Utils.getLogo(""));
		}
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