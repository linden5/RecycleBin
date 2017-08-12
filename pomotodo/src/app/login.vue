<template>
    <login-logo>
        <h1 class="font-size-title">登录</h1>
        <p class="error highlight" v-show="email.error">{{email.msg}}</p>
        <input type="text" class="block-input" placeholder="邮箱"
            v-model="email.value"
            v-bind:class="{'input-error': email.error}">
        <p class="error highlight" v-show="password.error">{{password.msg}}</p>
        <input type="text" class="block-input" placeholder="密码"
            v-model="password.value"
            v-bind:class="{'input-error': password.error}">
        <button class="button-theme" @click="login">登录</button>
        <router-link to="/forgot" class="tip highlight">忘记密码?</router-link>
        <router-link to="/register" class="tip highlight">创建账户</router-link>
    </login-logo>
</template>

<script>
import '../style/login.css'
import validate from '../service/validate'
import router from '../router'

export default {
  name: 'pomotodo',
  data() {
      return {
          email: {
              value: '',
              error: false,
              msg: ''
          },
          password: {
              value: '',
              error: false,
              msg: ''
          }
      }
  },
  methods: {
      login: function(event) {
        if (!this.email.value) {
            this.email.error = true;
            this.email.msg = '邮箱不能为空';
        } else if (!validate.validateEmail(this.email.value)) {
            this.email.error = true;
            this.email.msg = '邮箱格式错误';
        } else if (!this.password.value) {
            this.email.error = false;
            this.password.error = true;
            this.password.msg = '密码不能为空';
        } else if (this.email.value !== 'lyx@lyx.com') {
            this.email.error = true;
            this.email.msg = '账号不存在';
            this.password.error = false;
        } else if (this.password.value !== '123456') {
            this.email.error = false;
            this.password.msg = '密码错误';
            this.password.error = true;
        } else {
            this.email.error = false;
            this.password.error = false;
            router.push({path: '/main'});
        }
      }
  }
}
</script>