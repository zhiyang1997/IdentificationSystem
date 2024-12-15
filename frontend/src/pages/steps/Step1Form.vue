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
        :error="errors.NAME"
      />
      <span class="custom-error" v-if="errors.NAME">請填寫姓名</span>
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
        :error="errors.PHONE_NUMBER"
      />
      <span
        class="custom-error"
        v-if="errors.PHONE_NUMBER && !step1Data.PHONE_NUMBER"
        >請填寫手機門號</span
      >
      <span
        class="custom-error"
        v-if="errors.PHONE_NUMBER && step1Data.PHONE_NUMBER"
        >手機門號格式有誤，請確認</span
      >
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
        :error="errors.NATIONAL_ID"
      />
      <span
        class="custom-error"
        v-if="errors.NATIONAL_ID && !step1Data.NATIONAL_ID"
        >請填寫身分證字號</span
      >
      <span
        class="custom-error"
        v-if="errors.NATIONAL_ID && step1Data.NATIONAL_ID"
        >身分證字號格式有誤，請確認</span
      >
    </div>

    <!-- 出生日期 -->
    <div class="q-form-row q-mb-md">
      <label class="block q-mb-md"
        >出生日期<span class="required">*</span
        ><q-icon name="event" class="cursor-pointer" @click="showCalendar"
      /></label>
      <q-input
        dense
        filled
        v-model="step1Data.BIRTHDATE"
        placeholder="請從小月曆選擇出生日期"
        style="width: 100%"
        :error="errors.BIRTHDATE"
      />
      <span class="custom-error" v-if="errors.BIRTHDATE">請填寫出生日期</span>
      <!-- 小月曆彈窗 -->
      <q-popup-proxy
        v-model="calendar"
        transition-show="scale"
        transition-hide="scale"
      >
        <q-date
          v-model="step1Data.BIRTHDATE"
          mask="YYYY-MM-DD"
          @click="showCalendar"
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
        :error="errors.REGISTERED_ADDRESS"
      />
      <span class="custom-error" v-if="errors.REGISTERED_ADDRESS"
        >請填寫戶籍地址</span
      >
    </div>

    <!-- 親屬1 -->
    <div class="q-form-row q-mb-md flex-row">
      <div class="input-group">
        <label class="block q-mb-md"
          >親屬姓名1<span class="required">*</span></label
        >
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_NAME1"
          placeholder="請輸入親屬姓名"
          style="width: 100%"
          :error="errors.RELATIVE_NAME1"
        />
        <span class="custom-error" v-if="errors.RELATIVE_NAME1"
          >請填寫親屬姓名</span
        >
      </div>
      <div class="input-group">
        <label class="block q-mb-md"
          >親屬電話1<span class="required">*</span></label
        >
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_PHONE1"
          placeholder="請輸入親屬電話"
          style="width: 100%"
          :error="errors.RELATIVE_PHONE1"
        />
        <span class="custom-error" v-if="errors.RELATIVE_PHONE1"
          >請填寫親屬電話</span
        >
      </div>
      <div class="input-group">
        <label class="block q-mb-md"
          >親屬關係1<span class="required">*</span></label
        >
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_RELATION1"
          placeholder="請輸入親屬關係"
          style="width: 100%"
          :error="errors.RELATIVE_RELATION1"
        />
        <span class="custom-error" v-if="errors.RELATIVE_RELATION1"
          >請填寫親屬關係</span
        >
      </div>
    </div>

    <!-- 親屬2 -->
    <div class="q-form-row q-mb-md flex-row">
      <div class="input-group">
        <label class="block q-mb-md"
          >親屬姓名2<span class="required">*</span></label
        >
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_NAME2"
          placeholder="請輸入親屬姓名"
          style="width: 100%"
          :error="errors.RELATIVE_NAME2"
        />
        <span class="custom-error" v-if="errors.RELATIVE_NAME2"
          >請填寫親屬姓名</span
        >
      </div>
      <div class="input-group">
        <label class="block q-mb-md"
          >親屬電話2<span class="required">*</span></label
        >
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_PHONE2"
          placeholder="請輸入親屬電話"
          style="width: 100%"
          :error="errors.RELATIVE_PHONE2"
        />
        <span class="custom-error" v-if="errors.RELATIVE_PHONE2"
          >請填寫親屬電話</span
        >
      </div>
      <div class="input-group">
        <label class="block q-mb-md"
          >親屬關係2<span class="required">*</span></label
        >
        <q-input
          dense
          filled
          v-model="step1Data.RELATIVE_RELATION2"
          placeholder="請輸入親屬關係"
          style="width: 100%"
          :error="errors.RELATIVE_RELATION2"
        />
        <span class="custom-error" v-if="errors.RELATIVE_RELATION2"
          >請填寫親屬關係</span
        >
      </div>
    </div>

    <!-- 朋友 -->
    <div class="q-form-row q-mb-md flex-row">
      <div class="input-group">
        <label class="block q-mb-md"
          >朋友姓名<span class="required">*</span></label
        >
        <q-input
          dense
          filled
          v-model="step1Data.FRIEND_NAME"
          placeholder="請輸入朋友姓名"
          style="width: 100%"
          :error="errors.FRIEND_NAME"
        />
        <span class="custom-error" v-if="errors.FRIEND_NAME"
          >請填寫朋友姓名</span
        >
      </div>
      <div class="input-group">
        <label class="block q-mb-md"
          >朋友電話<span class="required">*</span></label
        >
        <q-input
          dense
          filled
          v-model="step1Data.FRIEND_PHONE"
          placeholder="請輸入朋友電話"
          style="width: 100%"
          :error="errors.FRIEND_PHONE"
        />
        <span class="custom-error" v-if="errors.FRIEND_PHONE"
          >請填寫朋友電話</span
        >
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

    <!-- 操作按鈕 -->
    <div class="button-group">
      <q-btn type="submit" label="繼續" color="green-6" class="action-button" />
    </div>
  </q-form>
