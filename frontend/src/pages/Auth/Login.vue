<template>
  <div class="login">
    <h2 class="login__title form__title">Войдите в аккаунт</h2>

    <form class="login__form" @submit.prevent="submitHandler">
      <email-field id="login-email" v-model="email" :v="$v.email" />

      <password-field id="login-password" v-model="password" :v="$v.password" />

      <div class="login__action">
        <button-hover
          type="submit"
          tag="button"
          variant="white"
          spinnerColor="#21a45d"
          :showSpinner="authStatus === 'loading'"
        >
          Войти
        </button-hover>
        <router-link class="login__link" :to="{ name: 'Forgot' }">
          Забыли пароль?
        </router-link>
      </div>
    </form>

  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import { required, email, minLength } from "vuelidate/lib/validators";
import PasswordField from "@/components/FormElements/PasswordField";
import EmailField from "@/components/FormElements/EmailField";
import ButtonHover from "@/components/ButtonHover";

export default {
  name: "Login",
  components: {
    PasswordField,
    EmailField,
    ButtonHover,
  },

  data: () => ({
    email: "",
    password: "",
  }),

  computed: {
    ...mapGetters("auth/api", ["authStatus"]),
    redirectUrl() {
      return this.$route.query.redirect || "News";
    },
  },
  methods: {
    ...mapActions("auth/api", ["login"]),
    ...mapActions("profile/info", ["apiInfo"]),

    async submitHandler() {
      if (this.$v.$invalid) {
        this.$v.$touch();
        return;
      }
      try {
        this.isLoading = true;
        await this.login({ email: this.email, password: this.password });
        await this.apiInfo();
        this.$router.push({ name: this.redirectUrl });
      } catch (err) {
        console.dir(err);
      }
    },
  },

  validations: {
    email: { required, email },
    password: { required, minLength: minLength(8) },
  },
};
</script>

<style lang="stylus" scoped>
@import '../../assets/stylus/base/vars.styl'

.login
  height 100%
  display flex
  flex-direction column
  justify-content center

.login__title
  margin-bottom 50px

.login__action
  display flex
  flex-wrap wrap
  gap 30px
  align-items center
  justify-content center
  margin-top 50px

.login__link
  position relative
  font-size 13px
  color rgba(255, 255, 255, 0.5)
  white-space nowrap
  transition all 0.2s
  &::before
    position: absolute
    content: ''
    bottom -1px
    width: 100%
    border-bottom: 1px solid transparent
    transition border-bottom-color 0.2s ease
  &:focus-visible::before
    border-bottom-color #fff
  &:hover
    color #fff
</style>
