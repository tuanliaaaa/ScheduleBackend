package com.g11.schedule.controller;

import com.g11.schedule.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiv1")
public class FileController {
    @Autowired
    private FileService fileService;
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<?> getPhoto(@PathVariable String fileName) {
        Resource file = fileService.loadAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Đổi kiểu MIME tùy thuộc vào loại file
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
