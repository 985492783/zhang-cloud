import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login'
import Dashboard from '../views/DashBoard'
import Add from '../views/Add'
import Center from '../views/Center'
import Search from '../views/Search'
import Chat from '../views/Chat'
Vue.use(Router)

const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Dashboard',
      component: Dashboard
    },
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path: '/Add',
      name: 'Add',
      component: Add
    },
    {
      path: '/Search',
      name: 'Search',
      component: Search
    },
    {
      path: '/Center',
      name: 'Center',
      component: Center
    },
    {
      path: '/Chat',
      name: 'Chat',
      component: Chat
    }
  ]
})
