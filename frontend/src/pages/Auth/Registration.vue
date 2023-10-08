<template>
  <div class="registration">
    <form class="registration__form" @submit.prevent="submitHandler">
      <div class="form__block">
        <h4 class="form__subtitle">Аккаунт</h4>

        <email-field
          id="register-email"
          v-model="email"
          :v="$v.email"
          :class="{ checked: $v.email.required && $v.email.email }"
        />

        <password-field
          id="register-password"
          v-model="password"
          :v="$v.password"
          info
          registration
          :class="{
            checked:
              $v.password.required &&
              $v.confirmPassword.sameAsPassword &&
              $v.password.minLength,
          }"
        />

        <password-repeat-field
          id="register-repeat-password"
          v-model="confirmPassword"
          :v="$v.confirmPassword"
          :class="{
            checked:
              $v.password.required &&
              $v.confirmPassword.sameAsPassword &&
              $v.confirmPassword.minLength,
          }"
        />
      </div>
      <div class="form__block">
        <h4 class="form__subtitle">Личные данные</h4>

        <name-field id="register-firstName" v-model="firstName" :v="$v.firstName" />

        <name-field
          id="register-lastName"
          v-model="lastName"
          :v="$v.lastName"
          label="Фамилия"
        />
      </div>

      <div class="form__block">
        <h4 class="form__subtitle">Введите символы, которые вы видите на экране</h4>
        <div class="img_captcha captcha-box">
          <btn-spinner v-if="isCaptchaLoading" class="captcha-box__spinner" size="40px" />
          <img :src="getCaptchaSrc" alt="Капча" v-else />
        </div>
        <code-field
          id="register-code"
          v-model="code"
          :v="$v.code"
          :class="{ checked: $v.code.required && $v.code.isCode }"
        />
        <button class="btn__update bold" @click.prevent="updateCaptcha">Обновить</button>
        <confirm-field id="register-confirm" v-model="confirm" :v="$v.confirm" />
      </div>
      <div class="registration__action">
        <button-hover
          type="submit"
          tag="button"
          variant="white"
          spinnerColor="#21a45d"
          :showSpinner="authStatus === 'loading'"
        >Зарегистрироваться</button-hover
        >
      </div>
    </form>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { required, email, minLength, sameAs } from "vuelidate/lib/validators";
import PasswordField from "@/components/FormElements/PasswordField";
import PasswordRepeatField from "@/components/FormElements/PasswordRepeatField";
import EmailField from "@/components/FormElements/EmailField";
import NameField from "@/components/FormElements/NameField";
import CodeField from "@/components/FormElements/CodeField";
import ConfirmField from "@/components/FormElements/ConfirmField";
import ButtonHover from "@/components/ButtonHover";
import BtnSpinner from "@/components/BtnSpinner.vue";

export default {
  name: "Registration",
  components: {
    PasswordField,
    EmailField,
    NameField,
    CodeField,
    ConfirmField,
    PasswordRepeatField,
    ButtonHover,
    BtnSpinner,
  },
  data: () => ({
    email: "",
    password: "",
    confirmPassword: "",
    firstName: "",
    lastName: "",
    confirm: true,
    code: "",
    isCode: true,
    isCaptchaLoading: false,
  }),
  computed: {
    ...mapGetters("auth/captcha", ["getCaptcha"]),
    ...mapGetters("auth/api", ["authStatus", 'getIsCode']),
    getCaptchaSrc() {
      return `data:image/png;base64,${this.getCaptcha}`;
    },
  },
  watch: {
    code() {
      if (this.isCode === false) {
        this.isCode = true;
      }
    },
  },
  async beforeMount() {
    await this.updateCaptcha();
  },
  methods: {
    ...mapActions("auth/api", ["register"]),
    ...mapActions("auth/captcha", ["fetchCaptcha"]),
    async updateCaptcha() {
      this.isCaptchaLoading = true;
      try {
        await this.fetchCaptcha();
      } finally {
        this.isCaptchaLoading = false;
      }
    },

    async submitHandler() {
      if (this.$v.$invalid) {
        this.$v.$touch();
        return;
      }
      const { email, password, confirmPassword, firstName, lastName, code } = this;
        await this.register({
          id: null,
          isDeleted: null,
          email,
          password,
          confirmPassword,
          firstName,
          lastName,
          captchaCode: code,
          captchaSecret: null,
        });
        if (this.authStatus === "success") {
          this.$router.push({ name: "Login" });
        }
        this.isCode = this.getIsCode
        if (!this.isCode) this.updateCaptcha()
    },
  },
  validations: {
    confirm: { sameAs: sameAs(() => true) },
    email: { required, email },
    password: {
      required,
      minLength: minLength(8),
    },
    confirmPassword: {
      required,
      minLength: minLength(8),
      sameAsPassword: sameAs("password"),
    },
    firstName: { required, minLength: minLength(2) },
    lastName: { required, minLength: minLength(2) },
    code: {
      required,
      isCode() {
        return this.isCode;
      },
    },
  },
};
</script>

<style lang="stylus">
@import '../../assets/stylus/base/vars.styl'

.registration
  min-height 100%
  display flex
  flex-direction column
  justify-content center

.registration__action
  margin-top 40px

  @media (max-width breakpoint-xxl)
    margin-top 20px

.img_captcha
  margin-bottom 15px
</style>
