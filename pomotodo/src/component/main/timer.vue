<template>
    <div class="header-bar progress-bar" @click="startTomato">
        <p class="progress-text">{{tomatoStart ? tomatoLeft : '开始番茄'}}</p>
        <div class="progress" v-show="tomatoStart"
            v-bind:style="'width: ' + tomatoPercent + '%'"></div>
        <span class="circle icon-close" v-show="tomatoStart" @click.stop="stopTomato">X</span>
    </div>
</template>
<script>
export default {
    computed: {
        tomatoLeft() {
            return this.$store.getters.tomatoLeft;
        },
        tomatoStart() {
            return this.$store.getters.tomatoStart;
        },
        tomatoPercent() {
            return this.$store.getters.tomatoPercent;
        },
        tomatoTimeout() {
            return this.$store.getters.tomatoTimeout;
        }
    },
    methods: {
        startTomato: function() {
            console.log(this);
            if (this.$store.state.started) {
                return;
            }
            this.$store.commit('tomatoStart')
            var timeId = setInterval(() => {
                if (!this.tomatoTimeout) {
                    this.$store.commit('tomatoTick')
                } else {
                    this.$store.commit('tomatoStop')
                }
            }, 1000);
            this.$store.commit('tomatoTimer', timeId);
        },
        stopTomato: function() {
            this.$store.commit('tomatoStop');
        }
    }
}
</script>
<style>
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
</style>


