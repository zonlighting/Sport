<template>
  <div>
    <v-card class="mx-auto" max-width="85%">
      <v-row class="container">
        <v-col cols="0" sm="1"></v-col>
        <v-col cols="12" sm="7">
          <v-card-text>
            <v-row>
              <v-col cols="12" sm="6">
                <h1 class="title-h1">Albania Squad</h1>
              </v-col>
            </v-row>
            <v-row style="max-height:50px">
              <v-col>
                <h2 class="pl-5">Tournament name</h2>
              </v-col>
              <v-col cols="12" sm="3">
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
            <h5 class="table__Title">November, 2020</h5>
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
    isHavedata: true,
    headers: [
      {
        text: "DATE",
        align: "start",
        sortable: false,
        value: "name",
      },
      { text: "", value: "calories", sortable: false },
      { text: "", value: "protein", sortable: false },
      { text: "MATCH", value: "fat", sortable: false },
      { text: "", value: "carbs", sortable: false },
      { text: "", value: "carbs", sortable: false },
      { text: "TIME", value: "", sortable: false },
      { text: "COMPETITION", value: "", sortable: false },
      { text: "Status", value: "", sortable: false },
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