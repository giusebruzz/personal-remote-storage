package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Dati.StorageNode;

public interface StorageNodeRepository extends JpaRepository<StorageNode,Long>{

	boolean existsByIpAddress(String ipAddress);
	
	boolean existsByAuthToken(String authToken);
	
	List <StorageNode>findByUserId (Long userId);
}
