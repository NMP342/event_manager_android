package com.example.demo.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Guest;

public interface Repository extends JpaRepository<Guest, Integer>{
	List<Guest> findByIdEvent(int idEvent);
	Guest findByEmailAndIdEvent(String email, int idEvent);
}
