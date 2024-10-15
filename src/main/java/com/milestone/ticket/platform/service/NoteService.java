package com.milestone.ticket.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milestone.ticket.platform.model.Note;
import com.milestone.ticket.platform.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	NoteRepository repository;
	
	public Object getById(Integer noteId) {
		return repository.findById(noteId).get();
	}

	public List<Note> findAllNotes() {

		return repository.findAll();

	}

    public void save(Note note) {
        repository.save(note); // Assicurati di salvare il note nel repository
    }
	
}