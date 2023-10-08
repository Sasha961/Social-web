<template>
  <div class="user-info-form__block">
    <span class="user-info-form__label_stylus">{{ sectionName }}</span>
    <div class="user-info-form__wrap">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    sectionName: { type: String, required: true },
  },
};
</script>

<style lang="scss" scoped></style>
