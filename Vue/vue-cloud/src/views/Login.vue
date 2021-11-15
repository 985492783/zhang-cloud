<template>
  <div class="wrapper">
    <div class="container">
      <h1>Welcome</h1>
      <form class="form">
        <input type="text" placeholder="Username" v-model="username">
        <input type="password" placeholder="Password" v-model="password">
        <button type="button" id="login-button" @click="submit">Login</button>
      </form>
    </div>
    <DialogView ref="myDialog"></DialogView>
    <ul class="bg-bubbles">
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios'
import DialogView from '../components/Form'
const requests = axios.create({baseURL: '/api'})
export default {
  data () {
    return {
      username: '',
      password: '',
      Info: {}
    }
  },
  mounted () {
    this.verify()
  },
  methods: {
    async submit () {
      this.Info.username = this.username
      this.Info.password = this.password
      if (this.username === '' || this.password === '') {
        this.$refs.myDialog.alert('账号或密码不能为空', null)
        return
      }
      let data = this.Info
      await requests.post('/user/login', data).then((res) => {
        if (res.data.code !== 200) {
          this.$refs.myDialog.alert('密码错误', null)
          return
        }
        this.$session.set('token', res.data.data.token)
        this.$session.set('username', res.data.data.username)
        this.$cookies.set('token', res.data.data.token)
        this.$cookies.set('username', res.data.data.username)
        this.$router.push({ path: '/Dashboard' })
      }, () => {
        this.$refs.myDialog.alert('服务器超时，请稍后重试', null)
      })
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
    }
  },
  components: {
    DialogView: DialogView
  }
}
</script>

<style scoped>
@import '../assets/css/login.css';
</style>
