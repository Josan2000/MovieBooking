package com.example.demo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Login;

public interface LoginRepository extends JpaRepository<Login,String>{

}