<template>
  <div>
    <v-row class="pl-10">
      <v-col cols="11" md="1" style="margin-left: 80px"></v-col>
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
        <ul style="border-bottom: solid 1px; width: 260px" id="myDIV">
          <li>
            <router-link
              class="btn active"
              :to="{
                path: `/tournamentDetail/${tournament.idTournament}/team`,
              }"
              >Rank</router-link
            >
          </li>
          <li>
            <router-link
              class="btn"
              :to="{
                path: `/tournamentDetail/${tournament.idTournament}/results`,
              }"
              >Results</router-link
            >
          </li>
          <li>
            <router-link
              class="btn"
              :to="{
                path: `/tournamentDetail/${tournament.idTournament}/fixtures`,
              }"
              >Fixtures</router-link
            >
          </li>
        </ul>
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
    this.$store.commit("auth/auth_overlay");
    await this.$store
      .dispatch("tournament/getById", this.$route.params.id)
      .then((response) => {
        this.$store.commit("auth/auth_overlay");
        this.tournament = response.data.payload;
      });

    var header = document.getElementById("myDIV");
    var btns = header.getElementsByClassName("btn");
    for (var i = 0; i < btns.length; i++) {
      btns[i].addEventListener("click", function () {
        var current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");
        this.className += " active";
      });
    }
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
.btn {
  border: none;
  outline: none;
  padding: 10px 16px;
  background-color: #f1f1f1;
  cursor: pointer;
  font-size: 18px;
}

/* Style the active class, and buttons on mouse-over */
.active,
.btn:hover {
  background-color: #666;
  color: white;
}
</style>
