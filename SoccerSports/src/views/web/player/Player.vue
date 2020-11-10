<template>
  <div>
    <v-row class="pl-10">
      <v-col cols="11" md="2" xm="2"></v-col>
      <v-col cols="11" md="9" xm="9">
        <v-row>
          <v-col cols="6" md="1">
            <v-img
              max-height="120"
              max-width="120"
              :src="baseUrl + playerProfile.avatar"
            ></v-img>
          </v-col>
          <v-col cols="6" md="3">
            <v-toolbar-title>
              <h1 style="font-weight: 500; line-height: 34px; color: #2b2c2d">
                {{ playerProfile.name }}
              </h1>
              <v-row class="mt-2">
                <v-img
                  class="ml-4"
                  max-height="30"
                  max-width="30"
                  :src="baseUrl + team.logo"
                ></v-img>
                {{ team.nameTeam }}
              </v-row>
              <h5 class="pt-3" style="font-size: 18px; font-weight: 400">
                {{ playerStatus.pos }}
              </h5>
            </v-toolbar-title>
          </v-col>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-col cols="6" md="2" class="pt-6">
            <p class="status-player">
              Height/Weight: {{ playerStatus.height }},
              {{ playerStatus.weight }}
            </p>
            <p class="status-player">Age: {{ playerStatus.age }}</p>
            <p class="status-player">Country: {{ playerStatus.nation }}</p>
          </v-col>
          <v-col>
            <v-card class="mx-auto" max-width="600">
              <v-row style="max-height: 30px">
                <v-col cols="4"></v-col>
                <v-col
                  style="
                    color: black;
                    font-weight: 600;
                    line-height: 34px;
                    font-size: 24px;
                  "
                  cols="5"
                  >2020 MLS STATS</v-col
                >
                <v-col cols="4"></v-col>
              </v-row>
              <v-divider style="margin-bottom: 0 !important"></v-divider>
              <v-row>
                <v-col cols="1"></v-col>
                <v-col cols="2">
                  <h5>Goals</h5>
                  <p class="statusStyle pl-3">{{ playerStatus.goal }}</p>
                </v-col>
                <v-col cols="2"
                  ><h5>Saves</h5>
                  <p class="statusStyle pl-3">{{ playerStatus.save }}</p></v-col
                >
                <v-col cols="2">
                  <h5>Assists</h5>
                  <p class="statusStyle pl-3">
                    {{ playerStatus.assists }}
                  </p></v-col
                >
                <v-col cols="2"
                  ><h5>Y Card</h5>
                  <p class="statusStyle pl-3">{{ playerStatus.yc }}</p></v-col
                >
                <v-col cols="2"
                  ><h5>R Card</h5>
                  <p class="statusStyle pl-3">{{ playerStatus.rc }}</p></v-col
                >
                <v-col cols="1"></v-col>
              </v-row>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <v-divider style="margin: 0 !important"></v-divider>
    <v-row class="pl-6">
      <v-col cols="12" md="2" xm="2"></v-col>
      <v-col> </v-col>
    </v-row>
  </div>
</template>

<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
      idPlayer: this.$route.params.id,
      playerStatus: this.$store.state.member.playerProfile,
      playerProfile: {},
      team: {},
    };
  },
  mounted() {
    console.log(this.$route.params.id);
    console.log(this.$store.state.member.playerProfile);
    this.getPlayer(this.idPlayer);
  },

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  methods: {
    getPlayer(id) {
      let self = this;
      this.$store
        .dispatch("member/getPlayerById", id)
        .then((response) => {
          if (response.data.code == 0) {
            console.log(response.data.payload);
            self.playerProfile = response.data.payload;
            self.getTeamById(self.playerProfile.idTeam);
          } else {
            console.log("Run here player");
            // alert(response.data.message);
          }
        })
        .catch(function (error) {
          console.log("Run here player");
          alert(error);
        });
    },

    getTeamById(id) {
      let self = this;
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("team/getTeamById", id)
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          console.log(response.data.payload);
          self.team = response.data.payload;
        })
        .catch(function (error) {
          alert(error);
        });
    },
  },
};
</script>
<style>
.status-player {
  color: #6c6d6f;
  font-weight: 600;
  line-height: 21px;
  font-size: 16px;
}
.statusStyle {
  font-weight: 600;
  line-height: 34px;
  font-size: 24px;
}
</style>