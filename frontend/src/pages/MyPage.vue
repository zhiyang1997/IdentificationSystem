<template>
  <q-layout>
    <!-- 頂部區域 -->
    <q-header class="custom-header" elevated>
      <q-toolbar>
        <q-space />
        <img src="../imgs/BoBoPay.png" alt="BoBoPay" class="header-logo" />
        <q-space />
      </q-toolbar>
    </q-header>

    <!-- 主要內容區域 -->
    <q-page-container class="custom-page-container">
      <q-page class="q-pa-md">
        <!-- 步驟條 -->
        <q-stepper v-model="step" animated>
          <!-- 步驟 1: 基本資料 -->
          <q-step name="step1" title="基本資料">
            <div class="">
              <div class="text-center">
                <h6>基本資料</h6>
              </div>
              <q-form @submit.prevent="nextStep" @validation-error="showErrors">
                <!-- 姓名 -->
                <div class="q-form-row q-mb-md">
                  <label class="block q-mb-md">姓名<span class="required">*</span></label>
                  <q-input
                    dense
                    filled
                    v-model="step1Data.NAME"
                    placeholder="請填入真實姓名"
                    style="width: 80%;"
                  />
                  </div>

                <!-- 手機門號 -->
                <div class="q-form-row q-mb-md">
                  <label class="block q-mb-md">手機門號<span class="required">*</span></label>
                  <q-input
                    dense
                    filled
                    v-model="step1Data.PHONE_NUMBER"
                    type="tel"
                    placeholder="填入格式：012345678(共十碼)"
                    style="width: 80%;"
                    maxlength="10"
                    @input="limitLength"
                    @keypress="onlyNumber($event)"
                  />
                </div>

                <!-- 身分證字號 -->
                <div class="q-form-row q-mb-md">
                  <label class="block q-mb-md">身分證字號<span class="required">*</span></label>
                  <q-input
                    dense
                    filled
                    v-model="step1Data.NATIONAL_ID"
                    placeholder="請輸入有效之身分證字號"
                    style="width: 80%; height:1%;"
                    maxlength="10"
                    :style="inputStyle"
                  />
                </div>

                <!-- 出生日期 -->
                <div class="q-form-row q-mb-md">
                  <label class="block q-mb-md">出生日期<span class="required">*</span><q-icon name="event" class="cursor-pointer" @click="showCalendar = true" /></label>
                  <q-input
                    dense
                    filled
                    v-model="step1Data.BIRTHDATE"
                    placeholder="請從小月曆選擇出生日期"
                    style="width: 80%;"
                  />
                  <!-- 小月曆彈窗 -->
                  <q-popup-proxy
                    v-model="showCalendar"
                    transition-show="scale"
                    transition-hide="scale"
                  >
                  <q-date
                    v-model="step1Data.BIRTHDATE"
                    mask="YYYY-MM-DD"
                    @input="showCalendar = false"
                    />
                  </q-popup-proxy>

                </div>

                <!-- 戶籍地址 -->
                <div class="q-form-row q-mb-md">
                  <label class="block q-mb-md">戶籍地址<span class="required">*</span></label>
                  <q-input
                    dense
                    filled
                    v-model="step1Data.REGISTERED_ADDRESS"
                    placeholder="請輸入戶籍地址"
                    style="width: 80%;"
                  />
                </div>

                <!-- 分期期數 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 q-pa-xs q-pr-md">
                    分期期數<span class="required">*</span>
                  </div>
                  <div class="col-6">
                    <q-select
                      v-model="step1Data.installmentPeriod"
                      :options="installmentOptions"
                      filled
                      :rules="[(value) => !!value || '分期期數為必填欄位']"
                      :disable="!step1Data.productPrice"
                      style="min-height: 80px"
                    />
                  </div>
                </div>

                <!-- 期付金額 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">期付金額</div>
                  <div class="col-6">
                    <q-input
                      filled
                      v-model="step1Data.installmentAmount"
                      type="number"
                      readonly
                      style="min-height: 80px"
                    />
                  </div>
                </div>

                <!-- 手機門號 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">
                    手機門號<span class="required">*</span>
                  </div>
                  <div class="col-6">
                    <q-input
                      clearable
                      filled
                      color="blue-12"
                      v-model="step1Data.phoneNumber"
                      type="tel"
                      :rules="[(value) => !!value || '手機門號為必填欄位']"
                      label="PhoneNumber"
                      style="min-height: 80px"
                    />
                  </div>
                </div>

                <!-- E-mail -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">E-mail</div>
                  <div class="col-6">
                    <q-input
                      clearable
                      filled
                      color="blue-12"
                      v-model="step1Data.email"
                      type="email"
                      label="E-mail"
                      style="min-height: 80px"
                    />
                  </div>
                </div>

                <!-- 可接聽時間 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">
                    可接聽時間<span class="required">*</span>
                  </div>
                  <div class="col-6">
                    <q-select
                      v-model="step1Data.availableTime"
                      :options="timeOptions"
                      filled
                      :rules="[(value) => !!value || '可接聽時間為必填欄位']"
                      style="min-height: 80px"
                    />
                  </div>
                </div>

                <!-- 勾選框 -->
                <div class="checkbox-container q-mt-md">
                  <div class="q-pa-xs q-pr-md">
                    <q-checkbox v-model="val" />
                  </div>
                  <span>
                    ＊我已詳細閱讀並充分了解、同意
                    <a href="#" @click="showPrivacyPolicyDialog"
                      >「隱私權政策」</a
                    >
                    及
                    <a href="#" @click="showTermsDialog"
                      >「行動身分識別服務使用者約定條款及隱私權告知條款」</a
                    >。
                  </span>
                </div>

                <!-- 彈出視窗 -->
                <!-- 使用 PrivacyPolicyDialog 組件 -->
                <q-dialog v-model="isPrivacyPolicyDialogOpen" persistent>
                  <PrivacyPolicyDialog @readed="closePrivacyPolicyDialog" />
                </q-dialog>
                <q-dialog v-model="isTermsDialogOpen" persistent>
                  <TermsDialog @readed="closeTermsDialog" />
                </q-dialog>

                <div class="text-center q-mt-lg">
                  <q-btn type="submit" label="下一步" color="primary" />
                </div>
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 2: OTP 認證 -->
          <q-step name="step2" title="OTP認證">
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
                    type="text"
                    maxlength="1"
                    filled
                    outlined
                    input-class="text-center"
                    ref="otpInput"
                    @input="onInput(index)"
                    @keyup="onInput(index)"
                    input-style="font-weight: bold; color: gray; font-size: 24px;"
                  />
                </div>
                <div class="text-center q-mt-lg">
                  <q-btn @click="prevStep" label="Back" color="secondary" />
                  <q-btn type="submit" label="Next" color="primary" />
                </div>
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 3: 證件照片 -->
          <q-step name="step3" title="證件照片">
            <div class="">
              <div class="text-center">
                <h6>證件照片</h6>
              </div>
              <q-form @submit.prevent="nextStep">
                <q-file filled v-model="step1Data.photo" label="上傳證件照片" />
                <div class="text-center q-mt-lg">
                  <q-btn @click="prevStep" label="Back" color="secondary" />
                  <q-btn type="submit" label="Next" color="primary" />
                </div>
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 4: 開始審核 -->
          <q-step name="step4" title="開始審核">
            <div class="">
              <div class="text-center">
                <h6>開始審核</h6>
              </div>
              <p>您的申請正在審核中。</p>
              <div class="text-center q-mt-lg">
                <q-btn @click="prevStep" label="Back" color="secondary" />
              </div>
            </div>
          </q-step>
        </q-stepper>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import axios from "axios";
