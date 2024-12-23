<template>
  <q-layout>
    <!-- 頂部區域 -->
    <q-header class="custom-header" elevated>
      <q-toolbar>
        <q-space />
        <h6>{{ currentStepTitle }}</h6>
        <q-space />
      </q-toolbar>
    </q-header>

    <q-page-container class="custom-page-container">
      <q-page class="q-pa-md">
        <!-- 步驟條 -->
        <q-stepper v-model="currentStep" alternative-labels animated>
          <!-- 步驟 1: 基本資料 -->
          <q-step name="step1" title="基本資料">
            <component :is="currentStepComponent" />
          </q-step>

          <!-- 步驟 2: OTP 認證 -->
          <q-step name="step2" title="OTP 認證">
            <component :is="currentStepComponent" />
          </q-step>

          <!-- 步驟 3: 申請資料 -->
          <q-step name="step3" title="申請資料">
            <component :is="currentStepComponent" />
          </q-step>

          <!-- 步驟 4: 證件與簽名 -->
          <q-step name="step4" title="證件與簽名">
            <component :is="currentStepComponent" />
          </q-step>

          <!-- 步驟 5: 開始審核 -->
          <q-step name="step5" title="開始審核">
            <component :is="currentStepComponent" />
          </q-step>
        </q-stepper>
      </q-page>
      <div class="copy-right"><b>© 2024 XXX股份有限公司 版權所有</b></div>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { computed } from "vue";
import { useFormStore } from "../stores/formStore";
import Step1Form from "./steps/Step1Form.vue";
import Step2Otp from "./steps/Step2Otp.vue";
import Step3Application from "./steps/Step3Application.vue";
import Step4Photo from "./steps/Step4Photo.vue";
import Step5Review from "./steps/Step5Review.vue";

// 動態載入步驟元件
const stepComponents = {
  step1: Step1Form,
  step2: Step2Otp,
  step3: Step3Application,
  step4: Step4Photo,
  step5: Step5Review,
};

const formStore = useFormStore();
const currentStepComponent = computed(
  () => stepComponents[formStore.currentStep]
);
const currentStep = computed({
  get: () => formStore.currentStep,
  set: (value) => (formStore.currentStep = value),
});

// 定義步驟標題
const stepTitles = {
  step1: "基本資料",
  step2: "OTP 認證",
  step3: "申請資料",
  step4: "證件與簽名",
  step5: "開始審核",
};

// 根據當前步驟動態計算標題
const currentStepTitle = computed(() => stepTitles[formStore.currentStep]);
</script>

<style scoped>
.custom-header {
  color: #4a4a4a; /* 深灰色字體，清晰且溫和 */
  background-color: #f2c86e; /* 固定米黃色背景 */
  border-bottom: 3px solid #333333; /* 深灰底線，增強視覺層次 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加輕微陰影 */
  padding: 20px 0; /* 增加內邊距 */
}

.custom-header h6 {
  font-size: 24px; /* 更改字體大小 */
  color: #5f5f5f; /* 深灰字體顏色 */
  font-weight: bolder; /* 字體加粗 */
  margin: 10px 0; /* 移除多餘的上下間距 */
  text-align: center; /* 水平居中對齊 */
}

.custom-page-container {
  max-width: 800px; /* 固定寬度 */
  margin: 0 auto; /* 水平居中 */
}

.copy-right {
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px; /* 與主體保持較大距離 */
  margin-bottom: 20px;
  font-size: 14px;
  color: #7a7a7a; /* 使用中灰色，柔和不刺眼 */
}

.q-stepper__nav {
  margin-top: 30px; /* 增加與步驟條的距離 */
}

.q-stepper__step--active {
  background-color: #f2c86e; /* 當前步驟點的米黃色背景 */
  border-radius: 50%; /* 確保步驟點為圓形 */
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15); /* 添加柔和陰影 */
  border: 2px solid #ffffff; /* 白色邊框，增強對比 */
}

.q-stepper__step {
  box-shadow: 10px 10px rgba(0, 0, 0, 0.1); /* 添加輕微陰影 */
}
</style>
