package com.rossier.data;

public class TeamRanking {
	private int id;
	private String name;
	private String image;
	private int match_played;
	private int match_won;
	private int match_equality;
	private int match_loss;
	private int goal_scored;
	private int goal_conceded;
	private int goal_diff;
	private int points;
	public TeamRanking(int id, String name, String image, int match_played,int match_won,
			int match_equality, int match_loss, int goal_scored,
			int goal_conceded, int goal_diff, int points) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.match_played = match_played;
		this.match_won = match_won;
		this.match_equality = match_equality;
		this.match_loss = match_loss;
		this.goal_scored = goal_scored;
		this.goal_conceded = goal_conceded;
		this.goal_diff = goal_diff;
		this.points = points;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMatch_played() {
		return match_played;
	}
	public void setMatch_played(int match_played) {
		this.match_played = match_played;
	}
	public int getMatch_won() {
		return match_won;
	}
	public void setMatch_won(int match_won) {
		this.match_won = match_won;
	}
	public int getMatch_equality() {
		return match_equality;
	}
	public void setMatch_equality(int match_equality) {
		this.match_equality = match_equality;
	}
	public int getMatch_loss() {
		return match_loss;
	}
	public void setMatch_loss(int match_loss) {
		this.match_loss = match_loss;
	}
	public int getGoal_scored() {
		return goal_scored;
	}
	public void setGoal_scored(int goal_scored) {
		this.goal_scored = goal_scored;
	}
	public int getGoal_conceded() {
		return goal_conceded;
	}
	public void setGoal_conceded(int goal_conceded) {
		this.goal_conceded = goal_conceded;
	}
	public int getGoal_diff() {
		return goal_diff;
	}
	public void setGoal_diff(int goal_diff) {
		this.goal_diff = goal_diff;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	
	
	
	
}
