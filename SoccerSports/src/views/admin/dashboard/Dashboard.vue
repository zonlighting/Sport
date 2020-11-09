<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="12" sm="2"> </v-col>
        <v-col cols="12" sm="8">
          <v-row>
            <v-col @click="tournament1"
              ><div
                class="border1"
                style="background-color: green; cursor: pointer"
              >
                <h3 class="text-center">
                  {{ this.tournament.length }} TOURNAMENT
                </h3>
              </div></v-col
            >
            <v-col @click="schedule1"
              ><div
                class="border1"
                style="background-color: blue; cursor: pointer"
              >
                <h3 class="text-center">{{ this.schedule.length }} SCHEDULE</h3>
              </div></v-col
            >
            <v-col @click="team1"
              ><div
                class="border1"
                style="background-color: red; cursor: pointer"
              >
                <h3 class="text-center">{{ this.team.length }} TEAM</h3>
              </div></v-col
            >
          </v-row>

          <h2>Notification</h2>
          <!-- <div v-for="(item,i) in schedule" :key="i">
        <div v-if="item.video==null &&item.status==2"> -->
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">Name</th>
                  <th class="text-left">Time</th>
                  <th class="text-left">Team</th>
                  <th class="text-left"></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, i) in schedule" :key="i">
                  <template v-if="item.status == 1">
                    <td>{{ item.tournament.nameTournament }}</td>
                    <td>
                      {{ new Date(item.timeStart).toString().substring(0, 21) }}
                    </td>
                    <td>
                      <v-row>
                        <v-col>
                          <v-avatar>
                            <img
                              :src="baseUrl + item.team[0].logo"
                              alt="John"
                            />
                          </v-avatar>
                          {{ item.team[0].nameTeam }}
                        </v-col>
                        <v-col cols="12" sm="2"> -<b>vs</b>- </v-col>
                        <v-col>
                          {{ item.team[1].nameTeam }}
                          <v-avatar>
                            <img
                              :src="baseUrl + item.team[1].logo"
                              alt="John"
                            /> </v-avatar
                        ></v-col>
                      </v-row>
                    </td>
                    <td>
                      <a
                        :href="
                          $router.resolve({
                            path: '/admin/schedule/' + item.idSchedule,
                          }).href
                        "
                        >Update Now
                      </a>
                    </td>
                  </template>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
      tournament: "",
      schedule: "",
      team: "",
    };
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  created() {
    this.$store.dispatch("tournament/getAll").then((response) => {
      this.tournament = response.data.payload;
    });
    this.$store.dispatch("team/getTeams").then((response) => {
      this.team = response.data.payload;
    });
    this.$store.dispatch("schedule/getAll").then((response) => {
      this.schedule = response.data.payload;
      // console.log(this.schedule);
    });
  },
  methods: {
    tournament1() {
      this.$router.push("/admin/tournament");
    },
    schedule1() {
      this.$router.push("/admin/schedule");
    },
    team1() {
      this.$router.push("/admin/teams");
    },
  },
};
</script>
<style>
.border1 {
  border-radius: 30px;
  background-color: green;
  color: white;
  width: 300px;
  height: 100px;
  padding: 20px;
}
</style>
