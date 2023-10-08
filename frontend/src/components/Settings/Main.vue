<template>
  <div class="settings-main" v-if="getInfo">
    <user-info-form-block
      label="Имя:"
      placeholder="Введите имя"
      :value="firstName"
      @change="firstName = $event"
      :v="$v.firstName"
    />

    <user-info-form-block
      label="Фамилия:"
      placeholder="Введите фамилию"
      :value="lastName"
      @change="lastName = $event"
      :v="$v.lastName"
    />

    <gender-input :gender="gender" @change="gender = $event" />
    <user-info-form-block
      label="Телефон:"
      placeholder="Введите телефон"
      :value="phone"
      @change="phone = $event"
      phone="phone"
    />
    <input-section sectionName="Страна:">
      <select
        class="select user-info-form__select country user-info-form__input"
        v-model="country"
      >
        <option value="">Не выбрано</option>
        <option v-for="(value, key, index) in countries" :key="index" :value="key">
          {{ key }}
        </option>
      </select>
    </input-section>
    <input-section sectionName="Город:">
      <select
        class="select user-info-form__select country user-info-form__input"
        v-model="city"
      >
        <option value="">Не выбрано</option>
        <option v-for="(city, index) in cities" :key="index" :value="city">
          {{ city }}
        </option>
      </select>
    </input-section>
    <input-section sectionName="Дата рождения:">
      <setting-select :propsList="days" :value="day" class="day" @select="day = $event" />
      <setting-select
        :propsList="months"
        :value="month"
        class="month"
        @select="month = $event"
      />
      <setting-select
        :propsList="years"
        :value="year"
        class="year"
        @select="year = $event"
      />
    </input-section>
    <input-section sectionName="Фотография:" class="user-info-form__block--photo">
      <div class="user-info-form__photo-wrap">
        <input
          class="user-info-form__input_stylus-photo"
          type="file"
          ref="photo"
          id="photo"
          @change="processFile($event)"
          accept="image/*"
        />

        <label
          class="user-info-form__input_stylus user-info-form__input_stylus--photo"
          for="photo"
        >
          <span class="setting-main__photo-delete" v-if="src">
            {{ photoName }}

            <delete-icon
              class="setting-main__delete-icon"
              @click.native.prevent="deletePhoto"
            />
          </span>
        </label>

        <button-hover variant="fill" bordered="bordered" @click.native="loadPhoto">
          Загрузить
        </button-hover>
      </div>
      <div class="main-layout__user-pic" v-if="src" style="margin: 10px">
        <img :src="src" :alt="firstName[0]" />
      </div>
    </input-section>
    <user-info-form-block :about="true" label="О себе:" @change="about = $event" :value="about"/>

    <div class="user-info-form__block user-info-form__block--actions">
      <span class="user-info-form__label_stylus" />
      <div class="user-info-form__wrap">
        <button-hover
          @click.native.prevent="submitHandler"
          :showSpinner="isUserDataSenfing"
        >
          Сохранить
        </button-hover>

        <router-link class="settings-main__back" :to="{ name: 'Profile' }">
          <button-hover variant="red" bordered="bordered"> Отменить </button-hover>
        </router-link>
      </div>
    </div>
  </div>
  <spinner v-else />
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import DeleteIcon from "../../Icons/DeleteIcon.vue";
// import moment from 'moment';
import UserInfoFormBlock from "@/components/Settings/UserInfoForm/Block.vue";
import GenderInput from "@/components/Settings/UserInfoForm/GenderInput.vue";
import axios from "axios";
import { required } from "vuelidate/lib/validators";
import ButtonHover from "@/components/ButtonHover";
import Spinner from "@/components/Spinner";
import SettingSelect from "@/components/Settings/UserInfoForm/SettingSelect.vue";
import InputSection from "@/components/Settings/UserInfoForm/InputSection.vue";

