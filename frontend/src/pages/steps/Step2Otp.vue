<template>
  <div class="">
    <div class="text-center">
      <h6>OTP 認證</h6>
    </div>
    <q-form @submit.prevent="nextStep">
      <!-- OTP输入框 -->
      <div class="q-gutter-md row justify-center">
        <q-input
          v-for="(digit, index) in otpDigits"
          :key="index"
          v-model="otpDigits[index]"
          type="tel"
          maxlength="1"
          filled
          input-class="text-center"
          ref="otpInputs"
          @keypress="onlyNumber"
          @update:model-value="onInput(index)"
          input-style="font-weight: bold; color: gray; font-size: 24px;"
          class="otp-input"
        />
      </div>

      <div class="text-center q-mt-lg">
        <q-btn @click="prevStep" label="返回" color="secondary" />
        <q-btn type="submit" label="繼續" color="primary" />
      </div>
    </q-form>
  </div>
</template>

<script setup>
import { computed, ref, nextTick } from "vue";
import { useFormStore } from "../../stores/formStore";

// 使用 Pinia store
const formStore = useFormStore();

// 初始化 OTP 資料
const otpDigits = computed(() => formStore.step2Data.otpDigits);
const otpInputs = ref([]); //位置

/**
 * 限制只能輸入數字
 */
const onlyNumber = (event) => {
  const keyCode = event.keyCode || event.which;
  const keyValue = String.fromCharCode(keyCode);

  // 如果不是數字，取消輸入
  if (!/^\d+$/.test(keyValue)) {
    event.preventDefault();
  }
};

// 輸入框邏輯
const onInput = (index) => {
  const currentValue = otpDigits.value[index];
  if (!currentValue) {
    // 如果是 Backspace 且當前輸入框為空，跳回到上一個輸入框
    if (index > 0) {
      const prevInput = otpInputs.value[index - 1];
      if (prevInput) {
        prevInput.$el.querySelector("input").focus();
      }
    }
  } else if (currentValue) {
    // 如果當前輸入框有值且長度為 1，跳到下一個輸入框
    if (index < otpDigits.value.length - 1) {
      nextTick(() => {
        const nextInput = otpInputs.value[index + 1];
        if (nextInput) {
          nextInput.$el.querySelector("input").focus();
        }
      });
    }
  }
};

const sendOtpRequest = async (phoneNumber) => {
  try {
    const response = await axios.post(
      "http://localhost:8080/api/otp/send",
      null,
      {
        params: {
          phoneNumber: phoneNumber,
        },
      }
    );
    return response.data; // 返回后端的响应数据
  } catch (error) {
    console.error("发送 OTP 失败:", error);
    throw error;
  }
};

const nextStep = () => {
  formStore.currentStep = "step3";
};

const prevStep = () => {
  formStore.currentStep = "step1";
};
</script>

<style scoped>
/* 第二步驟－OTP輸入框 */
.q-gutter-md {
  display: flex;
  flex-direction: row; /* 设置为横向排列 */
  justify-content: center;
  gap: 10px; /* 每个输入框之间的间距 */
}

.otp-input {
  width: 50px; /* 设置每个输入框的宽度 */
  height: 50px; /* 设置每个输入框的高度 */
  text-align: center;
}
</style>
