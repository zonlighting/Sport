import Vue from 'vue'
import Router from 'vue-router'
import index from '@/views/web/index'
import Home from '@/views/web/home/Home'
import Schedule from '@/views/web/schedule/Schedule'
import ScheduleDetail from '@/views/web/schedule/ScheduleDetail'
import Summary from '@/views/web/schedule/Summary'
import Tournament from '@/views/web/tournament/Tournament'
import Team from '@/views/web/team/Team'
 

Vue.use(Router)
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
export default router