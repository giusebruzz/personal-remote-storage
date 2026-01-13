package com.example.demo.Service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.demo.Dati.StorageNode;
import com.example.demo.Dati.User;
import com.example.demo.Exception.StorageNodeAlreadyExistsException;
import com.example.demo.Exception.StorageNodeNotFoundException;
import com.example.demo.Repository.StorageNodeRepository;
import com.example.demo.Repository.UserRepository;

@Service
public class StorageNodeService {

	StorageNodeRepository storageNodeRep;
	
	UserRepository userRep;

	public StorageNodeService(StorageNodeRepository storageNodeRep, UserRepository userRep) {
		super();
		this.storageNodeRep = storageNodeRep;
		this.userRep = userRep;
	}
	
	
	public StorageNode createNode(StorageNode storageNode, Long userId) throws StorageNodeAlreadyExistsException {
		
		 User user = userRep.findById(userId)
			        .orElseThrow(() -> new RuntimeException("Utente non trovato"));

		 storageNode.setUser(user);
			    return storageNodeRep.save(storageNode);
		
	}
	
	public StorageNode getNodeById(Long nodeId, Long userId) throws StorageNodeNotFoundException {
		
	    StorageNode node = storageNodeRep.findById(nodeId)
	        .orElseThrow(() -> new StorageNodeNotFoundException("Nodo non trovato"));

	    if (!node.getUser().getId().equals(userId)) {
	        throw new AccessDeniedException("Non puoi vedere questo nodo");
	    }

	    return node;
	}
	
	public StorageNode updateNode(Long nodeId, StorageNode updatedNode, Long userId) throws StorageNodeNotFoundException {
		
	    StorageNode node = storageNodeRep.findById(nodeId)
	        .orElseThrow(() -> new StorageNodeNotFoundException("Nodo non trovato"));

	    if (!node.getUser().getId().equals(userId)) {
	        throw new AccessDeniedException("Non puoi modificare questo nodo");
	    }

	    node.setName(updatedNode.getName());
	    node.setIpAddress(updatedNode.getIpAddress());
	    node.setPort(updatedNode.getPort());
	    node.setAuthToken(updatedNode.getAuthToken());

	    return storageNodeRep.save(node);
	}
	
	public void deleteNode(Long nodeId, Long userId) throws StorageNodeNotFoundException {
		
	    StorageNode node = getNodeById(nodeId, userId); 
	    storageNodeRep.delete(node);
	    
	}
	
	 public List<StorageNode> listNodesByUser(Long userId) {
	        return storageNodeRep.findByUserId(userId); 
	    }
	
}
