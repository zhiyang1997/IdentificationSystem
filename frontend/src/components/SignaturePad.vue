<template>
  <div class="signature-container">
    <canvas ref="canvas" class="signature-canvas"></canvas>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import SignaturePad from "signature_pad";

const canvas = ref(null);
let signaturePadInstance = null;

onMounted(() => {
  const canvasElement = canvas.value;

  // 設置高分辨率支持
  const ratio = Math.max(window.devicePixelRatio || 1, 1);
  canvasElement.width = canvasElement.offsetWidth * ratio;
  canvasElement.height = canvasElement.offsetHeight * ratio;
  canvasElement.getContext("2d").scale(ratio, ratio);

  // 初始化 SignaturePad
  signaturePadInstance = new SignaturePad(canvasElement);
});

/**
 * 清除簽名
 */
const clear = () => {
  if (signaturePadInstance) {
    signaturePadInstance.clear();
  }
};

/**
 * 保存簽名，返回圖片的 Base64
 */
const save = () => {
  if (signaturePadInstance && !signaturePadInstance.isEmpty()) {
    return signaturePadInstance.toDataURL("image/png");
  }
  return null;
};

// 將 `clear` 和 `save` 方法掛載到組件實例
defineExpose({
  clear,
  save,
});
</script>

<style scoped>
.signature-container {
  width: 100%;
  height: 200px;
  border: 1px solid #ccc;
  position: relative;
}

.signature-canvas {
  width: 100%;
  height: 100%;
}
</style>
