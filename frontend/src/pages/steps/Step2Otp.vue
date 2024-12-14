<template>
  <div class="">
    <div class="text-center">
      <h6>
        簡訊已發送到<span class="phone-number">{{
          formStore.step1Data.PHONE_NUMBER
        }}</span
        >請盡快輸入驗證碼
      </h6>
    </div>
    <q-form @submit.prevent="nextStep">
      <!-- OTP输入框 -->
      <div class="otp-wrapper q-gutter-md row justify-center">
        <div class="otp-container">
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
        <q-btn
          :label="resendLabel"
          color="yellow-9"
          class="resend-button"
          @click="resendOtp"
          :disable="resendDisabled"
        />
      </div>

      <!-- 按鈕區 -->
      <div class="button-group">
        <q-btn
          @click="prevStep"
          label="返回"
          color="grey-7"
          class="action-button"
        />
        <q-btn
          type="submit"
          label="繼續"
          color="green-6"
          class="action-button"
        />
      </div>
    </q-form>
  </div>
</template>

<script setup>
import { computed, ref, nextTick, onMounted, onBeforeUnmount } from "vue";
import { useFormStore } from "../../stores/formStore";
// 使用 Pinia store
const formStore = useFormStore();

// 初始化 OTP 資料
const otpDigits = computed(() => formStore.step2Data.otpDigits);
const otpInputs = ref([]); //位置

const resendDisabled = ref(true); // 是否禁用重新發送按鈕
const countdown = ref(60); // 倒數計時的秒數（1 分鐘）
let timer = null; // 計時器

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

// 重新發送按鈕的顯示文字
const resendLabel = computed(() =>
  resendDisabled.value ? `重新發送 (${countdown.value}s)` : "重新發送"
);

// 啟動倒數計時
const startCountdown = () => {
  resendDisabled.value = true;
  countdown.value = 60; // 重置倒數計時為 60 秒
  timer = setInterval(() => {
    if (countdown.value > 0) {
      countdown.value--;
    } else {
      clearInterval(timer); // 清除計時器
      resendDisabled.value = false; // 啟用按鈕
    }
  }, 1000);
};

// 重新發送驗證碼的邏輯
const resendOtp = () => {
  // 執行重新發送驗證碼的相關操作
  console.log("重新發送驗證碼");
  startCountdown(); // 重新開始倒數計時
};

// 初始化倒數計時
onMounted(() => {
  startCountdown();
});

// 清除計時器避免記憶體洩漏
onBeforeUnmount(() => {
  if (timer) {
    clearInterval(timer);
  }
});

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
.otp-wrapper {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}
.otp-container {
  display: flex;
  flex-direction: row; /* 设置为横向排列 */
  justify-content: center;
  gap: 10px; /* 每个输入框之间的间距 */
}

.q-input{
  width: 50px; /* 设置每个输入框的宽度 */
  height: 50px; /* 设置每个输入框的高度 */
  text-align: center;
}

/* 操作按鈕樣式 */
.resend-button {
  margin-left: 15px; /* 与OTP输入框保持距离 */
  font-weight: bold;
  height: 20px;
  width: 140px;
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 100px; /* 與上方 OTP 區域的間隔 */
}

.action-button {
  width: 120px; /* 統一按鈕寬度 */
}

.phone-number {
  color: rgba(32, 121, 255, 1); /* 將電話號碼設為藍色 */
}
</style>
