package com.example.demo.Security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.Dati.User;

public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = -3820976453485188217L;
	
	private final User user;

	public UserDetailsImpl(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return Collections.emptyList();
	}

	@Override
	public String getPassword() {
	    return user.getPasswordHash();
	}

	@Override
	public String getUsername() {
	    return user.getEmail(); 
	}

	@Override 
	public boolean isAccountNonExpired() { return true; }
	@Override 
	public boolean isAccountNonLocked() { return true; }
	@Override 
	public boolean isCredentialsNonExpired() { return true; }
	@Override 
	public boolean isEnabled() { return true; }

	public User getUser() {
	    return user;
	}
}
