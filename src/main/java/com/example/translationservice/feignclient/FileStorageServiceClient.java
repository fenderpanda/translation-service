package com.example.translationservice.feignclient;

import com.example.translationservice.entity.Status;
import com.example.translationservice.payload.MessageFileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "FileStorageServiceClient",
        url = "http://localhost:8002",
        path = "/api"
)
public interface FileStorageServiceClient {

    @GetMapping("/file/{fileId}/cost")
    int getFileCost(@PathVariable long fileId);

    @GetMapping("/file/{fileId}/message")
    MessageFileDto getMessageFileDto(@PathVariable long fileId);

    @PutMapping("/file/{fileId}/status")
    String updateStatus(@PathVariable long fileId, @RequestParam Status status);
}
