package com.milestone.ticket.platform.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.milestone.ticket.platform.model.Ticket;
import com.milestone.ticket.platform.service.TicketService;

@Controller
@RequestMapping("/")
public class TicketController {
	
	
	@Autowired
	TicketService ticketService;
	
//	@GetMapping("/entra")
//	public String entra () {
//		for(Ticket t : ticketService.findAllTicket())
//		{
//			System.out.println(t.getName());
//		}
//		return "entra";	
//	}
//	
//	@GetMapping("/dashboard")
//	public String hello () {
//		return "dashboard";	
//	}
//	
	@GetMapping("/ticket/{id}")
	public String ticket (Model model, @PathVariable("id") Integer ticketId) {
		model.addAttribute("ticket", ticketService.getById(ticketId));
		return "/ticket";	
	}
	
	@GetMapping("/tickets")
	public String ticket (Model model) {
		List<Ticket> tickets = ticketService.findAllTickets();
		model.addAttribute("tickets", tickets);
		System.out.println("**********QUI****************");
		System.out.println(tickets);
		return "/tickets";	
	}
	
	@GetMapping("/deleteTicket/{id}")
	public String deleteTicket (Model model, @PathVariable("id") Integer ticketId) {
		
		ticketService.deleteTicketById(ticketId);
		return "redirect:/tickets";
	}
	
	@GetMapping("/editTicket/{id}")
	public String editTicket (Model model, @PathVariable("id") Integer ticketId) {
		model.addAttribute("ticket", ticketService.getById(ticketId));
		return "/editTicket";	
	}
	
	@PostMapping("/updateTicket/{id}")
	public String updateTicket (@ModelAttribute("ticket") Ticket formTicket, Model model) {
		ticketService.update(formTicket);
		return "redirect:/tickets";	
	}
}
