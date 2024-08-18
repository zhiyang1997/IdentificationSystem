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
            <div class="center-content">
              <h6>申請資料</h6>
              <!-- 這裡是步驟 1 的內容 -->
              <q-form @submit.prevent="nextStep" @validation-error="showErrors">
                <!-- 商店/網站名稱 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">
                    商店/網站名稱
                  </div>
                  <div class="col-6">
                    <q-input filled v-model="formData.storeName" />
                  </div>
                </div>

                <!-- 產品名稱 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">產品名稱</div>
                  <div class="col-6">
                    <q-input filled v-model="formData.productName1" />
                  </div>
                </div>

                <!-- 產品金額 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">產品金額*</div>
                  <div class="col-6">
                    <q-input
                      filled
                      v-model="formData.productPrice"
                      type="number"
                      :rules="[(value) => !!value || '產品金額為必填欄位']"
                    />
                  </div>
                </div>

                <!-- 分期期數 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">分期期數*</div>
                  <div class="col-6">
                    <q-select
                      v-model="formData.installmentPeriod"
                      :options="installmentOptions"
                      filled
                      :rules="[(value) => !!value || '分期期數為必填欄位']"
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
                    />
                  </div>
                </div>

                <!-- 手機門號 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">手機門號*</div>
                  <div class="col-6">
                    <q-input
                      filled
                      v-model="formData.phoneNumber"
                      type="tel"
                      :rules="[(value) => !!value || '手機門號為必填欄位']"
                    />
                  </div>
                </div>

                <!-- E-mail -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">E-mail</div>
                  <div class="col-6">
                    <q-input filled v-model="formData.email" type="email" />
                  </div>
                </div>

                <!-- 可接聽時間 -->
                <div class="row items-center q-mb-md">
                  <div class="col-3 text-right q-pa-xs q-pr-md">
                    可接聽時間*
                  </div>
                  <div class="col-6">
                    <q-select
                      v-model="formData.availableTime"
                      :options="availableTimeOptions"
                      filled
                      :rules="[(value) => !!value || '可接聽時間為必填欄位']"
                    />
                  </div>
                </div>

                <!-- 勾選框 -->
                <div class="checkbox-container q-mt-md">
                  <q-checkbox v-model="val" />
                  <span class="checkbox-label">
                    ＊我已詳細閱讀並充分了解、同意「隱私權政策」及「行動身分識別服務使用者約定條款及隱私權告知條款」。
                  </span>
                </div>

                <div class="button-container q-mt-md">
                  <q-btn type="submit" label="下一步" color="primary" />
                </div>
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 2: OTP 認證 -->
          <q-step name="step2" title="OTP認證">
            <div class="center-content">
              <h6>OTP 認證</h6>
              <q-form @submit.prevent="nextStep">
                <q-input filled v-model="formData.otp" label="OTP" />
                <div class="button-container">
                  <q-btn type="submit" label="Next" color="primary" />
                  <q-btn @click="prevStep" label="Back" color="secondary" />
                </div>
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 3: 證件照片 -->
          <q-step name="step3" title="證件照片">
            <div class="center-content">
              <h6>證件照片</h6>
              <q-form @submit.prevent="nextStep">
                <q-file filled v-model="formData.photo" label="上傳證件照片" />
                <div class="button-container">
                  <q-btn type="submit" label="Next" color="primary" />
                  <q-btn @click="prevStep" label="Back" color="secondary" />
                </div>
              </q-form>
            </div>
          </q-step>

          <!-- 步驟 4: 開始審核 -->
          <q-step name="step4" title="開始審核">
            <div class="center-content">
              <h6>開始審核</h6>
              <p>您的申請正在審核中。</p>
              <div class="button-container">
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
import { ref } from "vue";

const step = ref("step1"); // 初始步驟
const val = ref(false); // 勾選框
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

.label {
  font-weight: bold;
}

.custom-page-container {
  max-width: 800px; /* 设定最大宽度 */
  margin: 0 auto; /* 居中显示 */
}

.center-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.button-container {
  display: flex;
  gap: 10px; /* 按钮间距 */
  justify-content: center;
  margin-top: 20px; /* 按钮与表单内容的间距 */
}

.checkbox-container {
  display: flex;
  align-items: center;
  width: 100%; /* 确保容器宽度为100% */
  margin-bottom: 20px; /* 为 checkbox 和其他元素之间的间距 */
}

.checkbox-label {
  margin-left: 8px; /* Checkbox 与文本之间的间距 */
  word-break: break-word; /* 强制文本换行 */
  flex: 1; /* 使文本占据剩余空间 */
}
</style>
