<template>
  <div class="wrapper">
    <header class="header-main">
      <div class="header-upper">
        <div class="container" style="min-height:40px">

        </div>
      </div>
      <div class="header-lower clearfix">
        <div id="login">
          <template v-if="isAdminProfile">
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
                    img="https://cdn.vuetifyjs.com/images/cards/girl.jpg"
                    height="35"
                    width="40"
                    v-bind="attrs"
                    v-on="on"
                  ></v-card>
                  <h4 style="color: white; padding-left: 5px">Name</h4>
                </template>

                <v-list>
                  <v-list-item>
                    <v-list-item-title>
                      <div class="fixButton row-pointer">Profile</div>
                    </v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-title
                      ><div class="fixButton row-pointer">
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
                      <h4 style="color: white; margin-left: 8px">Register</h4>
                    </a>
                  </template>

                  <Register :closeRegisterDialog="registerDialog" />
                </v-dialog>
              </template>
            </v-row>
          </template>
        </div>
        <div class="container">
          <div class="row">
            <h1 class="logo">
              <a href="index-2.html"
                ><v-img src=images/logo.png width="100px"/></a
              >
            </h1>

            <div class="menubar">
              <nav class="navbar">
                <div class="nav-wrapper">
                  <div class="nav-menu">
                    
                    <ul class="nav navbar-nav menu-bar">
                      <li>
                        <a :href="$router.resolve({ path: '/Home' }).href"
                          >Home
                        </a>
                      </li>
                      <li>
                        <a :href="$router.resolve({ path: '/Team' }).href"
                          >Team
                        </a>
                      </li>
                      <li>
                        <a :href="$router.resolve({ path: '/Tournament' }).href"
                          >Tournament
                        </a>
                        <ul class="sub-menu">
                          <li><a href="gallerypage01.html">UpComming</a></li>
                          <li>
                            <a href="gallery02.html">Ongame</a>
                          </li>
                          <li>
                            <a href="gallery03.html">Finshed</a>
                          </li>
                        </ul>
                      </li>
                      <li>
                        <a :href="$router.resolve({ path: '/Schedule' }).href"
                          >Schedule
                        </a>
                        <ul class="sub-menu">
                          <li><a href="gallerypage01.html">UpComming</a></li>
                          <li>
                            <a href="gallery02.html">Ongame</a>
                          </li>
                          <li>
                            <a href="gallery03.html">Finshed</a>
                          </li>
                        </ul>
                      </li>
                      <li><a href="shop.html">Rank </a></li>
                      <li><a href="contact.html">About </a></li>
                    </ul>
                  </div>
                </div>
              </nav>
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

export default {

  components: {
    Login,
    Register,
  },

  data: () => ({
    isLogged: false,
    showMenu: false,
    isProfile: false,
    LoginDialog: false,
    RegisterDialog: false,
  }),

  computed: {
    isAdminProfile: function () {
      return this.$store.state.user.isAdminProfile;
    },
  },

  methods: {
    loginDialog() {
      this.LoginDialog = !this.LoginDialog;
    },
    registerDialog: function () {
      this.RegisterDialog = !this.RegisterDialog;
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