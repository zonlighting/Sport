<template>
  <div>
    <v-breadcrumbs :items="linkScheduleDetail" large>
      <template v-slot:divider>
        <v-icon>mdi-chevron-right</v-icon>
      </template>
    </v-breadcrumbs>

    <v-btn v-if="schedule.status != 0" color="red" @click="dialogUpdate = true"
      >Update
    </v-btn>

    <v-container style="font-family: Times New Roman">
      <h3 style="color: blue; display: inline-block">#SUMMARY</h3>
      <v-btn
        v-if="schedule.status == 0"
        style="right: 0px; position: absolute"
        color="blue"
        @click="dialogEdit = true"
        >Edit</v-btn
      >
      <h1 class="text-center">
        {{ !!schedule ? schedule.tournament.nameTournament : "" }}
      </h1>
      <h3 class="text-center">
        Time Start: {{ new Date(Date.parse(schedule.timeStart)) }}
      </h3>
      <h3 class="text-center">Location:{{ schedule.location }}</h3>
      <div class="text-center">
        <v-avatar tile>
          <img
            :src="!!schedule ? baseUrl + schedule.team[0].logo : ''"
            alt="John"
          />
        </v-avatar>
        <h4 style="display: inline-block">
          {{ !!schedule ? schedule.team[0].nameTeam : "" }}
        </h4>
        <v-avatar style="margin-left: 50px; margin-right: 50px">
          <img src="@/assets/vs.png" alt="John" />
        </v-avatar>
        <h4 style="display: inline-block">
          {{ !!schedule ? schedule.team[1].nameTeam : "" }}
        </h4>
        <v-avatar tile>
          <img
            :src="!!schedule ? baseUrl + schedule.team[1].logo : ''"
            alt="John"
          />
        </v-avatar>
      </div>
      <hr />

      <div>
        <h3 style="color: blue">#Schedule</h3>
        <template v-if="schedule.status != 2">
          <h1 class="text-center">
            The match is not over or has not been updated
          </h1>
        </template>
        <template v-else style="font-family: arial">
          <h1 class="text-center" style="font-size: 80px">
            {{ schedule.status == 2 ? schedule.score1 : "" }}-{{
              schedule.status == 2 ? schedule.score2 : ""
            }}
          </h1>
          <v-row>
            <v-col class="text-right">
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-subtitle
                    v-for="(item, index) in goal1"
                    :key="index"
                    style="font-size: 40px"
                  >
                    <v-icon>mdi-soccer</v-icon>{{ item.profile.name }}-{{
                      item.time.substring(0, 5)
                    }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-col>
            <v-col>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-subtitle
                    v-for="(item, index) in goal2"
                    :key="index"
                    style="font-size: 40px"
                  >
                    <v-icon>mdi-soccer</v-icon>{{ item.profile.name }}-{{
                      item.time.substring(0, 5)
                    }}
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-col>
          </v-row>
        </template>
      </div>
      <hr />
      <h3 style="color: blue">#Team</h3>
      <v-row>
        <v-col v-for="(team, index) in schedule.team" :key="index">
          <div class="text-center">
            <img
              lazy-src="https://picsum.photos/id/11/10/6"
              style="width: 100px; height: 100px"
              :src="baseUrl + team.logo"
            />
            <h2>{{ team.nameTeam }}</h2>
          </div>
          <v-list disabled>
            <v-subheader>Detail</v-subheader>
            <v-list-item-group>
              <v-list-item>
                <v-list-item-content>
                  Total Match :
                  {{
                    !!detailTeam && !!detailTeam[index]
                      ? detailTeam[index].totalMatch
                      : ""
                  }}
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
            <v-list-item-group>
              <v-list-item>
                <v-list-item-content>
                  Total Win Match :
                  {{
                    !!detailTeam && !!detailTeam[index]
                      ? detailTeam[index].totalWin
                      : ""
                  }}
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
            <v-list-item-group>
              <v-list-item>
                <v-list-item-content>
                  Total Match By Tournament :
                  {{
                    !!detailTeam && !!detailTeam[index]
                      ? detailTeam[index].totalMatchByTour
                      : ""
                  }}
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
            <v-list-item-group>
              <v-list-item>
                <v-list-item-content>
                  Total Win By Tournament :
                  {{
                    !!detailTeam && !!detailTeam[index]
                      ? detailTeam[index].totalWinByTour
                      : ""
                  }}
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
          <v-list disabled>
            <v-subheader>MEMBER</v-subheader>
            <v-list-item-group>
              <v-list-item v-for="(item, i) in team.profile" :key="i">
                <v-list-item-icon>
                  <v-avatar>
                    <img :src="baseUrl + item.avatar" alt="avatar" />
                  </v-avatar>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title
                    >{{
                      item.name
                    }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{
                      item.age
                    }}
                    old&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{
                      item.country
                    }}</v-list-item-title
                  >
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-col>
      </v-row>
      <hr />
      <template v-if="schedule.video != null || schedule.image != null">
        <h3 style="color: blue">#Video,Photo</h3>
        <v-row>
          <v-col>
            <v-container>
              <h3>Photo</h3>
              <img style="max-height: 204px" :src="baseUrl + schedule.image" />
            </v-container>
          </v-col>
          <v-col>
            <v-container>
              <h3>Video</h3>
              <video
                width="320"
                height="240"
                controls
                :src="baseUrl + schedule.video"
                id="video"
              ></video>
            </v-container>
          </v-col>
        </v-row>
      </template>
    </v-container>
    <v-dialog
      v-model="dialogUpdate"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
    >
      <v-card>
        <ScheduleUpdate
          :hideDiaglog="hideDiaglog"
          :schedule="schedule"
          :getData="getData"
        />
      </v-card>
    </v-dialog>
    <v-dialog
      v-model="dialogEdit"
      width="700px"
      height="700px"
      hide-overlay
      transition="dialog-bottom-transition"
    >
      <v-card>
        <ScheduleEdit
          :hideDiaglog="hideDiaglog"
          :schedule="schedule"
          :getData="getData"
        />
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";
import ScheduleUpdate from "./ScheduleUpdate";
import ScheduleEdit from "./ScheduleEdit";
export default {
  data() {
    return {
      dialogEdit: false,

      goal1: [],
      goal2: [],
      tab: null,
      detailTeam: [],
      dialogUpdate: false,

      schedule: "",
      linkScheduleDetail: [
        {
          text: "Dashboard",
          disabled: false,
          href: "/admin/dashboard",
        },
        {
          text: "Schedule",
          disabled: false,
          href: "/admin/schedule",
        },
      ],
    };
  },
  components: {
    ScheduleUpdate,
    ScheduleEdit,
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  created() {
    this.getData();
  },
  mounted() {},
  methods: {
    hideDiaglog() {
      this.dialogUpdate = false;
      this.dialogEdit = false;
    },
    getDataGoal() {
      this.goal1 = [];
      this.goal2 = [];
      this.schedule.goal.forEach((element) => {
        if (element.team == 1) {
          this.schedule.team[0].profile.forEach((profile) => {
            if (profile.id == element.idMember) {
              this.goal1.push({
                profile: profile,
                time: element.time,
                idSchedule: this.schedule.idSchedule,
              });
            }
          });
        }
        if (element.team == 2) {
          this.schedule.team[1].profile.forEach((profile) => {
            if (profile.id == element.idMember) {
              this.goal2.push({
                profile: profile,
                time: element.time,
                idSchedule: this.schedule.idSchedule,
              });
            }
          });
        }
      });
    },
    getData() {
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("schedule/getById", this.$route.params.id)
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            this.schedule = response.data.payload;
            this.getDataGoal();
            if (this.schedule.idTeam1 > this.schedule.idTeam2) {
              this.$store
                .dispatch("team/getDetail", this.schedule.team[1].idTeam)
                .then((response) => {
                  this.detailTeam.push(response.data.payload);
                });
              this.$store
                .dispatch("team/getDetail", this.schedule.team[0].idTeam)
                .then((response) => {
                  this.detailTeam.push(response.data.payload);
                });
            } else {
              this.$store
                .dispatch("team/getDetail", this.schedule.team[0].idTeam)
                .then((response) => {
                  this.detailTeam.push(response.data.payload);
                });
              this.$store
                .dispatch("team/getDetail", this.schedule.team[1].idTeam)
                .then((response) => {
                  this.detailTeam.push(response.data.payload);
                });
            }
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },
  },
};
</script>