import { computed, nextTick, reactive, ref, watch } from "vue";
import { useFormStore } from "../stores/formStore";
import PrivacyPolicyDialog from "./PrivacyPolicyDialog.vue"; // 引入對話框組件
import TermsDialog from "./TermsDialog.vue";

// 使用 Pinia store
const formStore = useFormStore();

const step = ref("step1"); // 初始步驟
const val = ref(false); // 勾選框

const isPrivacyPolicyDialogOpen = ref(false);
const isTermsDialogOpen = ref(false);

const showPrivacyPolicyDialog = () => {
  isPrivacyPolicyDialogOpen.value = true;
};

const closePrivacyPolicyDialog = () => {
  isPrivacyPolicyDialogOpen.value = false;
};

const showTermsDialog = () => {
  isTermsDialogOpen.value = true;
};

const closeTermsDialog = () => {
  isTermsDialogOpen.value = false;
};

// 使用 步驟一 的資料
const step1Data = computed(() => formStore.step1Data);


/**
 * 步驟一 手機號碼 十碼
 */
const onlyNumber = (event) => {
  const keyCode = event.keyCode || event.which;
  const keyValue = String.fromCharCode(keyCode);

  // 如果不是數字，取消輸入
  if (!/^\d+$/.test(keyValue)) {
    event.preventDefault();
  }
};
const limitLength = () => {
  const maxLength = 10;
  if (step1Data.value.PHONE_NUMBER.length > maxLength) {
    step1Data.value.PHONE_NUMBER = step1Data.value.PHONE_NUMBER.slice(0, maxLength);
  }
};

