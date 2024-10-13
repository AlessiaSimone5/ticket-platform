package com.milestone.ticket.platform.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.milestone.ticket.platform.model.Ticket;
import com.milestone.ticket.platform.model.User;
import com.milestone.ticket.platform.repository.RoleRepository;
import com.milestone.ticket.platform.service.TicketService;
import com.milestone.ticket.platform.service.UserService;

@Controller
@RequestMapping("/")
public class TicketController {
	
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/ticket/{id}")
	public String ticket (Model model, @PathVariable("id") Integer ticketId) {
		model.addAttribute("ticket", ticketService.getById(ticketId));
		return "/ticket";	
	}
	
	@GetMapping("/dashboard")
	public String dashboard (Model model, Authentication authentication) {
		
		List<Ticket> tickets = ticketService.findAllTickets();
	    List<Ticket> operatorTickets = new ArrayList<>();

	    // Authorities dell'utente autenticato
	    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

	    // Controlla se l'utente ha il ruolo OPERATOR o ADMIN
	    boolean isOperator = authorities.stream()
	                                    .anyMatch(authority -> authority.getAuthority().equals("OPERATOR"));
	    boolean isAdmin = authorities.stream()
	                                 .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

	    // Se l'utente è un OPERATOR, filtriamo solo i suoi ticket
	    if (isOperator) {
	        for (Ticket t : tickets) {
	            if (t.getUser().getEmail().equals(authentication.getName())) {
	                operatorTickets.add(t);
	            }
	        }
	        model.addAttribute("tickets", operatorTickets);
	    } 
	    
	    // Se l'utente è ADMIN, mostriamo tutti i ticket
	    if (isAdmin) {
	        model.addAttribute("tickets", tickets);
	    }
		return "/dashboard";	
	}
	
	@GetMapping("/roles")
	public String roles () {
		System.out.println(roleRepository.findAll());
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
	
	@PostMapping("/createTicket")
	public String createTicket (@ModelAttribute("ticket") Ticket formTicket, Model model) {
		System.out.println("***********QUI**********");
		System.out.println(formTicket);
		ticketService.create(formTicket);
		return "redirect:/dashboard";
	}	
}

