package com.milestone.ticket.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milestone.ticket.platform.model.Ticket;
import com.milestone.ticket.platform.repository.TicketRepository;
import com.milestone.ticket.platform.repository.UserRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository repository;
	
	public List<Ticket> findAllTickets() {

		return repository.findAll();

	}
	
	public List<Ticket> findAllTicketsByStatus(String status) {
		
		return repository.findByStatus(status);
	}

	public Ticket getById(int id) {

		return repository.findById(id).get();

	}

	public Ticket update(Ticket ticket) {

		return repository.save(ticket);

	}
	
	public void deleteTicketById(int id) {
		repository.deleteById(id);
	}

	public Ticket create(Ticket createTicket) {
		return repository.save(createTicket);
		
	}
}
