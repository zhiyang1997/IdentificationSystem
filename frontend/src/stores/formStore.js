import { defineStore } from "pinia";

export const useFormStore = defineStore("form", {
  state: () => ({
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
    otpDigits: Array(6).fill(""), // 存储6位OTP
  }),
  actions: {
    updateStep1Data(key, value) {
      this.step1Data[key] = value;
    },
    updateOtpDigit(index, value) {
      this.otpDigits[index] = value;
    },
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
      this.otpDigits = Array(6).fill("");
    },
  },
});
