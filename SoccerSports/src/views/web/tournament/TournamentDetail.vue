<template>
  <div>
    <v-row class="pl-10">
      <v-col cols="11" md="1" style="margin-left: 80px"></v-col>
      <v-col cols="11" md="9">
        <v-img
          max-height="200px"
          min-width="140px"
          :src="
            tournament.banner != null
              ? baseUrl + tournament.banner
              : 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR3XsIP30DtgqB3coyj-Azvfgng0w7v5b4dIw&usqp=CAU'
          "
        ></v-img>
        <v-toolbar-title class="pl-5">
          <h1
            style="font-weight: 500; line-height: 34px; color: #2b2c2d"
            class="text-center"
          >
            {{ tournament.nameTournament }}-<b style="color: blue">{{
              tournament.status == 0
                ? "Up Comming"
                : tournament.status == 1
                ? "On Game"
                : "Finished"
            }}</b>
          </h1>
          <h5
            class="pt-3 text-center"
            style="font-size: 12px; font-weight: 400"
          >
            <v-icon>mdi-alarm-check</v-icon>{{ tournament.timeStart }}/{{
              tournament.timeEnd
            }}
          </h5>
        </v-toolbar-title>
      </v-col>
    </v-row>
    <v-divider style="margin: 0 !important"></v-divider>
    <v-row class="pl-6">
      <v-col cols="12" md="2" xm="2"></v-col>
      <v-col>
        <div>
          <ul class="nav nav-pills">
            <li class="nav-item">
              <router-link
                :to="{
                  path: `/tournamentDetail/${tournament.idTournament}/team`,
                }"
                class="nav-link"
                active-class="active"
                exact
                style="cursor: pointer"
                >Rank</router-link
              >
            </li>
            <li class="nav-item">
              <router-link
                :to="{
                  path: `/tournamentDetail/${tournament.idTournament}/results`,
                }"
                class="nav-link"
                active-class="active"
                style="cursor: pointer"
                >Results</router-link
              >
            </li>
            <li class="nav-item">
              <router-link
                :to="{
                  path: `/tournamentDetail/${tournament.idTournament}/fixtures`,
                }"
                class="nav-link"
                active-class="active"
                style="cursor: pointer"
                >Fixtures</router-link
              >
            </li>
          </ul>
          <hr />
        </div>
      </v-col>
    </v-row>
    <v-col cols="12" md="2" xm="2"></v-col>

    <v-col>
      <v-row>
        <v-col cols="12" sm="10"> <router-view></router-view></v-col> </v-row
    ></v-col>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
      tournament: {},
    };
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  methods: {},
  async created() {
    this.$store.commit("auth/auth_overlay_true");
    await this.$store
      .dispatch("tournament/getById", this.$route.params.id)
      .then((response) => {
        this.$store.commit("auth/auth_overlay_false");
        this.tournament = response.data.payload;
      });
  },
};
</script>
<style scoped>
.active {
  background-color: rgb(193, 218, 193);
}
</style>
