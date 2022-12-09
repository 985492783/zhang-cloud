<template>
  <div class="container">
    <template>
      <van-nav-bar title="聊天室" :border=true :fixed=true :placeholder=true>
        <template #left>
          <van-icon name="arrow-left" size="20" @click="back"/>
        </template>
      </van-nav-bar>
    </template>
    <div class="main" ref="main">
      <div v-for="item in list" :key="item.id" style="margin-bottom: 10px; margin-left:5px;">
        <div style="color:blue">{{item.nickname}}</div>
        <div class="text">{{item.text}}</div>
      </div>
    </div>
    <van-field
      v-model="value"
      center
      clearable
      label="文本"
      placeholder="请输入文本"
      label-width="30px"
      style="bottom: 5px;position: absolute"
    >
      <template #button>
        <van-button size="small" type="primary" @click="send">发送</van-button>
      </template>
    </van-field>
    <Verify ref="verify"></Verify>
  </div>
</template>

<script>
import {Toast} from 'vant'
import Verify from '../components/Verify'
import axios from 'axios'
const requests = axios.create({baseURL: '/api'})
export default {
  name: 'Chat',
  data () {
    return {
      value: '',
      list: []
    }
  },
  components: {
    Verify: Verify
  },
  created () {
    this.initWs()
  },
  mounted () {
    this.$refs.verify.verify()
  },
  destroyed () {
    this.closeWs()
  },
  methods: {
    initWs () {
      let token = this.$cookies.get('token')
      this.websock = new WebSocket('ws://10.12.34.96:9527/api/chat/1?token=' + token)
      this.websock.onopen = this.websocketonopen
      this.websock.onerror = this.websocketonerror
      this.websock.onmessage = this.websocketonmessage
      this.websock.onclose = this.websocketclose
    },
    closeWs () {
    },
    websocketonopen () {
      Toast.success('成功连接到聊天室')
    },
    websocketonerror () {
      Toast.fail('WebSocket连接发生错误')
    },
    websocketonmessage (e) {
      let json = JSON.parse(e.data)
      this.list.push(json)
      let div = this.$refs.main
      setTimeout(() => {
        div.scrollTop = div.scrollHeight
      }, 0)
    },
    websocketclose () {
      Toast.success('关闭聊天室')
    },
    send () {
      if (this.value === '') {
        Toast('不能为空')
        return
      }
      this.websock.send(this.value)
      Toast('发送成功')
      this.value = ''
    },
    back () {
      this.websock.close()
      this.$router.back()
    },
    test () {
      let data = {
        'userId': 2,
        'amount': '1.00',
        'subject': '商品',
        'status': '未支付'
      }
      let token = localStorage.getItem('token')
      requests.post('/consumer/mq', data, {headers: {'token': token}}).then((res) => {
        if (res.data.code === 200) {
          console.log(res.data)
        }
      })
    }
  }
}
</script>

<style scoped>
.main{
  position: absolute;
  margin:10px;
  left: 0;
  top: 46px;
  right: 0;
  bottom: 50px;
  overflow-y: scroll;
  background-color:#f5f7f9
}
.text{
  padding: 3px;
  word-break: break-all;
  word-wrap: break-word;
  border:1px solid black;
  margin-top:2px;
  border-radius:5px;
  width:80%
}
</style>
