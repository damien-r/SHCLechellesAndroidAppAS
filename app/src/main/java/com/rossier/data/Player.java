package com.rossier.data;

public class Player extends Data implements Comparable<Player>{
	private final String BASE_URL ="http://www.fshbr.ch/joueurs/joueur/";
	private String name;
	private String description;
	//private Image image;
	private String position;
	private String habilite;
	private String nationality;
	private int age;
	private int height =-1;
	private int weight =-1;
	private int current_match_played = -1;
	private int current_match_won = -1;
	private int current_match_lose = -1;
	private int current_goal = -1;
	private int current_assistance = -1;
	private int current_point = -1;
	private int no_license = -1;
	//season is the next task, not available because it's irrevelant yet, go fuck yourself
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
	/*public Image getImage() {
		return image;
	}*/
	public String getPosition() {
		return position;
	}
	public String getNationality() {
		return nationality;
	}
	public int getAge() {
		return age;
	}
	public int getHeight() {
		return height;
	}
	public int getWeight() {
		return weight;
	}
	public int getCurrent_match_played() {
		return current_match_played;
	}
	public int getCurrent_match_won() {
		return current_match_won;
	}
	public int getCurrent_match_lose() {
		return current_match_lose;
	}
	public int getCurrent_goal() {
		return current_goal;
	}
	public int getCurrent_assistance() {
		return current_assistance;
	}
	public int getCurrent_point() {
		return current_point;
	}
	
	
	
	public int getNo_license() {
		return no_license;
	}
	public void setNo_license(int no_license) {
		this.no_license = no_license;
	}
	public String getHabilite() {
		return habilite;
	}
	public void setHabilite(String habilite) {
		this.habilite = habilite;
	}
	public String toString() {
		/*
		 * name = "feds";
			description = "feds";
			//image;
			position = "feds";
			habilite = "feds";
			nationality = "feds";
			age = 12;
			height =2;
			weight =254;
			current_match_played = 2354;
			current_match_won = 25;
			current_match_lose = 354;
			current_goal = 25;
			current_assistance = 25;
			current_point = 235;
		 */
        StringBuffer sb = new StringBuffer();
        sb.append("name: ").append(getName());
        sb.append(" - description: ").append(getDescription());
        sb.append(" - position: ").append(getPosition());
        sb.append(" - habilite:").append(getHabilite());
        sb.append(" - nationality:").append(getNationality());
        sb.append(" - age:").append(getAge());
        sb.append(" - height:").append(getHeight());
        sb.append(" - weight:").append(getWeight());
        sb.append(" - current_match_played:").append(getCurrent_match_played());
        sb.append(" - current_match_won:").append(getCurrent_match_won());
        sb.append(" - current_match_lose:").append(getCurrent_match_lose());
        sb.append(" - current_goal:").append(getCurrent_goal());
        sb.append(" - current_assistance:").append(getCurrent_assistance());
        sb.append(" - current_point:").append(getCurrent_point());
        return sb.toString();
    }
	
	@Override
	public int compareTo(Player o) {
		return o.getCurrent_point()-current_point;
	}
	
	
	
	
	
	
}
