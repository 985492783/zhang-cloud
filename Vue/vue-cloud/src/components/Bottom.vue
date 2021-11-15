<template>
  <van-tabbar v-model="active" style="border:2px solid #f5f7f9" @change="change">
    <van-tabbar-item icon="home-o">失物大厅</van-tabbar-item>
    <van-tabbar-item icon="search">关键词检索</van-tabbar-item>
    <van-tabbar-item icon="plus">提交失物</van-tabbar-item>
    <van-tabbar-item icon="setting-o">标签</van-tabbar-item>
  </van-tabbar>
</template>

<script>
export default {
  name: 'Bottom',
  props: ['act'],
  data () {
    return {
      active: this.act
    }
  },
  methods: {
    change () {
      if (this.active === 0) {
        this.$router.push({path: '/Dashboard'})
      } else if (this.active === 2) {
        this.$router.push({path: '/Add'})
      } else if (this.active === 1) {
        this.$router.push({path: '/Search'})
      } else {
        this.$router.push({path: '/Center'})
      }
    }
  }
}
</script>

<style scoped>

</style>
