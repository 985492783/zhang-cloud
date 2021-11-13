import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login'
import Home from '../views/Home'
import Dashboard from '../views/DashBoard'
import Add from '../views/Add'
import Search from '../views/Search'
Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/Dashboard',
      name: 'Dashboard',
      component: Dashboard
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
    }
  ]
})
