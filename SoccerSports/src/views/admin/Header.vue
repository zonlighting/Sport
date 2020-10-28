<template>
  <v-app-bar app clipped-left clipped-right color="primary" dark>
    <v-toolbar-title class="align-center d-flex">
      <h2 class="pl-10">
      Sports</h2>
    </v-toolbar-title>
    <v-spacer />
    <!---right part -->
    <v-btn dark color="success" class="mr-3" @click="toWebPage">Back To Web</v-btn>
    <v-btn dark color="success" @click="logout">Logout</v-btn>
  </v-app-bar>
</template>
<script>
// Utilities
import { mapState, mapMutations } from "vuex";
export default {
  name: "Header",

  components: {},

  props: {
    value: {
      type: Boolean,
      default: false
    }
  },
  data: () => ({

    href() {
      return undefined;
    }
  }),

  computed: {
    ...mapState(["Sidebar_drawer"])
  },

  methods: {
    ...mapMutations({
      setSidebarDrawer: "SET_SIDEBAR_DRAWER"
    }),

    toWebPage() {
      window.open("http://localhost:8080/Home", "_self");
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
        window.open("http://localhost:8080/Home", "_self");
      });
    },

    checkProfileAdmin: function () {
      this.$store.commit("user/admin_profile");
    },

    checkProfile: function () {
      this.$store.commit("user/user_profile");
    },
  }
};
</script>