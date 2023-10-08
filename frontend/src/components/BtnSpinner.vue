<template>
  <div>
    <div
      class="spinner"
      :style="{
        width: size,
        height: size,
        '--border-width': borderWidth,
        '--border-color': color
      }"
    ></div>
  </div>
</template>

<script>
export default {
  props: {
    size: { type: String, reuired: false },
    borderWidth: { type: String, default: '5px' },
    color: { type: String, default: '#d8ce57' },
  },
};
</script>

<style lang="css" scoped>
.spinner {
  height: 1.7em;
  width: 1.7em;
  border-radius: 50%;
  position: relative;
  animation: rotate 1s linear infinite;
}

.spinner::before {
  content: '';
  box-sizing: border-box;
  position: absolute;
  inset: 0px;
  border-radius: 50%;
  border: 5px solid;
  border-width: var(--border-width);
  border-color: var(--border-color);
  animation: prixClipFix 2s linear infinite;
}

@keyframes rotate {
  100% {
    transform: rotate(360deg);
  }
}

@keyframes prixClipFix {
  0% {
    clip-path: polygon(50% 50%, 0 0, 0 0, 0 0, 0 0, 0 0);
  }

  25% {
    clip-path: polygon(50% 50%, 0 0, 100% 0, 100% 0, 100% 0, 100% 0);
  }

  50% {
    clip-path: polygon(50% 50%, 0 0, 100% 0, 100% 100%, 100% 100%, 100% 100%);
  }

  75% {
    clip-path: polygon(50% 50%, 0 0, 100% 0, 100% 100%, 0 100%, 0 100%);
  }

  100% {
    clip-path: polygon(50% 50%, 0 0, 100% 0, 100% 100%, 0 100%, 0 0);
  }
}
</style>
