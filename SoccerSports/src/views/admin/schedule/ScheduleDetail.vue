<template>
  <div>
    <v-breadcrumbs :items="linkScheduleDetail" large>
      <template v-slot:divider>
        <v-icon>mdi-chevron-right</v-icon>
      </template>
    </v-breadcrumbs>
    <v-btn v-if="schedule.status != 0" color="red">Update</v-btn>

    <v-container style="font-family: Times New Roman">
      <h3 style="color: blue; display: inline-block">#SUMMARY</h3>
      <v-btn
        v-if="schedule.status == 0"
        style="right: 0px; position: absolute"
        color="blue"
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
            :src="!!schedule ? baseUrl + schedule.team[0].log : ''"
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
            :src="!!schedule ? baseUrl + schedule.team[1].log : ''"
            alt="John"
          />
        </v-avatar>
      </div>
      <hr />
      <h3 style="color: blue">#Team</h3>
      <div>
        <v-row>
          <v-col v-for="(team, index) in schedule.team" :key="index">
            <div class="text-center">
              <img
                lazy-src="https://picsum.photos/id/11/10/6"
                style="width: 100px"
                src="https://picsum.photos/id/11/500/300"
              />
              <h2>{{ team.nameTeam }}</h2>
            </div>
            <v-list disabled>
              <v-subheader>MEMBER</v-subheader>
              <v-list-item-group>
                <v-list-item v-for="(item, i) in team.profile" :key="i">
                  <v-list-item-icon>
                    <v-avatar>
                      <img
                        src="https://cdn.vuetifyjs.com/images/john.jpg"
                        alt="John"
                      />
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
            <v-list disabled>
              <v-subheader>Detail</v-subheader>
              <v-list-item-group>
                <v-list-item>
                  <v-list-item-content>
                    Total Match : {{!!detailTeam? detailTeam[index].totalMatch:'' }}
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
              <v-list-item-group>
                <v-list-item>
                  <v-list-item-content>
                    Total Win Match : {{ detailTeam[index].totalWin }}
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
              <v-list-item-group>
                <v-list-item>
                  <v-list-item-content>
                    Total Match By Tournament :
                    {{ detailTeam[index].totalMatchByTour }}
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
              <v-list-item-group>
                <v-list-item>
                  <v-list-item-content>
                    Total Win By Tournament :
                    {{ detailTeam[index].totalWinByTour }}
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-col>
        </v-row>
        <hr />
        <h3 style="color: blue">#Schedule</h3>
        <template v-if="schedule.statu==2">
            <h1 class="text-center" >
                The match is not over or has not been updated

            </h1>
        </template>
        <template v-if="schedule.status!=2" style="font-family:arial">
        <v-tabs v-model="tab" >
          <v-tab >
            Set 1
          </v-tab>
           <v-tab >
            Set 2
          </v-tab>
           <v-tab >
            Summary
          </v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab">
          <v-tab-item >
            <v-card  flat>
              <v-card-text>
                <h1 class="text-center">2-3</h1>
                <v-row>
                    <v-col >
                           <h2>dsadsadsa</h2> 
                    </v-col>
                     <v-col>
                           <h2>dsadsadsa</h2> 
                    </v-col>
                </v-row>
              </v-card-text>
            </v-card>
          </v-tab-item>
           <v-tab-item >
            <v-card color="basil" flat>
              <v-card-text>
                  <h1 class="text-center">2-3</h1>
              </v-card-text>
            </v-card>
          </v-tab-item>
           <v-tab-item >
            <v-card color="basil" flat>
              <v-card-text>dsadsa</v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs-items>
        </template>
      </div>
    </v-container>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
        tab:null,
      detailTeam: [],

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
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("schedule/getById", this.$route.params.id)
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            this.schedule = response.data.payload;
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