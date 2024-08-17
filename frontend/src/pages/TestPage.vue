<template>
  <div>
    <h1>{{ title }}</h1>
  </div>
</template>

<script>
export default {
  name: "TestPage",
  data() {
    return {
      title: "Hello, Vue!",
    };
  },
};
</script>

<style>
/* 組件的樣式 */
div {
  text-align: center;
}
</style>
