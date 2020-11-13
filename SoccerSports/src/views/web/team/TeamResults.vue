<template>
  <div>
    <v-card class="mx-auto" max-width="85%">
      <v-row class="container">
        <v-col cols="12" sm="8">
          <v-card-text>
            <v-row class="mb-5">
              <v-col cols="12" sm="6">
                <h1 class="title-h1">{{ team.nameTeam }} Result</h1>
              </v-col>
            </v-row>
            <h4 class="pl-5">{{ $route.query.tourName }}</h4>
            <v-divider style="margin: 0 !important"></v-divider>
            <template v-if="schedules.length > 0">
              <div v-for="(item, index) in schedules" :key="index">
                <template v-if="getFilter(item.teamSchedules).length > 0">
                  <h5 class="table__Title">{{ item.monthStart }}</h5>
                  <v-row>
                    <v-col>
                      <v-data-table
                        :headers="headers"
                        :items="getFilter(item.teamSchedules)"
                        class="elevation-1 row-pointer"
                        @click:row="handleRowClick"
                        hide-default-footer
                        :items-per-page="15"
                      >
                        <template v-slot:[`item.nameTeam1`]="{ item }">
                          <p
                            class="pt-3"
                            style="color: red"
                            v-if="item.score1 > item.score2"
                          >
                            {{ item.nameTeam1 }}
                          </p>
                          <h5 v-else>
                            {{ item.nameTeam1 }}
                          </h5>
                        </template>
                        <template v-slot:[`item.logoTeam1`]="{ item }">
                          <img
                            :src="baseUrl + item.logoTeam1"
                            width="70px"
                            height="50px"
                            style="margin: 3px 0 3px 0"
                          />
                        </template>
                        <template v-slot:[`item.logoTeam2`]="{ item }">
                          <img
                            :src="baseUrl + item.logoTeam2"
                            width="70px"
                            height="50px"
                            style="margin: 3px 0 3px 0"
                          />
                        </template>
                        <template v-slot:[`item.nameTeam2`]="{ item }">
                          <p
                            class="pt-3"
                            style="color: red"
                            v-if="item.score1 < item.score2"
                          >
                            {{ item.nameTeam2 }}
                          </p>
                          <h5 v-else>
                            {{ item.nameTeam2 }}
                          </h5>
                        </template>
                        <template v-slot:[`item.status`]="{}">
                          <p class="pt-3" style="color: red">Ended</p>
                        </template>
                        <template v-slot:[`item.score`]="{ item }">
                          <h4>{{ item.score1 }}-{{ item.score2 }}</h4>
                        </template>
                      </v-data-table>
                    </v-col>
                  </v-row>
                </template>
              </div>
            </template>
            <template v-else> <h4>No Match Available</h4></template>
          </v-card-text>
        </v-col>
        <v-col cols="12" sm="4">
          <v-row style="height: 107px"></v-row>
          <v-row><RankByTour :tourId="parseInt(idTour)" /></v-row
        ></v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import RankByTour from "@/views/web/team/RankByTour";
import { ENV } from "@/config/env.js";

export default {
  components: {
    RankByTour,
  },
  data() {
    return {
      team: {},
      schedules: {},
      headers: [
        {
          text: "DATE",
          align: "start",
          sortable: false,
          value: "dayStart",
        },
        { text: "", value: "nameTeam1", sortable: false },
        { text: "", value: "logoTeam1", sortable: false },
        { text: "MATCH", value: "score", sortable: false },
        { text: "", value: "logoTeam2", sortable: false },
        { text: "", value: "nameTeam2", sortable: false },
        { text: "TIME", value: "timeStart", sortable: false },
        { text: "COMPETITION", value: "nameTour", sortable: false },
        { text: "STATUS", value: "status", sortable: false },
      ],
      idTour: 0
    };
  },

  mounted() {
    // console.log(this.$route)
    if (this.$route.params.id != undefined) {
      this.getTeamById(this.$route.params.id);
      this.getMatchsByTeamId(this.$route.params.id);
      // console.log(this.team);
      // this.$store.commit("team/team_detail", this.team);
    }
  },

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  methods: {
    getMatchsByTeamId(id) {
      let self = this;
      this.$store.commit("auth/auth_overlay_true");
      this.$store
        .dispatch("team/teamMatchs", id)
        .then((response) => {
          let schedules = response.data.payload;
          this.$store.commit("auth/auth_overlay_false");
          if (response.data.code == 0) {
            self.schedules = schedules;
            console.log(self.schedules);
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },

    getTeamById(id) {
      let self = this;
      this.$store
        .dispatch("team/getTeamById", id)
        .then((response) => {
          let res = response.data.payload;
          self.team = res;
          self.idTour = self.team.idTour;
        })
        .catch((e) => {
          alert(e);
        });
    },

    handleRowClick(item) {
      this.$router.push({ path: "/scheduleDetail/" + item.idSchedule });
    },

    getFilter(list) {
      return list.filter((s) => s.status == 2);
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