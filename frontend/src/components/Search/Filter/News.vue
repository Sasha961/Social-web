<template>
  <div class="search-filter">
    <div class="search-filter__block">
      <label class="search__label" for="search-news-author">Автор:</label>
      <input class="search__input" type="text" id="search-news-author" v-model="author" />
    </div>

    <div class="search-filter__block time">
      <label class="search__label">Время публикации:</label>

      <select class="select search-filter__select" v-model="dateFrom">
        <option value="null">За все время</option>
        <option value="year">За последний год</option>
        <option value="month">За последний месяц</option>
        <option value="week">За последнюю неделю</option>
      </select>
    </div>

    <div class="search-filter__block tags">
      <add-tags :tags="updateTags" @change-tags="onChangeTags" />
    </div>

    <div class="search-filter__block btn-news">
      <button-hover @click.native="onSearchNews">Применить</button-hover>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import moment from 'moment';
import AddTags from '@/components/News/AddTags';

export default {
  name: 'SearchFilterNews',
  components: { AddTags },

  data: () => ({
    updateTags: [],
    dateFrom: 'year',
    dateTo: 0,
    page: 1,
    itemPerPage: 5,
    author: '',
  }),

  computed: {
    ...mapGetters('global/search', ['getNewsQueryParams', 'searchText']),
  },

  // watch: {
  //   '$route.query.tags': {
  //     immediate: true,
  //     handler(value) {
  //       if (value) {
  //         this.updateTags = value.split(',');
  //       }
  //     },
  //   },
  // },

  mounted() {
    this.dateTo = moment().valueOf();
  },

  methods: {
    ...mapActions('global/search', ['searchNews']),

    onChangeTags(tags) {
      this.updateTags = tags;
      const dataTagsSplit = tags.map(tag => tag.name);
      this.$router.replace({
        query: {
          ...this.$route.query,
          tags: dataTagsSplit.join(','),
        },
      });
    },

    onSearchNews() {
      const dataTags = this.updateTags;
      const dataTagsSplit = dataTags.map(tag => tag.name);
      const dateFrom = this.dateFrom === 'null' ? 0 : moment().subtract(1, this.dateFrom).valueOf();
      let searchQuery = Object.assign({}, this.getNewsQueryParams, {
        dateFrom: Math.floor(dateFrom / 1000),
        dateTo: Math.floor(this.dateTo / 1000),
        author: this.author,
        tags: dataTagsSplit.join(','),
        text: this.searchText,
        withFriends: true,
      });
      this.searchNews(searchQuery);
    },
  },
};
</script>
