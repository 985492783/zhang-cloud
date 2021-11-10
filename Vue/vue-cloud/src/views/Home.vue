<template>
  <el-container style="height:100%; border: 1px solid #eee">
    <DialogView ref="myDialog" @userBehavior="changeData"></DialogView>
    <Side></Side>
    <el-container>
      <Header></Header>
      <el-main>
        <el-table :data="tableData" style="width: 100%" height="100%" v-if="isShowConfirm" >
          <el-table-column prop="id" label="id" width="120">
          </el-table-column>
          <el-table-column prop="username" label="用户名" width="240">
          </el-table-column>
          <el-table-column prop="nickname" label="昵称" width="240">
          </el-table-column>
          <el-table-column prop="gmtCreated" label="创建时间" width="240">
          </el-table-column>
          <el-table-column prop="gmtModified" label="创建时间">
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="240">
            <template  slot-scope="scope">
              <el-button icon="el-icon-search" type="primary" @click="Search(scope.row.id)"></el-button>
              <el-button icon="el-icon-edit" type="primary"></el-button>
              <el-button icon="el-icon-delete" type="primary"></el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-container v-if="!isShowConfirm">
          <template>
            <el-button icon="el-icon-back" type="primary" style="height: 50px;font-size:24px; display:block;" @click="back"></el-button>
          </template>
          <table style="border: 1px solid black;margin-top: 15%;margin-left: 25%;">
            <tr>
              <th>昵称</th>
              <th>{{User.nickname}}</th>
            </tr>
            <tr>
              <th>头像</th>
              <th><el-image :src="User.url" style="height:150px;width:150px" onerror="onerror=null;src='https://img.51miz.com/Element/00/88/82/42/c21e019b_E888242_0f2360ce.png'"></el-image></th>
            </tr>
          </table>
        </el-container>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import axios from 'axios'
import Header from '../components/Header'
import Side from '../components/Side'
import DialogView from '../components/Form'
const requests = axios.create({baseURL: '/api'})
export default {
  name: 'Home',
  components: {
    Header: Header,
    Side: Side,
    DialogView: DialogView
  },
  data () {
    return {
      tableData: [],
      isShowConfirm: true,
      User: {}
    }
  },
  mounted: function () {
    this.getData()
  },
  methods: {
    getData () {
      let token = this.$session.get('token')
      if (token === undefined) {
        this.$refs.myDialog.alert('您尚未登录，请登录后重试', {operate: 'unLogin'})
        return
      }
      requests.get('/admin/getUser', { headers: { 'token': token } }).then((res) => {
        this.tableData = res.data.data
      }, () => {
        this.$refs.myDialog.alert('您已在另一地点登录，请重试', {operate: 'failLogin'})
      })
    },
    Search (e) {
      let token = this.$session.get('token')
      requests.get('/admin/user/' + e, { headers: { 'token': token } }).then((res) => {
        if (res.data.code === 200) {
          this.User = res.data.data
          this.isShowConfirm = false
        } else {
          this.$refs.myDialog.alert('查询失败', null)
        }
      }, () => {
        this.$refs.myDialog.alert('接口未开启', null)
      })
    },
    changeData (type, data) {
      if (data.operate === 'failLogin') {
        this.$session.clear()
      }
      this.$router.push({ path: '/' })
    },
    back () {
      this.getData()
      this.isShowConfirm = true
      this.User = {}
    }
  }
}
</script>

<style scoped>

.el-main {
  position: absolute;
  left: 200px;
  right: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;
}
table,
table tr,
table th {
  border: 1px solid #000000;
}
</style>
