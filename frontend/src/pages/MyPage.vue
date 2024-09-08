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
          <!-- 步驟 1: 申請資料 -->
          <q-step name="step1" title="申請資料">
            <div class="">
              <div style="text-align: center">
                <h6>申請資料</h6>
              </div>
              <q-form @submit.prevent="nextStep" @validation-error="showErrors">
                <!-- 商店/網站名稱 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">
                    商店/網站名稱
                  </div>
                  <div class="col-6">
                    <q-input
                      clearable
                      filled
                      color="blue-12"
                      v-model="formData.storeName"
                      label="Shop/Web"
                      style="min-height: 80px"
                    />
                  </div>
                </div>

                <!-- 產品名稱 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">產品名稱</div>
                  <div class="col-6">
                    <q-input
                      clearable
                      filled
                      color="blue-12"
                      v-model="formData.productName1"
                      label="Product"
                      style="min-height: 80px"
                    />
                  </div>
                </div>

                <!-- 產品金額 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">
                    產品金額<span class="required">*</span>
                  </div>
                  <div class="col-6">
                    <q-input
                      clearable
                      filled
                      color="blue-12"
                      v-model="formData.productPrice"
                      :rules="[(value) => !!value || '產品金額為必填欄位']"
                      label="Cash"
                      style="min-height: 80px"
                    />
                  </div>
                </div>

                <!-- 分期期數 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">
                    分期期數<span class="required">*</span>
                  </div>
                  <div class="col-6">
                    <q-select
                      v-model="formData.installmentPeriod"
                      :options="installmentOptions"
                      filled
                      :rules="[(value) => !!value || '分期期數為必填欄位']"
                      :disable="!formData.productPrice"
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
                      v-model="formData.installmentAmount"
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
                      v-model="formData.phoneNumber"
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
                      v-model="formData.email"
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
                      v-model="formData.availableTime"
                      :options="availableTimeOptions"
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
                <q-dialog v-model="isPrivacyPolicyDialogOpen" persistent>
                  <q-card>
                    <q-card-section>
                      <div class="text-h6">隱私權政策</div>
                    </q-card-section>
                    <q-card-section class="q-pt-none">
                      <p>這裡放隱私權政策的內容...</p>
                    </q-card-section>
                    <q-card-actions align="right">
                      <q-btn
                        flat
                        label="關閉"
                        color="primary"
                        @click="isPrivacyPolicyDialogOpen = false"
                      />
                    </q-card-actions>
                  </q-card>
                </q-dialog>
                <q-dialog v-model="isTermsDialogOpen" persistent>
                  <q-card>
                    <q-card-section>
                      <div class="text-h6">
                        行動身分識別服務使用者約定條款及隱私權告知條款
                      </div>
                    </q-card-section>
                    <q-card-section class="q-pt-none">
                      <p>這裡放條款的內容...</p>
                    </q-card-section>
                    <q-card-actions align="right">
                      <q-btn
                        flat
                        label="關閉"
                        color="primary"
                        @click="isTermsDialogOpen = false"
                      />
                    </q-card-actions>
                  </q-card>
                </q-dialog>

                <div class="flex justify-center q-mt-md">
                  <q-btn type="submit" label="下一步" color="primary" />
                </div>
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 2: OTP 認證 -->
          <q-step name="step2" title="OTP認證">
            <div class="">
              <div style="text-align: center">
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
                <div class="flex justify-center q-mt-md">
                  <q-btn @click="prevStep" label="Back" color="secondary" />
                  <q-btn type="submit" label="Next" color="primary" />
                </div>
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 3: 證件照片 -->
          <q-step name="step3" title="證件照片">
            <div class="">
              <div style="text-align: center">
                <h6>證件照片</h6>
              </div>
              <q-form @submit.prevent="nextStep">
                <q-file filled v-model="formData.photo" label="上傳證件照片" />
                <div class="flex justify-center q-mt-md">
                  <q-btn @click="prevStep" label="Back" color="secondary" />
                  <q-btn type="submit" label="Next" color="primary" />
                </div>
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 4: 開始審核 -->
          <q-step name="step4" title="開始審核">
            <div class="">
              <div style="text-align: center">
                <h6>開始審核</h6>
              </div>
              <p>您的申請正在審核中。</p>
              <div class="flex justify-center q-mt-md">
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
import { ref, watch, nextTick } from "vue";
import { useFormStore } from "../stores/formStore";
import axios from "axios";
import { computed } from "vue";
// 使用 Pinia store
const formStore = useFormStore();

const step = ref("step1"); // 初始步驟
const val = ref(false); // 勾選框

const isPrivacyPolicyDialogOpen = ref(false);
const isTermsDialogOpen = ref(false);

const showPrivacyPolicyDialog = () => {
  isPrivacyPolicyDialogOpen.value = true;
};

const showTermsDialog = () => {
  isTermsDialogOpen.value = true;
};

// 使用 formStore 的数据
const formData = computed(() => formStore.formData);

const installmentOptions = [
  { label: "請選擇期數", value: null },
  { label: "1期", value: 1 },
  { label: "3期", value: 3 },
  { label: "6期", value: 6 },
  { label: "12期", value: 12 },
];

const availableTimeOptions = [
  { label: "上午", value: "AM" },
  { label: "下午", value: "PM" },
  { label: "晚上", value: "Evening" },
];

watch(
  () => formStore.formData.productPrice,
  (newPrice) => {
    if (!newPrice) {
      formStore.formData.installmentPeriod = null;
    }
  }
);

watch(
  () => [formStore.formData.productPrice, formStore.formData.installmentPeriod],
  ([newPrice, newPeriod]) => {
    const price = parseFloat(newPrice);
    const period = parseInt(newPeriod?.value, 10);

    if (!isNaN(price) && !isNaN(period) && period > 0) {
      const rawAmount = (price * 1.04) / period;
      formStore.formData.installmentAmount = Math.round(rawAmount);
    } else {
      formStore.formData.installmentAmount = ""; // 清空显示的值
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
      await sendOtpRequest(formData.value.phoneNumber);
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
</style>
