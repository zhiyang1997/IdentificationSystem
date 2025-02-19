import { defineStore } from "pinia";

// 定義初始狀態常量
const initialStep1Data = {
  NAME: "", // 姓名
  PHONE_NUMBER: "", // 手機號碼
  LANDLINE_PHONE: "", // 市內電話
  NATIONAL_ID: "", // 身分證字號
  BIRTHDATE: "", // 生日
  COMMUNICATION_ADDRESS: "", // 通訊地址
  REGISTERED_ADDRESS: "", // 戶籍地址
  RELATIVE_NAME1: "", // 親屬姓名1
  RELATIVE_RELATION1: "", // 親屬關係1
  RELATIVE_PHONE1: "", // 親屬電話1
  RELATIVE_NAME2: "", // 親屬姓名2
  RELATIVE_RELATION2: "", // 親屬關係2
  RELATIVE_PHONE2: "", // 親屬電話2
  FRIEND_NAME: "", // 朋友姓名
  FRIEND_PHONE: "", // 朋友電話
  CHECKBOX1: false, // 隱私政策勾選狀態
  CHECKBOX2: false, // 電子簽章勾選狀態
};

const initialStep3Data = {
  STORE_NAME: "豐原向陽山", // 商店/網站名稱（寫死）
  PRODUCT_NAME: "精品改裝", // 產品名稱（寫死）
  PRODUCT_PRICE: "", // 商品金額（寫死，預設為最低金額）
  INSTALLMENT_MONTHS: null, // 分期期數（可選）
  INSTALLMENT_AMOUNT: "", // 預計期付金額
  EMAIL: "", // Email
  AVAILABLE_TIME: "", // 可聯繫電話時段
};

const initialStep4Data = {
  ID_CARD_FRONT: null, // 身分證正面
  ID_CARD_BACK: null, // 身分證反面
  HEALTH_INSURANCE_CARD: null, // 健保卡
  SELFIE_WITH_ID_CARD: null, // 手持身分證自拍
  FINANCIAL_PROOF: null, // 財力證明
  OTHER_DOCUMENT1: null, // 其他身分證明 (軍/公/教/學生證)
  SIGN_DATA: null, // 簽名數據
};

export const useFormStore = defineStore("form", {
  state: () => ({
    currentStep: "step1", // 初始化當前步驟
    step1Data: { ...initialStep1Data },
    step2Data: {
      OTP_DIGITS: Array(6).fill(""), // 存放OTP六位數
    },
    step3Data: { ...initialStep3Data },
    step4Data: { ...initialStep4Data },
  }),

  actions: {
    // 更新 Step1 資料
    updateStep1Data(key, value) {
      if (key in this.step1Data) {
        this.step1Data[key] = value;
      }
    },

    // 更新 Step2 OTP 資料
    updateStep2Data(index, value) {
      if (index >= 0 && index < 6) {
        this.step2Data.OTP_DIGITS[index] = value;
      }
    },

    // 更新 Step3 資料
    updateStep3Data(key, value) {
      if (
        [
          "INSTALLMENT_MONTHS",
          "INSTALLMENT_AMOUNT",
          "EMAIL",
          "AVAILABLE_TIME",
        ].includes(key)
      ) {
        this.step3Data[key] = value;
      }
    },

    // 更新 Step4 資料
    updateStep4Data(key, value) {
      if (key in this.step4Data) {
        this.step4Data[key] = value;
      }
    },

    // 清除 Step1 資料
    clearStep1Data() {
      this.step1Data = { ...initialStep1Data };
    },

    // 清除 Step2 資料
    clearStep2Data() {
      this.step2Data.OTP_DIGITS = Array(6).fill("");
    },

    // 清除 Step3 資料
    clearStep3Data() {
      this.step3Data = { ...initialStep3Data };
    },

    // 清除 Step4 資料
    clearStep4Data() {
      this.step4Data = { ...initialStep4Data };
    },

    // 設置當前步驟
    setCurrentStep(step) {
      this.currentStep = step;
    },
  },
});
