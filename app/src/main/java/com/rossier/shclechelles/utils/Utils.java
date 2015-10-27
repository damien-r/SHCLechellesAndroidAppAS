package com.rossier.shclechelles.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.rossier.data.Match;
import com.rossier.shclechelles.R;

public class Utils {
	public static int getLogo(String team_name){
		if(team_name.contains("Léchelles"))return R.drawable.lechelles_t; 
		else if(team_name.contains("Léchelles"))return R.drawable.lechelles_t;
		else if(team_name.contains("Aumont"))return R.drawable.aumont_t;
		else if(team_name.contains("Rolling"))return R.drawable.rolling_t;
		else if(team_name.contains("Cheyres"))return R.drawable.cheyres_t;
		else if(team_name.contains("Dogs"))return R.drawable.gletterens_t;   
		else if(team_name.contains("Ducks"))return R.drawable.domdidier_t;
		else if(team_name.contains("Dzos"))return R.drawable.dzos_t;
		else if(team_name.contains("Esta"))return R.drawable.estavayer_t;
		else if(team_name.contains("Grolley"))return R.drawable.grolley_t;
		else if(team_name.contains("Aire"))return R.drawable.aire_t;
		else if(team_name.contains("Montagny"))return R.drawable.montagny_t;
		else if(team_name.contains("Montbrelloz"))return R.drawable.montbrelloz_t;
		else if(team_name.contains("Murist"))return R.drawable.murist_t;
		else if(team_name.contains("Payerne"))return R.drawable.payerne_t;
		else if(team_name.contains("Torniaco"))return R.drawable.torniaco_t;
		else if(team_name.contains("Vully"))return R.drawable.vully_t;
		else if(team_name.contains("Minotor"))return R.drawable.minotors_t;
		else return R.drawable.lechelles_t;
	}
	
	public static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
	
	public static void setMatch(Match match){
		
	}

}
