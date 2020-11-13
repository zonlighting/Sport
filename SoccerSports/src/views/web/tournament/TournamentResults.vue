<template>
  <v-data-table :headers="headers" :items="schedule">
    <template v-slot:[`item.time`]="{ item }">
      <div>
        {{ item.timeStart.substring(11, 16) }}
      </div>
    </template>
    <template v-slot:[`item.timeStart`]="{ item }">
      <div>
        {{ item.timeStart.substring(0, 10) }}
      </div>
    </template>
    <template v-slot:[`item.event`]="{ item }">
      <div>
        <v-avatar>
          <img :src="baseUrl + item.team[0].logo" />
        </v-avatar>
        <b style="font-size: 25px; margin-left: 40px; margin-right: 40px"
          >{{ item.score1 }}-{{ item.score2 }}</b
        ><v-avatar>
          <img :src="baseUrl + item.team[1].logo" />
        </v-avatar>
      </div>
    </template>
    <template v-slot:[`item.article`]="{ item }">
      <div>
        <router-link
          :to="{
            path: `/summary/${item.idSchedule}`,
          }"
        >
          <v-icon>mdi-chevron-double-right </v-icon>
        </router-link>
      </div>
    </template>
    <template v-slot:no-data>
      <div class="text-center">
        <v-img
          max-height="500"
          max-width="500"
          src="@/assets/soccer.png"
        ></v-img>
        <h1>No Match</h1>
      </div>
    </template>
  </v-data-table>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  data() {
    return {
      schedule: [],
      headers: [
        {
          text: "Time",
          align: "start",
          value: "time",
          sortable: false,
        },
        {
          text: "Date",
          align: "start",
          value: "timeStart",
        },
        { text: "EVENT", value: "event", sortable: false, width: "500px" },

        { text: "", value: "article", width: "100px" },
      ],
    };
  },
  created() {
    this.getData();
  },
  methods: {
    async getData() {
      var a = [];
      this.$store.commit("auth/auth_overlay_true");
      await this.$store
        .dispatch("schedule/getByTour", this.$route.params.id)
        .then((response) => {
          this.$store.commit("auth/auth_overlay_false");
          if (response.data.code == 0) {
            a = response.data.payload;
            console.log(response.data.payload);
          }
        });
      console.log(a);

      a.forEach((element) => {
        if (element.status == 2) {
          this.schedule.push(element);
        }
      });
    },
  },
};
</script>