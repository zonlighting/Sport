<template>
  <v-card>
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="close">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Create Tournamet</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text @click="save"> Save </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-form ref="form" v-model="valid" lazy-validation>
      <v-list three-line subheader>
        <v-subheader style="color: blue">Summary</v-subheader>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>Name Tournament</v-list-item-title>
            <v-list-item-subtitle>
              <v-text-field
                v-model="nameTournament"
                placeholder="Name Tournament"
                :counter="40"
                :rules="[
                  (v) => !!v || 'Name is required',
                  (v) =>
                    (v && v.length <= 40) ||
                    'Name must be less than 40 characters',
                ]"
              ></v-text-field>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-list-item>
          <v-row>
            <v-col>
              <v-row>
                <v-col cols="12" sm="6"
                  ><v-list-item-title>Time Start</v-list-item-title>
                  <v-list-item-subtitle>
                    <v-menu
                      ref="menuStart"
                      v-model="menuStart"
                      :close-on-content-click="false"
                      :return-value.sync="dateStart"
                      transition="scale-transition"
                      offset-y
                      min-width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          v-model="dateStart"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        v-model="dateStart"
                        no-title
                        scrollable
                        :min="
                          new Date(new Date().setDate(new Date().getDate() + 1))
                            .toISOString()
                            .substr(0, 10)
                        "
                      >
                        <v-spacer></v-spacer>
                        <v-btn text color="primary" @click="menuStart = false">
                          Cancel
                        </v-btn>
                        <v-btn
                          text
                          color="primary"
                          @click="$refs.menuStart.save(dateStart)"
                        >
                          OK
                        </v-btn>
                      </v-date-picker>
                    </v-menu>
                  </v-list-item-subtitle></v-col
                >
                <v-col cols="12" sm="6"
                  ><v-list-item-title>Time End</v-list-item-title>
                  <v-list-item-subtitle>
                    <v-menu
                      ref="menuEnd"
                      v-model="menuEnd"
                      :close-on-content-click="false"
                      :return-value.sync="dateEnd"
                      transition="scale-transition"
                      offset-y
                      min-width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          v-model="dateEnd"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                          :rules="rulesTimeEnd"
                          ref="a"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        v-model="dateEnd"
                        no-title
                        scrollable
                        :min="
                          new Date(new Date().setDate(new Date().getDate() + 1))
                            .toISOString()
                            .substr(0, 10)
                        "
                      >
                        <v-spacer></v-spacer>
                        <v-btn text color="primary" @click="menuEnd = false">
                          Cancel
                        </v-btn>
                        <v-btn
                          text
                          color="primary"
                          @click="$refs.menuEnd.save(dateEnd)"
                        >
                          OK
                        </v-btn>
                      </v-date-picker>
                    </v-menu>
                  </v-list-item-subtitle></v-col
                >
                <v-col>
                  <v-list-item-title>Description</v-list-item-title>
                  <v-list-item-subtitle
                    ><v-textarea
                      name="input-7-1"
                      placeholder="Description"
                      v-model="description"
                    ></v-textarea
                  ></v-list-item-subtitle>
                </v-col> </v-row
            ></v-col>
            <v-col style="margin-top: 10px">
              <v-list-item-title>Banner</v-list-item-title>
              <v-list-item-subtitle>
                <v-file-input
                  accept="image/png, image/jpeg, image/bmp"
                  placeholder="Pick an Banner"
                  prepend-icon="mdi-camera"
                  v-model="fileImage"
                ></v-file-input>
                <img
                  style="max-height: 204px"
                  src="https://sonypicturespublicity.com/dom/img/no_banner_image.gif"
                  id="image"
                />
              </v-list-item-subtitle>
            </v-col>
          </v-row>
        </v-list-item>
      </v-list>
      <v-divider></v-divider>
      <v-list three-line subheader>
        <v-subheader style="color: blue">Team </v-subheader>
        <v-list-item-subtitle>
          <v-container
            ><h2>Team selected</h2>

            <template v-if="teamSelected == ''">
              No team has been selected yet
            </template>
            <template v-else>
              <v-row>
                <v-col
                  cols="12"
                  sm="2"
                  v-for="(item, index) in teamSelected"
                  :key="index"
                >
                  <v-avatar color="blue">
                    <img
                      src="https://cdn.vuetifyjs.com/images/john.jpg"
                      alt="John"
                    />
                  </v-avatar>
                  <b style="text-transform: uppercase; margin-left: 20px"
                    >Man city</b
                  >
                </v-col>
              </v-row>
            </template>
          </v-container>
        </v-list-item-subtitle>
        <v-list-item-subtitle>
          <v-container>
            <h2>Choose Team</h2>
            <v-row>
              <v-col cols="12" sm="3" v-for="(n, index) in 10" :key="index">
                <v-avatar color="blue">
                  <img
                    src="https://cdn.vuetifyjs.com/images/john.jpg"
                    alt="John"
                  />
                </v-avatar>
                <b style="text-transform: uppercase; margin-left: 20px"
                  >Man city</b
                >
                <v-checkbox
                  style="display: inline-block; margin-left: 20px"
                  v-model="teamSelected"
                  :value="n"
                >
                </v-checkbox>
              </v-col>
            </v-row>
          </v-container>
        </v-list-item-subtitle>
      </v-list>
    </v-form>
  </v-card>
