import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'

import router from './router'
import store from './store/store'

import './component'

import App from './App.vue'

Vue.use(VueRouter);

new Vue({
  el: '#app',
  store,
  router,
  render: h => h(App)
});
