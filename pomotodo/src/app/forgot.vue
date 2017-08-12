<template>
    <login-logo>
        <h1 class="font-size-title">重置密码</h1>
        <div v-show="!commit">
            <p class="error highlight" v-show="email.error">邮箱不能为空</p>
            <input type="text" class="block-input" placeholder="邮箱" 
                v-model="email.value" v-bind:class="{'input-error': email.error}" >
            <p class="login-tip">我们会向您的邮箱发送有关如何重置密码的信息。</p>
            <button class="button-theme" @click="reset">重置密码</button>
        </div>
        <div v-show="commit">
            <p class="login-tip font-bold">我们已向您发送密码重置邮件</p>
            <p class="login-tip">如果您在收件箱没有找到相关邮件，请检查垃圾箱</p>
        </div>

        <router-link class="tip highlight" to="/login">登录</router-link>
    </login-logo>
</template>

<script>
import '../style/login.css'
import validate from '../service/validate'

export default {
  data() {
      return {
        email: {
            value: '',
            error: false
        },
        commit: false
      }
  },
  methods: {
      reset: function(event) {
          if (validate.validateEmail(this.email.value)) {
              this.email.error = false;
              this.commit = true;
          } else {
              this.email.error = true;
          }
      } 
  }
}
</script>