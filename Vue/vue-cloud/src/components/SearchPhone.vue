<template>
  <div id="container">
    <template>
      <van-nav-bar title="失物招领系统" :border=true :fixed=true :placeholder=true>
        <template #left>
          <van-icon v-if="!isShow" name="arrow-left" size="20" @click="back"/>
        </template>
      </van-nav-bar>
      <form action="/">
        <van-search
          shape="round"
          v-model="value"
          show-action
          placeholder="请输入搜索关键词"
          @search="onSearch"
          @cancel="onCancel"
        />
      </form>
    </template>
    <template  v-if="isShow">
      <van-list style="margin:4px;" class="main">
        <van-cell arrow-direction="" v-for="item in list" is-link :key="item.ddd" :title="item.name" :value="item.info" :label="item.gmtCreated" @click="showItem(item.id)"/>
      </van-list>
    </template>
    <div class="main" v-if="!isShow">
      <van-skeleton title avatar :row="3" :loading="loading" style="margin:20px;">
        <div style="margin:12px">
          <div>
            <van-cell title="物品名字" :value=UserInfo.name size="large" label="该物品的学名" />
            <van-cell title="物品信息" :value=UserInfo.info size="large" label="该物品的关键信息" />
            <van-cell title="物品位置" :value=UserInfo.location size="large" label="该物品所在位置" />
            <van-cell title="联系人" :value=UserInfo.connection size="large" label="捡到人的联系方式" />
          </div>
          <van-divider
            :style="{ color: '#1989fa', borderColor: '#1989fa', padding: '0 16px' }"
          >
            图片信息
          </van-divider>
          <div>
            <img v-lazy="UserInfo.image" :key="UserInfo.image" style="width: 100%;" @click="showImage(UserInfo.image)" />
          </div>
        </div>
      </van-skeleton>
    </div>
    <Bottom :act="1"></Bottom>
    <Verify ref="verify"></Verify>
  </div>
</template>

<script>
import Verify from './Verify'
import Bottom from './Bottom'
import { Toast, ImagePreview } from 'vant'
import axios from 'axios'
const requests = axios.create({baseURL: '/api'})
export default {
  name: 'SearchPhone',
  components: {
    Bottom: Bottom,
    Verify: Verify
  },
  data () {
    return {
      value: '',
      list: [],
      isShow: true,
      UserInfo: {},
      loading: true
    }
  },
  mounted () {
    this.$refs.verify.verify()
  },
  methods: {
    onSearch (val) {
      if (val === '') {
        Toast('不能为空')
      }
      let token = this.$session.get('token')
      requests.get('/user/searchLost?info=' + val, {headers: {'token': token}}).then((res) => {
        if (res.data.data.length === 0) {
          Toast('搜索为空')
          this.list = []
        } else {
          Toast('搜索成功')
          this.list = res.data.data
        }
      })
    },
    onCancel () {
      Toast('取消')
      this.list = []
      this.isShow = true
      this.UserInfo = {}
    },
    show () {
      this.isShow = !this.isShow
    },
    showItem (event) {
      this.show()
      this.loading = true
      let token = this.$session.get('token')
      requests.get('/user/getLostById?id=' + event, {headers: {'token': token}}).then((res) => {
        this.UserInfo = res.data.data
        if (this.UserInfo.qq !== null) {
          this.UserInfo.connection = 'qq：' + this.UserInfo.qq
        } else if (this.UserInfo.wx !== null) {
          this.UserInfo.connection = 'wx：' + this.UserInfo.wx
        } else {
          this.UserInfo.connection = '无'
        }
        this.loading = false
      })
    },
    showImage (e) {
      ImagePreview([e])
    },
    back () {
      this.show()
      this.UserInfo = {}
    }
  }
}
</script>

<style scoped>
.main{
  position: absolute;
  margin:10px;
  left: 0;
  top: 99px;
  right: 0;
  bottom: 50px;
  overflow-y: scroll;
}
</style>
