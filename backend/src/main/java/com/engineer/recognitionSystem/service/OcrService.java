package com.engineer.recognitionSystem.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
public class OcrService {

    public String performOcr(MultipartFile file) throws IOException {
        // 將上傳的圖片轉為 ByteString
        ByteString imgBytes = ByteString.readFrom(file.getInputStream());

        // 創建 Image 對象
        Image img = Image.newBuilder().setContent(imgBytes).build();

        // 定義 OCR 功能
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();

        // 創建請求
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .build();

        // 調用 Vision API
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            AnnotateImageResponse response = vision.batchAnnotateImages(Collections.singletonList(request))
                    .getResponses(0);
            if (response.hasError()) {
                throw new IOException("Error during OCR: " + response.getError().getMessage());
            }
            // 返回檢測的文字
            return response.getTextAnnotationsList().get(0).getDescription();
        }
    }
}
