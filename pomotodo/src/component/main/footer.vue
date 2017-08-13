<template>
    <footer class="font-size-footer">© 番茄土豆 
        <select>
            <option>简体中文</option>
            <option>繁体中文</option>
            <option>日本語</option>
            <option>English</option>
        </select>
        <span class="circle">
            <span class="half-circle"></span>
        </span>
        <span class="footer-right">
            <span>高级版</span>
            <span>应用程序</span>
            <span>帮助</span>
            <span>开发者</span>
            <span>媒体报道</span>
            <span>服务条款</span>
        </span>
    </footer>
</template>

<style>
.footer-right {
    float: right;
}

.font-size-footer {
    padding: 1em 0;
    font-size: .9em;
}

.half-circle {
    position: absolute;
    box-sizing: border-box;
    display: inline-block;
    height: 1em;
    width: .5em;
    border: 1px solid #999;
    background: #999;
    border-radius: .5em 0 0 .5em;
}
</style>
