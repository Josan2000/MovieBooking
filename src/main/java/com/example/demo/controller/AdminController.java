package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


import com.example.demo.interfaces.BookingRepository;
import com.example.demo.interfaces.LoginRepository;
import com.example.demo.interfaces.MovieQueueRepository;
import com.example.demo.interfaces.MovieRepository;
import com.example.demo.interfaces.MultiplexRepository;
import com.example.demo.interfaces.ScreenRepository;
import com.example.demo.interfaces.ShowsRepository;
import com.example.demo.interfaces.UserRepository;
import com.example.demo.model.Movie;
import com.example.demo.model.MovieQueue;
import com.example.demo.model.MovieSample;
import com.example.demo.model.Multiplex;
import com.example.demo.model.Screen;
import com.example.demo.model.Shows;
import com.example.demo.model.Error;
import com.example.demo.exception.*;

@RestController
@CrossOrigin("http://localhost:4200")
public class AdminController {
	
	@Autowired
	MovieRepository mRepo;
	@Autowired
	MultiplexRepository muRepo;
	@Autowired
	ScreenRepository sRepo;
	@Autowired
	ShowsRepository ssRepo;
	@Autowired
	MovieQueueRepository mqRepo;
	@Autowired
	BookingRepository bookRepo;

	@Autowired
	UserRepository uRepo;

	@Autowired
	LoginRepository loginRepo;
	
	
	
	@PostMapping("registermovie")
	public ResponseEntity movie(@RequestBody Movie movie) {

//		movie.setTitle(movie.getTitle());
//		movie.setGenre(movie.getGenre());
//		movie.setPrice(movie.getPrice());
//		movie.setImageurl(movie.getImageurl());

		// movie.setTheatre(movie.getTheatre());
		mRepo.save(movie);
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}

	@PostMapping("registermultiplex")
	public ResponseEntity multiplex(@RequestBody Multiplex multiplex) {
		multiplex.setMultiplexname(multiplex.getMultiplexname());
		multiplex.setLocation(multiplex.getLocation());

		muRepo.save(multiplex);
		return new ResponseEntity<Multiplex>(multiplex, HttpStatus.OK);
	}

	@PostMapping("registerscreen")
	public ResponseEntity screen(@RequestBody Screen screen) {

		screen.setScreename(screen.getScreename());
		screen.setMultiplex(screen.getMultiplex());
		sRepo.save(screen);
		return new ResponseEntity<Screen>(screen, HttpStatus.OK);

	}

	@PostMapping("registershow")
	public ResponseEntity shows(@RequestBody Shows shows) {
		shows.setShowname(shows.getShowname());
		shows.setScreen(shows.getScreen());
		shows.setShowtime(shows.getShowtime());
		ssRepo.save(shows);
		return new ResponseEntity<Shows>(shows, HttpStatus.OK);

	}

	@GetMapping("getallmultiplex")
	public ResponseEntity getmultiplex() {
		return new ResponseEntity<List<Multiplex>>(muRepo.findAll(), HttpStatus.OK);

	}

	@GetMapping("getallscreen")
	public ResponseEntity getscreen() {
		return new ResponseEntity<List<Screen>>(sRepo.findAll(), HttpStatus.OK);

	}

	@GetMapping("getallmovies")
	public ResponseEntity getmovies() {
		return new ResponseEntity<List<Movie>>(mRepo.findAll(), HttpStatus.OK);

	}

	@PostMapping("moviequeue")
	public ResponseEntity movieQueue(@RequestBody MovieSample moviesample) {
		List<MovieQueue> queues = new ArrayList<>();
		LocalDate startDate = LocalDate.parse(moviesample.getFromdate());
		LocalDate endDate = LocalDate.parse(moviesample.getTodate());
		while (!startDate.equals(endDate)) {
			java.sql.Date sqlDate = java.sql.Date.valueOf(startDate);
			MovieQueue queue = new MovieQueue();
			queue.setAvailableseats(moviesample.getAvailableseats());
			queue.setDate(sqlDate);
			queue.setMovie(moviesample.getMovie());
			queue.setShows(moviesample.getShows());
			queues.add(queue);
			startDate = startDate.plusDays(1);
			System.out.println(startDate);
		}
		mqRepo.saveAll(queues);

		return new ResponseEntity<List<MovieQueue>>(queues, HttpStatus.OK);
	}
	
	@ExceptionHandler(UserDefinedException.class)
	public ResponseEntity existUser(Exception e) {
		Error er=new Error();
		er.setErCode(HttpStatus.NOT_FOUND.toString());
		er.setErMsg(e.getMessage());
		return new ResponseEntity(er,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity idNotFoundException(Exception e) {
		Error er= new Error();
		er.setErCode(HttpStatus.NOT_FOUND.toString());
		er.setErMsg(e.getMessage());
		return new ResponseEntity(er,HttpStatus.NOT_FOUND);
	}
	

}
