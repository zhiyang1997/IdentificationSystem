<template>
  <q-form @submit.prevent="nextStep">
    <!-- 商店/網站名稱 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md">商店/網站名稱</label>
      <q-input
        dense
        filled
        v-model="step3Data.STORE_NAME"
        readonly
        style="width: 100%; background-color: #f5f5f5"
      />
    </div>

    <!-- 產品名稱 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md">產品名稱</label>
      <q-input
        dense
        filled
        v-model="step3Data.PRODUCT_NAME"
        readonly
        style="width: 100%; background-color: #f5f5f5"
      />
    </div>

    <!-- 產品金額 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md"
        >產品金額<span class="required">*</span></label
      >
      <q-input
        dense
        filled
        v-model="step3Data.PRODUCT_PRICE"
        placeholder="產品金額 $5,000 ~ $1,000,000 (輸入數字即可)"
        type="number"
        style="width: 100%"
        @update:model-value="validateProductPrice"
        @blur="finalizeProductPrice"
        :error="errors.PRODUCT_PRICE"
      />
      <span class="custom-error" v-if="errors.PRODUCT_PRICE"
        >請輸入有效的產品金額</span
      >
    </div>

    <!-- 分期期數 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md"
        >分期期數<span class="required">*</span></label
      >
      <q-select
        dense
        filled
        v-model="step3Data.INSTALLMENT_MONTHS"
        :options="[3, 6, 12, 24]"
        style="width: 100%"
        :error="errors.INSTALLMENT_MONTHS"
        @update:model-value="errors.INSTALLMENT_MONTHS = false"
      />
      <span class="custom-error" v-if="errors.INSTALLMENT_MONTHS"
        >請選擇分期期數</span
      >
    </div>

    <!-- Email -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md">E-Mail<span class="required">*</span></label>
      <q-input
        dense
        filled
        v-model="step3Data.EMAIL"
        type="email"
        placeholder="請輸入有效的電子信箱"
        style="width: 100%"
        @blur="validateEmail"
        :error="errors.EMAIL"
      />
      <span class="custom-error" v-if="errors.EMAIL">請輸入有效的 Email</span>
    </div>

    <!-- 可接聽電話時間 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md"
        >可接聽電話時間<span class="required">*</span></label
      >
      <q-btn-group push class="time-button-group">
        <q-btn
          v-for="time in phoneTimes"
          :key="time"
          :label="time"
          flat
          @click="selectPhoneTime(time)"
          :color="step3Data.AVAILABLE_TIME === time ? 'primary' : 'grey-5'"
          class="time-button"
        />
      </q-btn-group>
      <span class="custom-error" v-if="errors.AVAILABLE_TIME"
        >請選擇可接聽電話的時間</span
      >
    </div>

    <!-- 按鈕區 -->
    <div class="button-group">
      <q-btn
        @click="prevStep"
        label="返回"
        color="grey-7"
        class="action-button"
      />
      <q-btn type="submit" label="繼續" color="green-6" class="action-button" />
    </div>
  </q-form>
</template>

<script setup>
import { computed, ref } from "vue";
import { useFormStore } from "../../stores/formStore";

// 使用 Pinia 的 store
const formStore = useFormStore();
const step3Data = computed(() => formStore.step3Data); // 綁定 Step3 資料

// 可接聽電話時段選項
const phoneTimes = ["10:00-12:00", "12:00-15:00", "15:00-18:00", "18:00-21:00"];

const selectPhoneTime = (time) => {
  step3Data.value.AVAILABLE_TIME = time;
  errors.value.AVAILABLE_TIME = false; // 取消錯誤訊息
};

// 定義最小和最大金額
const MIN_PRICE = 5000;
const MAX_PRICE = 1000000;

// 錯誤訊息狀態
const errors = ref({
  PRODUCT_PRICE: false,
  INSTALLMENT_MONTHS: false,
  EMAIL: false,
  AVAILABLE_TIME: false,
});

// 驗證產品金額
const validateProductPrice = () => {
  const price = step3Data.value.PRODUCT_PRICE;
  if (!price || price < MIN_PRICE || price > MAX_PRICE) {
    errors.value.PRODUCT_PRICE = true;
  } else {
    errors.value.PRODUCT_PRICE = false;
  }
};

// Email 驗證
const validateEmail = () => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  errors.value.EMAIL = !emailRegex.test(step3Data.value.EMAIL);
};

// 檢查所有必填欄位，並顯示 alert
const validateForm = () => {
  let isValid = true;
  let missingFields = [];

  if (
    !step3Data.value.PRODUCT_PRICE ||
    step3Data.value.PRODUCT_PRICE < MIN_PRICE ||
    step3Data.value.PRODUCT_PRICE > MAX_PRICE
  ) {
    errors.value.PRODUCT_PRICE = true;
    missingFields.push("產品金額");
    isValid = false;
  }

  if (!step3Data.value.INSTALLMENT_MONTHS) {
    errors.value.INSTALLMENT_MONTHS = true;
    missingFields.push("分期期數");
    isValid = false;
  }

  if (
    !step3Data.value.EMAIL ||
    !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(step3Data.value.EMAIL)
  ) {
    errors.value.EMAIL = true;
    missingFields.push("有效的 Email");
    isValid = false;
  }

  if (!step3Data.value.AVAILABLE_TIME) {
    errors.value.AVAILABLE_TIME = true;
    missingFields.push("可接聽電話時間");
    isValid = false;
  }

  if (missingFields.length > 0) {
    alert(`請填寫完整: ${missingFields.join(", ")}`);
  }

  return isValid;
};

// 下一步操作
const nextStep = () => {
  if (validateForm()) {
    formStore.setCurrentStep("step4");
  }
};

// 上一步操作
const prevStep = () => {
  formStore.setCurrentStep("step2");
};
</script>

<style scoped>
.custom-error {
  color: red;
  font-size: 12px;
  margin-top: 4px;
  display: block;
}

.time-button-group {
  display: flex;
  justify-content: center;
  gap: 5px;
}

.time-button {
  flex: 1;
  margin: 0;
}

.button-group {
  display: flex;
  justify-content: space-between;
}

.action-button {
  width: 120px;
}

.required {
  color: red;
}

/* 自定義錯誤訊息樣式 */
.custom-error {
  color: red; /* 錯誤訊息的字體顏色 */
  font-size: 12px; /* 錯誤訊息的字體大小 */
}

::v-deep(.q-field__bottom) {
  display: none;
}

::v-deep(.q-field--with-bottom) {
  padding-bottom: 0;
}

.error-border ::v-deep(.q-checkbox__bg) {
  border-color: red; /* 當有錯誤時外框變紅色 */
}
</style>
