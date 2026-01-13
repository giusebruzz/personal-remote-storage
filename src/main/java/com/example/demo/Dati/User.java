package com.example.demo.Dati;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "app_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String username;
	@NotBlank
	@Email
	@Column(unique = true, nullable = false)
	private String email;
	@NotBlank
	@Size(min = 8, message = "La password deve contenere almeno 8 caratteri")
	private String passwordHash;
	private LocalDate createdAt;
	
	@OneToMany(mappedBy = "user")
	private List<StorageNode> storageNodes;

	@OneToMany(mappedBy = "user")
	private List<StoredFile> storedFiles;

	public User() {
		super();
		this.username = "N/A";
		this.email = "N/A";
		this.passwordHash = "N/A";
		this.createdAt = null;
	}
	
	@Override
	public String toString() {
		return "id=" + id + ", username=" + username + ", email=" + email + ", passwordHash=" + passwordHash
				+ ", createdAt=" + createdAt ;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	
	




}
