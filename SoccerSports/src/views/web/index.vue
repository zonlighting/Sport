<template>
  <div class="wrapper">
    <header class="header-main">
      <div class="header-lower clearfix">
        <div class="container">
          <div class="row">
            <h1 class="logo">
              <a href="/Home"><v-img src=images/logo.png width="100px"/></a>
            </h1>

            <div class="menubar">
              <nav class="navbar">
                <div class="nav-wrapper">
                  <div class="nav-menu">
                    <ul class="nav navbar-nav menu-bar">
                      <li>
                        <a>
                          <router-link to="/home">Home</router-link
                          ><span></span> <span></span> <span></span>
                          <span></span>
                        </a>
                      </li>
                      <li>
                        <a>
                          <router-link to="/teams">Team</router-link
                          ><span></span> <span></span> <span></span>
                          <span></span>
                        </a>
                      </li>
                      <li>
                        <a>
                          <router-link to="/tournament">Tournament</router-link
                          ><span></span> <span></span> <span></span>
                          <span></span>
                        </a>
                      </li>
                      <li>
                        <a>
                          <router-link to="/schedule">Schedule</router-link
                          ><span></span> <span></span> <span></span>
                          <span></span>
                        </a>
                      </li>
                      <li>
                        <a>
                          <router-link to="/rank">Rank</router-link
                          ><span></span> <span></span> <span></span>
                          <span></span>
                        </a>
                      </li>
                    </ul>
                  </div>
                </div>
              </nav>
            </div>
            <v-spacer></v-spacer>
            <div>
              <div>
                <template v-if="isProfile">
                  <v-row class="d-flex" justify="center">
                    <v-menu
                      v-model="showMenu"
                      absolute
                      offset-y
                      style="max-width: 600px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-card
                          class="portrait"
                          :img="baseUrl + profile.profile.avatar"
                          height="35"
                          width="40"
                          v-bind="attrs"
                          v-on="on"
                        ></v-card>
                        <h4 style="color: white; padding-left: 5px">
                          {{ profile.profile.name }}
                        </h4>
                      </template>

                      <v-list>
                        <v-list-item>
                          <v-list-item-title>

                            <div  @click="roleFunction(profile.role)" class="fixButton row-pointer">Profile</div>

                          </v-list-item-title>
                        </v-list-item>
                        <v-list-item>
                          <v-list-item-title
                            ><div class="fixButton row-pointer" @click="logout">
                              Logout
                            </div></v-list-item-title
                          >
                        </v-list-item>
                      </v-list>
                    </v-menu>
                  </v-row>
                </template>
                <template v-else-if="isAdminProfile">
                  <v-row class="d-flex" justify="center">
                    <v-menu
                      v-model="showMenu"
                      absolute
                      offset-y
                      style="max-width: 600px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-card
                          class="portrait"
                          :img="baseUrl + profile.profile.avatar"
                          height="35"
                          width="40"
                          v-bind="attrs"
                          v-on="on"
                        ></v-card>
                        <h4 style="color: white; padding-left: 5px">
                          {{ profile.profile.name }}
                        </h4>
                      </template>

                      <v-list>
                        <v-list-item>
                          <v-list-item-title>
                            <div
                              class="fixButton row-pointer"
                              @click="toAdminPage"
                            >
                              Admin Page
                            </div>
                          </v-list-item-title>
                        </v-list-item>
                        <v-list-item>
                          <v-list-item-title
                            ><div
                              class="fixButton ml-4 row-pointer"
                              @click="logout"
                            >
                              Logout
                            </div></v-list-item-title
                          >
                        </v-list-item>
                      </v-list>
                    </v-menu>
                  </v-row>
                </template>
                <template v-else
                  ><v-row>
                    <template>
                      <v-dialog v-model="LoginDialog" max-width="600px">
                        <template v-slot:activator="{ on, attrs }">
                          <a v-bind="attrs" v-on="on">
                            <h4 style="color: white">Login</h4>
                          </a>
                        </template>
                        <Login :closeLoginDialog="loginDialog" />
                      </v-dialog>
                    </template>

                    <template>
                      <v-dialog v-model="RegisterDialog" max-width="600px">
                        <template v-slot:activator="{ on, attrs }">
                          <a v-bind="attrs" v-on="on">
                            <h4 style="color: white; margin-left: 8px">
                              Register
                            </h4>
                          </a>
                        </template>

                        <Register :closeRegisterDialog="registerDialog" />
                      </v-dialog>
                    </template>
                  </v-row>
                </template>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>

    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Login from "@/views/web/Login.vue";
import Register from "@/views/web/Register.vue";
import { ENV } from "@/config/env.js";

export default {
  components: {
    Login,
    Register,
  },

  data: () => ({
    isLogged: false,
    showMenu: false,
    LoginDialog: false,
    RegisterDialog: false,
  }),

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },

    isAdminProfile: function () {
      return this.$store.state.user.isAdminProfile;
    },

    isProfile: function () {
      return this.$store.state.user.isProfile;
    },

    profile: function () {
      // console.log("Should Run last");
      // console.log(this.$store.state.user.userInfo);
      if (this.$store.state.user.userInfo != null) {
        return this.$store.state.user.userInfo;
      } else {
        return null;
      }
    },
  },

  methods: {
    loginDialog() {
      this.LoginDialog = !this.LoginDialog;
    },

    registerDialog: function () {
      this.RegisterDialog = !this.RegisterDialog;
    },

    checkProfileAdmin: function () {
      this.$store.commit("user/admin_profile");
    },

    checkProfile: function () {
      this.$store.commit("user/user_profile");
    },

    logout() {
      this.showMenu = false;
      this.$store.dispatch("auth/logout").then(() => {
        const role = this.$store.state.user.userInfo.role;
        if (role === "ROLE_ADMIN") {
          this.checkProfileAdmin();
        } else if (role === "ROLE_USER" || role === "ROLE_MEMBER") {
          this.checkProfile();
        }
        this.LoginDialog = false;
        // this.$router.push("/Home").catch((err) => {
        //   console.log(err);
        // });
        window.open("http://localhost:8080/Home", "_self");
      });
    },

    toAdminPage() {
      this.$router.push("/admin");
    },

    roleFunction(role) {
      console.log(role);
      if (role === "ROLE_MEMBER") {
      this.$router.push('/profile/'+this.$store.state.user.userInfo.profile.id)
      } else {
        this.controlModalUser();
      }
    },
  },
};
</script>

<style scoped>
#login {
  color: red;
  position: absolute;
  right: 90px;
  top: 5px;
}
</style>

<style lang="css">
.v-list-item .row-pointer:hover {
  cursor: pointer;
}
.fixButton {
  background: white !important;
}
</style>