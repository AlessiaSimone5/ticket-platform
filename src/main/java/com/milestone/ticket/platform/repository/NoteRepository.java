package com.milestone.ticket.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milestone.ticket.platform.model.Note;

public interface NoteRepository extends JpaRepository<Note, Integer> {
	
}