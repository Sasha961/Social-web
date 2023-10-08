<template>
  <div class="settings-security__value security-input" :class="{err: error}">
    <input
      class="settings-security__input security-input__input"
      :placeholder="placeholder"
      v-model="updateValue"
      :type="type"
    />
    <span class="security-input__error" v-if="error"> {{ error }}</span>
  </div>
</template>

<script>
export default {
  name: "SecurityInput",
  props: {
    value: { type: String, required: true },
    placeholder: { type: String, required: false },
    error: { type: String, required: false },
    type: { type: String, default: 'text' },
  },
  computed: {
    updateValue: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit('input', value);
      },
    },
  },
};
</script>

<style lang="css">
.security-input {
  position: relative;
}
.security-input.err .security-input__input {
  border-bottom-color: red;
}
.security-input__error {
  position: absolute;
  font-size: 12px;
  color: red;
  top: 34px;
}
.settings-security__value {
  margin-right: auto;
}
.settings-security__input {
  color: #414141;
  display: block;
  padding: 7px 0;
  border-bottom: 1px solid #21a45d;
}
</style>
