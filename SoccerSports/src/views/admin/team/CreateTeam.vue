<template>
  <v-card>
    <v-card-title>
      <span class="headline">Create Team</span>
    </v-card-title>
    <v-card-text>
      <v-container>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-row>
            <v-col cols="12" sm="6" md="4">
              <v-text-field
                v-model="name"
                :counter="21"
                :rules="nameRules"
                label="Team Name"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4">
              <v-text-field
                v-model="country"
                :counter="21"
                :rules="countryRules"
                label="Country"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4">
              <v-file-input
                accept="image/png, image/jpeg, image/bmp"
                :rules="rulesImage"
                v-model="fileImage"
                label="Add Logo"
              ></v-file-input>
            </v-col>
            <v-col>
              <v-textarea
                clearable
                v-model="description"
                clear-icon="mdi-close-circle"
                label="Description"
                value="Something about team"
              ></v-textarea>
            </v-col>
          </v-row>
        </v-form>
      </v-container>
      <small>*All the field need to be filled</small>
    </v-card-text>
    <v-card-actions>
      <v-btn color="blue darken-1" text @click="reset"> Reset </v-btn>
      <v-spacer></v-spacer>

      <v-btn color="blue darken-1" text @click="onSubmit"> Save </v-btn>
    </v-card-actions>

    <template>
      <div class="text-center">
        <v-dialog v-model="successDialog" hide-overlay persistent width="300">
          <v-alert class="mb-0" type="success"> Create Team Success! </v-alert>
        </v-dialog>
      </div>
    </template>
  </v-card>
</template>

<script>
export default {
  props: {
    closeCreateTeamDialog: {
      type: Function,
    },
  },

  data() {
    return {
      valid: true,
      successDialog: false,
      idTeam: 0,
      name: "",
      nameRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 21) || "Name must be less than 21 characters",
      ],
      country: "",
      countryRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 21) || "Name must be less than 21 characters",
      ],
      fileImage: [],
      rulesImage: [
        (v) => {
          if (v == undefined || Array.isArray(v)) {
            return false || "Image is required";
          }
          return true;
        },
      ],
      description: "",
    };
  },
  watch: {
    successDialog(val) {
      if (!val) return;
      setTimeout(() => (this.successDialog = false), 100000);
    },

    fileImage() {
      if (this.fileImage == undefined) {
        this.fileImage = [];
        this.rulesImage = [
          (v) => {
            if (v == undefined || Array.isArray(v)) {
              return false || "Item is required";
            }
            return true;
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
      }
    },
  },
  methods: {
    onSubmit() {
      if (!this.$refs.form.validate()) {
        this.$refs.form.validate();
      } else {
        var teamForm = new FormData();
        teamForm.append("nameTeam", this.name);
        teamForm.append("country", this.country);
        teamForm.append("description", this.description);
        teamForm.append("file", this.fileImage);
        let self = this;
        this.$store
          .dispatch("team/createTeam", teamForm)
          .then(function (response) {
            if (response.data.code === 9999 && response.data.payload == 409) {
              alert(response.data.message);
            } else {
              self.closeCreateTeamDialog();
              self.successDialog = !self.successDialog;
              setTimeout(function () {
                self.successDialog = !self.successDialog;
              }, 1500);
            }
          })
          .catch(function (error) {
            alert(error);
          });
      }
    },

    reset() {
      this.$refs.form.reset();
    },
  },
};
</script>