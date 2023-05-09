package com.example.demo.model;


import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class MovieQueue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int queueid;
	private int availableseats;
	private Date date;
	
	
	@ManyToOne
	private Movie movie;
	
	@ManyToOne
	private Shows shows;

	public int getQueueid() {
		return queueid;
	}

	public void setQueueid(int queueid) {
		this.queueid = queueid;
	}

	public int getAvailableseats() {
		return availableseats;
	}

	public void setAvailableseats(int availableseats) {
		this.availableseats = availableseats;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Shows getShows() {
		return shows;
	}

	public void setShows(Shows shows) {
		this.shows = shows;
	}
	
	

}
