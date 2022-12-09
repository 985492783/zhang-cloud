<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import {Toast} from 'vant'
import {Response} from './assets/js/Test'
export default {
  name: 'App',
  destroyed () {
  },
  created () {
    this.initWs()
  },
  methods: {
    initWs () {
      let token = localStorage.getItem('token')
      this.websock = new WebSocket('ws://10.12.34.96:8011/api/message?token=' + token)
      this.websock.onopen = this.websocketonopen
      this.websock.onerror = this.websocketonerror
      this.websock.onmessage = this.websocketonmessage
      this.websock.onclose = this.websocketclose
    },
    closeWs () {
    },
    websocketonmessage (e) {
      let obj = JSON.parse(e.data)
      Response(this, obj)
    },
    websocketclose () {
    },
    websocketonopen () {
    },
    websocketonerror () {
      Toast.fail('WebSocket连接发生错误')
    }
  }
}
</script>

<style>
</style>
