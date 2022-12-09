<template>
  <el-container class="main">
    <el-main>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :key="item.id" v-for="item in fileList"><a @click="goBack(item)">{{item.name}}</a></el-breadcrumb-item>
      </el-breadcrumb>
      <div class="boxx">
        <div :key="item.id" v-for="item in tableData" class="fileCell">
          <i class="icon iconfont" :class="[item.suffix===undefined?'icon-wenjianjia':'icon-'+item.suffix]"></i>
          <span @click="zip(item)">{{item.name + (item.suffix===undefined?'':'.'+item.suffix)}}</span>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import {Toast} from 'vant'
import axios from 'axios'
const requests = axios.create({baseURL: '/api'})
export default {
  name: 'File',
  data () {
    return {
      tableData: [],
      fileList: [{name: 'root', id: 0}]
    }
  },
  mounted () {
    this.getData()
  },
  methods: {
    zip (e) {
      if (e.type === 'DIRECTORY') {
        let token = this.$session.get('token')
        requests.get('/file/getById/' + e.id, {headers: {'token': token}}).then((res) => {
          if (res.data.code === 200) {
            this.tableData = res.data.data
            this.fileList.push({name: e.name, id: e.id})
          } else {
            Toast.fail(res.data.message)
          }
        }, (e) => {
          console.log(e)
        })
      }
    },
    goBack (e) {
      let i = 0
      for (; i < this.fileList.length; i++) {
        if (this.fileList[i].id === e.id) {
          break
        }
      }
      let token = this.$session.get('token')
      requests.get('/file/getById/' + e.id, {headers: {'token': token}}).then((res) => {
        if (res.data.code === 200) {
          this.tableData = res.data.data
          this.fileList.splice(i + 1)
        } else {
          Toast.fail(res.data.message)
        }
      }, (e) => {
        console.log(e)
      })
    },
    getData () {
      let token = this.$session.get('token')
      requests.get('/file/test', {headers: {'token': token}}).then((res) => {
        if (res.data.code === 200) {
          this.tableData = res.data.data
        } else {
          Toast.fail(res.data.message)
        }
      }, (e) => {
        console.log(e)
      })
    }
  }
}
</script>
<style scoped>
@import '../../assets/icon/iconfont.css';
.main{
  position: absolute;
  top: 70px;
  left: 200px;
  right: 0;
  bottom: 0;
}
.el-breadcrumb{
  margin-bottom: 10px;
}
.fileCell{
  padding: 10px;
  background-color: #F0F8FF;
  margin-bottom: 3px;
  user-select: none;
}
.fileCell:hover{
  background-color: #ADD8E6;
}

.fileCell > span:hover{
  cursor:pointer;
  text-decoration:underline;
  color: blue;
}
.boxx{
  border: 1px solid #F0F8FF;
  position: absolute;
  top: 50px;
  left: 20px;
  right: 10px;
  bottom: 10px;
}
</style>
