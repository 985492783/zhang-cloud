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
        <van-cell arrow-direction="" v-for="item in list" is-link :key="item.ddd" :title="item.name" :value="item.info" @click="showItem(item.id)"/>
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
            <img v-lazy="UserInfo.image" :key="UserInfo.image" style="width: 100%;"/>
          </div>
        </div>
      </van-skeleton>
    </div>
    <Bottom :act="0"></Bottom>
  </div>
</template>

<script>
import Bottom from './Bottom'
import axios from 'axios'
const requests = axios.create({baseURL: '/api'})
export default {
  components: {Bottom},
  data () {
    return {
      list: [],
      components: {
        Bottom: Bottom
      },
      isShow: true,
      UserInfo: {},
      loading: false,
      activeName: '0'
    }
  },
  mounted () {
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
    getData () {
      let token = this.$session.get('token')
      requests.get('/user/getAllLostProperty', {headers: {'token': token}}).then((res) => {
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
