<template>
  <div class="user-info-form__block" :class="{'show-error': errorClass}">

    <span class="user-info-form__label_stylus">
      <span class="user-info-form__label_stylus-star" v-if="v">*</span>
      {{ label }}
    </span>
    <div class="user-info-form__wrap">
      <textarea
        class="user-info-form__input_stylus user-info-form__input_stylus--textarea user-info-form__input"
        :value="value"
        @input="$emit('change', $event.target.value)"
        v-if="about"
      />

      <input
        class="user-info-form__input_stylus user-info-form__input"
        type="text"
        :value="value"
        :placeholder="placeholder"
        @input="$emit('change', $event.target.value)"
        :ref="phone && 'phone'"
        v-else
      />
      <span class="user-info-form__error">Не может быть пустым</span>
    </div>
  </div>
</template>

<script>
import Inputmask from 'inputmask';
export default {
  name: 'UserInfoFormBlock',

  props: {
    label: String,
    placeholder: String,
    value: String,
    phone: Boolean,
    about: Boolean,
    v: { type: Object, required: false }
  },
  computed: {
    errorClass() {
      if (this.v && this.v.$invalid && this.v.$dirty) {
        return this.v.$invalid && this.v.$dirty
      }

      return false;
    }
  },
  watch: {
    value() {
      if (this.v) {
        this.v.$touch()
      }
    }
  },
  mounted() {
    if (this.phone && this.$refs.phone) {
      var im = new Inputmask('+7 (999) 999 99 99');
      im.mask(this.$refs.phone);
    }
  },
};
</script>
<style>
  .user-info-form__wrap {
    position: relative;
  }
  .user-info-form__error {
    position: absolute;
    bottom: -17px;
    display: none;
    font-size: 12px;
    color: red;

  }
  .user-info-form__block.show-error .user-info-form__input_stylus {
    border-color: red;
  }
  .user-info-form__block.show-error .user-info-form__error {
    display: initial;
  }
  .user-info-form__block.show-error .user-info-form__label_stylus {
    color: red;
  }
  .user-info-form__label_stylus {
    position: relative;
  }
  .user-info-form__label_stylus-star {
    position: absolute;
    top: 0px;
    right: -6px;
    color: red;
  }
</style>
