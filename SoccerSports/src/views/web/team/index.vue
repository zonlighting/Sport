<template>
  <div>
    <v-row class="pl-10">
      <v-col cols="11" md="2" xm="2"></v-col>
      <v-col cols="11" md="9" xm="9">
        <v-row>
          <v-img
            max-height="70"
            max-width="70"
            :src="baseUrl + team.logo"
          ></v-img>
          <v-toolbar-title class="pl-5">
            <h1 style="font-weight: 500; line-height: 34px; color: #2b2c2d">
              {{ team.nameTeam }}
            </h1>
            <h5 class="pt-3" style="font-size: 18px; font-weight: 400">
              {{ team.country }}
            </h5>
          </v-toolbar-title>
        </v-row>
      </v-col>
    </v-row>
    <v-divider style="margin: 0 !important"></v-divider>
    <v-row class="pl-6">
      <v-col cols="12" md="2" xm="2"></v-col>
      <v-col>
        <v-tabs v-model="active_tab">
          <v-tab class="fix-tab-css" @click="teamRoute(1)">Fixtures</v-tab>
          <v-tab class="fix-tab-css" @click="teamRoute(2)">Results</v-tab>
          <v-tab class="fix-tab-css" @click="teamRoute(3)">Squad</v-tab>
        </v-tabs>
      </v-col>
    </v-row>
    <router-view></router-view>
  </div>
</template>

<script>
import { ENV } from "@/config/env.js";
export default {
  data() {
    return {
      active_tab: this.$store.state.team.currentTab,
      team: {},
    };
  },
  mounted() {
    this.team = this.$store.state.team.teamDetail;
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  methods: {
    teamRoute(check) {
      this.$store.commit("team/current_tab", check);
      if (check == 1) {
        this.$router.push({
          path: `/fixtures/${this.team.idTeam}`,
        });
      }
      if (check == 2) {
        this.$router.push({
          path: `/results/${this.team.idTeam}`,
        });
      }
      if (check == 3) {
        this.$router.push({
          path: `/squad/${this.team.idTeam}`,
        });
      }
    },
  },
};
</script>

<style>
.fix-tab-css {
  color: #151617;
  font-weight: 500;
  line-height: 19px;
  font-size: 14px;
}
</style>
