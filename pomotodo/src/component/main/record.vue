<template>
    <div class="row">
        <list-panel class="col">
            <timer slot="header"></timer>
            <ul slot="list">
                <li class="done-list" v-for="done in doneList">
                    <p class="list-title">{{done.date}}<span class="list-right">完成了{{done.doneNum}}个番茄</span></p>
                    <p class="list-content"><span class="list-left">{{done.duration}}</span>{{done.name}}</p>
                </li>
            </ul>
        </list-panel>
        <list-panel class="col">
            <input  slot="header" class="header-bar" placeholder="添加新任务" v-model="newTask"
                @keypress="submitNewTask"/>
            <ul slot="list">
                <li class="todo-list" v-for="todo in todoList">
                    <span class="circle"></span>{{todo.name}}
                    <span class="list-option">
                        <i>...</i>
                        <i v-html="icon.pin"></i>
                    </span>
                </li>
            </ul>
        </list-panel>
    </div>
</template>

<script>
import octicons from 'octicons'

import listPanel from './listPanel.vue'
import svgSize from '../../service/octicons'
import list from '../../api/list'
import timer from './timer.vue'


export default {
    components: {
        'list-panel': listPanel,
        'timer': timer
    },
    data() {
        return {
            icon: {
                pin: octicons.pin.toSVG(svgSize)
            },
            newTask: '',
            doneList: list.doneList,
            todoList: list.todoList
        }
    },

    methods: {
        submitNewTask: function(event) {
            if (event.keyCode === 13 && this.newTask) {
                this.todoList.push({name: this.newTask});
                this.newTask = '';
            }
        }
    }
}
</script>

<style lang="scss">
.list-right {
    float: right;
    color: #333;
    font-size: .8em;
}

.list-content {
    font-size: .9em;
    color: #333;
}

.list-left {
    margin-right: 1em;
    color: #999;
}

.done-list {
    margin: 1em 0;
}

.list-title {
    margin-bottom: .3em;
}

.todo-list {
    padding: .5em;
    font-size: .8rem;
    color: #333;
    border-bottom: 1px solid #ddd;

    .circle {margin-right: 1em;}
    &:hover {
        background-color: #f8f8f8;
        .list-option {
            display: inline-block;
        }
    }
}

.list-option {
    display: none;
    float: right;
}
</style>


