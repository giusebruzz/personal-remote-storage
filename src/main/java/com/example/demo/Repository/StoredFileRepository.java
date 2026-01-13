package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Dati.StoredFile;

public interface StoredFileRepository extends JpaRepository<StoredFile,Long>{
	
	boolean existsByUser_IdAndOriginalFileName(Long userId, String originalFileName);
	
	List<StoredFile> findByUserId(Long userId);
	
	List<StoredFile> findByStorageNodeId(Long nodeId);


}
