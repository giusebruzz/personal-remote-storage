package com.example.demo.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Dati.StorageNode;
import com.example.demo.Dati.StoredFile;
import com.example.demo.Dati.User;
import com.example.demo.Exception.FileAlreadyExistsException;
import com.example.demo.Exception.FileNotFoundException;
import com.example.demo.Exception.StorageNodeNotFoundException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Repository.StorageNodeRepository;
import com.example.demo.Repository.StoredFileRepository;
import com.example.demo.Repository.UserRepository;

import org.springframework.core.io.UrlResource;


@Service
public class StoredFileService {
	
	StoredFileRepository storedFileRep;
	
	UserRepository UserRep;
	
	StorageNodeRepository StorageNodeRep;
	
	 private static final String BASE_PATH = "storage";

	public StoredFileService(StoredFileRepository storedFileRep, UserRepository UserRep, StorageNodeRepository StorageNodeRep) {
		super();
		this.storedFileRep = storedFileRep;
		this.UserRep = UserRep;
		this.StorageNodeRep = StorageNodeRep;
	}
	
	public StoredFile uploadFile(MultipartFile file, Long userId, Long nodeId)
	        throws UserNotFoundException, StorageNodeNotFoundException, FileAlreadyExistsException {

	    User user = UserRep.findById(userId).orElseThrow(() -> new UserNotFoundException("User con id " + userId + " non trovato"));

	    StorageNode storedNode = StorageNodeRep.findById(nodeId).orElseThrow(() -> new StorageNodeNotFoundException("Node con id " + nodeId + " non trovato"));

	    if (file == null || file.isEmpty()) {
	        throw new IllegalArgumentException("File non presente o vuoto");
	    }

	    if (storedFileRep.existsByUser_IdAndOriginalFileName(userId, file.getOriginalFilename())) {
	        throw new FileAlreadyExistsException("File già esistente per questo utente");
	    }

	    Path userDir = Paths.get(BASE_PATH, "user-" + userId);
	    try {
	        Files.createDirectories(userDir);
	    } catch (IOException e) {
	        throw new RuntimeException("Errore nella creazione della directory", e);
	    }

	    Path targetPath = userDir.resolve(file.getOriginalFilename());
	    try {
	        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
	    } catch (IOException e) {
	        throw new RuntimeException("Errore durante il salvataggio del file", e);
	    }

	    StoredFile storedFile = new StoredFile();
	    storedFile.setOriginalFileName(file.getOriginalFilename());        
	    storedFile.setStoredFileName(file.getOriginalFilename());         
	    storedFile.setSize(file.getSize());
	    storedFile.setContentType(file.getContentType());
	    storedFile.setUser(user);
	    storedFile.setStorageNode(storedNode);

	    storedFileRep.save(storedFile);
	    return storedFile;
	}



	    public StoredFile getFileById(Long fileId) throws FileNotFoundException {
	        return storedFileRep.findById(fileId)
	                .orElseThrow(() -> new FileNotFoundException("File con id " + fileId + " non trovato"));
	    }

	    public UrlResource loadFileAsResource(Long fileId) throws FileNotFoundException {
	        StoredFile file = getFileById(fileId);

	        try {
	            
	        	 Path filePath = Paths.get(BASE_PATH)
	                    .resolve("user-" + file.getUser().getId())
	                    .resolve(file.getOriginalFileName())
	                    .normalize();

	            UrlResource resource = new UrlResource(filePath.toUri());

	            if (!resource.exists() || !resource.isReadable()) {
	                throw new FileNotFoundException("File non leggibile: " + file.getOriginalFileName());
	            }

	            return resource;

	        } catch (MalformedURLException e) {
	            throw new FileNotFoundException("Errore nel caricamento del file");
	        }
	    }
	    
	    public void deleteFile(Long fileId) throws FileNotFoundException {
	        StoredFile file = getFileById(fileId);

	        Path filePath = Paths.get(BASE_PATH)
	                .resolve("user-" + file.getUser().getId())
	                .resolve(file.getOriginalFileName())
	                .normalize();

	        try {
	            Files.deleteIfExists(filePath); 
	        } catch (IOException e) {
	            throw new RuntimeException("Errore nella cancellazione del file fisico", e);
	        }

	        storedFileRep.delete(file);
	    }
	    
	    public List<StoredFile> listFilesByUser(Long userId) {
	        return storedFileRep.findByUserId(userId);
	    }
	    
	    public List<StoredFile> listFilesByNode(Long nodeId) {
	        return storedFileRep.findByStorageNodeId(nodeId);
	    }


}
