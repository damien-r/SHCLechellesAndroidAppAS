package com.rossier.data;

public class Goal implements Comparable{

	private PlayerLight scorer;
	private PlayerLight assitant;
	private String time;
	private boolean isHome;
	private long id;
	
	public Goal(PlayerLight scorer, PlayerLight assitant, String time)  {
		this.scorer = scorer;
		this.assitant = assitant;
		this.time = time;
	}

	public PlayerLight getScorer() {
		return scorer;
	}

	public void setScorer(PlayerLight scorer) {
		this.scorer = scorer;
	}

	public PlayerLight getAssitant() {
		return assitant;
	}

	public void setAssitant(PlayerLight assitant) {
		this.assitant = assitant;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isHome() {
		return isHome;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}

	@Override
	public int compareTo(Object another) {
		String[] timehS = time.split("'");
		String[] timeaS = ((Goal)another).getTime().split("'");
		if(Integer.parseInt(timehS[0])>Integer.parseInt(timeaS[0]))return-1;
		else if(Integer.parseInt(timehS[0])<Integer.parseInt(timeaS[0]))return 1;
		else{
			if(Integer.parseInt(timehS[1])>Integer.parseInt(timeaS[1]))return-1;
			else return 1;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
	
	
}
