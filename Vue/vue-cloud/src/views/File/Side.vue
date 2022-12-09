<template>
  <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
    <el-menu :default-openeds="['1']" @select="change" :default-active="this.$route.path" router>
      <el-submenu index="1">
        <template slot="title"><i class="el-icon-message"></i>管理</template>
        <el-menu-item-group>
          <template slot="title">收藏夹</template>
          <el-menu-item index="/file/admin">管理文件</el-menu-item>
          <el-menu-item index="/file/audio">控制中心</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="商户管理">
          <el-menu-item index="/file/control">管理台</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="1-4">
          <template slot="title">选项4</template>
          <el-menu-item index="1-4-1">选项4-1</el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-submenu index="2">
        <template slot="title"><i class="el-icon-menu"></i>导航二</template>
        <el-menu-item-group>
          <template slot="title">分组一</template>
          <el-menu-item index="2-1">选项1</el-menu-item>
          <el-menu-item index="2-2">选项2</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="分组2">
          <el-menu-item index="2-3">选项3</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="2-4">
          <template slot="title">选项4</template>
          <el-menu-item index="2-4-1">选项4-1</el-menu-item>
        </el-submenu>
      </el-submenu>
    </el-menu>
  </el-aside>
</template>

<script>
export default {
  name: 'Side',
  methods: {
    change () {

    }
  }
}
</script>

<style scoped>
.el-aside {
  top: 70px;
  position: absolute;
  color: #333;
  bottom: 0;
}
</style>
