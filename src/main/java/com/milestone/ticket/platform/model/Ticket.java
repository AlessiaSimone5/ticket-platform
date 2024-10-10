package com.milestone.ticket.platform.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "tickets")
public class Ticket {
		@Id		
		private Integer id;
		
		@Column(nullable = false)	
		private String title;
		
		@Column(nullable = false)
		private String description;
		

		private LocalDateTime creationDate;
		
		private String status;
		
		@ManyToOne
		@JoinColumn(name = "user_id", nullable = false)
		private User user;
		

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	    public LocalDateTime getCreationDate() {
	        return this.creationDate;
	    }

	    public String getDateFormatted(){
	        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	        return creationDate.format(FORMATTER);
	    }

	    public void setCreationDate(LocalDateTime creationDate) {
	        this.creationDate = creationDate;
	    }

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}	
}