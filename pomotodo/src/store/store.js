import Vuex from 'vuex'
import Vue from 'vue'

Vue.use(Vuex);

var store = new Vuex.Store({
    state: {
        started: false,
        elapsed: 0,
        time: 25 * 60,
        break: 5 * 60,
        timeId: null
    },
    getters: {
        tomatoLeft(state) {
            var leftTime = state.time - state.elapsed;
            var minute = Math.floor(leftTime / 60);
            var second = leftTime % 60;
            return (minute > 9 ? '' : '0') + minute + ':' + (second > 9 ? '' : '0') + second;
        },
        tomatoStart(state) {
            return state.timeId;
        },
        tomatoTimeout(state) {
            return state.elapsed >= state.time;
        },
        tomatoPercent(state) {
            return state.elapsed/state.time * 100
        }
    },
    mutations: {
        tomatoStart(state) {
            state.started = true;
        },
        tomatoTick(state) {
            state.elapsed++;
        },
        tomatoStop(state) {
            state.started = false;
            state.elapsed = 0;
            clearInterval(state.timeId);
            state.timeId = null;
        },
        tomatoTimer(state, timeId) {
            state.timeId = timeId;
        }
    }
})

export default store;