<template>
  <div>
    <router-link
      :to="to"
      class="btn"
      :class="classObject"
      @mousemove.native="btnHandler($event)"
      @mouseleave.native="btnMouseLeave($event)"
      v-if="to"
    >
      <!-- <span class="helper" :style="{ left: x, top: y }"></span> -->
      <slot></slot>
    </router-link>
    <component
      :is="tag"
      class="btn"
      :class="classObject"
      @mousemove="btnHandler($event)"
      @mouseleave="btnMouseLeave($event)"
      v-else
      :disabled="showSpinner"
    >
      <btn-spinner
        v-if="showSpinner"
        :size="spinnerSize"
        :borderWidth="spinnerBorderWidth"
        :color="spinnerColor"
        class="btn__spinner"
      />
      <span class="helper" :style="{ left: x, top: y }"></span>
      <span :class="{ 'hidden-text': showSpinner }" class="123">
        <slot></slot>
      </span>
    </component>
  </div>
</template>

<script>
import BtnSpinner from "@/components/BtnSpinner.vue";

export default {
  components: {
    BtnSpinner,
  },
  props: {
    tag: {
      type: String,
      default: "a",
    },
    variant: {
      type: String,
      default: "green",
    },
    bordered: {
      type: Boolean,
    },
    to: {
      type: Object,
    },
    disable: {
      type: Boolean,
    },
    showSpinner: {
      type: Boolean,
    },
    spinnerSize: {
      type: String,
    },
    spinnerBorderWidth: {
      type: String,
    },
    spinnerColor: {
      type: String,
    },
  },
  data() {
    return {
      x: null,
      y: null,
      isAnimated: true,
    };
  },
  computed: {
    classObject() {
      return {
        "btn--white": this.variant === "white",
        "btn--dark": this.variant === "dark",
        "btn--red": this.variant === "red",
        "btn--warning": this.variant === "warning",
        "btn--fill": this.variant === "fill",
        "btn--bordered": this.bordered,
        "btn--disable": this.disable,
      };
    },
  },
  methods: {
    btnHandler(e) {
      if (!this.isAnimated) return null;
      this.isAnimated = false;
      this.x = `${e.offsetX}px`;
      this.y = `${e.offsetY}px`;
    },
    btnMouseLeave(e) {
      this.x = `${e.offsetX}px`;
      this.y = `${e.offsetY}px`;
      this.isAnimated = true;
      setTimeout(() => {
        this.x = 0;
        this.y = 0;
      }, 200);
    },
  },
};
</script>
<style lang="css" scoped>
.btn__spinner {
  position: absolute;
  z-index: 10;
}
.hidden-text {
  color: transparent;
}
</style>
