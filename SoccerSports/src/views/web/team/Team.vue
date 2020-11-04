<template>
  <div>
    <div>
      <img
        src="http://max-themes.net/demos/bigslam/soccer3/upload/page-title.jpg"
        class="md-bg"
        alt="Slide background"
        style="margin-left: 60px"
      />
      <div style="position: absolute; top: 320px; left: 800px">
        <p style="font-size: 70px; color: #ffffff">Teams</p>
      </div>
    </div>
    <v-row class="container ml-15">
      <v-col cols="12" sm="8">
        <v-card>
          <v-card-text>
            <v-row>
              <v-col cols="12" sm="6">
                <h1 style="font-weight: bold; color: black">Soccer Teams</h1>
              </v-col>
              <v-spacer></v-spacer>
              <v-col cols="12" sm="3">
                <v-select
                  :items="items"
                  label="Solo field"
                  dense
                  solo
                ></v-select></v-col
            ></v-row>

            <v-divider class="my-2"></v-divider>

            <v-row>
              <v-col cols="12" sm="6" v-for="team in teams" :key="team.idTeam">
                <v-row>
                  <v-img
                    src="https://picsum.photos/510/300?random"
                    max-width="50"
                    max-height="50"
                    class="ml-3"
                  ></v-img>
                  <v-col cols="12" sm="5" class="pt-0">
                    <a>
                      <h5 class="nameTeam">{{team.nameTeam}}</h5>
                    </a>
                    <v-row class="pl-3">
                      <a><p class="teamlink">Results</p></a>
                      <v-divider class="ml-1 mr-1" inset vertical></v-divider>
                      <a><p class="teamlink">Squad</p></a>
                      <v-divider class="ml-1 mr-1" inset vertical></v-divider>
                      <a><p class="teamlink">Stats</p></a>
                    </v-row>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-card-text>

          <v-divider></v-divider>

          <!-- <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="success" depressed> Post </v-btn>
          </v-card-actions> -->
        </v-card>
      </v-col>
      <v-col cols="12" sm="4">
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
      </v-col>
    </v-row>
  </div>
</template>
<script>
export default {
  data: () => ({
    items: ["Foo", "Bar", "Fizz", "Buzz"],
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
    teams:[]
  }),
  methods: {
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
</style>