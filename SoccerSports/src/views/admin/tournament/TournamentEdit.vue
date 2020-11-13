<template>
  <v-container>
    <v-form ref="form"  lazy-validation>
      <v-text-field
        v-model="nameTournament"
        placeholder="Name Tournament"
        :counter="40"
        :rules="[
          (v) => !!v || 'Name is required',
          (v) =>
            (v && v.length <= 40) || 'Name must be less than 40 characters',
        ]"
      ></v-text-field>
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
              <v-date-picker v-model="dateStart" no-title scrollable :min="
                          new Date(new Date().setDate(new Date().getDate() + 1))
                            .toISOString()
                            .substr(0, 10)
                        ">
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
              <v-date-picker v-model="dateEnd" no-title scrollable>
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
      </v-row>
      <v-list-item-title>Banner</v-list-item-title>
      <v-list-item-subtitle>
        <v-file-input
          accept="image/png, image/jpeg, image/bmp"
          placeholder="Pick an Banner"
          prepend-icon="mdi-camera"
          v-model="image"
          :rules="rulesImage"
        ></v-file-input>
        <img
          style="max-height: 204px"
          src="https://sonypicturespublicity.com/dom/img/no_banner_image.gif"
          id="image"
        />
      </v-list-item-subtitle>

      <v-btn @click="reset">Reset</v-btn> <v-btn @click="save">Save</v-btn>
    </v-form>
  </v-container>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
      nameTournament: "",
      dateEnd: new Date(new Date().setDate(new Date().getDate() + 1))
        .toISOString()
        .substr(0, 10),
      dateStart: new Date(new Date().setDate(new Date().getDate() + 1))
        .toISOString()
        .substr(0, 10),
      menuStart: false,
      menuEnd: false,
      image: [],
      rulesImage: [
        (v) => {
          if (v == undefined || Array.isArray(v)) {
            return true;
          } else {
            if (
              v.type == "image/png" ||
              v.type == "image/jpeg" ||
              v.type == "image/bmp"
            ) {
              return true;
            } else {
              return false || "Wrong data";
            }
          }
        },
      ],
    };
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
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
  created() {
    this.getOld();
  },
  mounted(){
    this.getOld();
  },
  methods: {
    reset() {
      this.getOld();
    },
    getOld() {
      this.dateEnd = this.tournament.timeEnd;
      this.dateStart = this.tournament.timeStart;
      this.nameTournament = this.tournament.nameTournament;
      if (this.tournament.banner != null && this.tournament.banner != "") {
        document.getElementById("image").src =
          this.baseUrl + this.tournament.banner;
      } else {
        document.getElementById("image").src =
          "https://sonypicturespublicity.com/dom/img/no_banner_image.gif";
      }
    },
    save() {
      if (!this.$refs.form.validate()) {
        this.$refs.form.validate();
      } else {
        var bodyFormData = new FormData();
        bodyFormData.append("idTournament",this.tournament.idTournament);
        bodyFormData.append("nameTournament", this.nameTournament);
        bodyFormData.append("timeEnd", this.dateEnd);
        bodyFormData.append("timeStart", this.dateStart);
        bodyFormData.append("banner", this.tournament.banner);
        if (this.image.size > 0&&this.image!=undefined) {
          bodyFormData.append("bannerFile", this.image);
        }
        this.$store
          .dispatch("tournament/update", bodyFormData)
          .then((response) => {
            if (response.data.code == 0) {
              alert(response.data.message)
              this.getData();
              this.hide();
            } else {
              alert(response.data.message);
            }
          })
          .catch((e) => {
            alert(e);
          });
      }
    },
  },
  props: {
    getData: Function,
    tournament: Object,
    hide:Function,
  },
  watch: {
    image(event) {
      if (this.image == undefined || this.image == "") {
        document.getElementById("image").src =
          this.baseUrl + this.tournament.banner;
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