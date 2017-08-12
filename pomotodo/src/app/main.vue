<template>
    <div>
        <div class="warning text-center">系统时间与服务器不匹配
            <span class="fix">重新检查</span>
            <span class="fix">忽略</span>
            <span class="fix">如何修复</span>
        </div>
        <header class="main-header">
            <h3 class="inline">番茄土豆</h3>
            <h5 class="inline upgrade">升级</h5>
            <span class="menu">
                <span v-html="icon.update"></span>
                <span>linden5</span>
                <span v-html="icon.chevronDown"></span>
            </span>    
        </header>
        <div>{{msg}}</div>
        <footer></footer>    
    </div>
</template>

<script>
import octicons from 'octicons'
import 'octicons-style'

export default {
    data() {
        return {
            msg: 'main page',
            icon: {
                update: octicons.sync.toSVG({'width': 20, 'height': 20}),
                chevronDown: octicons['chevron-down'].toSVG({'width': 20, 'height': 20})
            }
        }
    }
}
</script>

<style>
.warning {
    padding: .5em;
    font-size: .9em;
    background: #eee;
}

.fix {
    margin: .5em;
    color: blue;
    cursor: pointer;
}

.upgrade {
    color: green;
}

.main-header {
    margin: 0 2em;
    padding: 10px 0;
    border-bottom: 1px solid #ddd;
}

.menu {
    float: right;
    line-height: 0;
    color: #999;
}
</style>

