<template>
  <div class="">
    <div class="text-center">
      <h6>證件照片</h6>
    </div>
    <q-form @submit.prevent="nextStep">
      <q-file filled v-model="step4Data.photo" label="上傳證件照片" />
      <div class="text-center q-mt-lg">
        <q-btn @click="prevStep" label="返回" color="secondary" />
        <q-btn type="submit" label="繼續" color="primary" />
      </div>
    </q-form>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { useFormStore } from "../../stores/formStore";

// 使用 Pinia store
const formStore = useFormStore();
const step4Data = computed(() => formStore.step4Data); // 綁定整個 step3Data

const nextStep = () => {
  formStore.currentStep = "step4";
};

const prevStep = () => {
  formStore.currentStep = "step2";
};
</script>

<style scoped></style>
