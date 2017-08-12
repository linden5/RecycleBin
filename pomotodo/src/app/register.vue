<template>
    <login-logo>
        <h1 class="font-size-title">注册</h1>
        <input type="text" class="block-input" placeholder="名字">
        <input type="text" class="block-input" placeholder="邮箱">
        <input type="text" class="block-input" placeholder="密码">
        <button class="button-theme">注册</button>
        <p class="login-tip">点击注册，即表明您同意我们的 
            <a class="highlight" href="https://pomotodo.com/terms">服务条款</a> 和 
            <a class="highlight" href="https://pomotodo.com/privacy">隐私条款</a>
        </p>
        <p class="login-tip">已有账号？<router-link to="/login" class="highlight">登录</router-link></p>
    </login-logo>
</template>

<script>
import '../style/login.css'

export default {
  
}
</script>
