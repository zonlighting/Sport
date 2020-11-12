<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="desserts"
      class="elevation-1 row-pointer"
      hide-default-footer
     
      disable-pagination
    >
      <template v-slot:item="{ item, index }" >
        <tr @click="handleRowClick(item)">
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
export default {
  props: {
    tourId: Number,
  },
  data: () => ({
    select: "",
    isHavedata: true,
    headers: [
      {
        text: "Team",
        align: "start",
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

  mounted() {
    if (this.tourId != undefined) {
      this.getRankByTour(this.tourId);
    }
  },

  watch: {
    tourId: {
      handler: function (newValue) {
        // console.log(newValue);
        return this.getRankByTour(newValue);
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
            // alert(response.data.message);
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
      console.log(item);
      this.$router.push({
        path: `/team/${item.idTeam}`,
      });
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