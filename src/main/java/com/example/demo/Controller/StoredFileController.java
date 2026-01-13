package com.example.demo.Controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Dati.StoredFile;
import com.example.demo.Exception.FileAlreadyExistsException;
import com.example.demo.Exception.FileNotFoundException;
import com.example.demo.Exception.StorageNodeNotFoundException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Security.UserDetailsImpl;
import com.example.demo.Service.StoredFileService;

@RestController
@RequestMapping("/files")
public class StoredFileController {

    private final StoredFileService fileService;

    public StoredFileController(StoredFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(    value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StoredFile> uploadFile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "nodeId", required = false) Long nodeId) throws IOException, UserNotFoundException, StorageNodeNotFoundException, FileAlreadyExistsException {

        StoredFile uploaded = fileService.uploadFile(file, userDetails.getUser().getId(), nodeId);
        return ResponseEntity.status(201).body(uploaded);
    }

    @GetMapping
    public ResponseEntity<List<StoredFile>> listFiles(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<StoredFile> files = fileService.listFilesByUser(userDetails.getUser().getId());
        files.forEach(f -> f.setContentType(null));
        return ResponseEntity.ok(files);
    }

    @GetMapping("/node/{nodeId}")
    public ResponseEntity<List<StoredFile>> listFilesByNode(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long nodeId) {

        List<StoredFile> files = fileService.listFilesByNode(nodeId);
        files = files.stream()
                     .filter(f -> f.getUser().getId().equals(userDetails.getUser().getId()))
                     .collect(Collectors.toList());
        files.forEach(f -> f.setContentType(null));
        return ResponseEntity.ok(files);
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long fileId) throws IOException, FileNotFoundException {

        StoredFile file = fileService.getFileById(fileId);
        if (!file.getUser().getId().equals(userDetails.getUser().getId())) {
            return ResponseEntity.status(403).build();
        }

        Resource resource = fileService.loadFileAsResource(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFileName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long fileId) throws IOException, FileNotFoundException {

        StoredFile file = fileService.getFileById(fileId);
        if (!file.getUser().getId().equals(userDetails.getUser().getId())) {
            return ResponseEntity.status(403).build();
        }

        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }
}