</template>
<script>
export default {
  props: {
    hideDialog: Function,
  },
  data: () => ({
    valid: false,
    dateEnd: new Date(new Date().setDate(new Date().getDate() + 1))
      .toISOString()
      .substr(0, 10),
    dateStart: new Date(new Date().setDate(new Date().getDate() + 1))
      .toISOString()
      .substr(0, 10),
    menuStart: false,
    menuEnd: false,
    description: "",
    fileImage: [],
    urlImage: "",
    teamSelected: [],
    nameTournament: "",
  }),
  computed: {
    rulesTimeEnd() {
      return [
        (v) => {
          if (v <= this.dateStart) {
            return false || "The end time must be greater than the start time";
          }
          return true;
        },
      ];
    },
  },
  methods: {
    save() {
      if (!this.$refs.form.validate()) {
        this.$refs.form.validate();
      } else {
        if (this.teamSelected.length < 10) {
          alert("The tournament must have at least 10 teams participating");
        } else {
          this.$store.commit("auth/auth_overlay");
          var bodyFormData = new FormData();
          bodyFormData.append("nameTournament", this.nameTournament);
          bodyFormData.append("timeEnd", this.dateEnd);
          bodyFormData.append("timeStart", this.dateStart);
          bodyFormData.append("description", this.description);
          bodyFormData.append("bannerFile", this.fileImage);
          bodyFormData.append("listTeam", this.teamSelected);
          this.$store
            .dispatch("tournament/create", bodyFormData)
            .then((response) => {
              this.$store.commit("auth/auth_overlay");
              if (response.data.code == 0) {
                console.log(response.data.payload);
              } else {
                alert("Error");
              }
            });
        }
      }
    },
    async close() {
      await this.$refs.form.reset();
      this.dateEnd = new Date(new Date().setDate(new Date().getDate() + 1))
        .toISOString()
        .substr(0, 10);
      this.dateStart = new Date(new Date().setDate(new Date().getDate() + 1))
        .toISOString()
        .substr(0, 10);
      this.hideDialog();
    },
  },
  watch: {
    fileImage(event) {
      console.log(event);
      if (this.fileImage == undefined) {
        document.getElementById("image").src =
          "https://sonypicturespublicity.com/dom/img/no_banner_image.gif";
      } else {
        var reader = new FileReader();
        reader.onload = function () {
          var dataURL = reader.result;
          var output = document.getElementById("image");
          output.src = dataURL;
        };
        reader.readAsDataURL(event);
      }
    },
  },
};
</script>