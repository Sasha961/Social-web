<template>
  <div class="add-tags">
    <input
      class="add-tags__input"
      type="text"
      placeholder="Добавить тег..."
      v-model="tag"
      ref="input"
      @change="addTag"
      v-touppercase
    />

    <div class="add-tags__block">
      <div class="add-tags__item" v-for="(tag, index) in updateTags" :key="index">
        #{{ tag.name }}
        <span class="add-tags__delete" @click="deleteTag(index)"> &#10005; </span>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue';

Vue.directive( 'touppercase', {
    update (el) {
      const sourceValue = el.value;
      const newValue = sourceValue.toLowerCase();

      if (sourceValue !== newValue) {
        el.value = newValue;
        el.dispatchEvent(new Event('input', { bubbles: true }));
      }
    },
})

export default {
  name: 'AddTags',

  props: {
    tags: Array,
  },

  data: () => ({
    tagsList: [],
    tag: '',
  }),

  computed: {
    updateTags() {
      return this.tags;
    }
  },

  mounted() {
    setTimeout(() => {
      this.tagsList = this.tags;
    }, 200)
  },

  methods: {
    deleteTag(index) {
      this.tagsList = this.tags.filter((tag) => tag !== this.tags[index]);
      this.$emit('change-tags', this.tagsList);
    },

    addTag() {
      if (this.tag.length <= 0) return;
      if (this.tags.find((tag) => tag.name === this.tag)) {
        this.$store.dispatch('global/alert/setAlert', {
          status: 'response',
          text: 'Такой тег уже есть',
        });
        setTimeout(() => {
          this.$refs.input.focus();
        }, 0);
        return;
      }
      this.tagsList.push({
        name: this.tag,
      });
      this.tag = '';
      this.$emit('change-tags', this.tags);
      setTimeout(() => {
        this.$refs.input.focus();
      }, 0);
    },
  },
};
</script>

<style lang="stylus">
@import '../../assets/stylus/base/vars.styl'

.add-tags__input
  border-bottom 1px solid rgba(0, 0, 0, 0.12)
  padding-bottom 5px
  font-size 13px
  color steel-gray
  margin-bottom 15px

  &::placeholder
    color #B0B0BC

.add-tags__block
  margin 0 -4px 10px
  min-height 32px

.add-tags__item
  display inline-block
  align-items center
  color eucalypt
  font-size 13px
  background-color #F5F7FB
  padding 5px
  margin 0 5px 10px

.add-tags__delete
  margin-left 5px
  color #B0B0BC
  font-size 10px
  font-weight 600
  cursor pointer
</style>
