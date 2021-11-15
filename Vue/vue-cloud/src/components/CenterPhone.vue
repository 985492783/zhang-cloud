<template>
  <div id="container">
    <template>
      <van-nav-bar title="失物招领系统" :border=true :fixed=true :placeholder=true>
      </van-nav-bar>
    </template>
    <template>
    </template>
    <Bottom :act="3"></Bottom>
  </div>
</template>

<script>
import Bottom from './Bottom'
export default {
  name: 'CenterPhone',
  components: {
    Bottom: Bottom
  }
}
</script>

<style scoped>

</style>
