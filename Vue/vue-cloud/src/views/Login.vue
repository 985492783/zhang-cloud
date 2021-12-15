<template>
  <div class="body">
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/css/bootstrap.min.css'>
    <link rel='stylesheet' href='https://unicons.iconscout.com/release/v2.1.9/css/unicons.css'><link rel="stylesheet">
    <div class="section">
      <div class="container">
        <div class="row full-height justify-content-center">
          <div class="col-12 text-center align-self-center py-5">
            <div class="section pb-5 pt-5 pt-sm-2 text-center">
              <h6 class="mb-0 pb-3">
                <label class="selectText" for="reg-log">登录 </label>
                <label class="selectText" for="reg-log">注册</label>
              </h6>
              <input class="checkbox" type="checkbox" id="reg-log" name="reg-log"/>
              <label for="reg-log"></label>
              <div class="card-3d-wrap mx-auto">
                <div class="card-3d-wrapper">
                  <div class="card-front">
                    <div class="center-wrap">
                      <div class="section text-center">
                        <h4 class="mb-4 pb-3">登录</h4>
                        <div class="form-group">
                          <input v-model="username" type="name" name="logephone" class="form-style" placeholder="用户名" id="logephone" autocomplete="off">
                          <i class="input-icon uil uil-mobile-android"></i>
                        </div>
                        <div class="form-group mt-2">
                          <input v-model="password" type="password" name="logpass" class="form-style" placeholder="密码" autocomplete="off">
                          <i class="input-icon uil uil-lock-alt"></i>
                        </div>
                        <a @click="submit" class="btn mt-4">提交</a>
                        <p class="mb-0 mt-4 text-center">
                          <a class="link">忘记密码?</a>
                        </p>
                      </div>
                    </div>
                  </div>
                  <div class="card-back">
                    <div class="center-wrap">
                      <div class="section text-center">
                        <h4 class="mb-4 pb-3">注册</h4>
                        <div class="form-group">
                          <input v-model="newStdId" class="form-style" placeholder="用户名" id="logname" autocomplete="off">
                          <i class="input-icon uil uil-mobile-android"></i>
                        </div>
                        <div class="form-group mt-2">
                          <input v-model="newPwd" class="form-style" type="password" placeholder="密码" id="logpass" autocomplete="off">
                          <i class="input-icon uil uil-lock-alt"></i>
                        </div>
                        <div class="form-group mt-2">
                          <input v-model="newEmail"  name="logemail" class="form-style" placeholder="邮箱" autocomplete="off">
                          <i class="input-icon uil uil-credit-card"></i>
                        </div>

                        <div class="form-group mt-2">
                          <input v-model="emailCode" name="logpass" class="captcha" placeholder="验证码" autocomplete="off">
                          <i class="input-icon uil uil-code-branch"></i>
                          <van-button type="info" style="margin-top:2px;margin-bottom: 10px" :disabled=isDisabled @click='getCode'>{{nowTime}}</van-button>
                        </div>
                        <a @click="signUp" class="btn mt-4">提交</a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import {Toast} from 'vant'
const requests = axios.create({baseURL: '/api'})
export default {
  data () {
    return {
      username: '',
      password: '',
      Info: {},
      newEmail: '',
      newStdId: '',
      newPwd: '',
      emailCode: '',
      rawCode: '',
      newInfo: {},
      show: false,
      isDisabled: false,
      nowTime: '获取验证码',
      leaveTime: 0,
      isBlock: false
    }
  },
  mounted () {
    this.verify()
  },
  methods: {
    async submit () {
      if (this.isBlock) {
        return
      }
      this.isBlock = true
      this.Info.username = this.username
      this.Info.password = this.password
      if (this.username === '' || this.password === '') {
        Toast.fail('账号或密码不能为空')
        this.isBlock = false
        return
      }
      let data = this.Info
      await requests.post('/user/login', data).then((res) => {
        if (res.data.code === 200) {
          Toast.success('登录成功')
          this.$cookies.set('token', res.data.data.token)
          localStorage.setItem('token', res.data.data.token)
          this.$cookies.set('username', res.data.data.username)
          this.$router.push({ path: '/' })
        } else {
          Toast.fail(res.data.message)
        }
      }, () => {
        Toast.fail('接口异常')
      })
      this.isBlock = false
    },
    verify () {
      if (this.$cookies.get('token') !== null && this.$cookies.get('username') != null) {
        let token = this.$cookies.get('token')
        requests.get('/user/verify', {headers: {'token': token}}).then((res) => {
          if (res.data.code === 200) {
            this.$session.set('token', this.$cookies.get('token'))
            this.$session.set('username', this.$cookies.get('username'))
            this.$router.push({path: '/Dashboard'})
          }
        })
      }
    },
    async signUp () {
      if (this.isBlock) {
        return
      }
      this.isBlock = true
      this.newInfo.username = this.newStdId
      this.newInfo.password = this.newPwd
      this.newInfo.email = this.newEmail
      this.newInfo.token = this.emailCode
      if (this.newStdId === '' || this.newPwd === '' || this.email === '' || this.emailCode === '') {
        Toast.fail('账号、密码、邮箱或验证码不能为空')
        this.isBlock = false
        return
      }
      let date = this.newInfo
      await requests.post('/user/register', date).then((res) => {
        if (res.data.code === 200) {
          Toast.success('注册成功')
          location.reload()
        } else {
          Toast.fail(res.data.message)
        }
        this.isBlock = false
      }, () => {
        this.isBlock = false
      })
    },
    getCode () {
      if (this.newEmail === '') {
        Toast.fail('邮箱不能为空')
        return
      }
      this.isDisabled = true
      this.leaveTime = 60
      this.letFail()
      requests.get('/user/sendCode?email=' + this.newEmail).then((res) => {
        if (res.data.code === 200) {
          Toast.success('验证码发送成功，有效期5分钟')
        } else {
          Toast.fail(res.data.message)
        }
      }, () => {
        Toast.fail('接口异常')
      })
    },
    letFail () {
      if (this.leaveTime <= 0) {
        this.nowTime = '获取验证码'
        this.isDisabled = false
        this.leaveTime = 0
      } else {
        this.nowTime = this.leaveTime + 's后重试'
        this.leaveTime--
        setTimeout(this.letFail, 1000)
      }
    }
  }
}
</script>

<style scoped>
@import '../assets/css/login.css';
</style>
