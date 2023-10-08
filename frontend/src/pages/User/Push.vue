<template>
  <div class="page-push inner-page">
    <!-- <div class="inner-page__main" v-if="notifications && notifications.length > 0"> -->
    <div class="inner-page__main" >
      <push-block v-for="info in filterNotifications" :key="info.data.id" :info="info" />
    </div>
    <div class="inner-page__aside">
      <push-sidebar
        v-model="activeFilter"
        @change-push-sidebar="onChangeFilter($event)"
      />
    </div>
    <vue-pagination
      v-model="currentPage"
      :page-count="pageAmount"
      class="vue-pagination"
    />
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import PushSidebar from "@/components/Push/Sidebar";
import PushBlock from "@/components/Push/Block";
import VuePagination from 'vue-plain-pagination';

export default {
  name: "PagePush",
  components: { PushSidebar, PushBlock, VuePagination },

  data: () => ({
    activeFilter: "Все",
    currentPage: 1,
    pageAmount: 5,
  }),

  computed: {
    ...mapGetters("profile/notifications", ["getNotifications"]),
    filterNotifications() {
      switch (this.activeFilter) {
        case "Все":
          return this.notifications;
        case "Комментарии":
          return this.notifications.filter((el) => {
            return (
              el.data.notificationType === "POST_COMMENT" ||
              el.data.notificationType === "COMMENT_COMMENT"
            );
          });
        case "Друзья":
          return this.notifications.filter(
            (el) => el.data.notificationType === "FRIEND_REQUEST"
          );
      }
      return this.notifications;
    },
    notifications() {
      return this.getNotifications.content;
    },
  },
  mounted() {
    this.fetchNotifications();
  },
  methods: {
    ...mapActions("profile/notifications", ["fetchNotifications"]),
    onChangeFilter(item) {
      this.activeFilter = item;
    },
    myCallback() {
      console.log(this.page);
    },
  },
};
</script>

<style lang="stylus">
@import '../../assets/stylus/base/vars.styl'

.page-push
  flex-direction column

  .inner-page__aside
    display block
    max-width 100%
    order -1

    .aside-filter__item
      text-align center

  .inner-page__main
    margin-right 0
    position fixed
    top 230px
    max-height 75vh
    overflow-y scroll

  .pagination
    position relative
    z-index 1000
</style>
