<template>
  <div id="container">
    <template>
      <van-nav-bar title="失物招领系统" :border=true :fixed=true :placeholder=true>
      </van-nav-bar>
    </template>
    <template>
      <div class="main">
        <a href="mqqwpa://im/chat?chat_type=wpa&uin=985492783&version=1&src_type=web&web_src=oicqzone.com">点此联系作者</a>
        <van-button type="primary" @click="test">测试</van-button>
        <div style="margin-top: 220px;">聊天室已经开放，更多内测功能尽请期待！</div>
        <van-grid :column-num="3" style="margin-top: 20px;">
          <van-grid-item icon="chat-o" text="聊天室" to="/Chat" />
          <van-grid-item icon="photo-o" text="文字" />
          <van-grid-item icon="photo-o" text="文字" />
          <van-grid-item icon="photo-o" text="文字" />
          <van-grid-item icon="photo-o" text="文字" />
          <van-grid-item icon="photo-o" text="文字" />
        </van-grid>
      </div>
    </template>
    <Bottom :act="3"></Bottom>
    <van-button type="primary" size="large" round color="red" style="top: 500px;" @click="logout">退出登录</van-button>
  </div>
</template>

<script>
import Bottom from './Bottom'
import {Toast} from 'vant'
import axios from 'axios'
const requests = axios.create({baseURL: '/api'})
export default {
  name: 'CenterPhone',
  components: {
    Bottom: Bottom
  },
  methods: {
    logout () {
      this.$cookies.remove('token')
      this.$cookies.remove('username')
      this.$session.clear()
      Toast('登出')
      this.$router.push({path: '/Login'})
    },
    test () {
      let data = {
        'userId': 2,
        'amount': '3.00',
        'subject': '快递代拿',
        'status': '已支付'
      }
      let token = localStorage.getItem('token')
      requests.post('/consumer/mq', data, {headers: {'token': token}}).then((res) => {
        if (res.data.code === 200) {
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
}
</style>
