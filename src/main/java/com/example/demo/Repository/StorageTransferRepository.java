package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.ENUM.TransferStatus;
import com.example.demo.Entity.StorageTransfer;

public interface StorageTransferRepository extends JpaRepository<StorageTransfer,Long>{
	
	List<StorageTransfer> findByStoredFile_User_Id(Long userId);

	List<StorageTransfer> findByStatus(TransferStatus status);

}
