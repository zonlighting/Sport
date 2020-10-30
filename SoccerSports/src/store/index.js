import Vue from 'vue'
import Vuex from 'vuex'
import auth from '@/store/auth'
import user from '@/store/user'
import team from '@/store/team'
import tournament from '@/store/tournament'

Vue.use(Vuex)

export default new Vuex.Store({
	modules: {
		auth,
		user,
		team,
		tournament
	}, state: {
		Sidebar_drawer: null,
		Customizer_drawer: false,
		SidebarColor: 'white',
		SidebarBg: ''
	},
	mutations: {
		SET_SIDEBAR_DRAWER(state, payload) {
			state.Sidebar_drawer = payload
		},
		SET_CUSTOMIZER_DRAWER(state, payload) {
			state.Customizer_drawer = payload
		},
		SET_SIDEBAR_COLOR(state, payload) {
			state.SidebarColor = payload
		},
	},
})
