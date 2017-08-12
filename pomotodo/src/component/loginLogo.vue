<template>
    <div class="text-center logo">
        <router-link to="/">
            <img src="../assets/pomotodo_cn.png" alt="">
        </router-link>
        <slot></slot>
    </div>
</template>

<style>
.logo {
    margin-top: 60px;
}
</style>
