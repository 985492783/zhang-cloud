<template>
  <div>
    <van-nav-bar
      title="订单信息"
      left-text="完成"
      border
      fixed
      @click-left="onClickLeft"
    />
    <div class="main">
      <div class="top">
        <div id="mm">下单成功</div>
        <div id="mm2">订单号：{{this.order.orderId}}</div>
      </div>
      <div class="bottom">
        <div id="amount">价格：{{this.order.amount}}￥</div>
        <div id="subject">商品类型：{{this.order.subject}}</div>
        <div id="payer">支付信息-付款方：{{isNullReturn(this.order.payer)}}</div>
        <div id="payee">支付信息-收款方：{{isNullReturn(this.order.payee)}}</div>
        <div id="status">支付状态：{{this.order.status}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'orderFinish',
  data () {
    return {
      order: {}
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      this.order = this.$route.params.data
      if (this.order === undefined) {
        this.$router.go(-1)
      }
    },
    onClickLeft () {
      this.$router.go(-1)
    },
    isNullReturn (e) {
      if (e === null || e === undefined) {
        return 'null'
      }
      return e
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
}
.top{
  height: 30%;
  background: #1E90FF;
}
.bottom{
  height: 70%;
  background: #F5F5F5;
}
#mm{
  color:#F8F8FF;
  font-size: 36px;
  text-align: center;
  padding-top: 40px;
  font-family:"DFKai-SB","Arial",serif;
  font-weight: bold;
}
#mm2{
  color: #E6E6FA;
  font-size: 20px;
  padding-top: 75px;
  padding-right: 5px;
  text-align: right;
  font-family:"KaiTi","KaiTi",serif;
  font-weight: bold;
}
.bottom > * {
  padding:20px 10px 0 10px;
  font-size: 26px;
  font-family:"FangSong","KaiTi",serif;
  font-weight: bold;
}
</style>
