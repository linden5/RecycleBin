<template>
    <div>
        <div class="warning text-center" v-show="mismatch">系统时间与服务器不匹配
            <span class="fix">重新检查</span>
            <span class="fix" @click="mismatch = false">忽略</span>
            <span class="fix">如何修复</span>
        </div>
        <div class="main-container">
            <header class="main-header">
                <h3 class="inline">番茄土豆</h3>
                <h5 class="inline upgrade">升级</h5>
                <span class="menu">
                    <span v-html="icon.update"></span>
                    <span>linden5</span>
                    <span v-html="icon.chevronDown"></span>
                </span>    
            </header>
            <div class="main-body">
                <record></record>
                <section class="row status">
                    <stat-tab title="统计" sub="一周累计" stats="5"></stat-tab>
                    <stat-tab title="目标" sub="今日目标" stats="5/15"></stat-tab>
                    <stat-tab title="番茄历史" sub="累计完成番茄" stats="2345"></stat-tab>
                    <stat-tab title="任务历史" sub="累计完成任务" stats="435"></stat-tab>
                </section>
            </div>
            <main-footer></main-footer> 
        </div>
    </div>
</template>

<script>
import octicons from 'octicons'
import {svgSize} from '../service/octicons'
import '../style/grid.css'

import record from '../component/main/record.vue'
import footer from '../component/main/footer.vue'
import statTab from '../component/main/statTab.vue'

export default {
    components: {
        'main-footer': footer,
        'stat-tab': statTab,
        'record': record
    },
    data() {
        return {
            mismatch: true,
            icon: {
                update: octicons.sync.toSVG(svgSize),
                chevronDown: octicons['chevron-down'].toSVG(svgSize)
            }
        }
    }
}
</script>
<style lang="scss">
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

.main-container {
    padding: 0 2em;
    color: #999;
    background: #fdfdfd;
}

.main-header {
    padding: 10px 0;
    border-bottom: 1px solid #ddd;
}

.main-body {
    padding: 1em 0;
}

.menu {
    float: right;
    line-height: 2em;
    color: #999;
    font-size: .9em;
}

select {
    color: inherit;
}

.circle {
    position: relative;
    top: 2px;
    box-sizing: border-box;
    display: inline-block;
    width: 1rem;
    height: 1rem;
    border: 1px solid #999;
    border-radius: 50%;
}

.status {
    margin: 2em 0;
}

input.header-bar {
    width: 100%;
    font-size: 1rem;
}

</style>

