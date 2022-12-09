<template>
  <div>
    <van-nav-bar
      title="全部订单"
      left-text="返回"
      left-arrow
      border
      fixed
      @click-left="onClickLeft"
    />
    <div class="main">
      <div class="boxx" v-for="item of orderList" :key="item.id">
        <div style="display:inline-flex;">
          订单号：{{item.orderId}}<br/>
          订单信息：{{item.subject}}<br/>
          订单金额：{{item.amount}}
        </div>
        <van-icon name="more-o" size="36" @click="enter(item.orderId)"/>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
const requests = axios.create({baseURL: '/api'})
export default {
  name: 'OrderList',
  data () {
    return {
      orderList: []
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    onClickLeft () {
      this.$router.go(-1)
    },
    init () {
      let token = localStorage.getItem('token')
      requests.get('/consumer/orderList', {headers: {'token': token}}).then((res) => {
        if (res.data.code === 200) {
          this.orderList = res.data.data
        }
      })
    },
    enter (e) {
      console.log(e)
    }
  }
}
</script>

<style scoped>
.main{
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 46px;
  background: #F8F8FF;
}
.boxx{
  padding-left: 5px;
  padding-top: 5px;
  line-height: 25px;
  border-radius: 8px;
  margin: 30px 10px;
  height: 80px;
  border: 1px solid black;
  background: #F0F8FF;
}
.van-icon{
  padding-top: 23px;
  padding-left: 10px;
  padding-right: -10px;
  display: inline-flex;
  float: right;
  border: 1px solid black;
  height: 50px;
  width: 50px;
}
.van-icon:active{
  color: #1E90FF;
}
.van-icon:hover{
  cursor:pointer;
}
</style>
