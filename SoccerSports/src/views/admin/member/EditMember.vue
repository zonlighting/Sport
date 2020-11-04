<template>
  <div>
    <v-row>
      <v-col cols="12" md="3" sm="2"></v-col>
      <v-col class="p-0">
        <v-breadcrumbs style="color: #06b4c2" :items="teamLink" large>
          <template v-slot:divider>
            <v-icon>mdi-chevron-right</v-icon>
          </template>
        </v-breadcrumbs>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" md="3" sm="2"></v-col>
      <v-col>
        <v-btn
          color="primary"
          dark
          @click="toPage(true)"
          cols="12"
          md="3"
          sm="2"
        >
          Manage Page
        </v-btn>
      </v-col>
      <v-col class="pl-15 ml-10" v-if="idTeam != 0">
        <v-btn color="primary" dark @click="toPage(false)">
          Back To Team
        </v-btn>
      </v-col>
    </v-row>
    <v-card max-width="50%" class="mx-auto">
      <v-card-title> Edit Member </v-card-title>
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-card-text>
          <v-row class="ml-2">
            <img style="width: 200px; height: 200px" :src="avatar" />
          </v-row>
          <v-row>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="name"
                :rules="nameRules"
                :counter="21"
                label="Full Name"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="6">
              <v-file-input
                :rules="rulesImage"
                v-model="fileImage"
                label="Change Avatar"
              ></v-file-input>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="phone"
                label="PhoneNumber"
                :rules="phoneRules"
                :counter="15"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <v-select
                v-model="gender"
                :items="defaultGender"
                :rules="[(v) => !!v || 'Gender is required']"
                label="Gender"
              ></v-select>
            </v-col>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="age"
                label="Age"
                :rules="ageRules"
                :counter="2"
                required
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12" md="6" xl="6">
              <v-text-field
                :rules="countryRules"
                v-model="country"
                label="Country"
                required
              ></v-text-field
            ></v-col>
            <v-col cols="12" md="6" xl="6">
              <v-select
                v-model="position"
                :items="defaultPosition"
                :rules="[(v) => !!v || 'Position is required']"
                label="Position"
              ></v-select> </v-col
          ></v-row>
        </v-card-text>
        <v-card-actions>
          <v-spacer> </v-spacer>
          <v-btn
            color="primary"
            x-large
            text
            @click.prevent="onSubmit(memberEdit.id)"
            v-if="changeButton"
            >Update</v-btn
          >
          <v-btn disabled v-else>Processing</v-btn>
        </v-card-actions>
      </v-form>
      <template>
        <div class="text-center">
          <v-dialog v-model="successDialog" hide-overlay persistent width="300">
            <v-alert class="mb-0" type="success">
              Update Member Success!
            </v-alert>
          </v-dialog>
        </div>
      </template>
    </v-card>
  </div>
</template>

