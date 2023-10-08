<template>
  <div class="settings-security">
    <div class="settings-security__block">
      <h3 class="settings-security__title">E-mail:</h3>
      <security-input
        placeholder="Введите новый email"
        type="email"
        v-model.trim="newEmail"
        :error="emailError"
      />
      <button-hover @click.native="openConfirmModal('email')">Изменить</button-hover>
    </div>
    <div class="settings-security__block">
      <h3 class="settings-security__title">Пароль:</h3>
      <security-input
        placeholder="Введите новый пароль"
        type="password"
        v-model.trim="password"
        :error="passwordError"
      />
      <security-input
        placeholder="Повторите пароль"
        type="password"
        v-model.trim="passwordTwo"
        :error="passworTwoError"
      />
      <button-hover @click.native="openConfirmModal('password')">Изменить</button-hover>
    </div>

    <modal v-model="modalShow">
      <p v-html="modalText"></p>

      <template slot="actions">
        <button-hover :showSpinner="showSpinner" @click.native="modalHandler('email')">
          Ок
        </button-hover>
        <button-hover v-if="showBtnCancel" @click.native="modalShow.show = false">Отмена</button-hover >
      </template>
    </modal>
  </div>
</template>

<script>
import Modal from "@/components/Modal";
import auth from "@/requests/auth";
import { mapGetters, mapActions } from "vuex";
import ButtonHover from "@/components/ButtonHover";
import { required, email, sameAs, minLength } from "vuelidate/lib/validators";
import SecurityInput from "@/components/Settings/SecurityInput";

export default {
  name: "SettingsSecurity",
  components: { Modal, ButtonHover, SecurityInput },

  data: () => ({
    modalShow: {
      show: false,
      closeModalOnBackdrop: true,
    },
    modalText: "",

    currentEmail: "",
    newEmail: "",

    showBtnCancel: true,

    modalHandler: null,
    password: "",
    passwordTwo: "",
    currentPasswordInput: "",
    showSpinner: false,

    emailError: '',
    incorrectEmail: "Некорректный Email",
    theSameEmail: "Совпадает с текущим Email",
    required: "Не может быть пустым",

    passwordError: '',
    minLength: 'Не может быть короче 8 символов',

    passworTwoError: '',
    notTheSamePassword: 'Пароль не совпадает',

    passwordSuccessMessage: 'Пароль успешно изменён. <br> Необъодимо выйти из системы и залогиниться заново.',
    confirmChangePasswordMessage: `Вы уверены, что хотите изменить пароль?
      <br> После изменения пароля, произойдёт выход из системы.`
  }),

  computed: {
    ...mapGetters("profile/info", ["getInfo"]),
    confirmChangeEmailMessage() {
      return `
        Вы уверены, что хотите изменить
        <strong>${this.currentEmail}</strong>
        на <strong>${this.newEmail}</strong>?
        <br>
        После изменения Email, произойдёт выход из системы.`;
    },
    emailSuccessMessage() {
      return `Адрес электронной почты <strong>${this.currentEmail}</strong>
        успешно изменен на <strong>${this.newEmail}</strong>.
        Необъодимо выйти из системы и залогиниться заново.`;
    },
    emailErrorIndentical() {
      return `Введённый Email <strong>${this.newEmail}</strong>
        совпадает с текущим <strong>${this.currentEmail}</strong>.`;
    },
  },
  watch: {
    "getInfo.email": {
      immediate: true,
      handler(email) {
        this.currentEmail = email;
        this.newEmail = email;
      },
    },
    '$v.newEmail': {
      deep: true,
      handler(vValue) {
        this.checkEmail(vValue)
      }
    },
    '$v.password': {
      deep: true,
      handler(vValue) {
        this.checkPassword(vValue)
      }
    },
    '$v.passwordTwo': {
      deep: true,
      handler(vValue) {
        this.checkPasswordTwo(vValue)
      }
    }
  },
  methods: {
    ...mapActions("auth/api", ["logout"]),
    removeItemFomArray(array, item) {
      const index = array.indexOf(item);
      array.splice(index, 1)
    },
    checkEmail(vValue) {
        if (!vValue.email) {
          this.emailError = this.incorrectEmail;
        } else if (!vValue.required) {
          this.emailError = this.required;
        } else if (!vValue.isNewEmail) {
          this.emailError = this.theSameEmail;
        } else {
          this.emailError = '';
        }
    },
    checkPassword(vValue) {
      if (!vValue.required) {
        this.passwordError = this.required;
      } else if (!vValue.minLength) {
        this.passwordError = this.minLength;
      } else {
        this.passwordError = '';
      }
    },
    checkPasswordTwo(vValue) {
      if (!vValue.sameAsPassword && vValue.$dirty) {
        this.passworTwoError = this.notTheSamePassword;
      } else {
        this.passworTwoError = '';
      }
    },
    doLogout() {
      this.logout().then(() => this.$router.push("/login"));
    },
    closeModal() {
      this.modalShow.show = false;
    },
    openConfirmModal(type) {

      if (type === "email" && this.$v.newEmail.$invalid) {
        this.checkEmail(this.$v.newEmail)
        return;
      }
      if (type === 'password' && (this.$v.password.$invalid || this.$v.passwordTwo.$invalid)) {
        this.$v.passwordTwo.$touch()
        this.checkPassword(this.$v.password);
        this.checkPasswordTwo(this.$v.passwordTwo);
        return;
      }

      this.modalShow.show = true;
      if (type === "email") {
        this.modalText = this.confirmChangeEmailMessage;
        this.modalHandler = this.changeEamil;
      } else if (type === "password") {
        this.modalText = this.confirmChangePasswordMessage;
        this.modalHandler = this.changePassword;
      }
    },
    async changeEamil() {
      this.showBtnCancel = false;
      this.showSpinner = true;
      this.modalShow.closeModalOnBackdrop = false;
      try {
        await auth.requestChangeEmailLink({ email: this.newEmail });
        this.modalText = this.emailSuccessMessage;
        this.modalHandler = this.doLogout;
      } catch (err) {
        console.dir(err);
        this.modalShow.show = false;

        let errorMessage = '';
        if (err?.response?.statusText === 'Conflict') {
          errorMessage = 'Данный Email уже зарегестрирован в системе';
        } else { errorMessage = `Не удалось изменить Email: ${err.message}` }

        this.$store.dispatch('global/alert/setAlert', {
          status: 'error',
          text: errorMessage,
        });
      } finally {
        this.showSpinner = false;
      }
    },
    async changePassword() {
      this.showBtnCancel = false;
      this.showSpinner = true;
      this.modalShow.closeModalOnBackdrop = false;
      try {
        await auth.requestChangePasswordLink({ password: this.password });
        this.modalText = this.passwordSuccessMessage;
        this.modalHandler = this.doLogout;
      } catch(err) {
        console.dir(err);
        this.modalShow.show = false;

        let errorMessage = `Не удалось изменить пароль: ${err.message}`;

        this.$store.dispatch('global/alert/setAlert', {
          status: 'error',
          text: errorMessage,
        });
      } finally {
        this.showSpinner = false;
      }
    },
  },
  validations: {
    newEmail: {
      required,
      email,
      isNewEmail() {
        return this.newEmail !== this.currentEmail;
      },
    },
    password: {
      required,
      minLength: minLength(8),
    },
    passwordTwo: {
      sameAsPassword: sameAs('password')
    }
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

.not-first
  margin-right: 15px
</style>
