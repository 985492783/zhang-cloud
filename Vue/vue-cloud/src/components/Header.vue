<template>
  <el-header style="text-align: right; font-size: 24px">
    <el-dropdown>
      <i class="el-icon-setting" style="margin-right: 15px; font-size: 24px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>个人信息</el-dropdown-item>
        <el-dropdown-item>控制面板</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <span>{{username}}</span>
  </el-header>
</template>

<script>
export default {
  name: 'Header',
  data () {
    return {
      username: this.$session.get('username')
    }
  },
  methods: {
    logout () {
      this.$session.clear()
      this.$router.push({ path: '/Login' })
    }
  }
}
</script>

<style scoped>
.el-header {
  padding-top: 10px;
  width: 100%;
  height: 60px;
}
</style>
