import Vue from 'vue'
import Router from 'vue-router'
import store from '@/store'


import index from '@/views/web/index'
import Home from '@/views/web/home/Home'
import Schedule from '@/views/web/schedule/Schedule'
import ScheduleDetail from '@/views/web/schedule/ScheduleDetail'
import Summary from '@/views/web/schedule/Summary'
import Tournament from '@/views/web/tournament/Tournament'
import Team from '@/views/web/team/Team'

Vue.use(Router)

// const metaConfig = {
//   requiredAuth: true,
//   adminAuth: true,
//   userAuth: false
// }

let routes = [
  {
    path: '/',
    name: 'index',
    component: index,
    redirect: '/Home',
    children: [
      {
        path: '/Home',
        component: Home
      },
      {
        path: '/Schedule',
        component: Schedule,
        
      },
      {
        path: '/ScheduleDetail',
        component: ScheduleDetail,
        children:[
          {
            path:'/Summary',
            component:Summary,

          }
        ]
      },
      
      {
        path: '/Tournament',
        component: Tournament
      },
      {
        path: '/Team',
        component: Team
      }
    ]
  }

]

const router = new Router({
  routes,
  mode: 'history'
})

router.beforeEach(async (to, from, next) => {
  if (store.state.auth.token != '' && store.state.user.userInfo == null) {
    await store.dispatch("user/autoLogin").then(() => {
      if (store.state.user.userInfo.role === 'ROLE_ADMIN') {
        store.commit('user/admin_profile')
      }
      else {
        store.commit('user/user_profile')
      }
    })
  } else {
    next()
  }
  if (to.meta.requiredAuth) {
    const authUser = store.state.auth
    if (!authUser || !authUser.token) {
      next()
    }
    else if (to.meta.adminAuth) {
      let role = store.state.user.userInfo.role;
      if (role === 'ROLE_ADMIN') {
        next()
      } else {
        next('/admin/login')
      }
    }
  }
  else {
    next()
  }
})

export default router