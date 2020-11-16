<template>
  <div>
    <v-card class="mx-auto" max-width="85%">
      <v-row class="container">
        <v-col cols="0" sm="1"></v-col>
        <v-col cols="10">
          <v-card-text>
            <v-row>
              <v-col cols="12" sm="6">
                <h1 class="title-h1">{{ team.nameTeam }} Squad</h1>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" sm="3">
                <v-select
                  class="p-0"
                  v-model="select"
                  :items="tournaments"
                  item-text="nameTournament"
                  item-value="idTournament"
                  label="All Competitions"
                  dense
                  solo
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <h5 class="table__Title">Goalkeepers</h5>
              <v-divider style="margin: 0 !important"></v-divider>
              <v-row v-if="isHavedata">
                <v-col>
                  <v-data-table
                    @click:row="handleRowClick"
                    :headers="headers"
                    :items="desserts"
                    class="elevation-1 row-pointer"
                    hide-default-footer
                    :items-per-page="15"
                  >
                    <template v-slot:[`item.name`]="{ item }">
                      <p class="pt-3" style="color: blue">
                        {{ item.name }}
                      </p>
                    </template>
                  </v-data-table>
                </v-col>
              </v-row>
              <h2 v-else>No Data Available</h2>
              <h5 class="table__Title mt-10">Outfield Players</h5>
              <v-divider style="margin: 0 !important"></v-divider>
              <v-row v-if="isHavedata">
                <v-col>
                  <v-data-table
                    @click:row="handleRowClick"
                    :headers="headers1"
                    :items="desserts1"
                    class="elevation-1 row-pointer"
                    hide-default-footer
                    :items-per-page="15"
                  >
                    <template v-slot:[`item.name`]="{ item }">
                      <p class="pt-3" style="color: blue">
                        {{ item.name }}
                      </p>
                    </template>
                  </v-data-table>
                </v-col>
              </v-row>
              <h2 v-else>No Data Available</h2>
            </v-row>
          </v-card-text>
        </v-col>
        <v-col cols="0" sm="1"></v-col>
      </v-row>
    </v-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tournaments: [],
      team: {},
      select: "",
      isHavedata: true,
      headers: [
        {
          text: "Name",
          align: "start",
          value: "name",
        },
        { text: "Position", value: "pos" },
        { text: "Age", value: "age" },
        { text: "Height", value: "height" },
        { text: "Weight", value: "weight" },
        { text: "Nation", value: "nation" },
        { text: "Appearances", value: "played" },
        { text: "Catch", value: "ga" },
        { text: "Saves", value: "save" },
        { text: "Assists", value: "assists" },
        { text: "Fouls", value: "fc" },
        { text: "Yellow Card", value: "yc" },
        { text: "Red Card", value: "rc" },
      ],

      headers1: [
        {
          text: "Name",
          align: "start",
          value: "name",
        },
        { text: "Position", value: "pos" },
        { text: "Age", value: "age" },
        { text: "Height", value: "height" },
        { text: "Weight", value: "weight" },
        { text: "Nation", value: "nation" },
        { text: "Appearances", value: "played" },
        { text: "Goals", value: "goal" },
        { text: "Saves", value: "save" },
        { text: "Assists", value: "assists" },
        { text: "Fouls", value: "fc" },
        { text: "Yellow Card", value: "yc" },
        { text: "Red Card", value: "rc" },
      ],
      desserts: [],
      desserts1: [],
      goalkeepers: [],
      outfield: [],
    };
  },

  mounted() {
    // console.log(this.$route)
    if (this.$route.params.id == undefined) {
      alert("Team: " + this.$route.params.id);
      this.$router.push({ path: `/teams` });
    } else {
      this.getTeamById(this.$route.params.id);
    }
  },

  watch: {
    select(newValue) {
      // console.log(newValue);
      if (newValue != undefined) {
        this.getSquad(this.team.idTeam, newValue);
      }
    },
  },

  methods: {
    getSquad(teamValue, tourValue) {
      let self = this;
      this.$store.commit("auth/auth_overlay_true");
      this.$store
        .dispatch("team/squad", {
          idTeam: teamValue,
          idTour: tourValue,
        })
        .then((response) => {
          self.$store.commit("auth/auth_overlay_false");
          if (response.data.code == 0) {
            let list = response.data.payload;
            console.log(list.length);
            if (list.length > 0) {
              self.desserts = list.filter((d) => {
                return d.pos === "Goalkeepers";
              });
              self.desserts1 = list.filter((d) => {
                return d.pos !== "Goalkeepers";
              });
            } else {
              // self.getMemberByTeam(teamValue);
            }
          } else {
            console.log("Run here teamSquad");
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          console.log("Run here teamSquad");
          alert(error);
        });
    },

    getMemberByTeam(idTeam) {
      let self = this;
      this.$store.commit("auth/auth_overlay_true");
      this.$store
        .dispatch("member/members", idTeam)
        .then((response) => {
          if (response.data.code == 0) {
            let list = response.data.payload;
            // console.log(list)
            self.desserts = list.filter((d) => {
              return d.pos === "Goalkeepers";
            });
            self.desserts1 = list.filter((d) => {
              return d.pos !== "Goalkeepers";
            });
          } else {
            console.log("Run here teamSquad");
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },

    getTours(idTeam) {
      let self = this;
      this.$store.commit("auth/auth_overlay_true");
      this.$store
        .dispatch("team/toursByTeam", idTeam)
        .then((response) => {
          self.$store.commit("auth/auth_overlay_false");
          if (response.data.code == 0) {
            self.tournaments = response.data.payload;
          } else {
            console.log("Squad");
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },

    getTeamById(id) {
      let self = this;
      this.$store.commit("auth/auth_overlay_true");
      this.$store
        .dispatch("team/getTeamById", id)
        .then((response) => {
          self.$store.commit("auth/auth_overlay_false");
          self.team = response.data.payload;
          self.getTours(self.team.idTeam);
          self.getSquad(self.team.idTeam, self.team.idTour);
        })
        .catch(function (error) {
          alert(error);
        });
    },

    handleRowClick(item) {
      this.$store.commit("member/player_profile", item);
      this.$router.push({ path: `/player/${item.idMember}` });
    },
  },
};
</script>
<style >
.title-h1 {
  padding-left: 20px;
  font-size: 28px;
  font-weight: 700;
  color: #2b2c2d;
}
.table__Title {
  position: relative;
  text-align: left;
  text-transform: capitalize;
  color: #2b2c2d;
  font-size: 16px;
  font-weight: 600;
  line-height: 21px;
  margin-bottom: 8px;
  margin-top: 8px;
  padding-left: 21px;
}
</style>
<style lang="css" scoped>
.row-pointer >>> tbody tr :hover {
  cursor: pointer;
}
</style>