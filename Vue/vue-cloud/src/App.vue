<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import {Notify, Toast} from 'vant'
export default {
  name: 'App',
  destroyed () {
    this.closeWs()
  },
  created () {
    this.initWs()
  },
  methods: {
    initWs () {
      let token = localStorage.getItem('token')
      this.websock = new WebSocket('ws://101.34.40.117:9527/api/chat/1?token=' + token)
      this.websock.onopen = this.websocketonopen
      this.websock.onerror = this.websocketonerror
      this.websock.onmessage = this.websocketonmessage
      this.websock.onclose = this.websocketclose
    },
    closeWs () {
    },
    websocketonmessage (e) {
      Notify({ type: 'success', message: '收到一条新的信息' })
    },
    websocketclose () {
      Toast.success('关闭聊天室')
    },
    websocketonopen () {
      Toast.success('成功连接到聊天室')
    },
    websocketonerror () {
      Toast.fail('WebSocket连接发生错误')
    }
  }
}
</script>

<style>
</style>
