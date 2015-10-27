package com.rossier.data;

import java.util.Date;
import java.util.List;

public class Data {
	public long timestamp;
	public int id;
	//public final long TIME_LIGHT_PLAYERS_TEAMS=1000*6; //dev porpose
	private List<Player> playersData;
	private List<PlayerLight> playersLightData;
	private List<Team> teamsData;
	private List<TeamLight> teamsLightData;
	private List<MatchLight> last_club_matchs;
	private List<MatchLight> next_club_matchs;
	private List<Match> matchs;
	private List<Match> current_matchs;
	private Date last_update = new Date();
	private static Data instance;
	
	public static Data getInstance(){
		if(instance==null)instance = new Data();
		return instance;
	}
	/*
	 * Id section
	 */
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getLast_update() {
		return last_update;
	}

	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}

	public List<Player> getPlayersData() {
		return playersData;
	}

	public void setPlayersData(List<Player> playersData) {
		this.playersData = playersData;
	}

	public List<PlayerLight> getPlayersLightData() {
		return playersLightData;
	}

	public void setPlayersLightData(List<PlayerLight> playersLightData) {
		this.playersLightData = playersLightData;
	}

	public List<Team> getTeamsData() {
		return teamsData;
	}

	public void setTeamsData(List<Team> teamsData) {
		this.teamsData = teamsData;
	}

	public List<TeamLight> getTeamsLightData() {
		return teamsLightData;
	}

	public void setTeamsLightData(List<TeamLight> teamsLightData) {
		this.teamsLightData = teamsLightData;
	}

	public List<MatchLight> getLast_club_matchs() {
		return last_club_matchs;
	}

	public void setLast_club_matchs(List<MatchLight> last_club_matchs) {
		this.last_club_matchs = last_club_matchs;
	}

	public List<MatchLight> getNext_club_matchs() {
		return next_club_matchs;
	}

	public void setNext_club_matchs(List<MatchLight> next_club_matchs) {
		this.next_club_matchs = next_club_matchs;
	}

	public List<Match> getMatchs() {
		return matchs;
	}

	public void setMatchs(List<Match> matchs) {
		this.matchs = matchs;
	}
	
	
	
	public List<Match> getCurrent_matchs() {
		return current_matchs;
	}

	public void setCurrent_matchs(List<Match> current_matchs) {
		this.current_matchs = current_matchs;
	}

	public Player getPlayer(int id){
		if(playersData==null)return null;
		for(Player player:playersData){
			if(player.getId()==id)return player;
		}
		return null;
	}
	
	public Team getTeam(int id){
		if(teamsData==null)return null;
		for(Team team:teamsData){
			if(team.getId()==id)return team;
		}
		return null;
	}
	
	public Match getMatch(int id){
		if(matchs==null)return null;
		for(Match match:matchs){
			if(match.getId()==id)return match;
		}
		return null;
	}
	
	
	
	
}
