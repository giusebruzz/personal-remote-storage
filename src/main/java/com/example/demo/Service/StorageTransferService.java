package com.example.demo.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Dati.StorageTransfer;
import com.example.demo.Dati.StoredFile;
import com.example.demo.Dati.TransferStatus;
import com.example.demo.Exception.FileNotFoundException;
import com.example.demo.Exception.StorageNodeNotFoundException;
import com.example.demo.Repository.StorageTransferRepository;


@Service
public class StorageTransferService {
	
	private final StorageTransferRepository transferRep;
	
	StoredFileService fileService;
	
	StorageNodeService nodeService;

    public StorageTransferService(StorageTransferRepository transferRep, StoredFileService fileService, StorageNodeService nodeService) {
    	
        this.transferRep = transferRep;
        this.fileService = fileService;
        this.nodeService = nodeService;
   
    }

    
    public StorageTransfer createTransfer(Long fileId, Long nodeId, Long userId) throws StorageNodeNotFoundException, AccessDeniedException, FileNotFoundException {
        StoredFile file = fileService.getFileById(fileId);

        if (!file.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Non puoi trasferire file di un altro utente");
        }

        StorageTransfer transfer = new StorageTransfer();
        transfer.setStoredFile(file);
        transfer.setStorageNode(nodeService.getNodeById(nodeId, userId));
        transfer.setStatus(TransferStatus.PENDING);

        return transferRep.save(transfer);
    }

    public StorageTransfer getTransferById(Long transferId) throws FileNotFoundException {
    	
        return transferRep.findById(transferId).orElseThrow(() -> new FileNotFoundException("Transfer con id " + transferId + " non trovato"));
        
    }

  
    public List<StorageTransfer> listTransfersByUser(Long userId) {
        return transferRep.findByStoredFile_User_Id(userId);
    }

    public List<StorageTransfer> listTransfersByStatus(TransferStatus status) {
    	
    	List<StorageTransfer> transfers = transferRep.findByStatus(status);
    	return transfers != null ? transfers : new ArrayList<>();
        
    }

    public StorageTransfer updateTransferStatus(Long transferId, TransferStatus newStatus) throws FileNotFoundException {
        StorageTransfer transfer = getTransferById(transferId);
        transfer.setStatus(newStatus);
        return transferRep.save(transfer);
    }


	public List<StorageTransfer> listTransfersByUser(String status) {
		// TODO Auto-generated method stub
		return null;
	}

}
