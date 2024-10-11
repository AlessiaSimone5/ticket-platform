package com.milestone.ticket.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.milestone.ticket.platform.model.Ticket;
import com.milestone.ticket.platform.model.User;
import com.milestone.ticket.platform.service.TicketService;
import com.milestone.ticket.platform.service.UserService;

@Controller
@RequestMapping("/")
public class TicketController {
	
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/ticket/{id}")
	public String ticket (Model model, @PathVariable("id") Integer ticketId) {
		model.addAttribute("ticket", ticketService.getById(ticketId));
		return "/ticket";	
	}
	
	@GetMapping("/dashboard")
	public String dashboard (Model model) {
		List<Ticket> tickets = ticketService.findAllTickets();
		model.addAttribute("tickets", tickets);
		return "/dashboard";	
	}
	
	@GetMapping("/deleteTicket/{id}")
	public String deleteTicket (Model model, @PathVariable("id") Integer ticketId) {
		
		ticketService.deleteTicketById(ticketId);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/editTicket/{id}")
	public String editTicket (Model model, @PathVariable("id") Integer ticketId) {
		model.addAttribute("ticket", ticketService.getById(ticketId));
		return "/editTicket";	
	}
	
	@PostMapping("/updateTicket/{id}")
	public String updateTicket (@ModelAttribute("ticket") Ticket formTicket, Model model) {
		ticketService.update(formTicket);
		return "redirect:/dashboard";
	}
	
	
	@GetMapping("/createTicket")
	public String createTicket (Model model) {
		Ticket ticket = new Ticket();
		User userToAssign = new User();
		
		List<User> allUsers = userService.findAllUsers();
		for(User u : allUsers)
		{
			if(!u.isStatus())
			{
				userToAssign = u;
				break;
			}
		}
		if(userToAssign.getName().isEmpty())
		{
			return "/error";
		}
		
		ticket.setUser(userToAssign);
		model.addAttribute("ticket", ticket);
		return "/createTicket";	
	}
	
	@PostMapping("/postCreateTicket")
	public String postCreateTicket (@ModelAttribute("ticket") Ticket formTicket, Model model) {
		System.out.println("***********QUI**********");
		System.out.println(formTicket);
		ticketService.create(formTicket);
		return "redirect:/dashboard";
	}
	
	
}
