<template>
  <select
    class="select user-info-form__select user-info-form__input"
    v-model="getValue"
  >
    <option value="" v-if="defaultValueText">{{ defaultValueText }}</option>
    <option v-for="(value, index) in propsList" :key="index" :value="value.value || value">
      {{ value.title || value }}
    </option>
  </select>
</template>

<script>
export default {
  props: {
    defaultValueText: { type: String, required: false },
    propsList: { type: [Array, Number], required: true },
    value: { type: [String, Number], required: true },
  },
  computed: {
    getValue: {
      get() {
        return this.value;
      },
      set(newValue) {
        this.$emit('select', newValue)
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
