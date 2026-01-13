package com.example.demo.Dati;

import jakarta.validation.constraints.NotNull;

public class UpdateTransferStatusRequest {

	@NotNull
    private TransferStatus status; 

    public TransferStatus getStatus() { return status; }
    public void setStatus(TransferStatus status) { this.status = status; }
}