package com.rossier.data;

public class Penality implements Comparable<Penality>{
	
	private String start;
	private String end;
	private PlayerLight player;
	private String duration;
	private String code;
	private boolean isHome;
	public Penality(String start, String end, PlayerLight player,
			String duration, String code) {
		this.start = start;
		this.end = end;
		this.player = player;
		this.duration = duration;
		this.code = code;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public PlayerLight getPlayer() {
		return player;
	}
	public void setPlayer(PlayerLight player) {
		this.player = player;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isHome() {
		return isHome;
	}
	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}
	
	@Override
	public int compareTo(Penality another) {
		String[] timehS = start.split("'");
		String[] timeaS = another.getStart().split("'");
		if(Integer.parseInt(timehS[0])>Integer.parseInt(timeaS[0]))return-1;
		else if(Integer.parseInt(timehS[0])<Integer.parseInt(timeaS[0]))return 1;
		else{
			if(Integer.parseInt(timehS[1])>Integer.parseInt(timeaS[1]))return-1;
			else return 1;
		}
	}
	
	
}
