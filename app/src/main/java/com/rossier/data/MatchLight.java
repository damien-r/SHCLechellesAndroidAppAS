package com.rossier.data;

import java.util.Date;

public class MatchLight{
	private int id;
	private String code;
	private String date_loc;
	private TeamLight team_home;
	private TeamLight team_away;
	private int score_home;
	private int score_away;
	private Date update_date;
	private String ligue;
	
	
	public MatchLight(int id, String code,String ligue, String date_loc, TeamLight team_home, TeamLight team_away,int score_home, int score_away) {
		super();
		this.id = id;
		this.code = code;
		this.ligue = ligue;
		this.date_loc = date_loc;
		this.team_home = team_home;
		this.team_away = team_away;
		this.score_home = score_home;
		this.score_away = score_away;
		update_date = new Date();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


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


	public TeamLight getTeam_home() {
		return team_home;
	}


	public void setTeam_home(TeamLight team_home) {
		this.team_home = team_home;
	}


	public TeamLight getTeam_away() {
		return team_away;
	}


	public void setTeam_away(TeamLight team_away) {
		this.team_away = team_away;
	}


	public int getScore_home() {
		return score_home;
	}


	public void setScore_home(int score_home) {
		this.score_home = score_home;
	}


	public int getScore_away() {
		return score_away;
	}


	public void setScore_away(int score_away) {
		this.score_away = score_away;
	}


	public Date getUpdate_date() {
		return update_date;
	}


	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	


	
	public String getLigue() {
		return ligue;
	}


	public void setLigue(String ligue) {
		this.ligue = ligue;
	}

	
	
	
	
	
	
	
	
	
}
