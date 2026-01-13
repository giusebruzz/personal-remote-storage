package com.example.demo.Service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Dati.User;
import com.example.demo.Exception.UserAlreadyExistsException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Repository.UserRepository;

@Service
public class UserService {

	UserRepository userRep;
	
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRep, PasswordEncoder passwordEncoder) {
		super();
		this.userRep = userRep;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User createUser(User user, String passwordHash) throws UserAlreadyExistsException {
		
		if(userRep.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistsException("Email già registrata");
		}
		
		user.setPasswordHash(passwordEncoder.encode(passwordHash));
	    user.setCreatedAt(LocalDate.now());
	    
	    
		return userRep.save(user);
	}
	
	public User getUserById(Long id) throws UserNotFoundException {
		
		return userRep.findById(id).orElseThrow(() -> new UserNotFoundException("user non presente"));
	}
	
	public User updateUser(Long id, User user, String rawPassword)
	        throws UserNotFoundException, UserAlreadyExistsException {

	    User foundUser = userRep.findById(id)
	            .orElseThrow(() -> new UserNotFoundException("user non presente"));

	    if (!foundUser.getEmail().equals(user.getEmail())
	            && userRep.existsByEmail(user.getEmail())) {
	        throw new UserAlreadyExistsException("Email già in uso");
	    }

	    foundUser.setUsername(user.getUsername());
	    foundUser.setEmail(user.getEmail());

	    if (rawPassword != null && !rawPassword.isBlank()) {
	        foundUser.setPasswordHash(passwordEncoder.encode(rawPassword));
	    }

	    return userRep.save(foundUser);
	}
	
	public void deleteUser(Long id) throws UserNotFoundException {
		
		User foundUser = userRep.findById(id).orElseThrow(() -> new UserNotFoundException("id user non trovato"));
		
		userRep.delete(foundUser);
		
	}
	
	
}
