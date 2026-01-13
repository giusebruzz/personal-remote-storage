package com.example.demo.Dati;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class StorageNode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	@Pattern(regexp = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b", message = "IP non valido")
	@Column(unique = true, nullable = false)
	private String ipAddress;
	@NotNull
	@Min(1024)
	@Max(65535)
	private Integer port;
    @NotBlank
	private String authToken; 
    
	private boolean active;
	private LocalDate createdAt;
	
	@ManyToOne 
	private User user;
	@OneToMany 
	private List<StoredFile> storedFile;

	public StorageNode() {
		super();
		this.name = "N/A";
		this.ipAddress = "N/A";
		this.port = null;
		this.authToken = "N/A";
		this.active = true;
		this.createdAt = null;
		this.user = null;
		this.storedFile = null;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", ipAddress=" + ipAddress + ", port=" + port
				+ ", authToken=" + authToken + ", active=" + active + ", createdAt=" + createdAt;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean b) {
		this.active = b;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<StoredFile> getStoredFile() {
		return storedFile;
	}

	public void setStoredFile(List<StoredFile> storedFile) {
		this.storedFile = storedFile;
	}
	
	
	
	
}
