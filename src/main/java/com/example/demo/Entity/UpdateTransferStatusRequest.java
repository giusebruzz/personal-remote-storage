package com.example.demo.Entity;

import com.example.demo.ENUM.TransferStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateTransferStatusRequest {

	@NotNull
    private TransferStatus status; 

    public TransferStatus getStatus() { return status; }
    public void setStatus(TransferStatus status) { this.status = status; }
}