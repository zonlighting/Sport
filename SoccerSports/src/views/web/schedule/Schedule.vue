<template>
  <div>
    <div>
      <img
        src="http://max-themes.net/demos/bigslam/soccer3/upload/page-title.jpg"
        class="ls-bg"
        alt="Slide background"
        style="margin-left: 60px"
      />
      <div style="position: absolute; top: 320px; left: 770px">
        <p style="font-size: 70px; color: #ffffff">SCHEDULE</p>
      </div>
    </div>
    <section >
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
               <v-col cols="12" sm="2"></v-col>
          </v-row>
      <v-row>
        <v-col cols="12" sm="2"> </v-col>
        <v-col cols="12" sm="8">
          <v-container v-if="tournament.length>0">
            <v-card
              v-for="(item, i) in tournament"
              :key="i"
              style="margin-top: 50px"
            >
              <v-data-table
                :headers="headers"
                :items="item.schedule"
                hide-default-footer
              >
                <template v-slot:top>
                  <v-toolbar flat>
                    <v-toolbar-title>{{ item.nameTournament }}</v-toolbar-title>
                    <v-divider class="mx-4" inset vertical></v-divider>
                    <v-spacer></v-spacer>
                  </v-toolbar>
                </template>
                <template v-slot:[`item.timeStart`]="{ item }">
                  {{ new Date(item.timeStart).toString().substring(0, 16) }}
                </template>
                <template v-slot:[`item.status`]="{ item }">
                  <p
                    :style="
                      item.status == 0
                        ? 'color:green'
                        : item.status == 1
                        ? 'color:blue'
                        : ' color:red'
                    "
                  >
                    {{
                      item.status == 0
                        ? "Up Comming"
                        : item.status == 1
                        ? "On Game"
                        : " Finished"
                    }}
                  </p>
                </template>
                <template v-slot:[`item.time`]="{ item }">
                  {{ new Date(item.timeStart).toString().substring(16, 21) }}
                </template>
                <template v-slot:[`item.article`]="{ item }">
                  <router-link :to="'/scheduleDetail/' + item.idSchedule">
                    <v-icon>mdi-chevron-double-right</v-icon></router-link
                  >
                </template>
                <template v-slot:[`item.event`]="{ item }">
                  <v-avatar size="50" tile
                    ><img :src="baseUrl + item.team[0].logo" alt="Logo"
                  /></v-avatar>
                  <span v-if="item.status == 2" style="font-size: 15px">
                    {{ item.score1 }}-{{ item.score2 }}
                  </span>
                  <span v-else class="text-center" style="font-size: 15px">
                    VS
                  </span>

                  <v-avatar size="50" tile
                    ><img :src="baseUrl + item.team[1].logo" alt="Logo"
                  /></v-avatar>
                </template>
              </v-data-table>
            </v-card>
          </v-container>
          <v-container v-else>
            <v-img src="@/assets/soccer.png"></v-img>
            <h1>No Schedule Up Comming</h1>
          </v-container>
        </v-col>
      </v-row>
    </section>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  data: () => ({
    select:"",
     value: [
      { id: 3, text: "Select Status" },
      { id: 0, text: "Schedule Up Comming" },
      { id: 1, text: "Schedule On Game" },
      { id: 2, text: "Schedule Finished" },
    ],
    headers: [
      {
        text: "Date",
        align: "start",
        value: "timeStart",
        width: "200px",
      },
      { text: "Time", value: "time", sortable: false, width: "200px" },

      { text: "EVENT", value: "event", sortable: false, width: "350px" },
      { text: "Location", value: "location", width: "300px" },
      { text: "Status", value: "status" },
      { text: "", value: "article" },
    ],
    desserts: [],
    tournament: [],
  }),

  created() {
    this.getData();
  },

  methods: {
    getData() {
      this.$store.commit("auth/auth_overlay_true");
      this.$store.dispatch("tournament/getAllSchedule").then((response) => {
        this.$store.commit("auth/auth_overlay_false");
        if (response.data.code == 0) {
          this.tournament = response.data.payload;
        }
      });
    },

  },
  watch:{
      select(){
        if(this.select==3){
          this.getData();
        }
        else{
          this.$store.commit("auth/auth_overlay_true");
      this.$store.dispatch("tournament/getAllScheduleStatus",this.select).then((response) => {
        this.$store.commit("auth/auth_overlay_false");
        if (response.data.code == 0) {
          this.tournament = response.data.payload;
        }
      });
        }
      }
  }
};
</script>
