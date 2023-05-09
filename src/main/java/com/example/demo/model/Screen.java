package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Screen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int screenid;
	private String screename;
	
	@ManyToOne
	private Multiplex multiplex;
	
	@OneToMany
	private List<Shows> shows;

	public int getScreenid() {
		return screenid;
	}

	public void setScreenid(int screenid) {
		this.screenid = screenid;
	}

	public String getScreename() {
		return screename;
	}

	public void setScreename(String screename) {
		this.screename = screename;
	}

	public Multiplex getMultiplex() {
		return multiplex;
	}

	public void setMultiplex(Multiplex multiplex) {
		this.multiplex = multiplex;
	}

	public List<Shows> getShows() {
		return shows;
	}

	public void setShows(List<Shows> shows) {
		this.shows = shows;
	}
	
	

}