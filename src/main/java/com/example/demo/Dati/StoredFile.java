package com.example.demo.Dati;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class StoredFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String originalFileName;
	@NotBlank
	private String storedFileName; 
	@PositiveOrZero(message = "La dimensione del file deve essere zero o maggiore")
	private Long size;
	@NotBlank
	private String contentType;
	private LocalDate createdAt;
	
	@ManyToOne 
	private User user;
	@ManyToOne 
	private StorageNode storageNode;

	public StoredFile() {
		super();
		this.originalFileName = "N/A";
		this.storedFileName = "N/A";
		this.size = null;
		this.contentType = "N/A";
		this.createdAt = null;
		this.user = null;
		this.storageNode = null;
	}

	@Override
	public String toString() {
		return "id=" + id + ", originalFileName=" + originalFileName + ", storedFileName=" + storedFileName
				+ ", size=" + size + ", contentType=" + contentType + ", createdAt=" + createdAt;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getStoredFileName() {
		return storedFileName;
	}
	public void setStoredFileName(String storedFileName) {
		this.storedFileName = storedFileName;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
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

	public StorageNode getStorageNode() {
		return storageNode;
	}

	public void setStorageNode(StorageNode storageNode) {
		this.storageNode = storageNode;
	}
	
	
	
	
}
