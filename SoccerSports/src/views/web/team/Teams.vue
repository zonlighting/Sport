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
            <v-row style="max-height:60px">
              <h3 class="pl-4">{{ tournament.nameTournament }}</h3>
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
            <v-divider style="margin:0 !important"></v-divider>
            <v-row v-if="isHavedata">
              <v-col
                cols="12"
                sm="6"
                v-for="team in tournament.team"
                :key="team.idTeam"
              >
                <v-row>
                  <router-link :to="{name: 'fixtures', params: { teamId: team.idTeam }}">
                  <v-img
                    :src="baseUrl + team.logo"
                    max-width="50"
                    max-height="50"
                    class="ml-3"
                  ></v-img>
                  </router-link>
                  <v-col cols="12" sm="5" class="pt-0">
                    <router-link :to="{name: 'fixtures', params: { teamId: team.idTeam }}">
                      <h5 class="nameTeam">{{ team.nameTeam }}</h5>
                    </router-link>
                    <v-row class="pl-3">
                      <router-link :to="{name: 'results', params: { teamId: team.idTeam }}"><p class="teamlink">Results</p></router-link>
                      <v-divider class="ml-1 mr-1" inset vertical></v-divider>
                      <router-link :to="{name: 'squad', params: { teamId: team.idTeam }}"><p class="teamlink">Squad</p></router-link>
                      <v-divider class="ml-1 mr-1" inset vertical></v-divider>
                      <router-link :to="{name: 'stats', params: { teamId: team.idTeam }}"><p class="teamlink">Stats</p></router-link>
                    </v-row>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
            <h2 v-else>No Data Available</h2>
          </v-card-text>

          <!-- <v-divider></v-divider> -->

          <!-- <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="success" depressed> Post </v-btn>
          </v-card-actions> -->
        </v-col>
        <v-col cols="12" sm="4">
          <v-row style="height: 107px"></v-row>
          <v-row>
            <v-data-table
              :headers="headers"
              :items="desserts"
              class="elevation-1"
              :items-per-page="15"
            >
              <template v-slot:item.calories="{ item }">
                <v-chip :color="getColor(item.calories)" dark>
                  {{ item.calories }}
                </v-chip>
              </template>
            </v-data-table>
          </v-row>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import { ENV } from "@/config/env.js";

export default {
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
    select(newValue, oldValue) {
      console.log(newValue, oldValue);
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
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            self.tournament = response.data.payload;
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },

    getTours() {
      let self = this;
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("tournament/getAll")
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0 && response.data.payload.length > 0) {
            self.tournaments = response.data.payload;
            self.getTourById(self.tournaments[0].idTournament);
          } else {
            self.isHavedata = false;
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },

    getColor(calories) {
      if (calories > 400) return "red";
      else if (calories > 200) return "orange";
      else return "green";
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
</style>