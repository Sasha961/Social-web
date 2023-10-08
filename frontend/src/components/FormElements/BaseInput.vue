<template>
  <label>
    <input
      :type="type"
      :class="className"
      :placeholder="placeholder"
      :value="modelValue"
      @input="updateModelValue"
    />
  </label>
</template>

<script>
export default {
  name: "BaseInput",
  props: {
    type: { type: String, default: "text" },
    modelValue: { type: String, default: "" },
    className: { type: String, required: false },
    placeholder: { type: String, required: false },
  },
  methods: {
    updateModelValue(event) {
      this.$emit("modelValue", event.target.value);
    },
  },
};
</script>

<style lang="scss" scoped></style>
