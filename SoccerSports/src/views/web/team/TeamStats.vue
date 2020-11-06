<template>
  <div>
    <v-card class="mx-auto" max-width="85%">
      <v-row class="container">
        <v-col cols="0" sm="1"></v-col>
        <v-col cols="12" sm="7">
          <v-card-text>
            <v-row>
              <v-col cols="12" sm="6">
                <h1 class="title-h1">Albania Stats</h1>
              </v-col>
            </v-row>
            <v-row style="max-height: 50px">
              <v-col cols="12" sm="6" md="6">
                <h2 class="pl-5 title-h1" >Tournament name</h2>
              </v-col>
              <v-col cols="12" sm="3" md="3">
                <v-select
                  class="p-0"
                  v-model="select"
                  :items="tournaments"
                  item-text="nameTournament"
                  item-value="idTournament"
                  label="2020 - 21"
                  dense
                  solo
                ></v-select>
              </v-col>
              <v-col cols="12" sm="3" md="3">
                <v-select
                  class="p-0"
                  v-model="select"
                  :items="tournaments"
                  item-text="nameTournament"
                  item-value="idTournament"
                  label="All Competitions"
                  dense
                  solo
                ></v-select>
              </v-col>
            </v-row>

            <h5 class="table__Title">Top Scorers</h5>
            <v-divider style="margin: 0 !important"></v-divider>
            <v-row v-if="isHavedata">
              <v-col>
                <v-data-table
                  :headers="headers"
                  :items="desserts"
                  class="elevation-1"
                  :items-per-page="15"
                >
                  <template v-slot:item.calories="{ item }">
                    <v-chip :color="getColor(item.calories)" dark>
                      {{ item.calories }}
                    </v-chip>
                  </template>
                </v-data-table>
              </v-col>
            </v-row>
            <h2 v-else>No Data Available</h2>
            <h5 class="table__Title">Match Performance</h5>
            <v-divider style="margin: 0 !important"></v-divider>
            <v-row v-if="isHavedata1">
              <v-col>
                <v-data-table
                  :headers="headers"
                  :items="desserts"
                  class="elevation-1"
                  :items-per-page="15"
                >
                  <template v-slot:item.calories="{ item }">
                    <v-chip :color="getColor(item.calories)" dark>
                      {{ item.calories }}
                    </v-chip>
                  </template>
                </v-data-table>
              </v-col>
            </v-row>
            <h2 v-else>No Data Available</h2>
            <h5 class="table__Title">Streaks</h5>
            <v-divider style="margin: 0 !important"></v-divider>
            <v-row v-if="isHavedata2">
              <v-col>
                <v-data-table
                  :headers="headers"
                  :items="desserts"
                  class="elevation-1"
                  :items-per-page="15"
                >
                  <template v-slot:item.calories="{ item }">
                    <v-chip :color="getColor(item.calories)" dark>
                      {{ item.calories }}
                    </v-chip>
                  </template>
                </v-data-table>
              </v-col>
            </v-row>
            <h2 v-else>No Data Available</h2>
          </v-card-text>

          <!-- <v-divider></v-divider> -->

          <!-- <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="success" depressed> Post </v-btn>
          </v-card-actions> -->
        </v-col>
        <v-col cols="12" sm="4">
          <!-- <v-row style="height: 102px"></v-row> -->
          <v-row>
            <v-data-table
              :headers="headers"
              :items="desserts"
              class="elevation-1"
              :items-per-page="15"
            >
              <template v-slot:item.calories="{ item }">
                <v-chip :color="getColor(item.calories)" dark>
                  {{ item.calories }}
                </v-chip>
              </template>
            </v-data-table>
          </v-row>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>
<script>
export default {
  data: () => ({
    select: "",
    isHavedata1: true,
    isHavedata2: true,
    isHavedata: true,
    headers: [
      {
        text: "Name",
        align: "start",
        value: "name",
      },
      { text: "Position", value: "calories" },
      { text: "Age", value: "protein" },
      { text: "Nation", value: "fat" },
      { text: "Appearances", value: "fat" },
      { text: "Saves", value: "fat" },
      { text: "Assists", value: "fat" },
      { text: "Fouls Committed", value: "fat" },
    ],
    desserts: [],
    tournaments: [],
    tournament: {},
  }),
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