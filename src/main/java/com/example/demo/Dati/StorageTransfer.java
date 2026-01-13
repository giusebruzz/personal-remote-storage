package com.example.demo.Dati;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class StorageTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull(message = "Lo stato del trasferimento è obbligatorio")
	@Enumerated(EnumType.STRING)
	private TransferStatus status;
	private LocalDate startedAt;
    private LocalDate endedAt;
	private String errorMessage;
	
	@ManyToOne 
	@JoinColumn(name = "file_id")
	private StoredFile storedFile;
	@ManyToOne 
	@JoinColumn(name = "destination_node_id")
	private StorageNode storageNode;
	
	public StorageTransfer() {
		super();
		this.status = null;
		this.startedAt = null;
		this.endedAt = null;
		this.errorMessage = "N/A";
		this.storedFile = null;
		this.storageNode = null;
	}

	@Override
	public String toString() {
		return "id=" + id + ", status=" + status + ", startedAt=" + startedAt + ", endedAt=" + endedAt
				+ ", errorMessage=" + errorMessage + ", storedFile=" + storedFile.getId() + ", storageNode=" + storageNode.getId();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TransferStatus getStatus() {
		return status;
	}
	public void setStatus(TransferStatus status) {
		this.status = status;
	}
	public LocalDate getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(LocalDate startedAt) {
		this.startedAt = startedAt;
	}
	public LocalDate getEndedAt() {
		return endedAt;
	}
	public void setEndedAt(LocalDate endedAt) {
		this.endedAt = endedAt;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public StoredFile getStoredFile() {
		return storedFile;
	}
	public void setStoredFile(StoredFile storedFile) {
		this.storedFile = storedFile;
	}
	public StorageNode getStorageNode() {
		return storageNode;
	}
	public void setStorageNode(StorageNode storageNode) {
		this.storageNode = storageNode;
	}
	
	
	
}