/**
 * 步驟一 身份證字號驗證
 */

const isNationalIDValid = (id) => {
  if (!id) return true; // 如果是空值，視為有效（不提示）
  return /^[A-Z][12]/.test(id); // 第一碼是字母，第二碼是 1 或 2
};

// 動態樣式綁定
const inputStyle = reactive({
  border: "",
});

// 使用 watch 動態監聽
watch(
  () => step1Data.value.NATIONAL_ID,
  (newValue) => {
    inputStyle.border = isNationalIDValid(newValue) ? "" : "2px solid red";
  }
);

const installmentOptions = [
  { label: "請選擇期數", value: null },
  { label: "1期", value: 1 },
  { label: "3期", value: 3 },
  { label: "6期", value: 6 },
  { label: "12期", value: 12 },
];

const timeOptions = [
  { label: "上午", value: "AM" },
  { label: "下午", value: "PM" },
  { label: "晚上", value: "Evening" },
];

watch(
  () => formStore.step1Data.productPrice,
  (newPrice) => {
    if (!newPrice) {
      formStore.step1Data.installmentPeriod = null;
    }
  }
);

watch(
  () => [formStore.step1Data.productPrice, formStore.step1Data.installmentPeriod],
  ([newPrice, newPeriod]) => {
    const price = parseFloat(newPrice);
    const period = parseInt(newPeriod?.value, 10);

    if (!isNaN(price) && !isNaN(period) && period > 0) {
      const rawAmount = (price * 1.04) / period;
      formStore.step1Data.installmentAmount = Math.round(rawAmount);
    } else {
      formStore.step1Data.installmentAmount = ""; // 清空显示的值
    }
  }
);

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

const nextStep = async () => {
  const steps = ["step1", "step2", "step3", "step4"];
  const currentIndex = steps.indexOf(step.value);

  if (currentIndex === 0) {
    // 假设在第一个步骤时发送 OTP
    try {
      await sendOtpRequest(step1Data.value.phoneNumber);
      console.log("OTP 已成功发送");
    } catch (error) {
      // 处理发送失败的情况，比如显示错误消息
      console.error("无法发送 OTP");
      return; // 阻止进入下一步
    }
  }

  if (currentIndex < steps.length - 1) {
    step.value = steps[currentIndex + 1];
  }
};

const prevStep = () => {
  const steps = ["step1", "step2", "step3", "step4"];
  const currentIndex = steps.indexOf(step.value);
  if (currentIndex > 0) {
    step.value = steps[currentIndex - 1];
  }
};

// 显示验证错误
const showErrors = () => {
  // Here you can handle the errors as needed
  // E.g., show a toast or alert if there are validation errors
};

const otpDigits = formStore.otpDigits; // 从 store 获取 OTP

const onInput = async (index) => {
  if (otpDigits[index].length === 1 && index < otpDigits.length - 1) {
    await nextTick();
    const nextInput =
      document.querySelectorAll('input[type="text"]')[index + 1];
    if (nextInput) {
      nextInput.focus();
    }
  }

  if (otpDigits[index] === "" && index > 0) {
    await nextTick();
    const prevInput =
      document.querySelectorAll('input[type="text"]')[index - 1];
    if (prevInput) {
      prevInput.focus();
    }
  }
};
</script>

<style>
.custom-header {
  background-color: white; /* 背景色為白色 */
  border-bottom: 4px solid #0000ff; /* 藍色底線，寬度為4px */
}

.header-logo {
  height: 60px; /* 調整圖片高度 */
}

.label {
  font-weight: bold;
}

.custom-page-container {
  max-width: 800px; /* 设定最大宽度 */
  margin: 0 auto; /* 居中显示 */
}

.checkbox-container {
  display: flex;
  align-items: center;
  width: 100%; /* 确保容器宽度为100% */
  margin-bottom: 20px; /* 为 checkbox 和其他元素之间的间距 */
}

.required {
  color: red; /* 將*號設為紅色 */
}

.q-gutter-md .q-input {
  width: 40px; /* 调整宽度以适应每个字符 */
}

.q-input .q-field__control {
  text-align: center; /* 将字符置中 */
}

.invalid-input {
  border: 1px solid red !important; /* 將輸入框邊框設置為紅色 */
}
</style>
