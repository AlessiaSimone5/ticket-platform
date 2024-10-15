package com.milestone.ticket.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milestone.ticket.platform.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	public List<Ticket> findByStatus(String status);	
	
	List<Ticket> findByTitleContainingIgnoreCase(String title);

}