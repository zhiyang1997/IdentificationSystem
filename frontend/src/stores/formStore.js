import { defineStore } from "pinia";

export const useFormStore = defineStore("form", {
  state: () => ({
    currentStep: "step1", // 初始化當前步驟
    step1Data: {
      NAME: "",
      PHONE_NUMBER: "",
      NATIONAL_ID: "",
      BIRTHDATE: "",
      REGISTERED_ADDRESS: "",
      RELATIVE_NAME1: "",
      RELATIVE_RELATION1: "",
      RELATIVE_PHONE1: "",
      RELATIVE_NAME2: "",
      RELATIVE_RELATION2: "",
      RELATIVE_PHONE2: "",
      FRIEND_NAME: "",
      FRIEND_PHONE: "",
    },

    step2Data: {
      otpDigits: Array(6).fill(""), // 存储6位OTP
    },

    step4Data: {
      frontId: null, // 身分證正面
      backId: null, // 身分證反面
      healthCard: null, // 健保卡
      selfie: null, // 手持身分證自拍
      financialProof: null, // 財力證明
      otherProof: null, // 其他身分證明 (軍/公/教/學生證)
      signature: null, // 簽名數據
    },
  }),

  actions: {
    // 更新 Step1 資料
    updateStep1Data(key, value) {
      this.step1Data[key] = value;
    },

    // 更新 Step2 OTP 資料
    updateStep2OtpDigit(index, value) {
      this.step2Data.otpDigits[index] = value;
    },

    // 更新 Step4 資料
    updateStep4Data(key, value) {
      this.step4Data[key] = value;
    },

    // 清除 Step1 資料
    clearStep1Data() {
      this.step1Data = {
        NAME: "",
        PHONE_NUMBER: "",
        NATIONAL_ID: "",
        BIRTHDATE: "",
        REGISTERED_ADDRESS: "",
        RELATIVE_NAME1: "",
        RELATIVE_RELATION1: "",
        RELATIVE_PHONE1: "",
        RELATIVE_NAME2: "",
        RELATIVE_RELATION2: "",
        RELATIVE_PHONE2: "",
        FRIEND_NAME: "",
        FRIEND_PHONE: "",
      };
    },

    // 清除 Step2 資料
    clearStep2Data() {
      this.step2Data.otpDigits = Array(6).fill("");
    },

    // 清除 Step4 資料
    clearStep4Data() {
      this.step4Data = {
        frontId: null,
        backId: null,
        healthCard: null,
        selfie: null,
        financialProof: null,
        otherProof: null,
        signature: null,
      };
    },

    // 設置當前步驟
    setCurrentStep(step) {
      this.currentStep = step;
    },
  },
});
