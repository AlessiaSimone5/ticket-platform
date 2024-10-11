package com.milestone.ticket.platform.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.milestone.ticket.platform.model.User;


public class DatabaseUserDetails implements UserDetails {

	private final Integer id;
	private final String email;
	private final String password;
	private final Set<GrantedAuthority> authorities;

	public DatabaseUserDetails(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();

		authorities = new HashSet<GrantedAuthority>();

//		for (Role role : user.getRoles()) {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	
	public String getEmail() {
		return this.email;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	public Integer getId() {
		return id;
	}

}
