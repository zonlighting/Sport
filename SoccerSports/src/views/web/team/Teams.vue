<template>
  <div>
    <v-card class="mx-auto" max-width="85%">
      <v-row class="container">
        <v-col cols="0" sm="1"></v-col>
        <v-col cols="12" sm="7">
          <v-card-text>
            <v-row>
              <v-col cols="12" sm="6">
                <h1 style="font-weight: bold; color: black">Soccer Teams</h1>
              </v-col>
            </v-row>
            <v-row style="max-height: 60px">
              <h3 class="pl-4">
                {{ tournament.nameTournament }}
                <h5 style="color: green" v-if="tournament.status == 0">
                  (Upcomming Tournament)
                </h5>
                <h5 style="color: blue" v-else-if="tournament.status == 1">
                  (On happening)
                </h5>
                <h5 style="color: red" v-else>(Ended)</h5>
              </h3>
              <v-spacer></v-spacer>
              <v-col cols="12" sm="3">
                <v-select
                  v-model="select"
                  :items="tournaments"
                  item-text="nameTournament"
                  item-value="idTournament"
                  label="Select Tournaments"
                  dense
                  solo
                ></v-select>
              </v-col>
            </v-row>
            <v-divider style="margin: 0 !important"></v-divider>
            <v-row v-if="isHavedata">
              <v-col
                cols="12"
                sm="6"
                v-for="team in tournament.team"
                :key="team.idTeam"
              >
                <v-row>
                  <v-img
                    @click="linkTeamDetail(team, 1)"
                    :src="baseUrl + team.logo"
                    max-width="60"
                    max-height="60"
                    class="ml-3 pointer"
                  ></v-img>
                  <v-col cols="12" sm="5" class="pt-0">
                    <h5
                      @click="linkTeamDetail(team, 1)"
                      class="nameTeam pointer"
                    >
                      {{ team.nameTeam }}
                    </h5>
                    <v-row class="pl-3">
                      <p
                        @click="linkTeamDetail(team, 2)"
                        class="teamlink pointer"
                      >
                        Results
                      </p>
                      <v-divider class="ml-1 mr-1" inset vertical></v-divider>
                      <p
                        @click="linkTeamDetail(team, 3)"
                        class="teamlink pointer"
                      >
                        Squad
                      </p>
                      <v-divider class="ml-1 mr-1" inset vertical></v-divider>
                      <p
                        @click="linkTeamDetail(team, 4)"
                        class="teamlink pointer"
                      >
                        Stats
                      </p>
                    </v-row>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
            <h2 v-else>No Data Available</h2>
          </v-card-text>
        </v-col>
        <v-col cols="12" sm="4">
          <v-row style="height: 107px"></v-row>
          <v-row><RankByTour :tourId="tournament.idTournament" /></v-row
        ></v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import { ENV } from "@/config/env.js";
import RankByTour from "@/views/web/team/RankByTour";

export default {
  components: {
    RankByTour,
  },
  data: () => ({
    select: "",
    isHavedata: true,
    headers: [
      {
        text: "Team",
        align: "start",
        sortable: false,
        value: "name",
      },
      { text: "Rank", value: "calories", sortable: false },
      { text: "Win", value: "protein", sortable: false },
      { text: "Lose", value: "fat", sortable: false },
      { text: "Tie", value: "carbs", sortable: false },
      { text: "Point", value: "", sortable: false },
    ],
    desserts: [],
    tournaments: [],
    tournament: {},
  }),

  mounted() {
    this.getTours();
  },

  computed: {
    tourSelect(value) {
      return this.getTourById(value);
    },

    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  watch: {
    select(newValue) {
      // console.log(newValue, oldValue);
      this.getTourById(newValue);
    },
  },

  methods: {
    getTourById(id) {
      let self = this;
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("tournament/getById", id)
        .then((response) => {
          self.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            self.tournament = response.data.payload;
          } else {
            console.log("Run here 1");
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          console.log("Run here 1");
          alert(error);
        });
    },

    getTours() {
      let self = this;
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("tournament/getAll")
        .then((response) => {
          self.$store.commit("auth/auth_overlay");
          if (response.data.code == 0 && response.data.payload.length > 0) {
            self.tournaments = response.data.payload;
            self.getTourById(self.tournaments[0].idTournament);
          } else {
            self.isHavedata = false;
          }
        })
        .catch(function (error) {
          console.log("Run here 3");
          alert(error);
        });
    },

    linkTeamDetail(team, routerLink) {
      this.$store.commit("team/team_detail", team);
      this.$store.commit("team/tour_id", team.idTour);
      if (routerLink == 1) {
        this.$router.push({
          path: `/fixtures/${team.idTeam}`,
          query: { tourName: this.tournament.nameTournament },
        });
      } else if (routerLink == 2) {
        this.$router.push({
          path: `/results/${team.idTeam}`,
          query: { tourName: this.tournament.nameTournament },
        });
      } else if (routerLink == 3) {
        this.$router.push({
          path: `/squad/${team.idTeam}`,
        });
      } else {
        this.$router.push({
          path: `/stats/${team.idTeam}`,
        });
      }
    },
  },
};
</script>
<style scoped>
.nameTeam {
  color: #151617;
  display: inline;
  font-weight: 600;
  line-height: 26px;
  font-size: 18px;
}

.teamlink {
  color: #06c;
  font-weight: 400;
  font-size: 13px;
}

.my-span {
  color: white;
  font-weight: bold;
}
.pointer {
  cursor: pointer;
}
</style>