<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
      idTeam: "",
      memberEdit: {},
      successDialog: false,
      changeButton: true,
      valid: false,
      fileImage: new File([""], ""),
      country: "",
      name: "",
      email: "",
      number: 10,
      avatar: "",
      countryRules: [(v) => !!v || "Country is required"],
      nameRules: [(v) => !!v || "Name is required"],
      phone: "",
      phoneRules: [
        (v) => !!v || "Phone is required",
        (v) => {
          let inValid = /^[0-9]+$/;
          return inValid.test(v) || "Phone must be a number";
        },
        (v) => {
          return (
            (v != undefined && v.length > 5) ||
            "Phone number have at least 6 digit"
          );
        },
      ],
      age: "",
      ageRules: [
        (v) => !!v || "Age number is required",
        (v) =>
          (v <= 60 && v >= 6) || "Age must be less than 60 and greater than 6",
      ],
      gender: "",
      defaultGender: ["Male", "Female", "Orther"],
      position: "",
      defaultPosition: [
        "Goalkeepers",
        "Defenders",
        "Midfielders",
        "Forwards",
        "Coach",
      ],
      rulesImage: [],
      teamLink: [
        {
          text: "Dashboard",
          disabled: false,
          href: "/admin",
        },
        {
          text: "Teams",
          disabled: false,
          href: "/admin/teams",
        },
        {
          text: "",
          disabled: false,
          href: ``,
        },
        {
          text: "Edit Profile",
          disabled: true,
        },
      ],
    };
  },

  mounted() {
    this.getProfile(this.$route.params.id);
  },

  watch: {
    fileImage() {
      if (this.fileImage == undefined) {
        this.fileImage = new File([""], "");
        this.rulesImage = [
          (v) => {
            if (v == undefined || v.name == "") {
              return true;
            }
          },
        ];
      }
      let isContainImage = !this.fileImage.name;
      if (!isContainImage) {
        this.rulesImage = [
          (v) =>
            v.type == "image/png" ||
            v.type == "image/jpeg" ||
            v.type == "image/bmp" ||
            "Wrong type image",
        ];

        this.getBase64(this.fileImage);
      }
    },
  },

  methods: {
    getProfile(id) {
      let self = this;
      this.$store
        .dispatch("member/getPlayerById", id)
        .then((response) => {
          let res = response.data.payload;
          self.memberEdit = res;
          self.name = res.name;
          self.email = res.email;
          self.phone = res.phone;
          self.age = res.age;
          self.gender = res.gender;
          self.country = res.country;
          self.avatar = ENV.BASE_IMAGE + res.avatar;
          self.position = res.position;
          self.idTeam = res.idTeam;
          if (res.idTeam != 0) {
            this.getTeam(res.idTeam);
          } else {
            self.teamLink[2].text = "";
            self.teamLink[2].href = ``;
          }
        })
        .catch((e) => {
          alert(e);
        });
    },

    getTeam(id) {
      let self = this;
      this.$store
        .dispatch("team/getTeamById", id)
        .then((response) => {
          let res = response.data.payload;
          self.teamLink[2].text = res.nameTeam;
          self.teamLink[2].href = `/admin/team/detail/${res.idTeam}`;
        })
        .catch((e) => {
          alert(e);
        });
    },

    onSubmit(id) {
      if (!this.$refs.form.validate()) {
        this.$refs.form.validate();
      } else {
        let self = this;
        var memberForm = new FormData();
        memberForm.append("name", this.name);
        memberForm.append("email", this.email);
        memberForm.append("phone", this.phone);
        memberForm.append("gender", this.gender);
        memberForm.append("age", this.age);
        memberForm.append("country", this.country);
        memberForm.append("position", this.position);
        memberForm.append("file", this.fileImage);

        this.$store
          .dispatch("member/updateProfile", {
            id: id,
            formRequest: memberForm,
          })
          .then((response) => {
            let res = response.data;
            self.changeButton = !self.changeButton;
            if (res.code == 9999) {
              alert(res.message);
            } else if (res.payload == 400) {
              alert(res.message);
            } else {
              self.successDialog = !self.successDialog;
              setTimeout(() => {
                self.successDialog = !self.successDialog;
                this.$router.go(-1);
              }, 1200);
            }
          })
          .catch((e) => {
            alert(e);
            self.changeButton = !self.changeButton;
          });
        self.changeButton = !self.changeButton;
      }
    },

    getBase64(file) {
      let self = this;
      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function () {
        self.avatar = reader.result;
      };
      reader.onerror = function (error) {
        alert("Error: ", error);
      };
    },

    toPage(isCheckLink) {
      if (isCheckLink) {
        if (this.idTeam != 0) {
          this.$router.push({
            path: `/admin/team/${this.idTeam}/manage`,
          });
        } else {
          this.$router.go(-1);
        }
      } else {
        if (this.idTeam != 0) {
          this.$router.push({
            path: `/admin/team/detail/${this.idTeam}`,
          });
        } else {
          alert("Member are not in any team");
          this.$router.go(-1);
        }
      }
    },
  },
};
</script>