// TaiwanIdCardService.java
package com.engineer.recognitionSystem.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.engineer.recognitionSystem.exception.InvalidIdCardException;
import com.engineer.recognitionSystem.model.TaiwanIdCardData;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class TaiwanIdCardService {

    public TaiwanIdCardData processIdCard(String path) throws InvalidIdCardException {
        try {
            File file = new File(path);
            if (!file.exists() || !file.isFile()) {
                throw new InvalidIdCardException("圖片路徑無效或檔案不存在：" + path);
            }

            BufferedImage image = ImageIO.read(file);

            // 轉換圖片為灰階
            BufferedImage grayscaleImage = convertToGrayscale(image);

            // 初始化 Tesseract
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Users\\Steven\\Desktop\\tessdata-main");
            tesseract.setLanguage("chi_tra");
            tesseract.setOcrEngineMode(1);
            tesseract.setPageSegMode(6);

            String ocrResult = tesseract.doOCR(grayscaleImage);

            // 輸出原始 OCR 結果
            System.out.println("OCR 結果: " + ocrResult);

            // 解析結果
            return parseIdCardData(ocrResult);
        } catch (IOException e) {
            throw new InvalidIdCardException("無法讀取圖片，請檢查檔案路徑與格式！");
        } catch (TesseractException e) {
            throw new InvalidIdCardException("OCR 辨識失敗：" + e.getMessage());
        }
    }

    private BufferedImage convertToGrayscale(BufferedImage originalImage) {
        BufferedImage grayscaleImage = new BufferedImage(
                originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = grayscaleImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();
        return grayscaleImage;
    }

    private TaiwanIdCardData parseIdCardData(String ocrResult) {
        TaiwanIdCardData data = new TaiwanIdCardData();

        Matcher idMatcher = Pattern.compile("[A-Z][0-9]{9}").matcher(ocrResult);
        Matcher birthMatcher = Pattern.compile("\\d{4}[-/]\\d{2}[-/]\\d{2}").matcher(ocrResult);
        Matcher nameMatcher = Pattern.compile("[\\u4e00-\\u9fa5]{2,4}").matcher(ocrResult);

        if (idMatcher.find())
            data.setIdNumber(idMatcher.group());
        if (birthMatcher.find())
            data.setBirthDate(birthMatcher.group());
        if (nameMatcher.find())
            data.setName(nameMatcher.group());

        // 輸出解析結果
        System.out.println("姓名: " + data.getName());
        System.out.println("出生年月日: " + data.getBirthDate());
        System.out.println("身分證字號: " + data.getIdNumber());

        // 檢查必要資訊
        if (data.getIdNumber() == null || data.getBirthDate() == null || data.getName() == null) {
            throw new InvalidIdCardException("身分證資訊不完整，請確認圖片清晰度！");
        }

        return data;
    }
}
