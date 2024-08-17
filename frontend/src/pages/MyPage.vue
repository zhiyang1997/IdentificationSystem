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
    <q-page-container>
      <q-page>
        <!-- 步驟條 -->
        <q-stepper v-model="step" animated>
          <!-- 步驟 1: 申請資料 -->
          <q-step name="step1" title="申請資料">
            <div>
              <h6>申請資料</h6>
              <!-- 這裡是步驟 1 的內容 -->
              <q-form @submit.prevent="nextStep">
                <q-input
                  filled
                  v-model="formData.application"
                  label="申請資料"
                />
                <q-btn type="submit" label="Next" color="primary" />
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 2: OTP 認證 -->
          <q-step name="step2" title="OTP認證">
            <div>
              <h6>OTP 認證</h6>
              <!-- 這裡是步驟 2 的內容 -->
              <q-form @submit.prevent="nextStep">
                <q-input filled v-model="formData.otp" label="OTP" />
                <q-btn type="submit" label="Next" color="primary" />
                <q-btn @click="prevStep" label="Back" color="secondary" />
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 3: 證件照片 -->
          <q-step name="step3" title="證件照片">
            <div>
              <h6>證件照片</h6>
              <!-- 這裡是步驟 3 的內容 -->
              <q-form @submit.prevent="nextStep">
                <q-file filled v-model="formData.photo" label="上傳證件照片" />
                <q-btn type="submit" label="Next" color="primary" />
                <q-btn @click="prevStep" label="Back" color="secondary" />
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 4: 開始審核 -->
          <q-step name="step4" title="開始審核">
            <div>
              <h6>開始審核</h6>
              <!-- 這裡是步驟 4 的內容 -->
              <p>您的申請正在審核中。</p>
              <q-btn @click="prevStep" label="Back" color="secondary" />
            </div>
          </q-step>
        </q-stepper>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { ref } from "vue";

const step = ref("step1"); // 初始步驟
const formData = ref({
  application: "",
  otp: "",
  photo: null,
});

const nextStep = () => {
  const steps = ["step1", "step2", "step3", "step4"];
  const currentIndex = steps.indexOf(step.value);
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
</script>

<style>
.custom-header {
  background-color: white; /* 背景色為白色 */
  border-bottom: 4px solid #0000ff; /* 藍色底線，寬度為4px */
}

.header-logo {
  height: 60px; /* 調整圖片高度 */
}
</style>
