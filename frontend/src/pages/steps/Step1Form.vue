<template>
  <q-form @submit.prevent="nextStep" @validation-error="showErrors">
    <!-- 姓名 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md">姓名<span class="required">*</span></label>
      <q-input
        dense
        filled
        v-model="step1Data.NAME"
        placeholder="請填入真實姓名"
        style="width: 100%"
      />
    </div>

    <!-- 手機門號 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md"
        >手機門號<span class="required">*</span></label
      >
      <q-input
        dense
        filled
        v-model="step1Data.PHONE_NUMBER"
        type="tel"
        placeholder="填入格式：012345678(共十碼)"
        style="width: 100%"
        maxlength="10"
        @input="limitLength"
        @keypress="onlyNumber($event)"
      />
    </div>

    <!-- 身分證字號 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md"
        >身分證字號<span class="required">*</span></label
      >
      <q-input
        dense
        filled
        v-model="step1Data.NATIONAL_ID"
        placeholder="請輸入有效之身分證字號"
        style="width: 100%"
        maxlength="10"
        :style="inputStyle"
      />
    </div>

    <!-- 出生日期 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md"
        >出生日期<span class="required">*</span
        ><q-icon
          name="event"
          class="cursor-pointer"
          @click="showCalendar = true"
      /></label>
      <q-input
        dense
        filled
        v-model="step1Data.BIRTHDATE"
        placeholder="請從小月曆選擇出生日期"
        style="width: 100%"
      />
      <!-- 小月曆彈窗 -->
      <q-popup-proxy
        v-model="showCalendar"
        transition-show="scale"
        transition-hide="scale"
      >
        <q-date
          v-model="step1Data.BIRTHDATE"
          mask="YYYY-MM-DD"
          @input="showCalendar = false"
        />
      </q-popup-proxy>
    </div>

    <!-- 戶籍地址 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md"
        >戶籍地址<span class="required">*</span></label
      >
      <q-input
        dense
        filled
        v-model="step1Data.REGISTERED_ADDRESS"
        placeholder="請輸入戶籍地址"
        style="width: 100%"
      />
    </div>

    <!-- 親屬1 -->
    <div class="q-form-row q-mb-md flex-row">
      <div class="input-group">
        <label class="block">親屬姓名1<span class="required">*</span></label>
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_NAME1"
          placeholder="請輸入親屬姓名"
          style="width: 100%"
        />
      </div>
      <div class="input-group">
        <label class="block">親屬電話1<span class="required">*</span></label>
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_PHONE1"
          placeholder="請輸入親屬電話"
          style="width: 100%"
        />
      </div>
      <div class="input-group">
        <label class="block">親屬關係1<span class="required">*</span></label>
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_RELATION1"
          placeholder="請輸入親屬關係"
          style="width: 100%"
        />
      </div>
    </div>

    <!-- 親屬2 -->
    <div class="q-form-row q-mb-md flex-row">
      <div class="input-group">
        <label class="block">親屬姓名2<span class="required">*</span></label>
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_NAME2"
          placeholder="請輸入親屬姓名"
          style="width: 100%"
        />
      </div>
      <div class="input-group">
        <label class="block">親屬電話2<span class="required">*</span></label>
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_PHONE2"
          placeholder="請輸入親屬電話"
          style="width: 100%"
        />
      </div>
      <div class="input-group">
        <label class="block">親屬關係2<span class="required">*</span></label>
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_RELATION2"
          placeholder="請輸入親屬關係"
          style="width: 100%"
        />
      </div>
    </div>

    <!-- 朋友 -->
    <div class="q-form-row q-mb-md flex-row">
      <div class="input-group">
        <label class="block">朋友姓名<span class="required">*</span></label>
        <q-input
          dense
          filled
          v-model="step1Data.FRIEND_NAME"
          placeholder="請輸入朋友姓名"
          style="width: 100%"
        />
      </div>
      <div class="input-group">
        <label class="block">朋友電話<span class="required">*</span></label>
        <q-input
          dense
          filled
          v-model="step1Data.FRIEND_PHONE"
          placeholder="請輸入朋友電話"
          style="width: 100%"
        />
      </div>
    </div>
    <!-- 勾選框 -->
    <div class="checkbox-container q-mt-md">
      <div class="q-pa-xs q-pr-md">
        <q-checkbox v-model="val1" />
      </div>
      <span>
        <span class="required">*</span>我已詳細閱讀並充分了解、同意
        <a href="#" @click="showPrivacyPolicyDialog">「隱私權政策」</a>
        及
        <a href="#" @click="showTermsDialog"
          >「行動身分識別服務使用者約定條款及隱私權告知條款」</a
        >。
      </span>
    </div>
    <div class="checkbox-container q-mt-md">
      <div class="q-pa-xs q-pr-md">
        <q-checkbox v-model="val2" />
      </div>
      <span
        ><span class="required">*</span
        >本人同意以使用本系統產制之電子文件作為分期付款申請及交易之基礎，以電子簽章取代傳統之書面文件及簽名、蓋章確定之相關法律責任。
      </span>
    </div>
    <span
      ><span class="required">*</span>當您點選<span class="required"
        >【繼續】</span
      >按鈕後，系統會發送一份簡訊密碼至您的手機門號，請確認門號可正常接收簡訊。
    </span>
    <!-- 彈出視窗 -->
    <!-- 使用 PrivacyPolicyDialog 組件 -->
    <q-dialog v-model="isPrivacyPolicyDialogOpen" persistent>
      <PrivacyPolicyDialog @readed="closePrivacyPolicyDialog" />
    </q-dialog>
    <q-dialog v-model="isTermsDialogOpen" persistent>
      <TermsDialog @readed="closeTermsDialog" />
    </q-dialog>

    <div class="text-center q-mt-lg">
      <q-btn type="submit" label="繼續" color="primary" />
    </div>
  </q-form>
