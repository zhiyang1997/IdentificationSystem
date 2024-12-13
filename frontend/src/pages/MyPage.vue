<template>
  <q-layout>
    <!-- 頂部區域 -->
    <q-header class="custom-header" elevated>
      <q-toolbar>
        <q-space />
        <h6>基本資料</h6>
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
            <q-form @submit.prevent="nextStep" @validation-error="showErrors">
              <!-- 姓名 -->
              <div class="q-form-row q-mb-md">
                <label class="block q-mb-md"
                  >姓名<span class="required">*</span></label
                >
                <q-input
                  dense
                  filled
                  v-model="step1Data.NAME"
                  placeholder="請填入真實姓名"
                  style="width: 100%"
                />
              </div>

              <!-- 手機門號 -->
              <div class="q-form-row q-mb-md">
                <label class="block q-mb-md"
                  >手機門號<span class="required">*</span></label
                >
                <q-input
                  dense
                  filled
                  v-model="step1Data.PHONE_NUMBER"
                  type="tel"
                  placeholder="填入格式：012345678(共十碼)"
                  style="width: 100%"
                  maxlength="10"
                  @input="limitLength"
                  @keypress="onlyNumber($event)"
                />
              </div>

              <!-- 身分證字號 -->
              <div class="q-form-row q-mb-md">
                <label class="block q-mb-md"
                  >身分證字號<span class="required">*</span></label
                >
                <q-input
                  dense
                  filled
                  v-model="step1Data.NATIONAL_ID"
                  placeholder="請輸入有效之身分證字號"
                  style="width: 100%"
                  maxlength="10"
                  :style="inputStyle"
                />
              </div>

              <!-- 出生日期 -->
              <div class="q-form-row q-mb-md">
                <label class="block q-mb-md"
                  >出生日期<span class="required">*</span
                  ><q-icon
                    name="event"
                    class="cursor-pointer"
                    @click="showCalendar = true"
                /></label>
                <q-input
                  dense
                  filled
                  v-model="step1Data.BIRTHDATE"
                  placeholder="請從小月曆選擇出生日期"
                  style="width: 100%"
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
                <label class="block q-mb-md"
                  >戶籍地址<span class="required">*</span></label
                >
                <q-input
                  dense
                  filled
                  v-model="step1Data.REGISTERED_ADDRESS"
                  placeholder="請輸入戶籍地址"
                  style="width: 100%"
                />
              </div>

              <!-- 親屬1 -->
              <div class="q-form-row q-mb-md flex-row">
                <div class="input-group">
                  <label class="block"
                    >親屬姓名1<span class="required">*</span></label
                  >
                  <q-input
                    dense
                    filled
                    v-model="step1Data.RELATIVE_NAME1"
                    placeholder="請輸入親屬姓名"
                    style="width: 100%"
                  />
                </div>
                <div class="input-group">
                  <label class="block"
                    >親屬電話1<span class="required">*</span></label
                  >
                  <q-input
                    dense
                    filled
                    v-model="step1Data.RELATIVE_PHONE1"
                    placeholder="請輸入親屬電話"
                    style="width: 100%"
                  />
                </div>
                <div class="input-group">
                  <label class="block"
                    >親屬關係1<span class="required">*</span></label
                  >
                  <q-input
                    dense
                    filled
                    v-model="step1Data.RELATIVE_RELATION1"
                    placeholder="請輸入親屬關係"
                    style="width: 100%"
                  />
                </div>
              </div>

              <!-- 親屬2 -->
              <div class="q-form-row q-mb-md flex-row">
                <div class="input-group">
                  <label class="block"
                    >親屬姓名2<span class="required">*</span></label
                  >
                  <q-input
                    dense
                    filled
                    v-model="step1Data.RELATIVE_NAME2"
                    placeholder="請輸入親屬姓名"
                    style="width: 100%"
                  />
                </div>
                <div class="input-group">
                  <label class="block"
                    >親屬電話2<span class="required">*</span></label
                  >
                  <q-input
                    dense
                    filled
                    v-model="step1Data.RELATIVE_PHONE2"
                    placeholder="請輸入親屬電話"
                    style="width: 100%"
                  />
                </div>
                <div class="input-group">
                  <label class="block"
                    >親屬關係2<span class="required">*</span></label
                  >
                  <q-input
                    dense
                    filled
                    v-model="step1Data.RELATIVE_RELATION2"
                    placeholder="請輸入親屬關係"
                    style="width: 100%"
                  />
                </div>
              </div>

              <!-- 朋友 -->
              <div class="q-form-row q-mb-md flex-row">
                <div class="input-group">
                  <label class="block"
                    >朋友姓名<span class="required">*</span></label
                  >
                  <q-input
                    dense
                    filled
                    v-model="step1Data.FRIEND_NAME"
                    placeholder="請輸入朋友姓名"
                    style="width: 100%"
                  />
                </div>
                <div class="input-group">
                  <label class="block"
                    >朋友電話<span class="required">*</span></label
                  >
                  <q-input
                    dense
                    filled
                    v-model="step1Data.FRIEND_PHONE"
                    placeholder="請輸入朋友電話"
                    style="width: 100%"
                  />
                </div>
              </div>
              <!-- 勾選框 -->
              <div class="checkbox-container q-mt-md">
                <div class="q-pa-xs q-pr-md">
                  <q-checkbox v-model="val1" />
                </div>
                <span>
                  <span class="required">*</span>我已詳細閱讀並充分了解、同意
                  <a href="#" @click="showPrivacyPolicyDialog"
                    >「隱私權政策」</a
                  >
                  及
                  <a href="#" @click="showTermsDialog"
                    >「行動身分識別服務使用者約定條款及隱私權告知條款」</a
                  >。
                </span>
              </div>
              <div class="checkbox-container q-mt-md">
                <div class="q-pa-xs q-pr-md">
                  <q-checkbox v-model="val2" />
                </div>
                <span
                  ><span class="required">*</span
                  >本人同意以使用本系統產制之電子文件作為分期付款申請及交易之基礎，以電子簽章取代傳統之書面文件及簽名、蓋章確定之相關法律責任。
                </span>
              </div>
              <span
                ><span class="required">*</span
                >當您點選<span class="required">【繼續】</span>按鈕後，系統會發送一份簡訊密碼至您的手機門號，請確認門號可正常接收簡訊。
              </span>
              <!-- 彈出視窗 -->
              <!-- 使用 PrivacyPolicyDialog 組件 -->
              <q-dialog v-model="isPrivacyPolicyDialogOpen" persistent>
                <PrivacyPolicyDialog @readed="closePrivacyPolicyDialog" />
              </q-dialog>
              <q-dialog v-model="isTermsDialogOpen" persistent>
                <TermsDialog @readed="closeTermsDialog" />
              </q-dialog>

              <div class="text-center q-mt-lg">
                <q-btn type="submit" label="繼續" color="primary" />
              </div>
            </q-form>
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
                  <q-btn @click="prevStep" label="返回" color="secondary" />
                  <q-btn type="submit" label="繼續" color="primary" />
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
                <q-btn @click="prevStep" label="返回" color="secondary" />
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
const val1 = ref(false); // 勾選框1
const val2 = ref(false); // 勾選框2

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
const limitLength = () => {
  const maxLength = 10;
  if (step1Data.value.PHONE_NUMBER.length > maxLength) {
    step1Data.value.PHONE_NUMBER = step1Data.value.PHONE_NUMBER.slice(
      0,
      maxLength
    );
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

// 初始化 OTP 資料
const otpDigits = computed(() => formStore.otpDigits);
const otpInputs = ref([]); //位置

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

// 限制只能输入数字
const onKeyPress = (event) => {
  const charCode = event.which || event.keyCode;
  if (charCode < 48 || charCode > 57) {
    // 阻止非数字键
    event.preventDefault();
  }
};

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
  () => [
    formStore.step1Data.productPrice,
    formStore.step1Data.installmentPeriod,
  ],
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

  // if (currentIndex === 0) {
  //   // 假设在第一个步骤时发送 OTP
  //   try {
  //     await sendOtpRequest(step1Data.value.phoneNumber);
  //     console.log("OTP 已成功发送");
  //   } catch (error) {
  //     // 处理发送失败的情况，比如显示错误消息
  //     console.error("无法发送 OTP");
  //     return; // 阻止进入下一步
  //   }
  // }

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
</script>

<style>
.custom-header {
  color: black;
  background-color: #faf4df; /* 背景色為米色 */
  border-bottom: 3px solid black; /* 藍色底線，寬度為4px */
  max-width: 800px; /* 設定最大寬度 */
  margin: 0 auto; /* 居中显示 */
}

.custom-page-container {
  max-width: 800px; /* 設定最大寬度 */
  margin: 0 auto; /* 居中显示 */
}

.checkbox-container {
  display: flex;
  align-items: center;
  width: 100%; /* 确保容器宽度为100% */
  margin-bottom: 10px; /* 为 checkbox 和其他元素之间的间距 */
}

.required {
  color: red; /* 將*號設為紅色 */
}

.invalid-input {
  border: 1px solid red !important; /* 將輸入框邊框設置為紅色 */
}

/* 第一步驟－親屬、朋友欄位布局 */
.flex-row {
  display: flex;
  gap: 16px; /* 控制每個輸入框之間的間距 */
  align-items: flex-start; /* 對齊方式 */
}

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
