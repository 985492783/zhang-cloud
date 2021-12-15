<template>
  <div id="container" >
    <template>
      <van-nav-bar title="失物招领系统" :border=true :fixed=true :placeholder=true>
        <template #left>
          <van-icon v-if="!isShow" name="arrow-left" size="20" @click="back"/>
        </template>
      </van-nav-bar>
    </template>
    <template v-if="isShow">
      <van-list style="margin:4px;" class="main">
        <van-cell arrow-direction="" v-for="item in list" is-link :key="item.ddd"  :value="item.info" :label="item.gmtCreated" @click="showItem(item.id)">
          <template #title>
            <span class="custom-title">{{item.name}}</span>
            <van-tag :type="item.type==='丢失'?'primary':item.type==='捡到'?'danger':'warning'">{{item.type==='丢失'?'丢失':item.type==='捡到'?'捡到':'其他'}}</van-tag>
          </template>
        </van-cell>
        <van-pagination
          v-model="currentPage"
          :page-count=pageSize
          :total-items=list.length
          :show-page-size="3"
          force-ellipses
          :items-per-page="10"
          @change="changePage"
        />
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
    <Bottom :act="0"></Bottom>
    <Verify ref="verify"></Verify>
  </div>
</template>

<script>
import Bottom from './Bottom'
import Verify from './Verify'
import axios from 'axios'
import { ImagePreview } from 'vant'
const requests = axios.create({baseURL: '/api'})
export default {
  components: {
    Bottom: Bottom,
    Verify: Verify
  },
  data () {
    return {
      list: [],
      components: {
        Bottom: Bottom
      },
      isShow: true,
      UserInfo: {},
      loading: false,
      activeName: '0',
      currentPage: 1,
      pageSize: 10
    }
  },
  mounted () {
    this.$refs.verify.verify()
    this.getPage()
    this.getData()
  },
  methods: {
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
    back () {
      this.show()
      this.UserInfo = {}
    },
    async getData () {
      let token = this.$cookies.get('token')
      await requests.get('/user/getLostProperty?pageNo=1', {headers: {'token': token}}).then((res) => {
        this.list = res.data.data
      })
    },
    showImage (e) {
      ImagePreview([e])
    },
    async getPage () {
      let token = this.$cookies.get('token')
      await requests.get('/user/getPageNumber', {headers: {'token': token}}).then((res) => {
        this.pageSize = res.data.data
      }, () => {
        this.pageSize = 100
      })
    },
    changePage () {
      let token = this.$cookies.get('token')
      requests.get('/user/getLostProperty?pageNo=' + this.currentPage, {headers: {'token': token}}).then((res) => {
        this.list = res.data.data
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
