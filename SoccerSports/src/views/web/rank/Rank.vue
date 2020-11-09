<template>
  <v-container>
    <v-row style="margin-left: 100px; margin-right: 100px">
      <v-col cols="12" sm="2"> </v-col>
      <v-col cols="12" sm="8">
        <v-card>
          <v-row>
            <v-col cols="12" sm="6">
              <h1 style="font-weight: bold; color: black">Rank Tournament</h1>
            </v-col>
          </v-row>
          <v-row style="max-height: 60px">
            <v-spacer></v-spacer>
            <v-col cols="12" sm="4">
              <v-select
                v-model="select"
                :items="tournament"
                item-text="nameTournament"
                item-value="idTournament"
                label="Select Status"
                dense
                solo
              ></v-select>
            </v-col>
          </v-row>
          <v-divider style="margin: 0 !important"></v-divider>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">#</th>
                  <th class="text-left">Team</th>
                  <th class="text-left">Logo</th>
                  <th class="text-left">GP</th>
                  <th class="text-left">Win</th>
                  <th class="text-left">ADRAW</th>
                  <th class="text-left">LOSE</th>
                  <th class="text-left">Point</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in rank" :key="index" :style="index==0?'background: red':index==1?'background: green':index==2?'background: yellow':''">
                  <td>
                    {{ index + 1 }}
                  </td>
                  <td>{{ item.nameTeam }}</td>
                  <td>
                    <v-avatar tile>
                      <img :src="baseUrl + item.logo" alt="John" />
                    </v-avatar>
                  </td>
                  <td>{{ item.totalMatchByTour }}</td>
                  <td>{{ item.totalWinByTour }}</td>
                  <td>{{ item.totalAdrawByTour }}</td>
                  <td>
                    {{
                      item.totalMatchByTour -
                      item.totalAdrawByTour -
                      item.totalWinByTour
                    }}
                  </td>
                  <td>{{ item.pointByTour }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
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
      rank: "",
      tournament:"",
      select:''
    };
  },
  created() {
    this.getData();
    this.getTournament();
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  methods: {
    getData() {
      this.$store.commit("auth/auth_overlay");
      this.$store.dispatch("tournament/tournamentRank", 1).then((response) => {
        this.$store.commit("auth/auth_overlay");
        if (response.data.code == 0) {
          this.rank = response.data.payload;
        }
      });
    },
    getTournament(){
         this.$store.commit("auth/auth_overlay");
      this.$store.dispatch("tournament/getAll").then((response) => {
        this.$store.commit("auth/auth_overlay");
        if (response.data.code == 0) {
          this.tournament = response.data.payload;
        }
      });
    }
  },
  watch:{
      select(){
          this.$store.commit("auth/auth_overlay");
    this.$store
      .dispatch("tournament/tournamentRank", this.select)
      .then((response) => {
        this.$store.commit("auth/auth_overlay");
        if (response.data.code == 0) {
          this.rank = response.data.payload;
        }
      });
      }
  }
};
</script>