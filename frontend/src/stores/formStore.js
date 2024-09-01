import { defineStore } from "pinia";

export const useFormStore = defineStore("form", {
  state: () => ({
    formData: {
      storeName: "",
      productName1: "",
      productName2: "",
      productPrice: "",
      installmentPeriod: null,
      installmentAmount: "",
      phoneNumber: "",
      email: "",
      availableTime: null,
      termsAccepted: false,
    },
    otpDigits: Array(6).fill(""), // 存储6位OTP
  }),
  actions: {
    updateFormData(key, value) {
      this.formData[key] = value;
    },
    updateOtpDigit(index, value) {
      this.otpDigits[index] = value;
    },
    clearFormData() {
      this.formData = {
        storeName: "",
        productName1: "",
        productName2: "",
        productPrice: "",
        installmentPeriod: null,
        installmentAmount: "",
        phoneNumber: "",
        email: "",
        availableTime: null,
        termsAccepted: false,
      };
      this.otpDigits = Array(6).fill("");
    },
  },
});
