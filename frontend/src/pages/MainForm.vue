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
  color: black;
  background-color: #faf4df; /* 背景色為米色 */
  border-bottom: 2px solid rgba(0, 0, 0, 1); /* 藍色底線，寬度為4px */
  max-width: 700px; /* 設定最大寬度 */
  margin: 0 auto; /* 居中顯示 */
}

.custom-page-container {
  max-width: 800px; /* 設定最大寬度 */
  margin: 0 auto; /* 居中顯示 */
}

.copy-right {
  text-align: center; /* 文本水平居中 */
  display: flex; /* 使用 Flexbox 布局 */
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 30px; /* 可根據需求調整高度 */
  margin-bottom: 10px;
  font-size: 14px; /* 字體大小 */
  color: #5F5F5F;
}
</style>
