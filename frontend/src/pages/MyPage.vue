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
                    <q-input
                      clearable
                      filled
                      color="blue-12"
                      v-model="formData.storeName"
                      label="Shop/Web"
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
                      label="PhoneNuber"
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
                    />
                  </div>
                </div>

                <!-- 勾選框 -->
                <div class="checkbox-container q-mt-md">
                  <q-checkbox v-model="val" />
                  <span class="checkbox-label">
                    ＊我已詳細閱讀並充分了解、同意
                    <a href="#" @click.prevent="showPrivacyPolicyDialog"
                      >「隱私權政策」</a
                    >
                    及
                    <a href="#" @click.prevent="showTermsDialog"
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
                <div class="button-container">
                  <q-btn @click="prevStep" label="Back" color="secondary" />
                  <q-btn type="submit" label="Next" color="primary" />
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
                  <q-btn @click="prevStep" label="Back" color="secondary" />
                  <q-btn type="submit" label="Next" color="primary" />
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
import { ref, watch, nextTick } from "vue";

const step = ref("step2"); // 初始步驟
const val = ref(false); // 勾選框

const isPrivacyPolicyDialogOpen = ref(false);
const isTermsDialogOpen = ref(false);

const showPrivacyPolicyDialog = () => {
  isPrivacyPolicyDialogOpen.value = true;
};

const showTermsDialog = () => {
  isTermsDialogOpen.value = true;
};

const formData = ref({
  storeName: "",
  productName1: "",
  productName2: "",
  productPrice: "",
  installmentPeriod: "請選擇期數",
  installmentAmount: "",
  phoneNumber: "",
  email: "",
  availableTime: null,
  termsAccepted: false,
});

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
  () => formData.value.productPrice,
  (newPrice) => {
    if (!newPrice) {
      formData.value.installmentPeriod = null;
    }
  }
);

watch(
  () => [formData.value.productPrice, formData.value.installmentPeriod],
  ([newPrice, newPeriod]) => {
    const price = parseFloat(newPrice);
    const period = parseInt(newPeriod?.value, 10);

    console.log("Selected Period:", newPeriod);
    console.log("Parsed Period:", period);

    if (!isNaN(price) && !isNaN(period) && period > 0) {
      const rawAmount = (price * 1.04) / period;
      formData.value.installmentAmount = Math.round(rawAmount);
    } else {
      formData.value.installmentAmount = ""; // 清空显示的值
    }
  }
);

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

const otpDigits = ref(Array(6).fill("")); // 存储6位OTP

const onInput = async (index) => {
  console.log(`Input at index ${index}:`, otpDigits.value[index]);

  if (
    otpDigits.value[index].length === 1 &&
    index < otpDigits.value.length - 1
  ) {
    console.log(`Attempting to move focus from index ${index} to ${index + 1}`);
    await nextTick();
    const nextInput =
      document.querySelectorAll('input[type="text"]')[index + 1];
    console.log("Next input element:", nextInput);
    if (nextInput) {
      nextInput.focus();
      console.log(`Focus moved to input at index ${index + 1}`);
    }
  }

  if (otpDigits.value[index] === "" && index > 0) {
    console.log(`Attempting to move focus from index ${index} to ${index - 1}`);
    await nextTick();
    const prevInput =
      document.querySelectorAll('input[type="text"]')[index - 1];
    console.log("Previous input element:", prevInput);
    if (prevInput) {
      prevInput.focus();
      console.log(`Focus moved to input at index ${index - 1}`);
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
