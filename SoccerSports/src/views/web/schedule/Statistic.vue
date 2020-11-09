<template>
  <v-container>
    <v-row style="margin-left: 100px; margin-right: 100px">
      <v-col cols="12" sm="2"> </v-col>
      <v-col cols="12" sm="8">
        <v-card>
          <v-container>
            <b>Match Stats</b>
            <hr />
            <v-simple-table>
              <template v-slot:default width="650px">
                <thead>
                  <tr>
                    <th class="text-left">
                      <v-avatar size="50"
                        ><img :src="baseUrl + schedule.team[0].logo" alt="John"
                      /></v-avatar>
                      {{ !!schedule ? schedule.team[0].nameTeam : "" }}
                    </th>
                    <th class="text-left" width="500px"></th>
                    <th class="text-right">
                      {{ !!schedule ? schedule.team[1].nameTeam : ""
                      }}<v-avatar size="50"
                        ><img :src="baseUrl + schedule.team[1].logo" alt="John"
                      /></v-avatar>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td class="text-center">
                      {{ detailTeam1.totalMatchByTour }}
                    </td>
                    <td class="text-center">Total Match</td>
                    <td class="text-center">
                      {{ detailTeam2.totalMatchByTour }}
                    </td>
                  </tr>
                  <tr>
                    <td class="text-center">
                      {{ detailTeam1.totalWinByTour }}
                    </td>
                    <td class="text-center">Total Win</td>
                    <td class="text-center">
                      {{ detailTeam2.totalWinByTour }}
                    </td>
                  </tr>
                  <tr>
                    <td class="text-center">
                      {{ detailTeam1.totalAdrawByTour }}
                    </td>
                    <td class="text-center">Total Draw</td>
                    <td class="text-center">
                      {{ detailTeam2.totalAdrawByTour }}
                    </td>
                  </tr>
                  <tr>
                    <td class="text-center">
                      {{ detailTeam1.pointByTour }}
                    </td>
                    <td class="text-center">Point</td>
                    <td class="text-center">
                      {{ detailTeam2.pointByTour }}
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-container>
        </v-card>
        <v-card style="margin-top: 50px">
          <v-container>
            <b
              ><v-avatar size="50"
                ><img :src="baseUrl + schedule.team[0].logo" alt="John"
              /></v-avatar>
              Form</b
            >
            <hr />
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-left"></th>
                    <th class="text-left">Results</th>
                    <th class="text-left"></th>
                    <th class="text-left">Date</th>
                    <th class="text-left">Location</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, i) in teamResults1" :key="i">
                    <td>{{ item.team[0].nameTeam }}</td>
                    <td>
                      <v-avatar size="50"
                        ><img :src="baseUrl + item.team[0].logo" alt="John"
                      /></v-avatar>
                      {{ item.status == 2 ? item.score1 : "" }}-{{
                        item.status == 2 ? item.score2 : ""
                      }}
                      <v-avatar size="50"
                        ><img :src="baseUrl + item.team[1].logo" alt="John"
                      /></v-avatar>
                    </td>
                    <td>{{ item.team[1].nameTeam }}</td>
                    <td>
                      {{ new Date(item.timeStart).toString().substring(0, 16) }}
                    </td>
                    <td>{{ item.location }}</td>
                    <td>
                      <router-link :to="'/scheduleDetail/' + item.idSchedule">
                        <v-icon>mdi-chevron-double-right</v-icon></router-link
                      >
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-container>
        </v-card>
        <v-card style="margin-top: 50px">
          <v-container>
            <b
              ><v-avatar size="50"
                ><img :src="baseUrl + schedule.team[1].logo" alt="John"
              /></v-avatar>
              Form</b
            >
            <hr />
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-left"></th>
                    <th class="text-left">Results</th>
                    <th class="text-left"></th>
                    <th class="text-left">Date</th>
                    <th class="text-left">Location</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, i) in teamResults2" :key="i">
                    <td>{{ item.team[0].nameTeam }}</td>
                    <td>
                      <v-avatar size="50"
                        ><img :src="baseUrl + item.team[0].logo" alt="John"
                      /></v-avatar>
                      {{ item.status == 2 ? item.score1 : "" }}-{{
                        item.status == 2 ? item.score2 : ""
                      }}
                      <v-avatar size="50"
                        ><img :src="baseUrl + item.team[1].logo" alt="John"
                      /></v-avatar>
                    </td>
                    <td>{{ item.team[1].nameTeam }}</td>
                    <td>
                      {{ new Date(item.timeStart).toString().substring(0, 16) }}
                    </td>
                    <td>{{ item.location }}</td>
                    <td>
                      <router-link :to="'/scheduleDetail/' + item.idSchedule">
                        <v-icon>mdi-chevron-double-right</v-icon></router-link
                      >
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-container>
        </v-card>
      </v-col>
      <v-col cols="12" sm="4">
        <v-card> </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
      schedule: {},
      detailTeam1: [],
      detailTeam2: [],

      teamResults1: [],
      teamResults2: [],
    };
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("schedule/getById", this.$route.params.id)
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            this.schedule = response.data.payload;

            this.$store
              .dispatch("team/getDetail", {
                idTeam: this.schedule.team[1].idTeam,
                idTournament: this.schedule.tournament.idTournament,
              })
              .then((response) => {
                this.detailTeam2 = response.data.payload;
              });
            this.$store
              .dispatch("team/getDetail", {
                idTeam: this.schedule.team[0].idTeam,
                idTournament: this.schedule.tournament.idTournament,
              })
              .then((response) => {
                this.detailTeam1 = response.data.payload;
              });
            this.$store
              .dispatch("schedule/teamResults", {
                idTeam: this.schedule.team[0].idTeam,
                idTournament: this.schedule.tournament.idTournament,
              })
              .then((response) => {
                this.teamResults1 = response.data.payload;
              });
            this.$store
              .dispatch("schedule/teamResults", {
                idTeam: this.schedule.team[0].idTeam,
                idTournament: this.schedule.tournament.idTournament,
              })
              .then((response) => {
                this.teamResults2 = response.data.payload;
              });
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },
  },
};
</script>