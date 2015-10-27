package com.rossier.data;

import java.util.ArrayList;

public class Match extends Data {
	private final String BASE_URL = "http://www.fshbr.ch/matchs/match/";
	private String code;
	private String date_loc;
	private String team_home;
	private String team_away;
	private String team_home_icon;
	private String team_away_icon;
	private int id_home;
	private int id_away;
	private int result_home;
	private int result_away;
	private ArrayList<PlayerLight> players_home;
	private ArrayList<PlayerLight> players_away;
	private String[] stats_home;
	private String[] stats_away;
	private ArrayList<Penality> penalitys;
	private ArrayList<Goal> scorers_home;
	private ArrayList<Goal> scorers_away;
	private String arbitre_princ;
	private String arbitre_assistant;
	private String timer;
	private String marker;
	private String nb_viewer;
	private boolean playing;
	private String ligue;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDate_loc() {
		return date_loc;
	}
	public void setDate_loc(String date_loc) {
		this.date_loc = date_loc;
	}
	public String getTeam_home() {
		return team_home;
	}
	public void setTeam_home(String team_home) {
		this.team_home = team_home;
	}
	public String getTeam_away() {
		return team_away;
	}
	public void setTeam_away(String team_away) {
		this.team_away = team_away;
	}
	public String getTeam_home_icon() {
		return team_home_icon;
	}
	public void setTeam_home_icon(String team_home_icon) {
		this.team_home_icon = team_home_icon;
	}
	public String getTeam_away_icon() {
		return team_away_icon;
	}
	public void setTeam_away_icon(String team_away_icon) {
		this.team_away_icon = team_away_icon;
	}
	public int getId_home() {
		return id_home;
	}
	public void setId_home(int id_home) {
		this.id_home = id_home;
	}
	public int getId_away() {
		return id_away;
	}
	public void setId_away(int id_away) {
		this.id_away = id_away;
	}
	public int getResult_home() {
		return result_home;
	}
	public void setResult_home(int result_home) {
		this.result_home = result_home;
	}
	public int getResult_away() {
		return result_away;
	}
	public void setResult_away(int result_away) {
		this.result_away = result_away;
	}
	public ArrayList<PlayerLight> getPlayers_home() {
		return players_home;
	}
	public void setPlayers_home(ArrayList<PlayerLight> players_home) {
		this.players_home = players_home;
	}
	public ArrayList<PlayerLight> getPlayers_away() {
		return players_away;
	}
	public void setPlayers_away(ArrayList<PlayerLight> players_away) {
		this.players_away = players_away;
	}
	public String[] getStats_home() {
		return stats_home;
	}
	public void setStats_home(String[] stats_home) {
		this.stats_home = stats_home;
	}
	public String[] getStats_away() {
		return stats_away;
	}
	public void setStats_away(String[] stats_away) {
		this.stats_away = stats_away;
	}
	public ArrayList<Penality> getPenalitys() {
		return penalitys;
	}
	public void setPenalitys(ArrayList<Penality> penalitys) {
		this.penalitys = penalitys;
	}
	public ArrayList<Goal> getScorers_home() {
		return scorers_home;
	}
	public void setScorers_home(ArrayList<Goal> scorers_home) {
		this.scorers_home = scorers_home;
	}
	public ArrayList<Goal> getScorers_away() {
		return scorers_away;
	}
	public void setScorers_away(ArrayList<Goal> scorers_away) {
		this.scorers_away = scorers_away;
	}
	public String getArbitre_princ() {
		return arbitre_princ;
	}
	public void setArbitre_princ(String arbitre_princ) {
		this.arbitre_princ = arbitre_princ;
	}
	public String getArbitre_assistant() {
		return arbitre_assistant;
	}
	public void setArbitre_assistant(String arbitre_assistant) {
		this.arbitre_assistant = arbitre_assistant;
	}
	public String getTimer() {
		return timer;
	}
	public void setTimer(String timer) {
		this.timer = timer;
	}
	public String getMarker() {
		return marker;
	}
	public void setMarker(String marker) {
		this.marker = marker;
	}
	public String getNb_viewer() {
		return nb_viewer;
	}
	public void setNb_viewer(String nb_viewer) {
		this.nb_viewer = nb_viewer;
	}
	public boolean isPlaying() {
		return playing;
	}
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	public String getLigue() {
		return ligue;
	}
	public void setLigue(String ligue) {
		this.ligue = ligue;
	}
	
	
}



