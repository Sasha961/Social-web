<template>
  <div class="settings-delete">
    <h2 class="settings-delete__title">{{ title }}</h2>

    <div class="settings-delete__confirm">
      <input
        class="settings-delete__confirm-input"
        type="checkbox"
        id="confirm"
        v-model="confirm"
        v-if="!isAccountDeleted"
      />

      <label v-if="!isAccountDeleted" for="confirm" class="settings-delete__confirm-label">
        Да, удалить мою страницу и всю связаную с ней информацию
      </label>
    </div>

    <div class="settings-delete__actions">
      <button-hover
        v-if="!isAccountDeleted"
        :disable="!confirm"
        variant="warning"
        @click.prevent.native="openModal('delete')">
        Удалить профиль
      </button-hover>

      <button
        class="settings-delete__actions-button"
        @click.prevent="openModal('cancelDelete')"
        v-if="isAccountDeleted">
        Не удалять профиль, я хочу вернуться
      </button>
    </div>
    <modal v-model="modalShow">
      <p v-html="modalText"></p>

      <template slot="actions">
        <button-hover :showSpinner="showSpinner" @click.native="modalHandler">
          Ок
        </button-hover>
        <button-hover v-if="showBtnCancel" @click.native="closeModal">Отмена</button-hover >
      </template>
    </modal>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import Modal from "@/components/Modal";
import ButtonHover from "@/components/ButtonHover";

export default {
  name: 'SettingsDelete',
  components: { Modal, ButtonHover },
  data: () => ({
    confirm: false,
    modalShow: { show: false, closeModalOnBackdrop: true },
    modalText: '',
    modalHandler: null,
    showSpinner: false,
    showBtnCancel: true,
  }),
  computed: {
    ...mapGetters("profile/info", ["getInfo"]),
    isAccountDeleted() {
      return this.getInfo.isDeleted
    },
    title() {
      if (this.isAccountDeleted) {
        return 'Ваш профиль в процессе удаления.'
      }
      return `Ваш профиль будет полностью удалён, через 30 дней.
        После удаления профиля будет удалена вся связанная с ним информация: друзья, публикации,
        комментарии, лайки.`
    }
  },
  methods: {
    ...mapActions('profile/info', ['deleteInfo', 'cancelDelete']),
    ...mapActions('auth/api', ['logout']),

    async onDelete() {
      this.showSpinner = true;
      try {
        await this.deleteInfo();
        this.modalText = `Ваш акаунт в процессе удаления. <br>
        В течение 30-ти дней вы можете восстановить аккаунт в личном кабинете. <br>
        Далее произойдёт безвозвратное удаление.`
        this.showBtnCancel = false;
        this.modalShow.closeModalOnBackdrop = false;
        this.modalHandler = this.onLogout
      } catch(err) {
        console.log(err)
      } finally {
        this.showSpinner = false;
      }
    },
    async onCancelDelete() {
      this.showSpinner = true;
      try {
        await this.cancelDelete()
        this.modalText = 'Ваш аккаунт восстановлен.';
        this.showBtnCancel = false;
        this.modalShow.closeModalOnBackdrop = true;
        this.modalHandler = this.closeModal;
      } catch(err) {
        console.log(err)
      } finally {
        this.showSpinner = false;
      }
    },
    openModal(action) {
      this.modalShow.show = true;
      if (action === 'delete') {
        this.modalText = 'Вы уверены, что хотите удалить аккаунт?';
        this.modalHandler = this.onDelete;
      } else if (action === 'cancelDelete') {
        this.modalText = 'Вы уверены, что хотите отменить удаление аккаунта?';
        this.modalHandler = this.onCancelDelete;
      }
    },
    closeModal() {
      this.modalShow.show = false;
    },
    onLogout() {
      this.logout().then(() => this.$router.push('login'));
    }
  },
};
</script>

<style lang="stylus">
@import '../../assets/stylus/base/vars.styl'

.settings-delete
  background #fff
  box-shadow standart-boxshadow
  padding 45px 65px

  @media (max-width breakpoint-xl)
    padding 20px

.settings-delete__title
  margin-bottom 20px
  color #0A0A0A
  font-size 18px
  line-height 27px
  max-width 666px

.settings-delete__confirm
  margin-bottom 30px

.settings-delete__confirm-input
  display none

  &:checked
    & + .settings-delete__confirm-label
      &:before
        border-color eucalypt

      &:after
        opacity 1

.settings-delete__confirm-label
  position relative
  display flex
  align-items center
  color #A4A4B8
  font-size 16px
  cursor pointer

  &:before
    content ''
    display block
    border 2px solid #BCBCC7
    width 26px
    height 26px
    margin-right 24px
    transition all 0.2s

  &:after
    content ''
    display block
    width 8px
    height 13px
    border 2px solid transparent
    border-bottom-color eucalypt
    border-right-color eucalypt
    position absolute
    left 9px
    top 4px
    transform rotate(45deg)
    opacity 0
    transition all 0.2s

.settings-delete__actions
  display flex
  align-items center

.settings-delete__actions-button
  font-size 13px
  color eucalypt
  margin-left 20px
  background transparent
</style>
