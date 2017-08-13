<template>
    <div class="row">
        <list-panel class="col">
            <div class="header-bar progress-bar" slot="header" @click="startTomato">
                <p class="progress-text">{{tomato.started ? tomato.left : '开始番茄'}}</p>
                <div class="progress" v-show="tomato.started"
                    v-bind:style="'width: ' + (tomato.elapsed/tomato.time * 100) + '%'"></div>
                <span class="circle icon-close" v-show="tomato.started" @click.stop="stopTomato">X</span>
            </div>
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

export default {
    components: {
        'list-panel': listPanel
    },
    data() {
        return {
            icon: {
                pin: octicons.pin.toSVG(svgSize)
            },
            tomato: {
                started: false,
                elapsed: 0,
                time: 25 * 60,
                break: 5 * 60,
                timeId: null,
                left: ''
            },
            newTask: '',
            doneList: [
                {
                    date: '8月13日',
                    doneNum: 2,
                    duration: '09:25-10:31',
                    name: '仿番茄土豆',
                    active: false
                },
                {
                    date: '8月12日',
                    doneNum: 1,
                    duration: '19:25-20:31',
                    name: '仿番茄土豆x2',
                    active: false
                }
            ],
            todoList: [
                {
                    date: '8月13日',
                    doneNum: 2,
                    duration: '09:25-10:31',
                    name: '仿番茄土豆'
                },
                {
                    date: '8月12日',
                    doneNum: 1,
                    duration: '19:25-20:31',
                    name: '仿番茄土豆x2'
                }
            ]
        }
    },
    methods: {
        submitNewTask: function(event) {
            if (event.keyCode === 13 && this.newTask) {
                this.todoList.push({name: this.newTask});
                this.newTask = '';
            }
        },
        startTomato: function() {
            console.log(this);
            if (this.tomato.started) {
                return;
            }
            this.tomato.started = true;
            this.tomato.left = '25:00';
            this.tomato.timeId = setInterval(() => {
                if (this.tomato.elapsed < this.tomato.time) {
                    this.tomato.elapsed++;
                    var leftTime = this.tomato.time - this.tomato.elapsed;
                    var minute = Math.floor(leftTime / 60);
                    var second = leftTime % 60;
                    this.tomato.left = (minute > 9 ? '' : '0') + minute + ':' + (second > 9 ? '' : '0') + second;
                } else {
                    clearInterval(this.tomato.timeId);
                    this.stopTomato();
                }
            }, 1000);
        },
        stopTomato: function() {
            this.tomato.started = false;
            this.tomato.elapsed = 0;
            clearInterval(this.tomato.timeId);
        }
    }
}
</script>

<style lang="scss">
.header-bar {
    position: relative;
    height: 2.5rem;
    text-align: center;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.progress-bar {
    background:#f8f8f8;
}

.icon-close {
    position: absolute;
    top: -.5em;
    right: -.5em;
    font-family: Tahoma;
    line-height: 1.1em;
    font-size: .8rem;
    background: white;
}

.progress {
    height: 100%;
    width: 0;
    background:#efefef;
    background-image: linear-gradient(to bottom,#F8F8F8 0,#EEE 100%);
    border-right: 1px solid #ddd;
}

.progress-text {
    position: absolute;
    width: 100%;
    height: 100%;
    line-height: 2.5em;
    font-size: 1rem;
}

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

input.header-bar {
    width: 100%;
    font-size: 1rem;
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