</template>

<script setup>
import axios from "axios";
import { ref, computed } from "vue";
import { useFormStore } from "../../stores/formStore";
import PrivacyPolicyDialog from "../PrivacyPolicyDialog.vue";
import TermsDialog from "../TermsDialog.vue";

// 使用 Pinia store
const formStore = useFormStore();

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

const calendar = ref(false); // 控制小月曆彈窗

const showCalendar = () => {
  calendar.value = !calendar.value;
};

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

// 用於追蹤每個欄位的錯誤狀態
const errors = ref({});

// 檢查所有必填欄位
const validateForm = () => {
  let isValid = true;
  const missingFields = [];
  const invalidFields = [];

  // 定義欄位標籤對應
  const fieldLabels = {
    NAME: "姓名",
    PHONE_NUMBER: "手機門號",
    NATIONAL_ID: "身分證字號",
    BIRTHDATE: "出生日期",
    REGISTERED_ADDRESS: "戶籍地址",
    RELATIVE_NAME1: "親屬姓名1",
    RELATIVE_PHONE1: "親屬電話1",
    RELATIVE_RELATION1: "親屬關係1",
    RELATIVE_NAME2: "親屬姓名2",
    RELATIVE_PHONE2: "親屬電話2",
    RELATIVE_RELATION2: "親屬關係2",
    FRIEND_NAME: "朋友姓名",
    FRIEND_PHONE: "朋友電話",
  };

  // 驗證身分證字號
  const validNationalID = (id) => /^[A-Z][12]\d{8}$/.test(id);

  // 驗證手機門號
  const validPhoneNumber = (phone) => /^09\d{8}$/.test(phone);

  // 遍歷所有欄位，檢查是否有值和格式是否正確
  Object.keys(step1Data.value).forEach((key) => {
    const value = step1Data.value[key];

    if (!value) {
      // 如果欄位為空
      errors.value[key] = true;
      isValid = false;
      missingFields.push(fieldLabels[key]);
    } else {
      // 如果欄位有值，進行格式驗證
      if (key === "NATIONAL_ID" && !validNationalID(value)) {
        errors.value[key] = true;
        invalidFields.push(fieldLabels[key]);
        isValid = false;
      } else if (key === "PHONE_NUMBER" && !validPhoneNumber(value)) {
        errors.value[key] = true;
        invalidFields.push(fieldLabels[key]);
        isValid = false;
      } else {
        errors.value[key] = false; // 清除錯誤狀態
      }
    }
  });

  // 顯示錯誤提示
  if (missingFields.length > 0) {
    alert(`仍有[${missingFields.join(", ")}]尚未填寫！`);
  }

  if (invalidFields.length > 0) {
    alert(`[${invalidFields.join(", ")}]格式錯誤！`);
  }

  return isValid;
};

const nextStep = async () => {
  if (formStore.currentStep === "step1") {
    // 假设在第一个步骤时发送 OTP
    try {
      if (validateForm()) {
        if (val1.value && val2.value) {
          await sendOtpRequest(step1Data.value);
          formStore.currentStep = "step2";
        } else {
          alert(
            "請先勾選同意 「隱私權政策」 及 「行動身分識別服務使用者約定條款及隱私權告知條款」。\n以及勾選同意電子簽章"
          );
        }
        // 進入下一步邏輯
      }

      // console.log("OTP 已成功发送");
    } catch (error) {
      // 处理发送失败的情况，比如显示错误消息
      // console.error("无法发送 OTP");
      return; // 阻止进入下一步
    }
  }
};

const sendOtpRequest = async (step1Data) => {
  try {
    const requestData = {
      Step1ReqDto: { step1Data.value }, // 将 Pinia 中的 Step1 数据映射为后端需要的格式
    };
    const response = await axios.post(
      "http://localhost:8080/api/v1/sendotp",
      requestData,
      {
        headers: {
          "Content-Type": "application/json", // 设置为 JSON 格式
        },
      }
    );
    return response.data; // 返回后端的响应数据
  } catch (error) {
    console.log("发送 OTP 失败:", error);
    throw error;
  }
};
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

.error-message {
  color: red;
  font-size: 12px;
  display: block; /* 確保不影響結構 */
  margin-top: 0px; /* 固定的間距 */
}

/* 第一步驟－親屬、朋友欄位布局 */
.flex-row {
  display: flex;
  gap: 16px; /* 控制每個輸入框之間的間距 */
  align-items: flex-start; /* 對齊方式 */
}

/* 操作按鈕 */
.button-group {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.action-button {
  width: 120px; /* 統一按鈕寬度 */
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
</style>
