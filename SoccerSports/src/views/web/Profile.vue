<template>
  <v-container style="font-family: time new roman">
    <v-row>
      <v-col cols="12" sm="2"> </v-col>
      <v-col cols="12" sm="8">
        <v-card>
          <v-row>
            <v-col cols="12" sm="3">
              <v-img
                style=""
                lazy-src="https://picsum.photos/id/11/10/6"
                max-height="150"
                max-width="250"
                src="https://picsum.photos/id/11/500/300"
              ></v-img>
            </v-col>
            <v-col>
              <h2><b>JORDAN PICKFORD</b></h2>
              <v-avatar tile>
                <img
                  src="https://cdn.vuetifyjs.com/images/john.jpg"
                  alt="John"
                />
              </v-avatar>
              <h3 style="display: inline-block"><b>Man City</b></h3>
              <h3>Position:<b>Defenders</b></h3>
              <h5 style="font-family: time new roman; font-size: 20px">
                Tournament:<b>AFF Cup</b>
              </h5>
            </v-col>
            <v-col>
              <h3>Country:<b>America</b></h3>
              <h3>Age:<b>23</b></h3>
              <h3>Sex:<b>Male</b></h3>
            </v-col>
          </v-row>
        </v-card>
        <v-card style="margin-top: 50px">
          <v-card-title><b style="color: red"> Result</b></v-card-title>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">Tournament</th>
                  <th class="text-left">Date</th>
                  <th class="text-left">Event</th>
                  <th class="text-left">Location</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, i) in 5" :key="i">
                  <td>AFF CUP</td>
                  <td>May 5 2020 05:40</td>
                  <td>
                    <v-row>
                      <v-col>
                        <v-avatar tile>
                          <img
                            src="https://cdn.vuetifyjs.com/images/john.jpg"
                            alt="John"
                          /> </v-avatar></v-col
                      ><v-col> ManCity</v-col><v-col>-1 1-</v-col
                      ><v-col>Mu</v-col
                      ><v-col>
                        <v-avatar tile>
                          <img
                            src="https://cdn.vuetifyjs.com/images/john.jpg"
                            alt="John"
                          /> </v-avatar
                      ></v-col>
                    </v-row>
                  </td>
                  <td>Bắc Triều Tiên</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card>
        <v-card style="margin-top: 50px">
          <v-card-title><b style="color: red"> Schedule</b></v-card-title>
          <v-row class="fill-height">
            <v-col>
              <v-sheet height="64">
                <v-toolbar flat>
                  <v-btn
                    outlined
                    class="mr-4"
                    color="grey darken-2"
                    @click="setToday"
                  >
                    Today
                  </v-btn>
                  <v-btn fab text small color="grey darken-2" @click="prev">
                    <v-icon small> mdi-chevron-left </v-icon>
                  </v-btn>
                  <v-btn fab text small color="grey darken-2" @click="next">
                    <v-icon small> mdi-chevron-right </v-icon>
                  </v-btn>
                  <v-toolbar-title v-if="$refs.calendar">
                    {{ $refs.calendar.title }}
                  </v-toolbar-title>
                  <v-spacer></v-spacer>
                </v-toolbar>
              </v-sheet>
              <v-sheet height="600">
                <v-calendar
                  ref="calendar"
                  v-model="focus"
                  color="primary"
                  :events="events"
                  @click:event="showEvent"
                ></v-calendar>
                <v-menu
                  v-model="selectedOpen"
                  :close-on-content-click="false"
                  :activator="selectedElement"
                  offset-x
                >
                  <v-card color="grey lighten-4" min-width="350px" flat>
                    <v-toolbar :color="selectedEvent.color" dark>
                      <v-btn icon>
                        <v-icon>mdi-pencil</v-icon>
                      </v-btn>
                      <v-toolbar-title
                        v-html="selectedEvent.name"
                      ></v-toolbar-title>
                      <v-spacer></v-spacer>
                      <v-btn icon>
                        <v-icon>mdi-heart</v-icon>
                      </v-btn>
                      <v-btn icon>
                        <v-icon>mdi-dots-vertical</v-icon>
                      </v-btn>
                    </v-toolbar>
                    <v-card-text>
                      <span v-html="selectedEvent.details"></span>
                    </v-card-text>
                    <v-card-actions>
                      <v-btn
                        text
                        color="secondary"
                        @click="selectedOpen = false"
                      >
                        Cancel
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-menu>
              </v-sheet>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
export default {
  data: () => ({
    focus: "",
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [
      {
        end: "2020-11-06 03:39:28",
        start: "2020-11-06 03:39:28",
        timed: true,
        name: "Hiliday",
      },
      {
        end: "2020-11-06 03:39:28",
        start: "2020-11-06 03:39:28",
        timed: true,
        name: "Hiliday",
      },
      {
        end: "2020-11-06 03:39:28",

        start: "2020-11-06 03:39:28",
        timed: true,
        name: "Hiliday",
      },
    ],
  }),
  mounted() {
    this.$refs.calendar.checkChange();
  },
  methods: {
    prev() {
      this.$refs.calendar.prev();
    },
    setToday() {
      this.focus = "";
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        setTimeout(() => {
          this.selectedOpen = true;
        }, 10);
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        setTimeout(open, 10);
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },

    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
  },
};
</script>