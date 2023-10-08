<template>
  <div class="push-block">
    <div class="push__img" v-if="photoSrc">
      <img :src="photoSrc" :alt="firstName[0]" />
    </div>
    <div class="push__img" v-else>
      <div>
        {{ firstName[0] + ' ' + lastName[0] }}
      </div>
    </div>
    <p class="push__content">
      <router-link class="push__content-name" :to="getRouteByNotification(notificationType, authorId)">
        <span class="push__content-preview">{{ content }}</span>
        <span>
          <span class="push__notif-author">«{{ firstName + ' ' + lastName }}»</span>
          <span class="push__notif-type"> {{ getNotificationsTextType(notificationType) }}</span>
        </span>
      </router-link>
    </p>

    <span class="push__time">{{ sentTime | moment('from') }}</span>
  </div>
</template>
<script>
import { mapGetters } from 'vuex';
import { getRouteByNotification } from '@/utils/notifications.utils.js';

export default {
  name: 'PushBlock',
  props: {
    info: Object,
  },
  computed: {
    ...mapGetters('profile/notifications', ['getNotificationsTextType']),
    photoSrc() {
      return this.info.data.author.photo;
    },
    firstName() {
      return this.info.data.author.firstName;
    },
    lastName() {
      return this.info.data.author.lastName;
    },
    content() {
      return this.info.data.content;
    },
    notificationType() {
      return this.info.data.notificationType;
    },
    authorId() {
      return this.info.data.authorId;
    },
    sentTime() {
      return this.info.data.sentTime;
    }
  },
  methods: {
    getRouteByNotification,
  },
};
</script>

<style lang="stylus">
.push-block
  background #fff
  padding 25px 30px
  box-shadow 0px 2px 60px rgba(0, 0, 0, 0.1)
  display flex
  border-radius 3%
  &+&
    margin-top 20px

  .push__img
    flex none

  .push__content
    max-width 650px
    padding-top 10px
    margin-right 30px

  .push__time
    flex none
    padding-top 10px

.push__img
  width 36px
  height 36px
  border-radius 50%
  overflow hidden
  margin-right 15px
  flex none
  background-color #e6f4eb

  img
    display flex
    align-items center
    justify-content center
    width 100%
    height 100%
    object-fit cover

  div
    display flex
    align-items center
    justify-content center
    width 100%
    height 100%
    object-fit cover
.push__content-name
  display grid
.push__notif-type
  font-weight 400
.push__content-preview {
  margin-bottom 8px
}
</style>
