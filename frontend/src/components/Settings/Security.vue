<template>
  <div class="settings-security">
    <div class="settings-security__block">
      <h3 class="settings-security__title">E-mail:</h3>

      <input class="settings-security__value" v-model="changeEmail" />

      <button-hover @click.native="openModal('email')">Изменить</button-hover>
    </div>

    <div class="settings-security__block">
      <h3 class="settings-security__title">Пароль:</h3>
      <input class="settings-security__value not-first" type="password" v-model="password" placeholder="Введите пароль" />
      <input class="settings-security__value" type="password" v-model="passwordTwo" placeholder="Повторите пароль" />

      <button-hover @click.native="openModal('password')">Изменить</button-hover>
    </div>

    <modal v-model="modalShow">
      <p v-if="modalText">{{ modalText }}</p>

      <template slot="actions">
        <button-hover @click.native="closeModal">Ок</button-hover>
      </template>
    </modal>
  </div>
</template>

<script>
import Modal from '@/components/Modal';
import auth from '@/requests/auth';
import { mapGetters, mapActions } from 'vuex';

export default {
  name: 'SettingsSecurity',
  components: { Modal },

  data: () => ({
    modalShow: false,
    modalText: '',
    changeEmail: '',
    password: '',
    passwordTwo: '',
    currentPasswordInput: '',
  }),

  computed: {
    ...mapGetters('profile/info', ['getInfo']),
  },

  mounted() {
    setTimeout(() => {
      this.changeEmail = this.getInfo.email;
      this.password = '';
      this.passwordTwo = '';
    }, 300);
  },

  methods: {
    ...mapActions('auth/api', ['logout']),

    closeModal() {
      this.modalShow = false;
    },

    async openModal(id) {
      if (id === 'email') {
        await auth.requestChangeEmailLink({ email: this.changeEmail }).then(() => {
          this.modalText = `Адрес электронной почты успешно изменен на ${this.changeEmail}`;
          this.modalShow = true;
          setTimeout(() => {
            this.logout().finally(() => {
              this.$router.push('/login');
            });
          }, 3000);
        });
      }

      if (id === 'password') {
        if (this.password === this.passwordTwo) {
          await auth.requestChangePasswordLink({ password: this.passwordTwo }).then(() => {
            this.modalText = `Пароль успешно изменен`;
            this.modalShow = true;
          });
        }
      }
    },
  },
};
</script>

<style lang="stylus">
@import '../../assets/stylus/base/vars.styl'

.settings-security__block
  background #fff
  box-shadow standart-boxshadow
  display flex
  align-items center
  min-height 95px
  padding 0 33px 0 50px
  font-size 15px

  &+&
    margin-top 20px

.settings-security__title
  color #5F5E7A
  width 100px

.form__input_stylus
  color #000000

.settings-security__value
  color #414141
  display block
  margin-right auto
  border-bottom 1px solid eucalypt
  padding 7px 0

.not-first
  margin-right: 15px
</style>
