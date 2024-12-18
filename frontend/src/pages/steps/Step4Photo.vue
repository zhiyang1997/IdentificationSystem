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
          <div :class="['upload-box', { error: file.isError }]">
            <div v-if="file.preview">
              <img :src="file.preview" alt="預覽圖片" class="preview-image" />
            </div>
            <div v-if="!file.preview">
              <q-icon name="photo_camera" size="50px" color="grey-7" />
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
import { computed, ref, reactive } from "vue";
import SignaturePad from "../../components/SignaturePad.vue"; // 簽名板組件
import { useFormStore } from "../../stores/formStore";

const formStore = useFormStore();
const step4Data = computed(() => formStore.step4Data); // 綁定數據
const isSignatureDialogOpen = ref(false);
const signatureImage = ref(null); // 儲存簽名圖片
const signaturePad = ref(null); // 獲取簽名板實例

// 文件上傳項目配置
const fileItems = reactive([
  {
    label: "點此拍照上傳",
    description: "身分證正面",
    model: "ID_CARD_FRONT",
    preview: null,
    required: true,
  },
  {
    label: "點此拍照上傳",
    description: "身分證反面",
    model: "ID_CARD_BACK",
    preview: null,
    required: true,
  },
  {
    label: "點此拍照上傳",
    description: "健保卡",
    model: "HEALTH_INSURANCE_CARD",
    preview: null,
    required: true,
  },
  {
    label: "點此拍照上傳",
    description: "手持身分證自拍",
    model: "SELFIE_WITH_ID_CARD",
    preview: null,
    required: true,
  },
  {
    label: "點此拍照上傳",
    description: "財力證明",
    model: "FINANCIAL_PROOF",
    preview: null,
    required: false,
  },
  {
    label: "點此拍照上傳",
    description: "其他身分證明 (軍/公/教/學生證)",
    model: "OTHER_DOCUMENT1",
    preview: null,
    required: false,
  },
]);

// 方法
const openSignatureDialog = () => {
  isSignatureDialogOpen.value = true;
};

const closeSignatureDialog = () => {
  isSignatureDialogOpen.value = false;
};

//簽名
const saveSignature = () => {
  const signaturePadInstance = signaturePad.value;
  if (signaturePadInstance) {
    const signature = signaturePadInstance.save();
    if (!signature) {
      alert("請先簽名！");
    } else {
      signatureImage.value = signature;
      step4Data.value.SIGN_DATA = signatureImage.value;
      closeSignatureDialog();
    }
  }
};

// 清除簽名
const clearSignature = () => {
  const signaturePadInstance = signaturePad.value;
  if (signaturePadInstance) signaturePadInstance.clear();
  signatureImage.value = null;
};

// 圖片預覽
const onFileChange = (event, file) => {
  const selectedFile = event.target.files[0];
  if (!selectedFile) return;

  const reader = new FileReader();

  reader.onload = (e) => {
    const image = new Image();
    image.src = e.target.result;

    image.onload = () => {
      // 調用裁剪方法
      const croppedImage = cropImageToSize(image, 300, 150);
      file.preview = croppedImage; // 更新預覽
      formStore.step4Data[file.model] = croppedImage; // 保存到 store
      console.log("裁剪後的圖片數據: ", file.preview);
    };

    image.onerror = (err) => {
      console.error("圖片加載失敗: ", err);
    };
  };

  reader.onerror = (err) => {
    console.error("圖片讀取失敗: ", err);
  };

  reader.readAsDataURL(selectedFile);
};

// 圖片拖曳處理
const onDrop = (event, file) => {
  const uploadedFile = event.dataTransfer.files[0];
  if (uploadedFile) {
    const reader = new FileReader();
    reader.onload = (e) => {
      file.preview = e.target.result; // 設定圖片的 base64 數據
      formStore.step4Data[file.model] = croppedImage; // 保存到 store
      console.log("預覽圖片數據: ", file.preview); // 打印看一下
    };
    reader.onerror = () => {
      console.error("圖片讀取失敗");
    };
    reader.readAsDataURL(uploadedFile);
  } else {
    console.log("未拖曳圖片");
  }
};

/**
 * 裁剪圖片到指定寬高
 * @param {HTMLImageElement} image - 上傳的圖片
 * @param {number} targetWidth - 目標寬度
 * @param {number} targetHeight - 目標高度
 * @returns {string} - 回傳裁剪後的 Base64 圖片數據
 */
const cropImageToSize = (image, targetWidth, targetHeight) => {
  // 宣告 canvas
  const canvas = document.createElement("canvas");
  const ctx = canvas.getContext("2d");

  // 設定 canvas 的大小為目標大小
  canvas.width = targetWidth;
  canvas.height = targetHeight;

  // 計算裁剪的起點和縮放比例
  const imageAspectRatio = image.width / image.height;
  const targetAspectRatio = targetWidth / targetHeight;

  let sx, sy, sWidth, sHeight;

  if (imageAspectRatio > targetAspectRatio) {
    // 圖片過寬，裁剪寬度
    sHeight = image.height;
    sWidth = sHeight * targetAspectRatio;
    sx = (image.width - sWidth) / 2;
    sy = 0;
  } else {
    // 圖片過高，裁剪高度
    sWidth = image.width;
    sHeight = sWidth / targetAspectRatio;
    sx = 0;
    sy = (image.height - sHeight) / 2;
  }

  // 裁剪後的圖片存到canvas
  ctx.drawImage(
    image,
    sx,
    sy,
    sWidth,
    sHeight,
    0,
    0,
    targetWidth,
    targetHeight
  );

  // 回傳裁剪後的 Base64 數據
  return canvas.toDataURL("image/jpeg");
};

// 檢核必填照片有無上傳
const validatePhoto = () => {
  const requiredFiles = [
    "ID_CARD_FRONT",
    "ID_CARD_BACK",
    "HEALTH_INSURANCE_CARD",
    "SELFIE_WITH_ID_CARD",
  ];

  const missingFiles = requiredFiles.filter((key) => !step4Data.value[key]);

  fileItems.forEach((file) => {
    file.isError = missingFiles.includes(file.model);
  });

  if (missingFiles.length > 0) {
    const missingLabels = fileItems
      .filter((file) => missingFiles.includes(file.model))
      .map((file) => file.description);

    alert(`請上傳以下必填文件：\n${missingLabels.join("\n")}`);

    return false; // 阻止進入下一步
  }
  return true;
};
const nextStep = () => {
  if (validatePhoto()) {
    formStore.currentStep = "step5";
  }
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

.upload-box.error {
  border: 3px solid red;
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
  width: 100%;
  height: 100%;
  object-fit: contain;
  border-radius: 8px;
  justify-content: center;
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

.preview-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
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
