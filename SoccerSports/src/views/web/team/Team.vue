<template>
  <div>
    <div>
      <img
        src="http://max-themes.net/demos/bigslam/soccer3/upload/page-title.jpg"
        class="md-bg"
        alt="Slide background"
        style="margin-left: 60px"
      />
      <div style="position: absolute; top: 320px; left: 800px">
        <p style="font-size: 70px; color: #ffffff">Teams</p>
      </div>
    </div>
    <v-row class="container ml-15">
      <v-col cols="12" sm="8">
        <v-card>
          <v-card-text>
            <v-row>
              <v-col cols="12" sm="6">
                <h1 style="font-weight: bold; color: black">Soccer Teams</h1>
              </v-col>
              <v-spacer></v-spacer>
              <v-col cols="12" sm="3">
                <v-select
                  :items="items"
                  label="Solo field"
                  dense
                  solo
                ></v-select></v-col
            ></v-row>

            <v-divider class="my-2"></v-divider>

            <v-row>
              <v-col cols="12" sm="6" v-for="n in 10" :key="n">
                <v-row>
                  <v-img
                    src="https://picsum.photos/510/300?random"
                    max-width="50"
                    max-height="50"
                    class="ml-3"
                  ></v-img>
                  <v-col cols="12" sm="5" class="pt-0">
                    <a>
                      <h5 class="nameTeam">Name Team</h5>
                    </a>
                    <v-row class="pl-3">
                      <a><p class="teamlink">Results</p></a>
                      <v-divider class="ml-1 mr-1" inset vertical></v-divider>
                      <a><p class="teamlink">Squad</p></a>
                      <v-divider class="ml-1 mr-1" inset vertical></v-divider>
                      <a><p class="teamlink">Stats</p></a>
                    </v-row>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-card-text>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="success" depressed> Post </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
      <v-col cols="12" sm="4">
        <v-data-table :headers="headers" :items="desserts" class="elevation-1" :items-per-page="15">
          <template v-slot:item.calories="{ item }">
            <v-chip :color="getColor(item.calories)" dark>
              {{ item.calories }}
            </v-chip>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </div>
</template>
<script>
export default {
  data: () => ({
    items: ["Foo", "Bar", "Fizz", "Buzz"],
    headers: [
      {
        text: "Team",
        align: "start",
        sortable: false,
        value: "name",
      },

      { text: "Rank", value: "calories", sortable: false },
      { text: "Win", value: "protein", sortable: false },
      { text: "Lose", value: "fat", sortable: false },
      { text: "Tie", value: "carbs", sortable: false },
      { text: "Point", value: "", sortable: false },
    ],
    desserts: [
      {
        name: "Frozen Yogurt",
        calories: 159,
        fat: 6.0,
        carbs: 24,
        protein: 4.0,
        iron: "1%",
      },
      {
        name: "Ice cream sandwich",
        calories: 237,
        fat: 9.0,
        carbs: 37,
        protein: 4.3,
        iron: "1%",
      },
      {
        name: "Eclair",
        calories: 262,
        fat: 16.0,
        carbs: 23,
        protein: 6.0,
        iron: "7%",
      },
      {
        name: "Cupcake",
        calories: 305,
        fat: 3.7,
        carbs: 67,
        protein: 4.3,
        iron: "8%",
      },
      {
        name: "Gingerbread",
        calories: 356,
        fat: 16.0,
        carbs: 49,
        protein: 3.9,
        iron: "16%",
      },
      {
        name: "Jelly bean",
        calories: 375,
        fat: 0.0,
        carbs: 94,
        protein: 0.0,
        iron: "0%",
      },
      {
        name: "Lollipop",
        calories: 392,
        fat: 0.2,
        carbs: 98,
        protein: 0,
        iron: "2%",
      },
      {
        name: "Honeycomb",
        calories: 408,
        fat: 3.2,
        carbs: 87,
        protein: 6.5,
        iron: "45%",
      },
      {
        name: "Donut",
        calories: 452,
        fat: 25.0,
        carbs: 51,
        protein: 4.9,
        iron: "22%",
      },
      {
        name: "KitKat",
        calories: 518,
        fat: 26.0,
        carbs: 65,
        protein: 7,
        iron: "6%",
      },
      {
        name: "KitKat",
        calories: 518,
        fat: 26.0,
        carbs: 65,
        protein: 7,
        iron: "6%",
      },
      {
        name: "KitKat",
        calories: 518,
        fat: 26.0,
        carbs: 65,
        protein: 7,
        iron: "6%",
      },
      {
        name: "KitKat",
        calories: 518,
        fat: 26.0,
        carbs: 65,
        protein: 7,
        iron: "6%",
      },
      {
        name: "KitKat",
        calories: 518,
        fat: 26.0,
        carbs: 65,
        protein: 7,
        iron: "6%",
      },
      {
        name: "KitKat",
        calories: 518,
        fat: 26.0,
        carbs: 65,
        protein: 7,
        iron: "6%",
      },
    ],
  }),
  methods: {
    getColor(calories) {
      if (calories > 400) return "red";
      else if (calories > 200) return "orange";
      else return "green";
    },
  },
};
</script>
<style scoped>
.nameTeam {
  color: #151617;
  display: inline;
  font-weight: 600;
  line-height: 26px;
  font-size: 18px;
}

.teamlink {
  color: #06c;
  font-weight: 400;
  font-size: 13px;
}
</style>