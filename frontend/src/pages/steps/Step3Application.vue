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
      />
      <!-- <div class="helper-text">產品金額 $5,000 ~ $1,000,000 (輸入數字即可)</div> -->
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
      />
    </div>

    <!-- 預計期付金額 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md">期付金額</label>
      <q-input
        dense
        filled
        v-model="step3Data.INSTALLMENT_AMOUNT"
        readonly
        style="width: 100%; background-color: #f5f5f5"
        placeholder="期付金額"
      />
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
      />
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
import { computed } from "vue";
import { useFormStore } from "../../stores/formStore";

// 使用 Pinia 的 store
const formStore = useFormStore();
const step3Data = computed(() => formStore.step3Data); // 綁定 Step3 資料

// 可接聽電話時段選項
const phoneTimes = ["10:00-12:00", "12:00-15:00", "15:00-18:00", "18:00-21:00"];

const selectPhoneTime = (time) => {
  step3Data.value.AVAILABLE_TIME = time;
};

// 定義最小和最大金額
const MIN_PRICE = 5000;
const MAX_PRICE = 1000000;

// 檢查金額是否符合範圍
const validateProductPrice = () => {
  if (step3Data.value.PRODUCT_PRICE < MIN_PRICE) {
    step3Data.value.PRODUCT_PRICE = MIN_PRICE; // 小於最小值時，自動調整到最小值
  } else if (step3Data.value.PRODUCT_PRICE > MAX_PRICE) {
    step3Data.value.PRODUCT_PRICE = MAX_PRICE; // 大於最大值時，自動調整到最大值
  }
};

// 下一步操作
const nextStep = () => {
  formStore.setCurrentStep("step4");
};

// 上一步操作
const prevStep = () => {
  formStore.setCurrentStep("step2");
};
</script>

<style scoped>
/* 欄位間距與背景樣式 */
.q-form-row {
  margin-bottom: 20px;
}

.helper-text {
  font-size: 12px;
  color: grey;
  margin-top: 4px;
}

/* 電話時段按鈕樣式 */
.time-button-group {
  display: flex;
  justify-content: center; /* 按钮水平居中 */
  gap: 5px; /* 按钮间的间距 */
  margin: 0; /* 去除外部留白 */
  padding: 0; /* 去除内边距 */
}
.time-button {
  width: 20%; /* 固定寬度，確保按鈕排列整齊 */
  flex: 1; /* 使按钮均匀分布，去除额外空隙 */
  margin: 0; /* 去除按钮之间的外边距 */
}

/* 操作按鈕樣式 */
.button-group {
  display: flex;
  justify-content: space-between;
}

.action-button {
  width: 120px; /* 統一按鈕寬度 */
}
.required {
  color: red; /* 將*號設為紅色 */
}
</style>
