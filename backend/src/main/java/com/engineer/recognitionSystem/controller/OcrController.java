package com.engineer.recognitionSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.engineer.recognitionSystem.service.OcrService;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @PostMapping(value = "/detect", consumes = "multipart/form-data")
    public ResponseEntity<String> detectText(@RequestParam("file") MultipartFile file) {
        try {
            String result = ocrService.performOcr(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error performing OCR: " + e.getMessage());
        }
    }
}
