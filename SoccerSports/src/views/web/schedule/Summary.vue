<template>
  <v-container>
    <v-row style="margin-left: 100px; margin-right: 100px">
      <v-col cols="12" sm="3">
        <v-card>
          <v-tabs v-model="tab" grow>
            <v-tab v-for="item in items" :key="item">
              {{ item }}
            </v-tab>
          </v-tabs>

          <v-tabs-items v-model="tab">
            <v-tab-item>
              <v-card>
                <v-card-text>
                  <v-list-item v-for="n in 10" :key="n">
                    <v-list-item-content> D.RICE </v-list-item-content>
                  </v-list-item>
                </v-card-text>
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <v-card>
                <v-card-text>
                  <v-list-item v-for="n in 10" :key="n">
                    <v-list-item-content> D.Hery Hery </v-list-item-content>
                  </v-list-item>
                </v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-col>
      <v-col cols="12" sm="6">
        <v-card>
          <v-container>
            <b>Top Scorers</b>
            <hr />
            <v-row>
              <v-col cols="12" sm="6">
                <div class="text-center">
                  <v-avatar size="50"
                    ><img src="@/assets/logodemo.png" alt="John"
                  /></v-avatar>
                  <h5>Atletico Madrid</h5>
                  <v-row>
                    <v-col cols="12" sm="4" v-for="n in 9" :key="n">
                      <v-card>
                        <v-img
                          lazy-src="https://picsum.photos/id/11/10/6"
                          src="https://picsum.photos/id/11/500/300"
                        ></v-img>
                        <b> Patson Daka</b>
                        Goals:{{ n }}
                      </v-card>
                    </v-col>
                  </v-row>
                </div>
              </v-col>

              <v-col cols="12" sm="6" style="border-left: solid 1px #eaeaea">
                <div class="text-center">
                  <v-avatar size="50"
                    ><img src="@/assets/logodemo.png" alt="John"
                  /></v-avatar>
                  <h5>Atletico Madrid</h5>
                  <v-row>
                    <v-col cols="12" sm="4" v-for="n in 9" :key="n">
                      <v-card>
                        <v-img
                          lazy-src="https://picsum.photos/id/11/10/6"
                          src="https://picsum.photos/id/11/500/300"
                        ></v-img>
                        <b> Patson Daka</b>
                        Goals:{{ n }}
                      </v-card>
                    </v-col>
                  </v-row>
                </div>
              </v-col>
            </v-row>
          </v-container>
        </v-card>
      </v-col>
      <v-col cols="12" sm="3">
        <v-card>
          <v-container>
            <b>UEFA Champions League Standings</b>
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-left">POS</th>
                    <th class="text-left">TEAM</th>
                    <th class="text-left">GP</th>
                    <th class="text-left">GD</th>
                    <th class="text-left">PTS</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="item in desserts" :key="item.name">
                    <td>{{ item.name }}</td>
                    <td>{{ item.calories }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table><div class="text-center">
            <a href="#" >Full table</a></div>
          </v-container>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
export default {
  data() {
    return {
      tab: null,
      items: ["Team1", "Team2"],
      desserts: [
        {
          name: "Frozen Yogurt",
          calories: 159,
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
        },
        {
          name: "Eclair",
          calories: 262,
        },
        {
          name: "Cupcake",
          calories: 305,
        },
        {
          name: "Gingerbread",
          calories: 356,
        },
        {
          name: "Jelly bean",
          calories: 375,
        },
        {
          name: "Lollipop",
          calories: 392,
        },
        {
          name: "Honeycomb",
          calories: 408,
        },
        {
          name: "Donut",
          calories: 452,
        },
        {
          name: "KitKat",
          calories: 518,
        },
      ],
    };
  },
};
</script>