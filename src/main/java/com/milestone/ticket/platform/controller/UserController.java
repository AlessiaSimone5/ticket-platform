package com.milestone.ticket.platform.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.milestone.ticket.platform.model.Ticket;
import com.milestone.ticket.platform.model.User;
import com.milestone.ticket.platform.repository.UserRepository;
import com.milestone.ticket.platform.service.TicketService;
import com.milestone.ticket.platform.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TicketService ticketService;
	
	@GetMapping("/user")
	public String user (Model model, Authentication authentication) {
		String userEmail = authentication.getName();		// Ottiene la mail dell'utente loggato
    	User user = userService.getByEmail(userEmail);		// Ottiene l'utente a partire dalla mail
    	
		model.addAttribute("user", user);
		return "/user";	
	}
	
	@GetMapping("/users")
	public String user (Model model) {
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "/users";	
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser (Model model, @PathVariable("id") Integer userId) {
		
		// TODO: modificare prima tutti i ticket assegnati all'operatore da eliminare
		// assegnando la user_id del ticket ad un admin, poi eliminare
		userService.deleteUserById(userId);
		return "redirect:/users";
	}
	
	@GetMapping("/editUser/{id}")
	public String editUser (Model model, @PathVariable("id") Integer userId) {
		model.addAttribute("user", userService.getById(userId));
		return "/editUser";	
	}
	
	@PostMapping("/updateUser/{id}")
	public String updateUser (@ModelAttribute("user") User formUser, Model model) {
		
		// Setta il ruolo all'utente
		formUser.setRoles(userService.getById(formUser.getId()).getRoles());
		
		List<Ticket> allTickets = ticketService.findAllTickets();	// Tutti i ticket
		List<Ticket> userTickets = new ArrayList<>();
		for(Ticket t : allTickets) {
			if(t.getUser().getEmail().equals(formUser.getEmail()))	
			{
				userTickets.add(t);	// Aggiunge i ticket alla lista dei ticket assegnati
			}
		}
		
		boolean canUpdate = true;
		String errorString = "";
		for(Ticket t : userTickets) {
			if(t.getStatus().equals("in corso") || t.getStatus().equals("da fare"))
			{
				canUpdate = false;
			}
		}
		
		if(canUpdate)
		{
			userService.update(formUser);
		}
		else
		{
			errorString =  "Errore, ci sono dei ticket assegnati da completare!";
		}
		model.addAttribute("errorString", errorString);
		return "/user";
	}
}
