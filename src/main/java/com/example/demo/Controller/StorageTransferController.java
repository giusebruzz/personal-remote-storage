package com.example.demo.Controller;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dati.CreateTransferRequest;
import com.example.demo.Dati.StorageTransfer;
import com.example.demo.Dati.TransferStatus;
import com.example.demo.Dati.UpdateTransferStatusRequest;
import com.example.demo.Exception.FileNotFoundException;
import com.example.demo.Exception.StorageNodeNotFoundException;
import com.example.demo.Security.UserDetailsImpl;
import com.example.demo.Service.StorageTransferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transfers")
public class StorageTransferController {

    private final StorageTransferService transferService;

    public StorageTransferController(StorageTransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<StorageTransfer> createTransfer(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CreateTransferRequest request) throws AccessDeniedException, StorageNodeNotFoundException, FileNotFoundException {

        StorageTransfer transfer = transferService.createTransfer(
                request.getFileId(),
                request.getNodeId(),
                userDetails.getUser().getId() 
        );

        return ResponseEntity.status(201).body(transfer);
    }

    @GetMapping
    public ResponseEntity<List<StorageTransfer>> listTransfers(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<StorageTransfer> transfers = transferService.listTransfersByUser(userDetails.getUser().getId());
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<StorageTransfer>> listTransfersByStatus(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable String status) {

        TransferStatus ts;
    	try {
    	    ts = TransferStatus.valueOf(status.toUpperCase());
    	} catch (IllegalArgumentException e) {
    	    return ResponseEntity.badRequest().build();
    	}

    	List<StorageTransfer> transfers = transferService.listTransfersByStatus(ts);
    	if (transfers == null) transfers = new ArrayList<>(); // sicurezza

    	return ResponseEntity.ok(transfers);
    }

    @PutMapping("/{transferId}/status")
    public ResponseEntity<StorageTransfer> updateTransferStatus(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long transferId,
            @Valid @RequestBody UpdateTransferStatusRequest request) throws FileNotFoundException {

        StorageTransfer transfer = transferService.getTransferById(transferId);

        if (!transfer.getStoredFile().getUser().getId().equals(userDetails.getUser().getId())) {
            return ResponseEntity.status(403).build();
        }

        StorageTransfer updated = transferService.updateTransferStatus(
                transferId,
                request.getStatus()
        );

        return ResponseEntity.ok(updated);
    }
}
