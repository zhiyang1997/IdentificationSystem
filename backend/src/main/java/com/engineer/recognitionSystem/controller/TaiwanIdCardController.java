// TaiwanIdCardController.java
package com.engineer.recognitionSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engineer.recognitionSystem.exception.InvalidIdCardException;
import com.engineer.recognitionSystem.model.TaiwanIdCardData;
import com.engineer.recognitionSystem.service.TaiwanIdCardService;

@RestController
@RequestMapping("/api/id-card")
public class TaiwanIdCardController {

    @Autowired
    private TaiwanIdCardService idCardService;

    // 接收圖片絕對路徑
    @PostMapping("/recognize-path")
    public ResponseEntity<?> recognizeIdCardFromPath(@RequestParam("path") String path) {
        try {
            TaiwanIdCardData result = idCardService.processIdCard(path);
            return ResponseEntity.ok(result);
        } catch (InvalidIdCardException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("系統錯誤，請稍後再試！");
        }
    }
}