<template>
  <div>
    <div class="banner" id="layerSlider" style="width: 100%">
       <img
       src=images/background/latestnews.jpg class=ls-bg alt="Slide
        background"
      />
   
    </div>
    <section class="booking bg-smallwhite">
      <div class="container">
        <v-row class="text-center">
          <v-col cols="12" md="2" />
          <v-col cols="12" md="4">
            <v-card
              max-width="400"
              min-height="415"
              style="
                margin-left: 80px;
                background-image: url(https://rstheme.com/products/html/khelo/images/background/upcoming-match-bg.jpg);
              "
            >
              <v-card-title class="justify-center" style="color: white"
                >UpComming Match</v-card-title
              >

              <v-card-text>
                <v-row class="text-center" style="color: white">
                  <v-col
                    ><h2 style="color: yellow">16</h2>
                    DAYS</v-col
                  >
                  <v-col
                    ><h2 style="color: yellow">8</h2>
                    HOURS</v-col
                  >
                  <v-col
                    ><h2 style="color: yellow">6</h2>
                    MINUTES</v-col
                  >
                  <v-col
                    ><h2 style="color: yellow">6</h2>
                    SECONDS</v-col
                  >
                </v-row>

                <div class="my-4 subtitle-1">
                  <v-row class="text-center">
                    <v-col style="color: white"
                      ><v-img src=images/soccer-logo-by-Vexels.png width="100px"
                      lazy-src="@/assets/err.png" /> Valencia</v-col
                    >
                    <v-col style="margin-top: 20px; color: yellow"
                      ><h3>VS</h3></v-col
                    >
                    <v-col style="color: white"
                      ><v-img src=images/soccer-logo-by-Vexels.png width="100px"
                      lazy-src="@/assets/err.png" /> Real Sociedad</v-col
                    >
                  </v-row>
                </div>
                <div class="text-center" style="color: white">
                  <h5>Mestalla Stadium | August 14, 2019</h5>
                  <h3>19:00:00</h3>
                </div>
              </v-card-text>
            </v-card>
          </v-col>
          <v-col cols="12" sm="6">
            <v-card min-height="415" max-width="640">
              <v-img
                src="https://rstheme.com/products/html/khelo/images/background/video1-bg.jpg"
                height="415"
              ></v-img>
              <div style="position: absolute; top: 180px">
                <v-btn fab style="left: 270px"
                  ><v-icon>mdi-arrow-right-drop-circle</v-icon>
                </v-btn>
              </div>
              <h3
                style="
                  position: absolute;
                  top: 250px;
                  left: 170px;
                  color: white;
                "
              >
                Last Match Highlights
              </h3>
            </v-card>
          </v-col>
        </v-row>
      </div>
    </section>
    <section class="booking bg-smallwhite">
      <div class="container">
        <h1 class="text-center">Results</h1>
        <v-row class="text-center">
          <v-col cols="12" md="2" />
          <v-col cols="12" md="8">
            <v-carousel
              v-model="model"
              height="210"
              hide-delimiters
              style="margin-left: 80px"
            >
              <v-carousel-item
                v-for="(item, i) in schedule.length - 2"
                :key="i"
              >
                <v-sheet color="white" height="100%">
                  <v-container>
                    <v-row>
                      <span v-for="(team, index) in schedule" :key="index">
                        <v-col v-if="index >= i">
                          <div
                            style="
                              margin-left: 60px;
                              background-image: url(https://rstheme.com/products/html/khelo/images/background/result-bg.jpg);
                            "
                          >
                            <div><h3>Mestalla Stadium</h3></div>
                            <v-row>
                              <v-col>
                                <v-img src=images/soccer-logo-by-Vexels.png
                                lazy-src="@/assets/err.png" width="100px" />
                                {{ team.team1 }}
                              </v-col>
                              <v-col style="margin-top: 20px"
                                ><h2>1-0</h2></v-col
                              >
                              <v-col
                                ><v-img src=images/soccer-logo-by-Vexels.png
                                lazy-src="@/assets/err.png" width="100px" />
                                Valencia</v-col
                              >
                            </v-row>
                          </div>
                        </v-col>
                      </span>
                    </v-row>
                    <v-card> </v-card>
                  </v-container>
                </v-sheet>
              </v-carousel-item>
            </v-carousel>
          </v-col>
        </v-row>
      </div>
    </section>
    <section class="booking bg-smallwhite">
      <v-container>
        <v-row>
          <v-col cols="12" sm="2"></v-col>
          <v-col cols="12" sm="6">
            <h1>LATEST TOURNAMENT</h1>
            <v-img
              lazy-src="https://picsum.photos/id/11/10/6"
              min-width="800"
              min-height="530"
              src="https://picsum.photos/id/11/500/300"
            ></v-img>
            <div style="position: absolute; bottom: 40px; color: white">
              <v-container
                style="
                  background-image: linear-gradient(
                    to top,
                    rgba(33, 71, 144, 0.85) 30%,
                    rgba(255, 255, 255, 0) 70%
                  );
                "
              >
                <h3>May 25,2019</h3>
                <h2>Latest Point Table For The Premier League</h2>
                <p>
                  The snatch is a wide-grip, one-move lift. The clean and jerk
                  is a close-grip...
                </p>
              </v-container>
            </div>
          </v-col>
          <v-col cols="12" sm="3">
            <h1>POINT TABLE</h1>
            <div>
              <v-simple-table
                style="
                  background-image: url(https://rstheme.com/products/html/khelo/images/background/point-table-bg.jpg);
                "
                width=""
              >
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-left" id="title"></th>
                      <th class="text-left" id="title">TEAM</th>
                      <th class="text-left" id="title">W</th>
                      <th class="text-left" id="title">D</th>
                      <th class="text-left" id="title">L</th>
                      <th class="text-left" id="title">PTS</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="item in rank" :key="item.name">
                      <th style="color: white">{{ item.stt }}</th>
                      <th style="color: white">{{ item.team }}</th>
                      <th style="color: white">{{ item.win }}</th>
                      <th style="color: white">9</th>
                      <th style="color: white">{{ item.lose }}</th>
                      <th style="color: white">{{ item.pts }}</th>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </div>
          </v-col>
        </v-row>
      </v-container>
    </section>
    <section class="booking bg-smallwhite">
      <v-container>
        <v-row>
          <v-col cols="12" sm="2"> </v-col>
          <v-col cols="12" sm="6">
            <v-row>
              <v-col
                ><v-card max-width="250">
                  <v-img
                    height="250"
                    src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
                  ></v-img>

                  <v-card-title>Cafe Badilico</v-card-title>

                  <v-card-text> </v-card-text> </v-card
              ></v-col>
              <v-col
                ><v-card max-width="250">
                  <v-img
                    height="250"
                    src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
                  ></v-img>

                  <v-card-title>Cafe Badilico</v-card-title>

                  <v-card-text> </v-card-text> </v-card
              ></v-col>
              <v-col
                ><v-card max-width="250">
                  <v-img
                    height="250"
                    src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
                  ></v-img>

                  <v-card-title>Cafe Badilico</v-card-title>

                  <v-card-text> </v-card-text> </v-card
              ></v-col>
            </v-row>
          </v-col>
          <v-col cols="12" sm="3">
            <h1>MATCH FIXTURES</h1>
            <v-carousel v-model="model" height="210" hide-delimiters>
              <v-carousel-item v-for="(item, i) in schedule" :key="i">
                <v-sheet color="white" height="100%">
                  <v-container>
                    <div
                      style="
                        background-image: url(https://rstheme.com/products/html/khelo/images/background/result-bg.jpg);
                      "
                    >
                      <div><h3 class="text-center">Mestalla Stadium</h3></div>
                      <v-row>
                        <v-col class="text-center">
                          <v-img src=images/soccer-logo-by-Vexels.png
                          lazy-src="@/assets/err.png" width="100px" />
                          {{ item.team1 }}
                        </v-col>
                        <v-col style="margin-top: 20px" class="text-center"
                          ><h5>24-4-2019</h5>
                          19:00:00</v-col
                        >
                        <v-col class="text-center"
                          ><v-img src=images/soccer-logo-by-Vexels.png
                          lazy-src="@/assets/err.png" width="100px" />
                          Valencia</v-col
                        >
                      </v-row>
                    </div>
                    <v-card> </v-card>
                  </v-container>
                </v-sheet>
              </v-carousel-item>
            </v-carousel>
          </v-col>
        </v-row>
      </v-container>
    </section>
    <section class="booking bg-smallwhite">
      <v-container>
        <h1 class="text-center">Team</h1>
        <v-row class="text-center">
          <v-col cols="12" md="2" />
          <v-col cols="12" md="9">
            <v-carousel v-model="model" height="370" hide-delimiters>
              <v-carousel-item
                v-for="(item, i) in schedule.length - 2"
                :key="i"
              >
                <v-sheet color="white" height="100%">
                  <v-container>
                    <v-row>
                      <span v-for="(team, index) in schedule" :key="index">
                        <v-col v-if="index >= i">
                          <div
                            style="
                              margin-left: 12px;
                              background-image: url(https://rstheme.com/products/html/khelo/images/background/result-bg.jpg);
                            "
                          >
                            <v-card width="290">
                              <v-img
                                height="250"
                                src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
                              ></v-img>

                              <v-card-title>Cafe Badilico</v-card-title>

                              <v-card-text> </v-card-text>
                            </v-card>
                          </div>
                        </v-col>
                      </span>
                    </v-row>
                    <v-card> </v-card>
                  </v-container>
                </v-sheet>
              </v-carousel-item>
            </v-carousel>
          </v-col>
        </v-row>
      </v-container>
    </section>
  </div>
</template>
<script>
export default {
  data: () => ({
    model: 0,
    schedule: [
      { team1: "1", team2: "b" },
      { team1: "2", team2: "b" },
      { team1: "3", team2: "b" },
      { team1: "4", team2: "b" },
      { team1: "5", team2: "b" },
      { team1: "6", team2: "b" },
      { team1: "7", team2: "b" },
      { team1: "8", team2: "b" },
      { team1: "9", team2: "b" },
    ],
    rank: [
      { stt: "1", team: "Mancity", win: "2", lose: "3", pts: "4" },
      { stt: "2", team: "Mancity", win: "2", lose: "3", pts: "4" },
      { stt: "3", team: "Mancity", win: "2", lose: "3", pts: "4" },
      { stt: "4", team: "Mancity", win: "2", lose: "3", pts: "4" },
      { stt: "5", team: "Mancity", win: "2", lose: "3", pts: "4" },
      { stt: "6", team: "Mancity", win: "2", lose: "3", pts: "4" },
      { stt: "7", team: "Mancity", win: "2", lose: "3", pts: "4" },
      { stt: "8", team: "Mancity", win: "2", lose: "3", pts: "4" },
      { stt: "9", team: "Mancity", win: "2", lose: "3", pts: "4" },
      { stt: "10", team: "Mancity", win: "2", lose: "3", pts: "4" },
    ],
  }),
};
</script>
<style>
#title {
  color: yellow;
}
</style>