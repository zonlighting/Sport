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
                  :src="baseUrl + currentTeam.logo"
                ></v-img>
                {{ currentTeam.nameTeam }}
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
                    line-height: 10px;
                    font-size: 18px;
                  "
                  cols="5"
                  >{{ year }} MLS STATS</v-col
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
      <v-col cols="12" md="2" xm="2">
        <v-card max-width="374">
          <v-card-title class="card-title">Switch Player </v-card-title>
          <v-divider class="mx-5" style="margin-top: 0 !important"></v-divider>
          <v-select
            v-model="teamSelect"
            :items="teams"
            item-text="nameTeam"
            item-value="idTeam"
            class="mx-6"
            label="Select Team"
            dense
            solo
          ></v-select>
          <v-select
            v-model="positionSelect"
            :items="defaultPosition"
            class="mx-6"
            label="Position"
            dense
            solo
          ></v-select>

          <template v-if="team != null">
            <div v-for="(member, index) in team.profile" :key="index">
              <template v-if="member.position == positionSelect && index < 5">
                <v-row class="ml-6">
                  <v-avatar
                    style="border: 1px solid grey"
                    class="pr-3"
                    size="48"
                  >
                    <v-img
                      :src="baseUrl + member.avatar"
                      max-width="60"
                      max-height="60"
                      class="ml-3 pointer"
                    ></v-img>
                  </v-avatar>
                  <div>
                    <p
                      style="
                        margin-bottom: 0px;
                        color: #2b2c2d;
                        font-weight: 600;
                        font-size: 14px;
                      "
                    >
                      {{ member.name }}
                    </p>
                    <p style="font-size: 12px">Age: {{ member.age }}</p>
                  </div>
                </v-row>
              </template>
            </div>
          </template>
          <v-divider style="margin: 0 !important"></v-divider>
          <h5 @click="toSquad" class="center" style="color: blue">
            Full Squad
          </h5>
        </v-card>
      </v-col>
      <v-col cols="12" md="6" xm="6">
        <v-card>
          <v-card-title class="card-title">Next Match</v-card-title>
          <v-divider class="mx-5" style="margin: 0 !important"></v-divider>
          <template v-if="nextMatch != null">
            <v-card-subtitle class="center">
              <h3>{{ nextMatch.year }} {{ nextMatch.nameTour }}</h3>
            </v-card-subtitle>
            <v-card-text>
              <v-row>
                <v-col cols="3"></v-col>
                <h4>{{ nextMatch.nameTeam1 }}</h4>
                <v-img
                  class="ml-4"
                  max-height="50"
                  max-width="50"
                  :src="baseUrl + nextMatch.logoTeam1"
                ></v-img>
                <div class="px-15">
                  <h5 style="margin-bottom: 0">{{ nextMatch.dayStart }}</h5>
                  <h5>{{ nextMatch.timeStart }}</h5>
                </div>
                <v-img
                  class="mr-4"
                  max-height="50"
                  max-width="50"
                  :src="baseUrl + nextMatch.logoTeam2"
                ></v-img>
                <h4>{{ nextMatch.nameTeam2 }}</h4>
              </v-row>
            </v-card-text>
          </template>
          <template v-else>
            <v-card-subtitle class="center"
              >PLayer Don't Have Schedule
            </v-card-subtitle>
          </template>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { ENV } from "@/config/env.js";
var d = new Date();
export default {
  data() {
    return {
      teamSelect: "",
      positionSelect: "Goalkeepers",
      idPlayer: this.$route.params.id,
      playerStatus: this.$store.state.member.playerProfile,
      playerProfile: {},
      currentTeam: this.$store.state.team.teamDetail,
      team: this.$store.state.team.teamDetail,
      year: "",
      teams: [],
      defaultPosition: ["Goalkeepers", "Defenders", "Midfielders", "Forwards"],
      nextMatch: {},
    };
  },
  mounted() {
    console.log(this.$route.params.id);
    // console.log(this.$store.state.member.playerProfile);
    // console.log(this.$store.state.team.teamDetail);
    this.getPlayer(this.idPlayer);
    this.getYear();
    this.getTeams();
    this.getNextMatch(this.$route.params.id);
  },

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  watch: {
    teamSelect(newValue) {
      // console.log(newValue, oldValue);
      this.team = this.getTeamById(newValue);
    },

    positionSelect(newValue) {
      this.positionSelect = newValue;
    },
  },

  methods: {
    getPlayer(id) {
      let self = this;
      this.$store
        .dispatch("member/getPlayerById", id)
        .then((response) => {
          if (response.data.code == 0) {
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
          self.team = response.data.payload;
        })
        .catch(function (error) {
          alert(error);
        });
    },

    getTeams() {
      let self = this;
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("team/getTeams")
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code === 0) {
            self.teams = response.data.payload;
            // console.log(self.desserts)
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },

    getNextMatch(id) {
      let self = this;
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("member/nextMatch", id)
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          self.nextMatch = response.data.payload;
          console.log(self.nextMatch);
        })
        .catch(function (error) {
          alert(error);
        });
    },

    getYear() {
      this.year = d.getFullYear();
    },

    toSquad() {
      this.$router.push({
        path: `/squad/${this.team.idTeam}`,
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
.card-title {
  color: #151617;
  font-size: 16px;
  font-weight: 800;
  line-height: 12px;
}
.center {
  margin: auto;
  border: 1px solid gray;
  padding: 10px;
}
</style>