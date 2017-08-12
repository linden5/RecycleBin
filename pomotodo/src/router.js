import VueRouter from 'vue-router'

import login from './app/login.vue'
import demo from './app/demo.vue'
import forgot from './app/forgot.vue'
import register from './app/register.vue'
import main from './app/main.vue'

const routes = [
  {path: '/login', component: login},
  {path: '/', component: demo},
  {path: '/forgot', component: forgot},
  {path: '/register', component: register},
  {path: '/main', component: main},
];

export default new VueRouter({
  routes
});