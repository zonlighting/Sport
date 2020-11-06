<template>
  <div>
    <v-row class="pl-10">
      <v-col cols="11" md="1" mr="10px"></v-col>
      <v-col cols="11" md="9">
        <v-img
          lazy-src="https://picsum.photos/id/11/10/6"
          max-height="200px"
          min-width="140px"
          :src="baseUrl + tournament.banner"
        ></v-img>
        <v-toolbar-title class="pl-5">
          <h1
            style="font-weight: 500; line-height: 34px; color: #2b2c2d"
            class="text-center"
          >
            {{ tournament.nameTournament }}
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
        <ul>
          <li>
            <router-link :to="{ name: TournamnetHome }">Home</router-link>
          </li>
          <li><a href="#news">Schedule</a></li>
          <li><a href="#contact">Team</a></li>
          <li><a href="#about">Rank</a></li>
        </ul>
      </v-col>
    </v-row>
    <v-col cols="12" md="2" xm="2"></v-col>
    <v-col> <router-view></router-view></v-col>
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
    this.$store.commit("auth/auth_overlay");
    await this.$store
      .dispatch("tournament/getById", this.$route.params.id)
      .then((response) => {
        this.$store.commit("auth/auth_overlay");
        this.tournament = response.data.payload;
      });
    console.log(this.tournament);
  },
};
</script>
<style scoped>
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

li {
  float: left;
}

li a {
  display: block;
  padding: 8px;
}
a:hover {
  color: white;
  background: #ff0000;
}
a:active {
  background: #ff0000;
}
</style>