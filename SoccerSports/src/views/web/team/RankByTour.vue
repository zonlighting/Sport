<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="desserts"
      class="elevation-1 row-pointer"
      hide-default-footer
      disable-pagination
    >
      <template v-slot:item="{ item, index }">
        <tr @click="handleRowClick(item)">
          <td>
            <v-img width="40" height="40" :src="baseUrl + item.logo"></v-img>
          </td>
          <td>
            <p class="name-team">{{ item.teamName }}</p>
          </td>
          <td>
            <v-chip :color="getColor(index + 1)" dark>
              <p class="pt-4" style="color: white" v-if="index + 1 < 4">
                {{ index + 1 }}
              </p>
              <p class="pt-4" style="color: black" v-if="index + 1 >= 4">
                {{ index + 1 }}
              </p>
            </v-chip>
          </td>
          <td>{{ item.matchPlayed }}</td>
          <td>{{ item.win }}</td>
          <td>{{ item.lose }}</td>
          <td>{{ item.tie }}</td>
          <td>
            <p class="name-team">{{ item.point }}</p>
          </td>
        </tr>
      </template>
    </v-data-table>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";
export default {
  props: {
    tourId: Number,
  },
  data: () => ({
    id: "",
    select: "",
    isHavedata: true,
    headers: [
      { text: "logo", align: "start", sortable: false, value: "logo" },
      {
        text: "Team",

        sortable: false,
        value: "teamName",
      },
      { text: "Rank", value: "rank", sortable: false },
      { text: "TM", value: "matchPlayed", sortable: false },
      { text: "Win", value: "win", sortable: false },
      { text: "Lose", value: "lose", sortable: false },
      { text: "Tie", value: "tie", sortable: false },
      { text: "Point", value: "point", sortable: false },
    ],
    desserts: [],
  }),

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  watch: {
    tourId: {
      immediate: true,
      handler: function (newValue) {
        if (newValue > 0 && newValue != undefined) {
          // console.log("newvalue" + newValue)
          this.getRankByTour(newValue);
        }
      },
    },
  },

  methods: {
    getRankByTour(id) {
      let self = this;
      this.$store
        .dispatch("tournament/rankByTour", id)
        .then(function (response) {
          if (response.data.code == 9999) {
            console.log("Run here Rank tour");
            alert(response.data.message);
          } else {
            self.desserts = response.data.payload;
          }
        })
        .catch(function (error) {
          console.log("Run here 8");
          alert(error);
        });
    },

    getColor(rank) {
      if (rank == 1) return "red";
      else if (rank == 2) return "orange";
      else if (rank == 3) return "green";
      else return "white";
    },

    handleRowClick(item) {
      let path = `/team/${item.idTeam}`;
      this.$router.push({ path: path });
      location.reload();
    },
  },
};
</script>
<style>
.name-team {
  margin: 0 !important;
  font-size: 15px;
  font-weight: 700;
  color: #2b2c2d;
}
</style>
<style lang="css" scoped>
.row-pointer >>> tbody tr :hover {
  cursor: pointer;
}
</style>