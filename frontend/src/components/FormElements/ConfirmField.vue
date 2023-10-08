<template>
  <div class="form__group">
    <input
      type="checkbox"
      name="confirm"
      class="form__checkbox"
      v-model="confirm"
      :id="id"
      :class="{ invalid: v.$dirty && !v.sameAs }"
    />

    <label :for="id" class="form__checkbox-label">
      <span>Я согласен с <a class="form__link" href="#">полит. конф-ти</a> и передачей <a class="form__link" href="#">перс. данных</a></span>
    </label>
  </div>
</template>

<script>
export default {
  name: 'ConfirmField',
  props: {
    value: {
      type: Boolean,
      default: false,
    },
    v: {
      type: Object,
      required: true,
    },
    id: {
      type: String,
      required: true,
    },
  },
  computed: {
    confirm: {
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
