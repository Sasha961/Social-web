<template>
  <div class="user-info-form__block">
    <span class="user-info-form__label_stylus">Пол:</span>

    <ul class="radio-btns-list user-info-form__input_stylus">
      <li class="radio-btns-list__item">
        <input
          id="male"
          class="radio-btns-list__btn"
          type="radio"
          :checked="gender === 'male'"
          value="male"
          @change="$emit('change', $event.target.value)"
        />
        <label for="male" class="radio-btns-list__label"> Мужской </label>
      </li>

      <li class="radio-btns-list__item">
        <input
          id="female"
          class="radio-btns-list__btn"
          type="radio"
          :checked="gender === 'female'"
          value="female"
          @change="$emit('change', $event.target.value)"
        />
        <label for="female" class="radio-btns-list__label"> Женский </label>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "GenderInput",
  props: ["gender"],
  methods: {
    update() {
      this.$emit("gender");
    },
  },
};
</script>
<style>

</style>
