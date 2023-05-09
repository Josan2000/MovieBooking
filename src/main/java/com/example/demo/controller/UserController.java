package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.example.demo.microservices.LoginFeignClient;
import com.example.demo.model.*;
import com.example.demo.model.Error;
import com.example.demo.exception.*;


@RestController
@CrossOrigin("http://localhost:4200")
public class UserController {
	

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
	
	@Autowired
	LoginFeignClient lFeign;
	
	

	
	@PostMapping("loginPage")
	public ResponseEntity<Login> loginUser(@RequestBody String username, String password){
		ResponseEntity<Login> l=lFeign.registerUser(username, password);
		return l;
	}
	
	
	@GetMapping("bookticket/{showid}/{userid}/{seatcount}/{ticketdate}")
	public ResponseEntity Booking(@PathVariable int seatcount, @PathVariable int showid, @PathVariable int userid,
			@PathVariable String ticketdate) {
		LocalDate date = LocalDate.parse(ticketdate);
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		MovieQueue q = mqRepo.findByShow(showid, sqlDate);
		User u = uRepo.findById(userid).get();
		Booking booking = new Booking();
		booking.setSeat_count(seatcount);
		booking.setUser(u);

		q.setAvailableseats(q.getAvailableseats() - booking.getSeat_count());
		mqRepo.save(q);

		bookRepo.save(booking);
		return new ResponseEntity<Booking>(booking, HttpStatus.OK);
	}

	@GetMapping("showmovie")
	public ResponseEntity<List<Movie>> getMovie() {

		return new ResponseEntity<List<Movie>>(mRepo.findAll(), HttpStatus.OK);

	}

	@GetMapping("theatre/{movieid}")
	public ResponseEntity<List<Multiplex>> getTheatre(@PathVariable int movieid) {

//	mqRepo.findByMovie(movieid);
		HashSet<Multiplex> multiplex = new HashSet<Multiplex>();
		List<MovieQueue> movieQueue = mqRepo.findByMovie(movieid);
		movieQueue.forEach(queue -> {
			multiplex.add(queue.getShows().getScreen().getMultiplex());
		});
		return new ResponseEntity(multiplex, HttpStatus.OK);

	}

	@GetMapping("screens/{movieid}/{theatreid}")
	public ResponseEntity<List<Screen>> getScreens(@PathVariable int movieid, @PathVariable int theatreid) {

//	mqRepo.findByMovie(movieid);
		HashSet<Screen> screen = new HashSet<Screen>();
		List<MovieQueue> movieQueue = mqRepo.findByMovie(movieid);
		movieQueue.forEach(queue -> {
			System.out.println(queue.getShows().getScreen().getMultiplex().getMultiplexid());
			if (theatreid == (int) queue.getShows().getScreen().getMultiplex().getMultiplexid()) {
				screen.add(queue.getShows().getScreen());
			}
		});
		return new ResponseEntity(screen, HttpStatus.OK);

	}

	@GetMapping("shows/{movieid}/{showid}")
	public ResponseEntity<List<Shows>> getShows(@PathVariable int movieid, @PathVariable int showid) {

//	mqRepo.findByMovie(movieid);
		HashSet<Shows> show = new HashSet<Shows>();
		List<MovieQueue> movieQueue = mqRepo.findByMovie(movieid);
		movieQueue.forEach(queue -> {
			if (showid == queue.getShows().getScreen().getScreenid()) {
				show.add(queue.getShows());
			}
		});
		return new ResponseEntity(show, HttpStatus.OK);

	}

	@GetMapping("moviequeue/{movieid}/{showid}")
	public ResponseEntity<List<Shows>> getQueue(@PathVariable int movieid, @PathVariable int showid) {

//	mqRepo.findByMovie(movieid);
		HashSet<MovieQueue> queues = new HashSet<MovieQueue>();
		List<MovieQueue> movieQueue = mqRepo.findByMovie(movieid);
		movieQueue.forEach(queue -> {
			if (showid == queue.getShows().getShowsid()) {
				queues.add(queue);
			}
		});
		return new ResponseEntity(queues, HttpStatus.OK);

	}

	@GetMapping("rawMQ/{movieid}")
	public ResponseEntity<List<Shows>> getRawMq(@PathVariable int movieid) {

//	mqRepo.findByMovie(movieid);
		List<MovieQueue> movieQueue = mqRepo.findByMovie(movieid);
		return new ResponseEntity(movieQueue, HttpStatus.OK);

	}

	
	@GetMapping("showuser")
	public ResponseEntity<List<Login>> showAllUser() {

		return new ResponseEntity<List<Login>>(loginRepo.findAll(), HttpStatus.OK);

	}

	@GetMapping("getshows/{screenid}")
	public ResponseEntity<List<Shows>> getshows(@PathVariable int screenid) {

		return new ResponseEntity<List<Shows>>(ssRepo.findByScreen(screenid), HttpStatus.OK);
	}

	@GetMapping("getscreens/{multiplexid}")
	public ResponseEntity<List<Screen>> getscreens(@PathVariable int multiplexid) {

		return new ResponseEntity<List<Screen>>(sRepo.findByMultiplex(multiplexid), HttpStatus.OK);
	}

	@GetMapping("getmovie/{movieid}")
	public ResponseEntity getmovie(@PathVariable int movieid) {
try {
	Movie m = mRepo.findById(movieid).get();

	return new ResponseEntity(m, HttpStatus.OK);
}catch(Exception e){
	throw new IdNotFoundException("movie  already exists");
}

		

	}

	@GetMapping("getscreenname/{screenid}")
	public ResponseEntity getscreenname(@PathVariable int screenid) {
		Screen s = sRepo.findById(screenid).get();
		// s.getScreename();
		return new ResponseEntity(s, HttpStatus.OK);

	}

	@GetMapping("moviequueu/{showid}")
 public ResponseEntity <List<MovieQueue>>showqueue(@PathVariable int showid)
	{
		List<MovieQueue> q =  mqRepo.findByShowq(showid);
		return new ResponseEntity(q, HttpStatus.OK);
		
		
	}

	@GetMapping("getmultiplexname/{multiplexid}")
	public ResponseEntity getmultiplexname(@PathVariable int multiplexid) {
		Multiplex mm = muRepo.findById(multiplexid).get();
		// s.getScreename();
		return new ResponseEntity(mm, HttpStatus.OK);

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



