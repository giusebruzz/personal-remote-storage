package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Dati.StorageTransfer;
import com.example.demo.Dati.TransferStatus;

public interface StorageTransferRepository extends JpaRepository<StorageTransfer,Long>{
	
	List<StorageTransfer> findByStoredFile_User_Id(Long userId);

	List<StorageTransfer> findByStatus(TransferStatus status);

}
