<template>
  <v-container>
    <v-row style="margin-left: 100px; margin-right: 100px">
      <v-col cols="12" sm="2">
        <v-card>
          <v-tabs v-model="tab" grow>
            <v-tab v-for="item in items" :key="item">
              {{ item }}
            </v-tab>
          </v-tabs>

          <v-tabs-items v-model="tab">
            <v-tab-item>
              <v-card>
                <v-card-text>
                  <v-list-item v-for="(n, i) in team1.profile" :key="i">
                    <v-list-item-content>
                      <v-row>
                        <v-col>
                          <v-avatar tile>
                            <img
                              :src="baseUrl + n.avatar"
                              alt="John"
                            /> </v-avatar></v-col
                        ><v-col style="margin-top: 10px">{{ n.name }}</v-col>
                      </v-row></v-list-item-content
                    >
                  </v-list-item>
                </v-card-text>
              </v-card>
            </v-tab-item>

            <v-tab-item>
              <v-card>
                <v-card-text>
                  <v-list-item v-for="(n, i) in team2.profile" :key="i">
                    <v-list-item-content>
                      <v-row>
                        <v-col>
                          <v-avatar tile>
                            <img
                              :src="baseUrl + n.avatar"
                              alt="John"
                            /> </v-avatar></v-col
                        ><v-col style="margin-top: 10px">{{ n.name }}</v-col>
                      </v-row></v-list-item-content
                    >
                  </v-list-item>
                </v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-col>
      <v-col cols="12" sm="6">
        <v-card>
          <v-container>
            <b>Top Scorers</b>
            <hr />
            <v-row>
              <v-col cols="12" sm="6">
                <div class="text-center">
                  <v-avatar size="50"
                    ><img :src="baseUrl + team1.logo" alt="John"
                  /></v-avatar>
                  <h5>{{ team1.nameTeam }}</h5>
                  <v-row>
                    <v-col cols="12" sm="4" v-for="(n,i) in member1" :key="i">
                      <v-card>
                        <v-img
                          lazy-src="https://picsum.photos/id/11/10/6"
                          :src="baseUrl+n.avatar"
                        ></v-img>
                        <b>{{n.name}}</b>
                        Goals:{{ n.history.length }}
                      </v-card>
                    </v-col>
                  </v-row>
                </div>
              </v-col>

              <v-col cols="12" sm="6" style="border-left: solid 1px #eaeaea">
                <div class="text-center">
                  <v-avatar size="50"
                    ><img :src="baseUrl + team2.logo" alt="John"
                  /></v-avatar>
                  <h5>{{ team2.nameTeam }}</h5>
                  <v-row>
                    <v-col cols="12" sm="4" v-for="(n,i) in member2" :key="i">
                      <v-card>
                        <v-img
                          lazy-src="https://picsum.photos/id/11/10/6"
                          :src="baseUrl+n.avatar"
                        ></v-img>
                        <b>{{n.name}}</b>
                        Goals:{{ n.history.length }}
                      </v-card>
                    </v-col>
                  </v-row>
                </div>
              </v-col>
            </v-row>
          </v-container>
        </v-card>
      </v-col>
      <v-col cols="12" sm="4">
        <v-card>
          <v-container>
            <b>{{this.schedule.tournament.nameTournament}}</b>
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-left">POS</th>
                    <th class="text-left">TEAM</th>
                    <th class="text-left">GP</th>
                    <th class="text-left">W</th>
                    <th class="text-left">D</th>
                    <th class="text-left">L</th>
                    <th class="text-left">Point</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, i) in rank" :key="i">
                    <td>{{ i + 1 }}</td>
                    <td>{{ item.nameTeam }}</td>
                    <td>{{ item.totalMatchByTour }}</td>
                    <td>{{ item.totalWinByTour }}</td>
                    <td>{{ item.totalAdrawByTour }}</td>
                    <td>
                      {{
                        item.totalMatchByTour -
                        item.totalWinByTour -
                        item.totalAdrawByTour
                      }}
                    </td>
                    <td>{{ item.pointByTour }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-container>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
      tab: null,
      items: ["Team1", "Team2"],
      desserts: [
        {
          name: "Frozen Yogurt",
          calories: 159,
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
        },
        {
          name: "Eclair",
          calories: 262,
        },
        {
          name: "Cupcake",
          calories: 305,
        },
        {
          name: "Gingerbread",
          calories: 356,
        },
        {
          name: "Jelly bean",
          calories: 375,
        },
        {
          name: "Lollipop",
          calories: 392,
        },
        {
          name: "Honeycomb",
          calories: 408,
        },
        {
          name: "Donut",
          calories: 452,
        },
        {
          name: "KitKat",
          calories: 518,
        },
      ],
      schedule: {},
      team1: {},
      team2: {},
      rank: [],
      member1:[],
      member2:[]
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
          if (response.data.code == 0) {
            this.schedule = response.data.payload;
            this.$store
              .dispatch("team/getHistory", {
                idTeam: this.schedule.idTeam1,
                idTour: this.schedule.idTour,
                idSchedule: this.$route.params.id,
              })
              .then((response) => {
                if (response.data.code == 0) {
                  this.team1 = response.data.payload;
                }
              });
            this.$store
              .dispatch("team/getHistory", {
                idTeam: this.schedule.idTeam2,
                idTour: this.schedule.idTour,
                idSchedule: this.$route.params.id,
              })
              .then((response) => {
                if (response.data.code == 0) {
                  this.team2 = response.data.payload;
                }
              });
               this.$store
              .dispatch("member/getTourGoal",this.schedule.idTeam1 )
              .then((response) => {
                if (response.data.code == 0) {
                  this.member1 = response.data.payload;
                }
              });
               this.$store
              .dispatch("member/getTourGoal",this.schedule.idTeam2 )
              .then((response) => {
                if (response.data.code == 0) {
                  this.member2 = response.data.payload;
                }
              });
            this.$store
              .dispatch(
                "tournament/tournamentRank",
                this.schedule.tournament.idTournament
              )
              .then((response) => {
                if (response.data.code == 0) {
                  this.$store.commit("auth/auth_overlay");
                  this.rank = response.data.payload;
                  console.log(this.rank);
                }
              });
          }
        });
    },
  },
};
</script>