// TaiwanIdCardData.java
package com.engineer.recognitionSystem.model;

public class TaiwanIdCardData {
    private String idNumber; // 身分證字號
    private String name; // 姓名
    private String birthDate; // 出生年月日

    // Getters and Setters
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
