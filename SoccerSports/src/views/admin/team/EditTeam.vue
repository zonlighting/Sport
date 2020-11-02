<template>
  <v-row class="pl-5">
    <v-col cols="12" md="2" sm="2">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-row>
          <v-img max-width="200" :src="baseUrl + team.logo"></v-img>
          <v-file-input
            v-model="fileImage"
            hide-input
            truncate-length="15"
            :rules="rulesImage"
          ></v-file-input>
        </v-row>
      </v-form>
    </v-col>
    <v-col cols="12" md="2" sm="2">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field
          v-model="name"
          :rules="nameRules"
          :counter="21"
          required
        ></v-text-field>
        <v-text-field
          v-model="country"
          :rules="countryRules"
          :counter="21"
          required
        ></v-text-field>
      </v-form>
      <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
        <div style="color: red" v-if="team.tourName != null">
          {{ team.tourName }}
        </div>
        <div style="color: green" v-else>Not in tournament</div>
      </h5>
      <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
        Current Members:
        {{ team.profile && team.profile.length ? team.profile.length : 0 }}
      </h5>
    </v-col>
    <v-col cols="12" md="2" sm="2" class="pt-15 pl-10">
      <h5 class="country-text">
        Win Rate:
        {{ team.totalmatch > 0 ? (team.totalwin / team.totalmatch) * 100 : 0 }}
      </h5>
      <h5 class="country-text">Total Win : {{ team.totalwin }}</h5>
      <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
        Total Match: {{ team.totalmatch }}
      </h5>
      <div class="pb-15"></div>
      <v-btn color="primary" class="ml-15" dark @click="onSubmit(team.idTeam)">
        Confirm
      </v-btn>
    </v-col>
    <v-divider class="mx-4" inset vertical></v-divider>
    <v-col cols="12" md="5" sm="5">
      <h2>Description</h2>
      <v-textarea
        clearable
        v-model="team.description"
        clear-icon="mdi-close-circle"
        value="Something about team"
      ></v-textarea>
    </v-col>
    <v-dialog persistent v-model="dialogSuccess" max-width="500">
      <template>
        <v-alert class="mb-0" type="success"> Update Success! </v-alert>
      </template>
    </v-dialog>
  </v-row>
</template>

<script>
import { ENV } from "@/config/env.js";

export default {
  props: {
    team: Object,
    editTeam: {
      type: Function,
    },
    getTeamById: {
      type: Function,
    },
  },

  data() {
    return {
      dialogSuccess: false,
      valid: true,
      fileImage: [],
      rulesImage: [],
      name: this.team.nameTeam,
      nameRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 21) || "Name must be less than 21 characters",
      ],
      country: this.team.country,
      countryRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 21) || "Name must be less than 21 characters",
      ],
    };
  },

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  methods: {
    confirmEdit() {
      this.editTeam();
    },

    onSubmit(id) {
      let isContainImage = !this.fileImage.name;
      if (!this.$refs.form.validate()) {
        this.$refs.form.validate();
      } else if (!isContainImage) {
        this.rulesImage = [
          (v) =>
            v.type == "image/png" ||
            v.type == "image/jpeg" ||
            v.type == "image/bmp" ||
            alert("Wrong type image")
        ];
      } else {
        console.log("Run here");
        console.log(id);
        let self = this;
        var teamForm = new FormData();
        teamForm.append("nameTeam", this.name);
        teamForm.append("type", this.selectedType);
        teamForm.append("description", this.description);
        teamForm.append("file", this.fileImage);
        this.$store
          .dispatch("team/updateTeam", {
            id: id,
            formRequest: teamForm,
          })
          .then((res) => {
            console.log(res);
            self.dialogSuccess = !self.dialogSuccess;
            setTimeout(function () {
              self.getTeamById(id);
              self.dialogSuccess = !self.dialogSuccess;
            }, 1500);
          })
          .catch((e) => {
            alert(e);
          });
      }
    },
  },
};
</script>