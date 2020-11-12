<template>
  <v-card>
    <template v-if="loginForm">
      <v-card-title>
        <span class="headline">Login</span>
      </v-card-title>

      <v-card-text>
        <v-container>
          <p>Defaut Account:</p>
          <p>1. admin - admin (Role_admin)</p>
          <p>2. nguyencongtoan14051997@gmail.com-123456</p>
          <h6
            style="color: red"
            align="center"
            justify="center"
            v-if="checkAccount"
          >
            Wrong Username or Password
          </h6>
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-text-field
              v-model="user.email"
              label="Email"
              :rules="usernameRules"
              required
            ></v-text-field>
            <v-text-field
              v-model="user.password"
              label="Password"
              :rules="passwordRules"
              required
              type="password"
            ></v-text-field>
            <v-row>
              <v-col>
                <v-btn @click="login" color="blue darken-1" text>Confirm</v-btn>
              </v-col>
              <v-spacer></v-spacer>
              <v-col>
                <v-btn @click="forgetPassword" color="blue darken-1" text
                  >Forget Password?</v-btn
                >
              </v-col>
            </v-row>
            <template>
              <div class="text-center">
                <v-dialog
                  v-model="successDialog"
                  hide-overlay
                  persistent
                  width="300"
                >
                  <v-alert class="mb-0" type="success">
                    Login Success!
                  </v-alert>
                </v-dialog>
              </div>
            </template>
          </v-form>
        </v-container>
      </v-card-text>
    </template>
    <template v-else>
      <v-card-title>
        <span class="headline">Forget Password</span>
      </v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="valid1" lazy-validation>
          <v-text-field
            v-model="email"
            :rules="emailRules"
            label="Input email to recive password"
            required
          ></v-text-field>
        </v-form>
        <v-row>
          <v-btn @click="backToLogin" color="blue darken-1" text
            >Back To Login</v-btn
          >
          <v-spacer></v-spacer>
          <v-btn @click="confirmRecivePassword" color="blue darken-1" text
            >Confirm</v-btn
          >
        </v-row>
      </v-card-text>
    </template>
  </v-card>
</template>
<script>
export default {
  props: {
    closeLoginDialog: {
      type: Function,
    },
    checkProfile: {
      type: Function,
    },
  },

  data() {
    return {
      successDialog: false,
      valid: false,
      valid1: false,
      loginForm: true,
      user: {
        email: "admin@gmail.com",
        password: "admin",
      },
      usernameRules: [(v) => !!v || "Username is required"],
      passwordRules: [(v) => !!v || "Password is required"],
      isEmailNotFound: false,
      emailRules: [
        (v) => !!v || "Email is required",
        (v) => {
          let inValid = /\s/;
          return !inValid.test(v) || "E-mail can not have white space";
        },
        (v) => !!/.+@.+/.test(v) || "E-mail must be valid",
      ],
    };
  },

  computed: {
    checkAccount: function () {
      return this.$store.state.auth.checkAccount;
    },
    isLoggedIn: function () {
      return this.$store.getters.isLoggedIn;
    },
  },

  methods: {
    login: function () {
      if (!this.$refs.form.validate()) {
        this.$refs.form.validate();
      } else {
        let self = this;
        // let userInfo = this.$store.state.user.userInfo;
        // this.$store.commit("auth/auth_overlay");
        this.$store
          .dispatch("auth/login", this.user)
          .then((res) => {
            // this.$store.commit("auth/auth_overlay");
            self.overlay = false;
            let userInfo = res.data.payload.account;
            console.log(userInfo.role);
            if (userInfo.role === null || userInfo.role === undefined) {
              self.checkProfile();
            } else if (userInfo.role === "ROLE_ADMIN") {
              self.closeLoginDialog();
              self.successDialog = !self.successDialog;
              setTimeout(function () {
                self.successDialog = !self.successDialog;
                self.$store.commit("user/user_info", userInfo);
                self.$store.commit("user/admin_profile");
                self.commonLogin(userInfo);
              }, 1100);
            } else if (
              userInfo.role === "ROLE_USER" ||
              userInfo.role === "ROLE_MEMBER"
            ) {
              self.closeLoginDialog();
              self.successDialog = !self.successDialog;
              setTimeout(function () {
                self.successDialog = !self.successDialog;
                self.closeLoginDialog();
                self.$store.commit("user/user_info", userInfo);
                self.$store.commit("user/user_profile");
                self.commonLogin(userInfo);
              }, 1100);
            }
          })
          .catch((err) => alert(err + " : 404"));
      }
    },

    commonLogin(userInfo) {
      console.log(userInfo);
      this.username = "";
      this.password = "";
    },

    backToLogin() {
      this.loginForm = !this.loginForm;
    },

    forgetPassword() {
      this.loginForm = !this.loginForm;
    },

    confirmRecivePassword() {
      if (!this.$refs.form.validate()) {
        this.$refs.form.validate();
      } else {
        this.$store.commit("auth/auth_overlay_true");
        let self = this;
        self.closeLoginDialog();
        this.$store
          .dispatch("user/forgetEmail", this.email)
          .then((res) => {
            if (res.data.code === 9999 && res.data.payload != null) {
              this.$store.commit("auth/auth_overlay_false");
              self.closeLoginDialog();
              alert("Email not found");
            } else if (res.data.code === 0) {
              self.loginForm = true;
              this.$store.commit("auth/auth_overlay_false");
              alert("Recive email success");
            } else {
              alert("Failed to action!!!!");
            }
          })
          .catch();
      }
    },
  },
};
</script>