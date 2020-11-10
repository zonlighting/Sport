<template>
  <div>
    <v-breadcrumbs :items="linkTournamentDetail" large>
      <template v-slot:divider>
        <v-icon>mdi-chevron-right</v-icon>
      </template>
    </v-breadcrumbs>

    <div class="text-center">
      <v-img
        max-height="200px"
        min-width="140px"
        :src="
          tournament.banner != ''
            ? baseUrl + tournament.banner
            : 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR3XsIP30DtgqB3coyj-Azvfgng0w7v5b4dIw&usqp=CAU'
        "
      ></v-img>
      <h1>{{ tournament.nameTournament }}</h1>
      <v-btn @click.stop="dialog = true"
        v-if="tournament.status == 0"
        style="right: 130px; position: absolute; background: dodgerblue"
        >Edit</v-btn
      >
      {{ tournament.timeStart }}<v-icon>mdi-arrow-right-bold</v-icon
      >{{ tournament.timeEnd }}<br />
      <div
        :style="
          tournament.status == 0
            ? 'color:green'
            : tournament.status == 1
            ? 'color:blue'
            : 'color:red'
        "
      >
        {{
          tournament.status == 0
            ? "Up Comming"
            : tournament.status == 1
            ? "On Game"
            : "finished"
        }}
      </div>
    </div>
    <div style="margin-top: 50px">
      <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
        <v-tab>Team </v-tab> <v-tab>Schedule </v-tab> <v-tab>Rank </v-tab>
      </v-tabs>

      <v-tabs-items v-model="tab">
        <v-tab-item>
          <v-card color="basil" flat>
            <TournamentTeam :getData="getData" :tournament="tournament" />
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card color="basil">
            <v-card-text
              ><TournamentSchedule
                :getDataTournament="getData"
                :tournament="tournament"
            /></v-card-text>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card color="basil" flat>
            <v-card-text
              ><TournamentRank :getData="getData" :tournament="tournament"
            /></v-card-text>
          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </div>
    <v-dialog v-model="dialog" max-width="800px">
      <v-card>
        <v-card-title class="headline">
          Edit Tournament 
        </v-card-title>

        <v-card-text>
          <TournamentEdit :getData="getData" :tournament="tournament" :hide="hide"></TournamentEdit>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";

import TournamentSchedule from "./TournamentSchedule";
import TournamentTeam from "./TournamentTeam";
import TournamentRank from "./TournamentRank";
import TournamentEdit from "./TournamentEdit";
export default {
  data: () => {
    return {
      dialog: false,
      tournament: {},
      tab: null,
      linkTournamentDetail: [
        {
          text: "Dashboard",
          disabled: false,
          href: "/admin",
        },
        {
          text: "Tournament",
          disabled: false,
          href: "/admin/tournament",
        },
      ],
    };
  },
  components: {
    TournamentSchedule,
    TournamentTeam,
    TournamentRank,
    TournamentEdit,
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  created() {
    this.getData();
  },
  mounted() {
    this.$store.commit("auth/auth_overlay");
    this.$store
      .dispatch("tournament/getById", this.$route.params.id)
      .then((response) => {
        this.$store.commit("auth/auth_overlay");
        if (response.data.code == 0) {
          this.tournament = response.data.payload;
        }
      });
  },
  methods: {
    hide(){
      this.dialog=!this.dialog
    },
    getData() {
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("tournament/getById", this.$route.params.id)
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            this.tournament = response.data.payload;
          }
        });
    },
  },
};
</script>