<template>
  <div id="container">
    <template>
      <van-nav-bar title="失物招领系统" :border=true :fixed=true :placeholder=true>
      </van-nav-bar>
    </template>
    <Bottom :act="1"></Bottom>
  </div>
</template>

<script>
import Bottom from './Bottom'
export default {
  name: 'SearchPhone',
  components: {
    Bottom: Bottom
  }
}
</script>

<style scoped>

</style>
