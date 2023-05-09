package com.example.demo.interfaces;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Shows;

public interface ShowsRepository extends JpaRepository<Shows,Integer>{
	
	@Query(value="select s from Shows s where s.screen.screenid=:screenid")
	public List<Shows> findByScreen(@Param(value="screenid")int screenid);


}