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

import admin from '@/views/admin/index'


Vue.use(Router)

const metaConfig = {
  requiredAuth: true,
  adminAuth: true,
  userAuth: false
}

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
        children: [
          {
            path: '/Summary',
            component: Summary,

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
    ],
  },
  {

    path: '/admin',
    name: 'admin',
    component: admin,
    redirect: '/admin/dashboard',
    children: [
      {
        path: '/admin/dashboard',
        name: 'Dashboard',

        component: () => import('@/views/admin/dashboard/Dashboard'),
        meta: metaConfig
      },
      {
        path: '/admin/schedule',
        name: 'Schedule',

        component: () => import('@/views/admin/schedule/Schedule'),
        meta: metaConfig
      },
      {
        path: '/admin/tournament',
        name: 'Tournament',

        component: () => import('@/views/admin/tournament/Tournament'),
        meta: metaConfig
      },
      {
        path: '/admin/tournament/:id',
        name: 'TournamentDetail',

        component: () => import('@/views/admin/tournament/TournamentDetail'),
        meta: metaConfig
      },
      {
        path: '/admin/user',
        name: 'User',
        component: () => import('@/views/admin/user/User'),
        meta: metaConfig
      },
      {
        path: '/admin/member/:id',
        name: "EditMember",
        component: () => import('@/views/admin/member/EditMember'),
        meta: metaConfig
      },
      {
        path: '/admin/teams',
        name: 'team',
        component: () => import('@/views/admin/team/Teams'),
        meta: metaConfig
      },
      {
        path: '/admin/team/detail/:id',
        name: "TeamDetail",
        component: () => import('@/views/admin/team/TeamDetail'),
        meta: metaConfig
      },
      {
        path: '/admin/team/:id/manage',
        name: "ManageTeam",
        component: () => import('@/views/admin/member/ManageMembers'),
        meta: metaConfig
      },
    ],
    meta: metaConfig
  },



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
        next('/')
      }
    }
  }
  else {
    next()
  }
})

export default router