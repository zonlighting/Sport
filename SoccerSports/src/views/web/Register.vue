<template>
  <v-card>
    <v-card-title>
      <span class="headline">Register</span>
    </v-card-title>
    <v-card-text>
      <v-container>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
            :rules="emailRules"
            v-model="email"
            label="E-mail"
            required
          ></v-text-field>

          <v-text-field
            v-model="password"
            label="Password"
            :rules="passwordRules"
            required
            type="password"
          ></v-text-field>

          <v-btn class="mr-4" @click="register">Submit</v-btn>
          <v-alert type="success" v-if="success"> Register Success! </v-alert>
        </v-form>
      </v-container>
    </v-card-text>
  </v-card>
</template>
<script>
export default {
  props: {
    closeRegisterDialog: {
      type: Function,
    },
  },
  data() {
    return {
      success: false,
      valid: true,
      email: "",
      emailRules: [
        (v) => !!v || "Email is required",
        (v) => {
          let inValid = /\s/;
          return !inValid.test(v) || "E-mail can not have white space";
        },
        (v) => !!/.+@.+/.test(v) || "E-mail must be valid",
      ],
      password: "",
      passwordRules: [(v) => !!v || "Password is required"],
    };
  },

  methods: {
    register: function () {
      if (!this.$refs.form.validate()) {
        this.$refs.form.validate();
      } else {
        let self = this;
        let user = {
          email: this.email,
          password: this.password,
        };
        this.$store
          .dispatch("auth/register", user)
          .then((res) => {
            if (res.data.payload === 0) {
              alert("Email has exisits");
            } else {
              self.$store.dispatch("auth/login", user).then((res) => {
                let userInfo = res.data.payload.account;
                self.success = true;
                setTimeout(function () {
                  self.success = false;
                  self.$store.commit("user/user_info", userInfo);
                  self.$store.commit("user/user_profile");
                  self.closeRegisterDialog();
                  self.email = "";
                  self.password = "";
                }, 1500);
              });
            }
          })
          .catch((err) => console.log(err));
      }
    },
  },
};
</script>
