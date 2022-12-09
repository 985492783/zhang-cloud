// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vuesession from 'vue-session'
import VueCookies from 'vue-cookies'
import ElementUI from 'element-ui'
import Vant, { Lazyload, PullRefresh } from 'vant'
import 'vant/lib/index.css'
import 'element-ui/lib/theme-chalk/index.css'
import VeRing from 'v-charts/lib/ring.common'// 环形图
import {VeLine} from 'v-charts/lib/index.esm'
Vue.component(VeRing.name, VeRing)
Vue.config.productionTip = true
Vue.use(Vuesession)
Vue.component(VeLine.name, VeLine)
Vue.use(PullRefresh)
Vue.use(Lazyload)
Vue.use(Vant)
Vue.use(ElementUI)
Vue.use(VueCookies)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
