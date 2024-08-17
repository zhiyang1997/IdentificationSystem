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
              <q-form @submit.prevent="nextStep" @validation-error="showErrors">
                <div class="form-table">
                  <!-- 商店/網站名稱 -->
                  <q-item>
                    <q-item-section class="label">商店/網站名稱</q-item-section>
                    <q-item-section>
                      <q-input filled v-model="formData.storeName" />
                    </q-item-section>
                  </q-item>

                  <!-- 產品名稱 -->
                  <q-item>
                    <q-item-section class="label">產品名稱</q-item-section>
                    <q-item-section>
                      <q-input filled v-model="formData.productName1" />
                    </q-item-section>
                  </q-item>

                  <!-- 產品金額 -->
                  <q-item>
                    <q-item-section class="label">產品金額*</q-item-section>
                    <q-item-section>
                      <q-input
                        filled
                        v-model="formData.productPrice"
                        type="number"
                        :rules="[(value) => !!value || '產品金額為必填欄位']"
                      />
                    </q-item-section>
                  </q-item>

                  <!-- 分期期數 -->
                  <q-item>
                    <q-item-section class="label">分期期數*</q-item-section>
                    <q-item-section>
                      <q-select
                        v-model="formData.installmentPeriod"
                        :options="installmentOptions"
                        filled
                        :rules="[(value) => !!value || '分期期數為必填欄位']"
                      />
                    </q-item-section>
                  </q-item>

                  <!-- 期付金額 -->
                  <q-item>
                    <q-item-section class="label">期付金額</q-item-section>
                    <q-item-section>
                      <q-input
                        filled
                        v-model="formData.installmentAmount"
                        type="number"
                      />
                    </q-item-section>
                  </q-item>

                  <!-- 手機門號 -->
                  <q-item>
                    <q-item-section class="label">手機門號*</q-item-section>
                    <q-item-section>
                      <q-input
                        filled
                        v-model="formData.phoneNumber"
                        type="tel"
                        :rules="[(value) => !!value || '手機門號為必填欄位']"
                      />
                    </q-item-section>
                  </q-item>

                  <!-- E-mail -->
                  <q-item>
                    <q-item-section class="label">E-mail</q-item-section>
                    <q-item-section>
                      <q-input filled v-model="formData.email" type="email" />
                    </q-item-section>
                  </q-item>

                  <!-- 可接聽時間 -->
                  <q-item>
                    <q-item-section class="label">可接聽時間*</q-item-section>
                    <q-item-section>
                      <q-select
                        v-model="formData.availableTime"
                        :options="availableTimeOptions"
                        filled
                        :rules="[(value) => !!value || '可接聽時間為必填欄位']"
                      />
                    </q-item-section>
                  </q-item>

                  <!-- 勾選框 -->
                  <q-item>
                    <q-item-section class="label">
                      <q-checkbox
                        v-model="formData.termsAccepted"
                        label="我已詳細閱讀並充分了解、同意「隱私權政策」及「行動身分識別服務使用者約定條款及隱私權告知條款」*"
                        :rules="[(value) => value || '必須同意條款']"
                      />
                    </q-item-section>
                  </q-item>
                </div>
                <q-btn type="submit" label="Next" color="primary" />
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 2: OTP 認證 -->
          <q-step name="step2" title="OTP認證">
            <div>
              <h6>OTP 認證</h6>
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
  storeName: "",
  productName1: "",
  productName2: "",
  productPrice: "",
  installmentPeriod: null,
  installmentAmount: "",
  phoneNumber: "",
  email: "",
  availableTime: null,
  termsAccepted: false,
});

const installmentOptions = [
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

// 显示验证错误
const showErrors = () => {
  // Here you can handle the errors as needed
  // E.g., show a toast or alert if there are validation errors
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

.form-table {
  display: grid;
  grid-template-columns: 1fr 2fr; /* 1fr 為欄位寬度，2fr 為內容寬度 */
  gap: 10px; /* 欄位和內容間的間距 */
}

.q-item {
  display: contents; /* 使 q-item 不影響 grid 的布局 */
}

.label {
  font-weight: bold;
}
</style>
