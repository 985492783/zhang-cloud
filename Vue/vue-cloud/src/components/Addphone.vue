<template>
  <div id="container" >
    <template>
      <van-nav-bar title="失物招领系统" :border=true :fixed=true :placeholder=true>
      </van-nav-bar>
    </template>
    <template v-if="isShow">

    </template>
    <div class="main" v-if="!isShow">
      <van-notice-bar
        left-icon="volume-o"
        text="如物品未放在具体位置或被带走，请输入正确的qq或vx以让失主找到你"
      />
      <van-form @submit="onSubmit">
        <van-field
          v-model="Property.name"
          name="物品名称"
          label="物品名称"
          placeholder="物品名称"
          :rules="[{ required: true, message: '请填写用物品名称' }]"
        />
        <van-field
          v-model="Property.info"
          name="物品信息"
          label="物品信息"
          placeholder="物品信息"
          :rules="[{ required: true, message: '请填写物品信息' }]"
        />
        <van-field
          v-model="Property.location"
          name="失物地点"
          label="失物地点"
          placeholder="失物地点"
          :rules="[{ required: true, message: '请填写失物地点' }]"
        />
        <van-field name="radio" label="联系方式">
          <template #input>
            <van-radio-group v-model="radio" direction="horizontal">
              <van-radio name="0" @click="isNeedConnect=false;">无</van-radio>
              <van-radio name="1" @click="isNeedConnect=true;isNeedOne=true;">qq</van-radio>
              <van-radio name="2" @click="isNeedConnect=true;isNeedOne=false;">wx</van-radio>
            </van-radio-group>
          </template>
        </van-field>
        <van-field
          v-if="isNeedConnect && isNeedOne"
          v-model="Property.qq"
          name="qq"
          label="qq"
          placeholder="qq"
          :rules="[{ message: '请填写qq' }]"
        />
        <van-field
          v-if="isNeedConnect && !isNeedOne"
          v-model="Property.wx"
          name="wx"
          label="wx"
          placeholder="wx"
          :rules="[{ message: '请填写qq' }]"
        />
        <van-field name="uploader" label="物品图片" :rules="[{ required: true, message: '请上传物品图片' }]">
          <template #input>
            <van-uploader v-model="uploader" :max-count="1" multiple accept="image/*"/>
          </template>
        </van-field>
        <div style="margin: 16px;">
          <van-button round block type="info">提交</van-button>
        </div>
      </van-form>
    </div>
    <Bottom :act="2"></Bottom>
    <van-overlay :show="cover">
      <van-loading type="spinner" style="top: 40%;left: 40%;" size="40px">上传中。。。</van-loading>
    </van-overlay>
  </div>
</template>

<script>
import Bottom from './Bottom'
import {Toast} from 'vant'

import axios from 'axios'
const requests = axios.create({baseURL: '/api'})

export default {
  name: 'Addphone',
  components: {
    Bottom: Bottom
  },
  data () {
    return {
      isShow: false,
      Property: {},
      radio: '0',
      isNeedConnect: false,
      isNeedOne: true,
      uploader: [],
      cover: false
    }
  },
  methods: {
    async onSubmit () {
      this.cover = true
      let img = new Image()
      img.src = this.uploader[0].content
      this.Property.image = this.compress(img, 0.2)
      let token = this.$session.get('token')
      let data = this.Property
      await requests.post('/user/upload', data, {headers: {'token': token}}).then((res) => {
        Toast.success('上传成功')
        location.reload()
      }, () => {
        Toast.fail('服务器超时')
      })
      this.cover = false
    },
    compress (img, size) {
      let canvas = document.createElement('canvas')
      let ctx = canvas.getContext('2d')
      let width = img.width
      let height = img.height
      canvas.width = width
      canvas.height = height
      ctx.fillStyle = '#fff'
      ctx.fillRect(0, 0, canvas.width, canvas.height)
      ctx.drawImage(img, 0, 0, width, height)
      let ndata = canvas.toDataURL('image/jpeg', size)
      return ndata
    }
  }
}
</script>

<style scoped>
.main{
  position: absolute;
  margin:10px;
  left: 0;
  top: 46px;
  right: 0;
  bottom: 50px;
  overflow-y: scroll;
}
</style>
