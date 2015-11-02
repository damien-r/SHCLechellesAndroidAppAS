package com.rossier.data;

public class MatchNotification {
	
	private String team_home;
	private String team_away;
	private int result_home;
	private int result_away;
	private String ligue;
	private String icon_away;
	private String icon_home;
	public MatchNotification(String team_home, String team_away, int result_home, int result_away, String ligue, String icon_home, String icon_away) {
		super();
		this.team_home = team_home;
		this.team_away = team_away;
		this.result_home = result_home;
		this.result_away = result_away;
		this.ligue = ligue;
		this.icon_away = icon_away;
		this.icon_home = icon_home;
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
	public String getLigue() {
		return ligue;
	}
	public void setLigue(String ligue) {
		this.ligue = ligue;
	}
	public String getIcon_away() {
		return icon_away;
	}
	public void setIcon_away(String icon_away) {
		this.icon_away = icon_away;
	}
	public String getIcon_home() {
		return icon_home;
	}
	public void setIcon_home(String icon_home) {
		this.icon_home = icon_home;
	}
	
	
	

}
