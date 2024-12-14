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
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { computed } from "vue";
import { useFormStore } from "../stores/formStore";
import { defineAsyncComponent } from "vue";

// 動態載入步驟元件
const stepComponents = {
  step1: defineAsyncComponent(() => import("./steps/Step1Form.vue")),
  step2: defineAsyncComponent(() => import("./steps/Step2Otp.vue")),
  step3: defineAsyncComponent(() => import("./steps/Step3Application.vue")),
  step4: defineAsyncComponent(() => import("./steps/Step4Photo.vue")),
  step5: defineAsyncComponent(() => import("./steps/Step5Review.vue")),
};

const formStore = useFormStore();
const currentStepComponent = computed(
  () => stepComponents[formStore.currentStep]
);
const currentStep = computed({
  get: () => formStore.currentStep,
  set: (value) => (formStore.currentStep = value),
});
</script>

<style scoped>
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
</style>