export default {
  name: "SettingsMain",
  components: {
    UserInfoFormBlock,
    DeleteIcon,
    GenderInput,
    ButtonHover,
    Spinner,
    SettingSelect,
    InputSection,
  },

  data: () => ({
    photoName: "Фотография",
    firstName: "",
    lastName: "",
    gender: "",
    phone: "",
    about: "",
    photoPath: "",
    src: "",
    country: "",
    city: "",
    countries: {},
    cities: [],
    isUserDataSenfing: false,
    day: new Date().getDate(),
    month: new Date().getMonth(),
    year: new Date().getFullYear(),
    months: [
      { value: 0, title: "Января" },
      { value: 1, title: "Февраля" },
      { value: 2, title: "Марта" },
      { value: 3, title: "Апреля" },
      { value: 4, title: "Мая" },
      { value: 5, title: "Июня" },
      { value: 6, title: "Июля" },
      { value: 7, title: "Августа" },
      { value: 8, title: "Сентября" },
      { value: 9, title: "Октября" },
      { value: 10, title: "Ноября" },
      { value: 11, title: "Декабря" },
    ],
  }),

  computed: {
    ...mapGetters("global/storage", ["getStorage"]),
    ...mapGetters("profile/info", ["getInfo"]),
    phoneNumber() {
      return this.phone.replace(/\D+/g, "");
    },
    years() {
      const currentYear = new Date().getFullYear();
      return Array.from({ length: 100 }, (_, index) => currentYear - index);
    },
    days() {
      return new Date(this.year, this.month + 1, 0).getDate();
    },
    isFieldsValid() {
      if (this.$v.firstName.$invalid || this.$v.lastName.$invalid) {
        this.$v.$touch();
        return false;
      }
      return true;
    },
    birthDate() {
      let _birthDate = "";
      if (!isNaN(this.year) && !isNaN(this.month) && !isNaN(this.day)) {
        _birthDate = new Date(Date.UTC(this.year, this.month, this.day)).toISOString();
      }
      return _birthDate;
    },
  },
  watch: {
    photoPath: {
      immediate: true,
      handler() {
        if (this.photoPath) {
          this.src = this.photoPath;
        }
      },
    },

    getInfo(value) {
      if (!value) return;
      this.setInfo();
    },

    country() {
      if (this.country) {
        this.loadCities(this.country)
      } else {
        this.cities = []
        this.city = ''
      }
    },
  },
  mounted() {
    this.loadCountries();
    if (this.getInfo) {
      this.setInfo();
      this.src = this.photoPath;
    }

  },
  methods: {
    ...mapActions("global/storage", ["apiStorage"]),
    ...mapActions("profile/info", ["apiChangeInfo"]),
    ...mapMutations("global/storage", ["setStorage"]),
    async loadCountries() {
      const res = await axios.get("/users/geo/country");
      this.countries = res.data;
      this.loadCities(this.country)
    },

    loadCities(country) {
      this.cities = this.countries[country]
      if (this.cities && !this.cities.includes(this.city)) {
        this.city = ''
      }
    },
    async submitHandler() {
      if (!this.isFieldsValid) return;

      this.isUserDataSenfing = true;

      if (typeof this.photoPath === 'object' && this.photoPath !== null) {
        try {
          const newSrc = await this.apiStorage(this.photoPath);
          this.photoPath = newSrc;
        } catch (err) {
          console.dir(err);
        }
      }
      try {
        const userData = {
          firstName: this.firstName.trim(),
          lastName: this.lastName.trim(),
          gender: this.gender,
          birthDate: this.birthDate,
          phone: this.phoneNumber,
          about: this.about,
          country: this.country,
          city: this.city,
          photo: this.photoPath,
        }
        await this.apiChangeInfo(userData);
        this.setStorage(null);
      } catch (err) {
        console.dir(err);
      }
      this.isUserDataSenfing = false;
    },

    processFile(event) {
      [this.photoPath] = event.target.files;
      const reader = new window.FileReader();
      reader.onload = (e) => {
        this.src = e.target.result;
      };
      reader.readAsDataURL(this.photoPath);
    },

    loadPhoto() {
      this.$refs.photo.click();
    },

    deletePhoto() {
      this.photoName = "";
      this.photoPath = "";
      this.src = "";
      this.setStorage("");
    },

    setInfo() {
      this.firstName = this.getInfo.firstName;
      this.lastName = this.getInfo.lastName;
      this.gender = this.getInfo.gender;
      this.photoPath = this.getInfo.photo;
      if (this.getInfo.phone) {
        this.phone = this.getInfo.phone.replace(/^[+]?[78]/, "");
      } else this.phone = "";

      if (this.getInfo.birthDate) {
        const birthDate = new Date(this.getInfo.birthDate);
        this.day = birthDate.getDate();
        this.month = birthDate.getMonth();
        this.year = birthDate.getFullYear();
      }
      this.about = this.getInfo.about;
      this.country = this.getInfo.country;
      this.city = this.getInfo.city;
    },
  },
  validations: {
    firstName: { required },
    lastName: { required },
    gender: { required },
  },
};
</script>

<style lang="stylus">
@import '../../assets/stylus/base/vars.styl'

.settings-main
  background #fff
  box-shadow standart-boxshadow
  padding 40px 10px 50px

  .user-info-form__label_stylus
    white-space pre-wrap

  @media (max-width breakpoint-xl)
    padding 40px 20px

.settings-main__back
  margin-left 20px

.setting-main__delete-icon
  position absolute
  right 10px
  top 12px
  width 12px
  height 12px

.country
  width 100%
.user-info-form__input_stylus--photo
  cursor pointer
</style>
