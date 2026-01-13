package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dati.StorageNode;
import com.example.demo.Dati.StorageNodeRequest;
import com.example.demo.Exception.StorageNodeAlreadyExistsException;
import com.example.demo.Exception.StorageNodeNotFoundException;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.UserDetailsImpl;
import com.example.demo.Service.StorageNodeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/nodes")
public class StorageNodeController {

    private final StorageNodeService nodeService;
    
    UserRepository userRep;

    public StorageNodeController(StorageNodeService nodeService, UserRepository userRep) {
        this.nodeService = nodeService;
        this.userRep = userRep;
    }

    @PostMapping
    public ResponseEntity<StorageNode> createNode(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody StorageNodeRequest request) throws StorageNodeAlreadyExistsException {
    	
    	StorageNode node = new StorageNode();
        node.setName(request.getName());
        node.setIpAddress(request.getIp());
        node.setPort(request.getPort());
        node.setAuthToken(request.getToken());
        node.setCreatedAt(LocalDate.now());
        node.setActive(true);

        StorageNode created = nodeService.createNode(node, userDetails.getUser().getId());

        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<StorageNode>> listNodes(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<StorageNode> nodes = nodeService.listNodesByUser(userDetails.getUser().getId());
        return ResponseEntity.ok(nodes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageNode> getNode(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id) throws StorageNodeNotFoundException {

        StorageNode node = nodeService.getNodeById(id, userDetails.getUser().getId());
        return ResponseEntity.ok(node);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageNode> updateNode(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @Valid @RequestBody StorageNodeRequest request) throws StorageNodeNotFoundException {

        StorageNode node = new StorageNode();
        node.setName(request.getName());
        node.setIpAddress(request.getIp());
        node.setPort(request.getPort());
        node.setAuthToken(request.getToken());

        StorageNode updated = nodeService.updateNode(id, node, userDetails.getUser().getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id) throws StorageNodeNotFoundException {

        nodeService.deleteNode(id, userDetails.getUser().getId());
        return ResponseEntity.noContent().build();
    }
}