<template>
  <el-container class="main">
    <el-main>
      <div class="boxx">
        <el-select v-model="value" remote filterable reserve-keyword placeholder="请输入关键词" :remote-method="getMenu" :loading="isMenu">
          <el-option
            v-for="item in menu"
            :key="item.id"
            :label="item.file_name"
            :value="item.id">
          </el-option>
        </el-select>
        <el-button @click="getVideo" style="margin-left:10px;" :disabled="loading">播放</el-button>
        <span v-loading="loading">
          <audio  controls="controls" controlsList="nodownload" oncontextmenu="return false" :src="video">
          </audio>
        </span>
        <el-upload
          v-loading="isUpload"
          action="http://localhost/api/file/upload"
          class="upload"
          :limit="1"
          :on-change="add"
          :on-remove="add"
          ref="upload"
          :auto-upload="false"
          :file-list="fileList">
          <el-button slot="trigger" size="small" type="primary" :disabled="fileList.length >= 1">选取文件</el-button>
          <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload" :disabled="fileList.length < 1">上传到服务器</el-button>
        </el-upload>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import {Toast} from 'vant'
import axios from 'axios'
const requests = axios.create({baseURL: '/api'})
export default {
  name: 'Audio',
  data () {
    return {
      value: [],
      video: '',
      loading: false,
      menu: [],
      isMenu: false,
      fileList: [],
      isUpload: false
    }
  },
  methods: {
    getVideo () {
      if (this.value === '') {
        return
      }
      this.loading = true
      let token = this.$session.get('token')
      requests.get('/file/getVideo/' + this.value, {headers: {'token': token}}).then((res) => {
        if (res.data.code === 200) {
          this.video = res.data.data.file
        } else {
          Toast.fail(res.data.message)
        }
        this.loading = false
      }, (e) => {
        console.log(e)
        this.loading = false
      })
    },
    add (file, fileList) {
      this.fileList = fileList
    },
    submitUpload () {
      this.isUpload = true
      let source = {}
      let file = this.fileList[0]
      let self = this
      const reader = new FileReader()
      reader.onload = function () {
        source.file = reader.result
        source.file_name = file.name
        let token = self.$session.get('token')
        requests.post('/file/upload', source, {headers: {'token': token}}).then((res) => {
          if (res.data.code === 200) {
            Toast.success('上传成功')
            self.fileList = []
          } else {
            Toast.fail(res.data.message)
          }
          self.isUpload = false
        }, (e) => {
          console.log(e)
          self.isUpload = false
        })
      }
      reader.readAsDataURL(file.raw)
    },
    getMenu (e) {
      if (e === '') {
        return
      }
      this.isMenu = true
      let token = this.$session.get('token')
      requests.get('/file/getMenuByName/' + e, {headers: {'token': token}}).then((res) => {
        if (res.data.code === 200) {
          this.menu = res.data.data
        } else {
          Toast.fail(res.data.message)
        }
        this.isMenu = false
      }, (e) => {
        console.log(e)
        this.isMenu = false
      })
    }
  }
}
</script>

<style scoped>
.boxx{
  border: 1px solid #F0F8FF;
  position: absolute;
  top: 50px;
  left: 20px;
  right: 10px;
  bottom: 10px;
}
.main{
  position: absolute;
  top: 70px;
  left: 200px;
  right: 0;
  bottom: 0;
}
audio{
  margin-left: 20px;
  margin-bottom: -20px;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.demonstration {
  display: block;
  color: #8492a6;
  font-size: 14px;
  margin-bottom: 20px;
}
.upload{
  margin-top:40px;
}
</style>
