<template>
  <v-card>
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
          @click.prevent="onSubmit(member.id)"
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
</template>

<script>
import { ENV } from "@/config/env.js";

export default {
  props: {
    member: Object,
    isModalEditMember: {
      type: Function,
    },
    getTeamById: {
      type: Function,
    },
    teamId: Number,
  },

  data() {
    return {
      successDialog: false,
      changeButton: true,
      valid: false,
      response: "",
      dialogCreateMember: false,
      fileImage: new File([""], ""),
      country: this.member.country,
      name: this.member.name,
       email: this.member.email,
      number: 10,
      avatar: ENV.BASE_IMAGE + this.member.avatar,
      countryRules: [(v) => !!v || "Country is required"],
      nameRules: [(v) => !!v || "Name is required"],
      phone: this.member.phone,
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
      age: this.member.age,
      ageRules: [
        (v) => !!v || "Age number is required",
        (v) =>
          (v <= 60 && v >= 6) || "Age must be less than 60 and greater than 6",
      ],
      gender: this.member.gender,
      defaultGender: ["Male", "Female", "Orther"],
      position: this.member.position,
      defaultPosition: [
        "Goalkeepers",
        "Defenders",
        "Midfielders",
        "Forwards",
        "Coach",
      ],
      rulesImage: [],
    };
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
              self.isModalEditMember();
              self.successDialog = !self.successDialog;
              setTimeout(() => {
                self.successDialog = !self.successDialog;
                self.getTeamById(self.teamId);
              }, 1100);
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
  },
};
</script>