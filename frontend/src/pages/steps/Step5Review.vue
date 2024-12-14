<template>
  <div class="review-container">
    <!-- 圖片標題區 -->
    <div class="header">
      <h6>給公司放圖</h6>
    </div>

    <!-- 提示訊息 -->
    <p class="info-text">
      若您送出表單為非服務時間，我們將會於次一日上班日審核！
    </p>

    <!-- 指引訊息 -->
    <div class="instructions">
      <p>
        請點選下方
        <span class="line-highlight">Line</span>
        圖示加入 <span class="service-highlight">XXX服務</span>，並使用
        <strong class="case-number">（受理編號：2411180001）</strong> 進行登入
      </p>
      <p>您可於系統中查詢您的申辦進度、索取繳費單、查詢繳款紀錄...等服務</p>
      <p>
        點擊下方
        <span class="line-highlight">Line</span>
        圖示後會離開此畫面，請您詳細記錄受理編號或截圖保存
      </p>
    </div>

    <!-- Line 按鈕 -->
    <div class="line-button-container">
      <q-btn flat round color="green-6" class="line-button">
        <q-icon name="fab fa-line" size="50px" />
      </q-btn>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

// Mock data (受理編號可以動態從 API 或 store 獲取)
const caseNumber = ref("2411180001");
</script>

<style scoped>
.review-container {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
  text-align: center;
}

.header h6 {
  font-size: 20px;
  color: #333;
  margin-bottom: 10px;
}

.info-text {
  font-size: 16px;
  color: #666;
  margin: 15px 0;
}

.instructions {
  margin: 20px 0;
  font-size: 14px;
  line-height: 1.5;
  color: #333;
}

.line-highlight {
  color: #00c300;
  font-weight: bold;
}

.service-highlight {
  color: #007bff;
  font-weight: bold;
}

.case-number {
  color: #ff0000;
  font-weight: bold;
}

.line-button-container {
  margin-top: 20px;
}

.line-button {
  padding: 0;
  height: 60px;
  width: 60px;
}

.line-button q-icon {
  font-size: 50px;
}
</style>
