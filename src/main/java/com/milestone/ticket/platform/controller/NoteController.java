package com.milestone.ticket.platform.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.milestone.ticket.platform.model.Note;
import com.milestone.ticket.platform.model.Ticket;
import com.milestone.ticket.platform.model.User;
import com.milestone.ticket.platform.service.NoteService;
import com.milestone.ticket.platform.service.TicketService;
import com.milestone.ticket.platform.service.UserService;

@Controller
@RequestMapping("/")
public class NoteController {

    @Autowired
    NoteService noteService;
    
    @Autowired
    TicketService ticketService;
    
    @Autowired
    UserService userService;

    @GetMapping("/note/{id}")
    public String note(Model model, @PathVariable("id") Integer ticketId) {
    	List<Note> notesToPass = new ArrayList(); // Inizializzazione di una lista di note vuota
        List<Note> notes = noteService.findAllNotes();
        for (Note n : notes) {	// Cicla su tutte le note
            if (n.getTicket().getId().equals(ticketId)) { // Controlla se l'id del ticket è uguale a quello che ho in input
            	notesToPass.add(n);	// se è uguale, aggiunge alla lista delle note da passare all'html
            }
        }
        model.addAttribute("notes", notesToPass);
        model.addAttribute("ticketId", ticketId);
        return "note";
    }

    @GetMapping("/createNote/{id}")
    public String createNote(Model model, @PathVariable("id") Integer ticketId, Authentication authentication) {
    	Note note = new Note();	// Crea una nota vuota
    	
    	Ticket ticket = ticketService.getById(ticketId);	// Cerca il ticket dal suo id
    	note.setTicket(ticket);	// Setta ticket alla nota
    	
    	String userEmail = authentication.getName();		// Ottiene la mail dell'utente loggato
    	User user = userService.getByEmail(userEmail);		// Ottiene l'utente a partire dalla mail
    	note.setCreator(user);								// Setta l'utente (Autore) alla nota
    	
        model.addAttribute("note", note);	// Passa nota all'html
        return "createNote";
    }

    @PostMapping("/createNote")
    public String saveNote(@ModelAttribute("note") Note note, Model model) {
        noteService.save(note);
        
        // Controlla che il ticket non sia null prima di fare il redirect
        if (note.getTicket() != null) {
            return "redirect:/note/" + note.getTicket().getId(); 
        } else {
            // Caso in cui il ticket è null
            return "redirect:/error"; // Pagina di errore
        }
    }
}
