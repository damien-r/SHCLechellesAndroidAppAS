/**
 * 
 */
package com.rossier.data;

import java.util.ArrayList;

/**
 * @author Florian
 *
 */
public class Team extends Data {
	private final String BASE_URL ="http://www.fshbr.ch/equipes/equipe/";
	private String name;
	private String description;
	private ArrayList<PlayerLight> players;
	private ArrayList<MatchLight> next_matchs;
	private ArrayList<MatchLight> last_matchs;
	private ArrayList<TeamRanking> ranking;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<PlayerLight> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<PlayerLight> players) {
		this.players = players;
	}

	public ArrayList<MatchLight> getNext_matchs() {
		return next_matchs;
	}

	public void setNext_matchs(ArrayList<MatchLight> next_matchs) {
		this.next_matchs = next_matchs;
	}

	public ArrayList<MatchLight> getLast_matchs() {
		return last_matchs;
	}

	public void setLast_matchs(ArrayList<MatchLight> last_matchs) {
		this.last_matchs = last_matchs;
	}

	public ArrayList<TeamRanking> getRanking() {
		return ranking;
	}

	public void setRanking(ArrayList<TeamRanking> ranking) {
		this.ranking = ranking;
	}
	
	
}
