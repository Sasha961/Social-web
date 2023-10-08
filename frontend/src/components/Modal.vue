<template>
  <transition name="fade">
    <div
      class="modal"
      v-show="isDisplay"
      tabindex="-1"
      @click.self="close"
      @keyup.esc="close"
      ref="modal"
    >
      <div class="modal__wrapper">
        <div class="modal__body">
          <slot />
        </div>
        <div class="modal__actions">
          <slot name="actions" />
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: "Modal",
  props: {
    value: { type: Object },
  },

  computed: {
    isDisplay() {
      return this.value.show;
    },

    scrollBarWidth() {
      return window.innerWidth - document.documentElement.clientWidth;
    },
  },

  watch: {
    value: {
      deep: true,
      handler() {
        if (this.value.show) {
          this.setScrollPadding();
          document.body.classList.add("overflow-hidden");
        } else {
          setTimeout(() => {
            this.removeScrollPadding();
            document.body.classList.remove("overflow-hidden");
          }, this.DELAY_EFFECT);
        }
      },
    },
  },

  created() {
    this.DELAY_EFFECT = 300;
  },

  methods: {
    close() {
      if (this.value.closeModalOnBackdrop) {
        this.value.show = false
        this.$emit("input", this.value);
      }
    },

    checkBodyOverflowing() {
      const rect = document.body.getBoundingClientRect();
      return rect.left + rect.right < window.innerWidth;
    },

    setScrollPadding() {
      this.isBodyOverflowing = this.checkBodyOverflowing();
      if (this.isBodyOverflowing) {
        document.body.style.paddingRight = `${this.scrollBarWidth}px`;
      }
    },

    removeScrollPadding() {
      document.body.style.paddingRight = 0;
    },
  },
};
</script>
