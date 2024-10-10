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

import com.milestone.ticket.platform.model.User;
import com.milestone.ticket.platform.repository.UserRepository;
import com.milestone.ticket.platform.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/login")
	public String login () {
		for(User u : userService.findAllUsers())
		{
			System.out.println(u.getName());
		}
		return "login";	
	}
	
	@GetMapping("/dashboard")
	public String hello () {
		return "dashboard";	
	}
	
	@GetMapping("/user/{id}")
	public String user (Model model, @PathVariable("id") Integer userId) {
		model.addAttribute("user", userService.getById(userId));
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
		userService.update(formUser);
		return "redirect:/users";	
	}
}
