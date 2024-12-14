<template>
  <div class="certificate-upload">
    <!-- 簽名區 -->
    <div class="text-center">
      <div class="signature-box" @click="openSignatureDialog">
        <img v-if="signatureImage" :src="signatureImage" alt="簽名圖片" />
        <span v-else>點此簽名</span>
      </div>
      <div class="signature-actions">
        <span>
          同意 <a href="#" class="link-green">契約內容</a> 所列事項
          <span class="required">請簽名</span>
        </span>
        <q-btn flat label="清除重簽" color="negative" @click="clearSignature" />
      </div>
    </div>

    <!-- 文件上傳區 -->
    <q-form @submit.prevent="nextStep">
      <div class="file-grid">
        <div
          v-for="file in fileItems"
          :key="file.label"
          class="file-item"
          @dragover.prevent
          @drop="onDrop($event, file)"
        >
          <div class="upload-box">
            <img v-if="file.preview" :src="file.preview" alt="預覽圖片" />
            <div v-else class="placeholder">
              <q-icon name="photo_camera" size="48px" color="grey-7" />
              <span>{{ file.label }}</span>
            </div>
            <input
              type="file"
              class="file-input"
              accept="image/*"
              @change="onFileChange($event, file)"
            />
          </div>
          <p class="file-label">
            {{ file.description }}
            <span v-if="file.required" class="required">(必填)</span>
          </p>
        </div>
      </div>

      <!-- 操作按鈕 -->
      <div class="button-group">
        <q-btn
          @click="prevStep"
          label="返回"
          color="grey-7"
          class="action-button"
        />
        <q-btn
          type="submit"
          label="繼續"
          color="green-6"
          class="action-button"
        />
      </div>
    </q-form>

    <!-- 簽名對話框 -->
    <q-dialog v-model="isSignatureDialogOpen" persistent>
      <q-card>
        <q-card-section>
          <h6>簽名區塊</h6>
        </q-card-section>
        <q-card-section>
          <SignaturePad ref="signaturePad" />
        </q-card-section>
        <q-card-actions align="right">
          <q-btn
            flat
            label="取消"
            color="negative"
            @click="closeSignatureDialog"
          />
          <q-btn flat label="確定" color="primary" @click="saveSignature" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import SignaturePad from "../../components/SignaturePad.vue"; // 簽名板組件
import { useFormStore } from "../../stores/formStore";

const formStore = useFormStore();
const step4Data = computed(() => formStore.step4Data); // 綁定數據
const isSignatureDialogOpen = ref(false);
const signatureImage = ref(null); // 儲存簽名圖片
const signaturePad = ref(null); // 獲取簽名板實例

// 文件上傳項目配置
const fileItems = [
  {
    label: "點此拍照上傳",
    description: "身分證正面",
    model: step4Data.value.frontId,
    preview: null,
    required: true,
  },
  {
    label: "點此拍照上傳",
    description: "身分證反面",
    model: step4Data.value.backId,
    preview: null,
    required: true,
  },
  {
    label: "點此拍照上傳",
    description: "健保卡",
    model: step4Data.value.healthCard,
    preview: null,
    required: true,
  },
  {
    label: "點此拍照上傳",
    description: "手持身分證自拍",
    model: step4Data.value.selfie,
    preview: null,
    required: true,
  },
  {
    label: "點此拍照上傳",
    description: "財力證明",
    model: step4Data.value.financialProof,
    preview: null,
    required: false,
  },
  {
    label: "點此拍照上傳",
    description: "其他身分證明 (軍/公/教/學生證)",
    model: step4Data.value.otherProof,
    preview: null,
    required: false,
  },
];

// 方法
const openSignatureDialog = () => {
  isSignatureDialogOpen.value = true;
};

const closeSignatureDialog = () => {
  isSignatureDialogOpen.value = false;
};

const saveSignature = () => {
  const signaturePadInstance = signaturePad.value;
  if (signaturePadInstance) {
    const signature = signaturePadInstance.save();
    if (!signature) {
      alert("請先簽名！");
    } else {
      signatureImage.value = signature;
      closeSignatureDialog();
    }
  }
};

const clearSignature = () => {
  const signaturePadInstance = signaturePad.value;
  if (signaturePadInstance) signaturePadInstance.clear();
  signatureImage.value = null;
};

const onDrop = (event, fileItem) => {
  event.preventDefault();
  const files = event.dataTransfer.files;
  if (files.length > 0) {
    setPreview(files[0], fileItem);
  }
};

const onFileChange = (event, fileItem) => {
  const file = event.target.files[0];
  if (file) {
    setPreview(file, fileItem);
  }
};

const setPreview = (file, fileItem) => {
  const reader = new FileReader();
  reader.onload = (e) => {
    fileItem.preview = e.target.result;
  };
  reader.readAsDataURL(file);
};

const nextStep = () => {
  formStore.currentStep = "step5";
};

const prevStep = () => {
  formStore.currentStep = "step3";
};
</script>

<style scoped>
/* 整體背景與佈局 */
.certificate-upload {
  background-color: #f5f5f5; /* 柔和的背景色 */
  padding: 20px;
  border-radius: 8px;
}

/* 簽名區塊 */
.signature-box {
  width: 100%;
  height: 100px;
  border: 2px solid #ccc;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
  font-size: 18px;
  color: #555;
  background-color: #fff;
  cursor: pointer;
}

.signature-box img {
  width: 100%;
  height: 100px;
  object-fit: contain;
  border-radius: 8px;
}

.signature-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  margin-top: 5px;
}

.link-green {
  color: green;
  text-decoration: underline;
}

.required {
  color: red;
}

/* 文件上傳區塊 */
.file-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* 兩列佈局 */
  gap: 20px;
  margin-top: 20px;
}

.file-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  text-align: center;
  padding: 10px;
}

.upload-box {
  width: 100%;
  height: 140px;
  background-color: #eaeaea;
  border: 1px solid #ccc;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
}

.upload-box img {
  width: 100px;
  height: 100px;
  object-fit: contain;
  border-radius: 8px;
}

.placeholder {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #555;
}

.placeholder q-icon {
  margin-bottom: 10px;
}

.file-input {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  cursor: pointer;
}

.file-label {
  margin-top: 10px;
  font-size: 14px;
  color: #333;
}

/* 必填標籤樣式 */
.required {
  color: red;
  margin-left: 5px;
}

/* 操作按鈕 */
.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.action-button {
  width: 120px;
}
</style>
