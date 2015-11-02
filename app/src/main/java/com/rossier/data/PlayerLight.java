package com.rossier.data;

import java.util.Date;

public class PlayerLight implements Comparable{

	private int id;
	private String jersey_num;
	private String name;
	private String position;
	private Date last_update;
	private boolean isHome;
	
	public PlayerLight(int id, String jersey_num, String name, String position) {
		super();
		this.id = id;
		this.jersey_num = jersey_num;
		this.name = name;
		this.position = position;
		last_update = new Date();
	}

	public Date getLast_update() {
		return last_update;
	}

	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}

	public String getJersey_num() {
		return jersey_num;
	}

	public void setJersey_num(String jersey_num) {
		this.jersey_num = jersey_num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isHome() {
		return isHome;
	}

	public void setIsHome(boolean isHome) {
		this.isHome = isHome;
	}

	@Override
	public int compareTo(Object another) {
		PlayerLight anotherP = (PlayerLight) another;
		if (isHome && !anotherP.isHome()) return -1;
		if (!isHome && anotherP.isHome()) return 1;
		return 0;
	}
}
