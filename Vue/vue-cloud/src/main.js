// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vuesession from 'vue-session'
import ElementUI from 'element-ui'
import Vant, { Lazyload, PullRefresh } from 'vant'
import 'vant/lib/index.css'
import 'element-ui/lib/theme-chalk/index.css'
Vue.config.productionTip = true
Vue.use(Vuesession)
Vue.use(PullRefresh)
Vue.use(Lazyload)
Vue.use(Vant)
Vue.use(ElementUI)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
