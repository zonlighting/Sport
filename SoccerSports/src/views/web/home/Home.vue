<template>
  <div>
    <img
      src="https://i.pinimg.com/564x/38/d2/c3/38d2c37984a06bd6cdc0fa46bf01ea64.jpg"
      style="width: -webkit-fill-available"
      class="ls-bg"
      alt="Slide background"
    />

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
                <template v-if="Object.keys(this.recentMatch).length > 0">
                  <v-row class="text-center" style="color: white">
                    <v-col
                      ><h2 style="color: yellow">{{ this.timeDate.ngay }}</h2>

                      DAYS</v-col
                    >
                    <v-col
                      ><h2 style="color: yellow">{{ this.timeDate.gio }}</h2>
                      HOURS</v-col
                    >
                    <v-col
                      ><h2 style="color: yellow">{{ this.timeDate.phut }}</h2>
                      MINUTES</v-col
                    >
                    <v-col
                      ><h2 style="color: yellow">{{ this.timeDate.giay }}</h2>
                      SECONDS</v-col
                    >
                  </v-row>

                  <div class="my-4 subtitle-1">
                    <v-row class="text-center">
                      <v-col style="color: white"
                        ><v-img
                          :src="
                            !!recentMatch
                              ? baseUrl + recentMatch.team[0].logo
                              : ''
                          "
                          style="height: 100px"
                          width="100px"
                          lazy-src="@/assets/err.png"
                        />
                        {{
                          !!recentMatch ? recentMatch.team[0].nameTeam : ""
                        }}</v-col
                      >
                      <v-col style="margin-top: 20px; color: yellow"
                        ><h3>VS</h3></v-col
                      >
                      <v-col style="color: white"
                        ><v-img
                          :src="
                            !!recentMatch
                              ? baseUrl + recentMatch.team[1].logo
                              : ''
                          "
                          width="100px"
                          lazy-src="@/assets/err.png"
                          style="height: 100px"
                        />{{
                          !!recentMatch ? recentMatch.team[1].nameTeam : ""
                        }}</v-col
                      >
                    </v-row>
                  </div>
                  <div class="text-center" style="color: white">
                    <h5>
                      {{ recentMatch.location }} |
                      {{ new Date(time).toString().substring(0, 15) }}
                    </h5>
                    <h3>{{ time.substring(11, 16) }}</h3>
                  </div>
                </template>
                <template v-else>
                  <h2 style="color: white">No Recent Match</h2>
                </template>
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
                <v-btn fab style="left: 270px" @click="lastVideo = true"
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
            <v-carousel height="210" hide-delimiters style="margin-left: 80px">
              <v-carousel-item v-for="(item, i) in lastResults" :key="i">
                <v-sheet color="white" height="100%">
                  <v-container>
                    <v-row>
                      <span v-for="(item, index) in lastResults" :key="index">
                        <template v-if="index + 3 > i">
                          <v-col
                            @click="detailResults(item)"
                            style="cursor: pointer"
                          >
                            <div
                              style="
                                margin-left: 60px;
                                background-image: url(https://rstheme.com/products/html/khelo/images/background/result-bg.jpg);
                              "
                            >
                              <div>
                                <h4 style="color: white">
                                  {{
                                    item.location.length > 30
                                      ? item.location.substring(0, 23) + "..."
                                      : item.location
                                  }}
                                </h4>
                              </div>
                              <v-row>
                                <v-col>
                                  <v-img
                                    :src="baseUrl + item.team[0].logo"
                                    style="
                                      margin-left: 20px;
                                      height: 100px;
                                      width: 70px;
                                    "
                                    lazy-src="@/assets/err.png"
                                    min-width="30px"
                                  />
                                  {{
                                    item.team[0].nameTeam.length > 10
                                      ? item.team[0].nameTeam
                                          .toString()
                                          .substring(0, 8)
                                      : item.team[0].nameTeam
                                  }}</v-col
                                >
                                <v-col style="margin-top: 20px"
                                  ><h2>
                                    {{ item.score1 }}-{{ item.score2 }}
                                  </h2></v-col
                                >
                                <v-col
                                  ><v-img
                                    :src="baseUrl + item.team[1].logo"
                                    style="
                                      margin-right: 20px;
                                      height: 100px;
                                      width: 70px;
                                    "
                                    lazy-src="@/assets/err.png"
                                    min-width="30px"
                                  />
                                  {{
                                    item.team[1].nameTeam.length > 10
                                      ? item.team[1].nameTeam
                                          .toString()
                                          .substring(0, 8)
                                      : item.team[1].nameTeam
                                  }}</v-col
                                >
                              </v-row>
                            </div>
                          </v-col>
                        </template>
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
            <div
              @click="
                detailTournament(
                  tournamentResults[tournamentResults.length - 1].idTournament
                )
              "
              style="cursor: pointer"
            >
              <v-img
                lazy-src="https://picsum.photos/id/11/10/6"
                min-width="800"
                min-height="530"
                :src="
                  tournamentResults.length > 0
                    ? baseUrl +
                      tournamentResults[tournamentResults.length - 1].banner
                    : ''
                "
              ></v-img>
              <div style="position: absolute; bottom: 40px; color: white">
                <v-container
                  style="
                    background-image: linear-gradient(
                      rgba(33, 71, 144, 0.85) 30%,
                      rgba(255, 255, 255, 0) 70%
                    );
                  "
                >
                  <h3>
                    {{
                      tournamentResults.length > 0
                        ? new Date(
                            tournamentResults[
                              tournamentResults.length - 1
                            ].timeStart.substring(0, 4),
                            tournamentResults[
                              tournamentResults.length - 1
                            ].timeStart.substring(5, 7),
                            tournamentResults[
                              tournamentResults.length - 1
                            ].timeStart.substring(8, 10)
                          )
                            .toString()
                            .substring(0, 18)
                        : ""
                    }}
                  </h3>
                  <h2>
                    {{
                      tournamentResults.length > 0
                        ? tournamentResults[tournamentResults.length - 1]
                            .nameTournament
                        : "No data"
                    }}
                  </h2>
                  <p style="color: white">
                    {{
                      tournamentResults.length > 0
                        ? tournamentResults[tournamentResults.length - 1]
                            .description
                        : ""
                    }}
                  </p>
                </v-container>
              </div>
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
                    <tr
                      v-for="(item, index) in rankAll"
                      :key="index"
                      @click="detailTeam(item)"
                    >
                      <template v-if="index < 12">
                        <th style="color: white">{{ index + 1 }}</th>
                        <th style="color: white">{{ item.nameTeam }}</th>
                        <th style="color: white">{{ item.totalWin }}</th>
                        <th style="color: white">{{ item.totalAdraw }}</th>
                        <th style="color: white">
                          {{
                            item.totalMatch - item.totalWin - item.totalAdraw
                          }}
                        </th>
                        <th style="color: white">{{ item.pointAll }}</th>
                      </template>
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
              <v-col v-for="(item, index) in tournamentResults" :key="index"
                ><v-card
                  max-width="250"
                  min-height="360px"
                  @click="detailTournament(item.idTournament)"
                >
                  <v-img height="250" :src="baseUrl + item.banner"></v-img>

                  <v-card-title>{{ item.nameTournament }}</v-card-title>

                  <v-card-text> </v-card-text> </v-card
              ></v-col>
            </v-row>
          </v-col>
          <v-col cols="12" sm="3">
            <h1>MATCH FIXTURES</h1>
            <template v-if="matchFixtures.length > 0">
              <v-carousel v-model="model2" height="210" hide-delimiters>
                <v-carousel-item v-for="(item, i) in matchFixtures" :key="i">
                  <v-sheet color="white" height="100%">
                    <v-container>
                      <div
                        @click="detailResults(item)"
                        style="
                          cursor: pointer;
                          background-image: url(https://rstheme.com/products/html/khelo/images/background/result-bg.jpg);
                        "
                      >
                        <div>
                          <h3 class="text-center">{{ item.location }}</h3>
                        </div>
                        <v-row>
                          <v-col class="text-center" style="margin-left: 20px">
                            <v-img
                              :src="baseUrl + item.team[0].logo"
                              lazy-src="@/assets/err.png"
                              width="100px"
                              height="100xp"
                              style="height: 100px"
                            />
                            {{ item.team[0].nameTeam }}
                          </v-col>
                          <v-col style="margin-top: 20px" class="text-center"
                            ><h5>{{ item.timeStart.substring(0, 10) }}</h5>
                            {{ item.timeStart.substring(11, 16) }}</v-col
                          >
                          <v-col class="text-center"
                            ><v-img
                              :src="baseUrl + item.team[1].logo"
                              lazy-src="@/assets/err.png"
                              width="100px"
                              style="height: 100px"
                            />
                            Valencia</v-col
                          >
                        </v-row>
                      </div>
                      <v-card> </v-card>
                    </v-container>
                  </v-sheet>
                </v-carousel-item>
              </v-carousel>
            </template>
            <template v-else> No Match Fixtures </template>
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
            <v-carousel height="350" hide-delimiters>
              <v-carousel-item v-for="(items, i) in team.length" :key="i">
                <v-sheet color="white" height="100%">
                  <v-container>
                    <v-row>
                      <span v-for="(item, index) in team" :key="index">
                        <v-col v-if="index > i">
                          <div
                            @click="detailTeam(item)"
                            style="
                              margin-left: 12px;
                              cursor: pointer;
                              background-image: url(https://rstheme.com/products/html/khelo/images/background/result-bg.jpg);
                            "
                          >
                            <v-card width="290">
                              <v-img
                                height="250"
                                :src="baseUrl + item.logo"
                              ></v-img>
                              <v-card-title>{{ item.nameTeam }}</v-card-title>

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
    <v-dialog v-model="lastVideo" max-width="500px">
      <video
        controls
        width="500"
        height="500"
        :src="baseUrl + video"
        style="background: black"
      ></video>
    </v-dialog>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  created() {
    this.getRecentMatch();
    this.getLastResults();
    this.getTournament();
    this.getLastVideo();
    this.getRank();
    this.getMatchFixtures();
    this.getTeam();
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  mounted() {},
  data: () => ({
    rankAll: [],
    video: "",
    tournamentResults: [],
    recentMatch: [],
    lastVideo: false,
    day: "",
    time: "",
    model1: 0,
    model2: 0,
    model3: 0,
    timeDate: "",
    lastResults: [],
    matchFixtures: [],
    team: [],
  }),
  methods: {
    getTeam() {
      this.$store.dispatch("team/getTeams").then((response) => {
        this.team = response.data.payload;
      });
    },
    getRecentMatch() {
      this.$store
        .dispatch("schedule/recentMatch")
        .then((response) => {
          if (response.data.code == 0) {
            this.recentMatch = response.data.payload;
            if (Object.keys(this.recentMatch).length > 0) {
              this.time = response.data.payload.timeStart;

              this.setintervalTime(this.time);
            }
          } else {
            console.log(response);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },
    getLastVideo() {
      this.$store
        .dispatch("schedule/lastVideo")
        .then((response) => {
          if (response.data.code == 0) {
            this.video = response.data.payload;
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },
    getLastResults() {
      this.$store
        .dispatch("schedule/lastResults")
        .then((response) => {
          if (response.data.code == 0) {
            this.lastResults = response.data.payload;
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },
    getTournament() {
      this.$store
        .dispatch("tournament/tournamentStatus", 2)
        .then((response) => {
          if (response.data.code == 0) {
            this.tournamentResults = response.data.payload;
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },
    getRank() {
      this.$store
        .dispatch("tournament/rankAll")
        .then((response) => {
          if (response.data.code == 0) {
            this.rankAll = response.data.payload;
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },
    detailTournament(item) {
      this.$router.push({ path: "/tournamentDetail/" + item });
    },
    detailResults(item) {
      console.log(item.idSchedule);
      this.$router.push({ path: "/summary/" + item.idSchedule });
    },
    getMatchFixtures() {
      this.$store
        .dispatch("schedule/getByStatus", 0)
        .then((response) => {
          if (response.data.code == 0) {
            this.matchFixtures = response.data.payload;
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },
    detailTeam(item) {
      this.$router.push({
        path: `/team/${item.idTeam}`,
        query: { idTab: 1 },
      });
    },

    setintervalTime(time) {
      console.log(time);
      var a = Date.parse(time);
      var b = Date.now();
      var c = a - b;
      c = Math.floor(c / 1000);
      setInterval(
        function () {
          c = c - 5;
          var giay = c % 60;
          var phut = (c / 60) % 60;
          var gio = (c / 3600) % 24;
          var ngay = c / 86000;
          this.timeDate = {
            giay: Math.ceil(giay),
            phut: Math.ceil(phut),
            gio: Math.ceil(gio),
            ngay: Math.ceil(ngay),
          };
        }.bind(this),
        5000
      );
    },
  },
};
</script>

<style lang="scss" scoped >
#title {
  color: yellow;
}
tbody {
  tr:hover {
    background-color: red !important;
    cursor: pointer;
  }
}
</style>