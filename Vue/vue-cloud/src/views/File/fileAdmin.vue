<template>
  <el-container>
    <Side></Side>
    <el-header style="text-align: right; font-size: 12px">
      <el-dropdown @command="handleCommand">
        <i class="el-icon-setting" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>查看</el-dropdown-item>
          <el-dropdown-item>新增</el-dropdown-item>
          <el-dropdown-item command="logout">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span>{{this.$cookies.get('mobile')}}</span>
    </el-header>
    <router-view></router-view>
  </el-container>
</template>

<script>
import Side from '../file/Side'
export default {
  name: 'fileAdmin',
  components: {
    Side: Side
  },
  methods: {
    handleCommand (command) {
      if (command === 'logout') {
        localStorage.removeItem('token')
        this.$cookies.remove('token')
        this.$cookies.remove('mobile')
        this.$router.push('/')
      }
    }
  }
}
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

</style>
