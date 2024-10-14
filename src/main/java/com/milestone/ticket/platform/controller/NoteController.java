package com.milestone.ticket.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.milestone.ticket.platform.model.Note;
import com.milestone.ticket.platform.service.NoteService;

@Controller
@RequestMapping("/")
public class NoteController {

	@Autowired
	NoteService noteService;
	
	@GetMapping("/note/{id}")
	public String note (Model model, @PathVariable("id") Integer ticketId) {
		Note note = new Note();
		List<Note> notes = noteService.findAllNotes();
		for(Note n : notes ) {
			if (n.getTicket().getId() == ticketId) {
				note = n;
			}
		}
		model.addAttribute("note", note);
		return "/note";	
	}

	
}