</template>

<script setup>
import { ref, reactive, computed, watch } from "vue";
import { useFormStore } from "../../stores/formStore";
import PrivacyPolicyDialog from "../PrivacyPolicyDialog.vue";
import TermsDialog from "../TermsDialog.vue";

// 使用 Pinia store
const formStore = useFormStore();

const nextStep = () => {
  formStore.currentStep = "step2";
};

const val1 = ref(false); // 勾選框1
const val2 = ref(false); // 勾選框2

const isPrivacyPolicyDialogOpen = ref(false);
const isTermsDialogOpen = ref(false);

const showPrivacyPolicyDialog = () => {
  isPrivacyPolicyDialogOpen.value = true;
};

const closePrivacyPolicyDialog = () => {
  isPrivacyPolicyDialogOpen.value = false;
};

const showTermsDialog = () => {
  isTermsDialogOpen.value = true;
};

const closeTermsDialog = () => {
  isTermsDialogOpen.value = false;
};

const showCalendar = ref(false); // 控制小月曆彈窗

// 使用 步驟一 的資料
const step1Data = computed(() => formStore.step1Data);

// 显示验证错误
const showErrors = () => {
  // Here you can handle the errors as needed
  // E.g., show a toast or alert if there are validation errors
};

const limitLength = () => {
  const maxLength = 10;
  if (step1Data.value.PHONE_NUMBER.length > maxLength) {
    step1Data.value.PHONE_NUMBER = step1Data.value.PHONE_NUMBER.slice(
      0,
      maxLength
    );
  }
};

/**
 * 限制只能輸入數字
 */
const onlyNumber = (event) => {
  const keyCode = event.keyCode || event.which;
  const keyValue = String.fromCharCode(keyCode);

  // 如果不是數字，取消輸入
  if (!/^\d+$/.test(keyValue)) {
    event.preventDefault();
  }
};

// 動態樣式綁定
const inputStyle = reactive({
  border: "",
});

/**
 * 步驟一 身份證字號驗證
 */
const isNationalIDValid = (id) => {
  if (!id) return true; // 如果是空值，視為有效（不提示）
  return /^[A-Z][12]/.test(id); // 第一碼是字母，第二碼是 1 或 2
};

// 使用 watch 動態監聽
watch(
  () => step1Data.value.NATIONAL_ID,
  (newValue) => {
    inputStyle.border = isNationalIDValid(newValue) ? "" : "2px solid red";
  }
);
</script>

<style scoped>
.checkbox-container {
  display: flex;
  align-items: center;
  width: 100%; /* 确保容器宽度为100% */
  margin-bottom: 10px; /* 为 checkbox 和其他元素之间的间距 */
}

.required {
  color: red; /* 將*號設為紅色 */
}

.invalid-input {
  border: 1px solid red !important; /* 將輸入框邊框設置為紅色 */
}

/* 第一步驟－親屬、朋友欄位布局 */
.flex-row {
  display: flex;
  gap: 16px; /* 控制每個輸入框之間的間距 */
  align-items: flex-start; /* 對齊方式 */
}
</style>
