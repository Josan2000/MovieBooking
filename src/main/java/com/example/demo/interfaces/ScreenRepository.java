package com.example.demo.interfaces;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Screen;

public interface ScreenRepository extends JpaRepository<Screen,Integer>{

	@Query(value="select s from Screen s where s.multiplex.multiplexid=:multiplexid")
	public List<Screen> findByMultiplex(@Param(value="multiplexid")int multiplexid);
	
}