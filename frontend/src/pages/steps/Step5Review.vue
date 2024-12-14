<template>
  <div class="">
    <div class="text-center">
      <h6>開始審核</h6>
    </div>
    <p>您的申請正在審核中。</p>
    <div class="text-center q-mt-lg">
      <q-btn @click="prevStep" label="返回" color="secondary" />
    </div>
  </div>
</template>

<script setup>
import { useFormStore } from "../../stores/formStore";

// 使用 Pinia store
const formStore = useFormStore();

const prevStep = () => {
  formStore.currentStep = "step4";
};
</script>

<style scoped></style>
