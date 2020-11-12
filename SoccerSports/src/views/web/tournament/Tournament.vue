<template>
  <div>
    <v-row class="container">
      <v-col cols="0" sm="1"></v-col>
      <v-col cols="12" sm="7">
        <v-card-text>
          <v-row>
            <v-col cols="12" sm="6">
              <h1 style="font-weight: bold; color: black">Soccer Tournament</h1>
            </v-col>
          </v-row>
          <v-row style="max-height: 60px">
            <v-spacer></v-spacer>
            <v-col cols="12" sm="4">
              <v-select
                v-model="select"
                :items="value"
                item-text="text"
                item-value="id"
                label="Select Status"
                dense
                solo
              ></v-select>
            </v-col>
          </v-row>
          <v-divider style="margin: 0 !important"></v-divider>
          <v-row v-for="(item, i) in tournament" :key="i">
            <v-card width="900px" height="200px">
              <v-row>
                <v-col cols="12" sm="4">
                  <v-img
                    lazy-src="https://picsum.photos/id/11/10/6"
                    max-height="150"
                    max-width="250"
                    :src="baseUrl + item.banner"
                  ></v-img>
                </v-col>
                <v-col>
                  <router-link
                    :to="{ path: '/tournamentDetail/' + item.idTournament }"
                    style="color: black"
                  >
                    <h1>{{ item.nameTournament }}</h1></router-link
                  >
                  <h4
                    :style="
                      item.status == 0
                        ? 'color:green'
                        : item.status == 1
                        ? 'color:blue'
                        : 'red'
                    "
                  >
                    {{
                      item.status == 0
                        ? "UpComming"
                        : item.status == 1
                        ? "OnGame"
                        : "Ended"
                    }}
                  </h4>
                  <h5>
                    <router-link
                      :to="{
                        path:
                          '/tournamentDetail/' +
                          item.idTournament +
                          '/fixtures',
                      }"
                      >Fixtures||</router-link
                    >
                    <router-link
                      :to="{
                        path:
                          '/tournamentDetail/' + item.idTournament + '/results',
                      }"
                      >Results</router-link
                    >
                    <router-link
                      :to="{
                        path:
                          '/tournamentDetail/' + item.idTournament + '/team',
                      }"
                      >||Table</router-link
                    >
                  </h5>
                </v-col>
              </v-row>
            </v-card>
          </v-row>
        </v-card-text>
      </v-col>
      <v-col cols="12" sm="4">
        <v-row style="height: 107px"></v-row>
        <v-row>
          <v-img
            src="https://i.pinimg.com/originals/dc/95/9f/dc959fd2539127fcd5a1ca32715551cc.jpg"
          ></v-img>
        </v-row>
      </v-col>
    </v-row>
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
    value: [
      { id: 3, text: "Select Status" },
      { id: 0, text: "Tournament Up Comming" },
      { id: 1, text: "Tournament On Game" },
      { id: 2, text: "Tournament Finished" },
    ],
    desserts: [],
    tournament: [],
  }),

  mounted() {
    this.getTournament();
  },

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  methods: {
    getTournament() {
      this.$store.commit("auth/auth_overlay_true");
      this.$store.dispatch("tournament/getAll").then((response) => {
        this.$store.commit("auth/auth_overlay_false");
        this.tournament = response.data.payload;
      });
    },
  },
  watch: {
    select() {
      if (this.select == 3) {
        this.getTournament();
      } else {
        this.$store.commit("auth/auth_overlay_true");
        this.$store
          .dispatch("tournament/tournamentStatus", this.select)
          .then((response) => {
            this.$store.commit("auth/auth_overlay_false");
            this.tournament = response.data.payload;
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
</style>