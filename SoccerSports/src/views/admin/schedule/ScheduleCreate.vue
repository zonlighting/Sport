<template>
  <v-container>
    <v-form ref="form" v-model="valid" lazy-validation>
      <h1>Create Schedule</h1>
      <v-row>
        <v-col>
          <v-select
            :items="listTournament"
            item-text="nameTournament"
            item-value="idTournament"
            v-model="selectTournament"
            label="Tournament"
            :rules="[(v) => !!v || 'Tournament required']"
            required
          ></v-select>
        </v-col>
        <v-col>
          <v-text-field
            label="Location"
            v-model="location"
            :rules="[(v) => !!v || 'Location required']"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-select
            :items="listTeam"
            item-text="nameTeam"
            item-value="idTeam"
            v-model="selectTeam1"
            label="Team 1"
            :rules="[
              (v) => {
                if (v == null) {
                  return false || 'Team1 required';
                }
                if (v == selectTeam2) {
                  return false || 'Duplicate Team 2';
                }
                return true;
              },
            ]"
          >
            <template slot="item" slot-scope="{ item }">
              <v-row>
                <v-col>
                  <v-avatar tile>
                    <img :src="baseUrl + item.logo" alt="logo" />
                  </v-avatar>
                </v-col>
                <v-col>
                  {{ item.nameTeam }}
                </v-col>
                <v-col>{{ item.country }}</v-col>
              </v-row>
            </template>
          </v-select>
        </v-col>
        <v-col>
          <v-select
            :items="listTeam"
            item-text="nameTeam"
            item-value="idTeam"
            label="Team 2"
            v-model="selectTeam2"
            :rules="[
              (v) => {
                if (v == null) {
                  return false || 'Team2 required';
                }
                if (v == selectTeam1) {
                  return false || 'Duplicate Team 1';
                }
                return true;
              },
            ]"
          >
            <template slot="item" slot-scope="{ item }">
              <v-row>
                <v-col>
                  <v-avatar tile>
                    <img :src="baseUrl + item.logo" alt="logo" />
                  </v-avatar>
                </v-col>
                <v-col>
                  {{ item.nameTeam }}
                </v-col>
                <v-col>{{ item.country }}</v-col>
              </v-row>
            </template>
          </v-select>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            :return-value.sync="date"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="date"
                label="Picker in date Start"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
                :rules="rulesDate"
              ></v-text-field>
            </template>
            <v-date-picker v-model="date" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu = false"> Cancel </v-btn>
              <v-btn text color="primary" @click="$refs.menu.save(date)">
                OK
              </v-btn>
            </v-date-picker>
          </v-menu></v-col
        >
        <v-col>
          <v-menu
            ref="menuTime"
            v-model="menuTime1"
            :close-on-content-click="false"
            :nudge-right="40"
            :return-value.sync="time"
            transition="scale-transition"
            offset-y
            max-width="290px"
            min-width="290px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="time"
                label="Picker in time"
                prepend-icon="mdi-clock-time-four-outline"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-time-picker
              v-if="menuTime1"
              v-model="time"
              full-width
              @click:minute="$refs.menuTime.save(time)"
            ></v-time-picker> </v-menu
        ></v-col>
      </v-row>
    </v-form>
    <v-btn @click="reset">Reset</v-btn>
    <v-btn @click.prevent="create">Create</v-btn>
  </v-container>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  props: {
    hideDialog: Function,
    getData: Function,
  },
  data() {
    return {
      valid: true,
      rulesDate: [
        (v) => {
          if (v == null) {
            return false || "time required";
          }
          return true;
        },
      ],
      time: null,
      selectTeam2: null,
      selectTeam1: null,
      menuTime1: false,
      listTournament: [],
      selectTournament: "",
      listTeam: [],
      location: "",
      date: null,
      menu: false,
    };
  },
  created() {
    this.getTournament();
  },
  methods: {
    getTournament() {
      this.$store
        .dispatch("tournament/tournamentUpComming")
        .then((response) => {
          this.listTournament = response.data.payload;
        });
    },
    create() {
      this.$store.commit("auth/auth_overlay");
      if (this.$refs.form.validate()) {
        this.$store.commit("auth/auth_overlay");
        this.$store
          .dispatch("schedule/create", {
            idTeam1: this.selectTeam1,
            idTeam2: this.selectTeam2,
            location: this.location,
            idTour: this.selectTournament,
            timeStart: this.date + "T" + this.time,
          })
          .then((response) => {
            if (response.data.payload == 400) {
              alert(response.data.message);
            } else {
              alert(response.data.message);
              this.reset();
              this.getData();
              this.hideDialog();
            }
          })
          .catch(function (error) {
            alert(error);
          });
      } else {
        this.$refs.form.validate();
        this.$store.commit("auth/auth_overlay");
      }
    },
    reset() {
      this.$refs.form.reset();
    },
  },
  watch: {
    selectTournament() {
      this.$store
        .dispatch("tournament/getById", this.selectTournament)
        .then((response) => {
          this.listTeam = response.data.payload.team;
          this.rulesDate = [
            (v) => {
              if (v == null) {
                return false || "time required";
              }
              if (
                v < response.data.payload.timeStart ||
                v > response.data.payload.timeEnd
              ) {
                return false || "Past tournament time";
              } else {
                return true;
              }
            },
          ];
        });
    },
  },
};
</script>