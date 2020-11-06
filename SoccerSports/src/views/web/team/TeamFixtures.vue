<template>
  <div>
    <v-card class="mx-auto" max-width="85%">
      <v-row class="container">
        <v-col cols="0" sm="1"></v-col>
        <v-col cols="12" sm="7">
          <v-card-text>
            <v-row class="mb-5">
              <v-col cols="12" sm="6">
                <h1 class="title-h1">{{ team.nameTeam }} Fixtures</h1>
              </v-col>
            </v-row>

            <h2 class="pl-5">Tournament name</h2>

            <v-divider style="margin: 0 !important"></v-divider>
            <h5 class="table__Title">{{ month.name }} , {{ year }}</h5>
            <v-row v-if="isHavedata">
              <v-col>
                <v-data-table
                  :headers="headers"
                  :items="desserts"
                  class="elevation-1"
                  :items-per-page="15"
                >
                  <template v-slot:[`item.date`]="{ item }">
                   {{getMonth(item.timeStart)}} , {{ new Date(item.timeStart).getDate() }}
                  </template>
                </v-data-table>
              </v-col>
            </v-row>
            <h2 v-else>No Data Available</h2>
          </v-card-text>
        </v-col>
        <v-col cols="12" sm="4">
          <v-row style="height: 107px"></v-row>
          <v-row><RankByTour :tourId="parseInt($route.query.tourId)" /></v-row
        ></v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import RankByTour from "@/views/web/team/RankByTour";
const d = new Date();
const months = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];
export default {
  components: {
    RankByTour,
  },
  data: () => ({
    select: "",
    isHavedata: true,
    headers: [
      {
        text: "DATE",
        align: "start",
        sortable: false,
        value: "date",
      },
      { text: "", value: "team1", sortable: false },
      { text: "", value: "logo1", sortable: false },
      { text: "MATCH", value: "vs", sortable: false },
      { text: "", value: "team2", sortable: false },
      { text: "", value: "logo2", sortable: false },
      { text: "TIME", value: "time", sortable: false },
      { text: "COMPETITION", value: "nameTour", sortable: false },
      { text: "STATUS", value: "status", sortable: false },
    ],
    desserts: [],
    month: {},
    year: "",
    team: {},
  }),

  mounted() {
    // console.log(this.$route)
    if (this.$route.query.tourId != undefined) {
      this.getTourById(this.$route.query.tourId);
    }
    this.currentMonth(d);
    this.year = d.getFullYear();
    this.team = this.$store.state.team.teamDetail;
  },

  methods: {
    getTourById(id) {
      let self = this;
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("schedule/getByTour", id)
        .then((response) => {
          let schedule = response.data.payload;
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            self.desserts = schedule.filter((schedule) => {
              return (
                schedule.idTeam1 == self.team.idTeam ||
                schedule.idTeam2 == self.team.idTeam
              );
            });
            // self.desserts = schedule.filter((schedule) => {
            //   let date = new Date(schedule.timeStart);
            //   return date.getMonth() > self.month.month;
            // });

          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },

    currentMonth(passMonth) {
      let nameMonth = months[passMonth.getMonth()];
      this.month = {
        month: passMonth.getMonth(),
        name: nameMonth,
      };
    },

    getMonth(passMonth){
       return months[passMonth.getMonth()];
    }
  },
};
</script>
<style >
.title-h1 {
  padding-left: 20px;
  font-size: 28px;
  font-weight: 700;
  color: #2b2c2d;
}
.table__Title {
  position: relative;
  text-align: left;
  text-transform: capitalize;
  color: #2b2c2d;
  font-size: 16px;
  font-weight: 600;
  line-height: 21px;
  margin-bottom: 8px;
  margin-top: 8px;
  padding-left: 21px;
}
</style>