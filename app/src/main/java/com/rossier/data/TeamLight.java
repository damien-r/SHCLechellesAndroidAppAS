package com.rossier.data;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class TeamLight {
	private int id;
	private String name;
	private String image;
	private int nb_players;
	private String ligue;
	private Date last_update;
	
	public TeamLight(int id,String name, String image) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		last_update = new Date();
	}
	
	public TeamLight(int id,String name, String image,int nb_players, String ligue) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.nb_players = nb_players;
		this.ligue = ligue;
		last_update = new Date();
	}

	public Date getLast_update() {
		return last_update;
	}

	public void setLast_update(Date last_update) {
		this.last_update = last_update;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getNb_players() {
		return nb_players;
	}

	public void setNb_players(int nb_players) {
		this.nb_players = nb_players;
	}

	public String getLigue() {
		return ligue;
	}

	public void setLigue(String ligue) {
		this.ligue = ligue;
	}

	public String getTopicId(){
		String id = getName()+getLigue();
		id = id.replace(" ","").trim();
		id = StringUtils.stripAccents(id);
		return id;
	}

	
	
	
	
	

}
