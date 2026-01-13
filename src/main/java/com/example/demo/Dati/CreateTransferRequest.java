package com.example.demo.Dati;

import jakarta.validation.constraints.NotNull;

public class CreateTransferRequest {

    @NotNull
    private Long fileId;

    @NotNull
    private Long nodeId;

    public Long getFileId() { return fileId; }
    public void setFileId(Long fileId) { this.fileId = fileId; }

    public Long getNodeId() { return nodeId; }
    public void setNodeId(Long nodeId) { this.nodeId = nodeId; }
}
