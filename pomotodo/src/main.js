import Vue from 'vue'
import VueRouter from 'vue-router'

import router from './router'
import './component.js'

import App from './App.vue'

Vue.use(VueRouter);

new Vue({
  el: '#app',
  router,
  render: h => h(App)
});
