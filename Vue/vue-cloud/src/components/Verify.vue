<template>
  <div style="display:none"></div>
</template>

<script>
import axios from 'axios'
import { Dialog } from 'vant'
const requests = axios.create({baseURL: '/api'})
export default {
  name: 'Verify',
  components: {
    Dialog: Dialog.Component
  },
  methods: {
    async verify () {
      if (this.$cookies.get('token') !== null && this.$cookies.get('username') != null) {
        let token = this.$cookies.get('token')
        await requests.get('/user/verify', {headers: {'token': token}}).then((res) => {
          if (res.data.code === 200) {
            this.$session.set('token', this.$cookies.get('token'))
            this.$session.set('username', this.$cookies.get('username'))
          } else {
            this.alert('您的登录已失效或在别处登录，请重新登录后重试')
          }
        })
      } else {
        this.$cookies.remove('token')
        this.$cookies.remove('username')
        this.$session.clear()
        this.$router.push({path: '/Login'})
      }
    },
    alert (e) {
      Dialog.alert({
        title: '通知',
        message: e
      }).then(() => {
        this.$cookies.remove('token')
        this.$cookies.remove('username')
        this.$session.clear()
        this.$router.push({path: '/Login'})
      })
    }
  }
}
</script>

<style scoped>

</style>
