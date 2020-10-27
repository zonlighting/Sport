<template>
  <div>
    <div>
      <img
        src="http://max-themes.net/demos/bigslam/soccer3/upload/page-title.jpg"
        class="ls-bg"
        alt="Slide background"
        style="margin-left: 60px"
      />
      <div style="position: absolute; top: 320px; left: 770px">
        <p style="font-size: 70px; color: #ffffff">SCHEDULE</p>
      </div>
    </div>
    <section>
      <v-container>
        <v-card>
          <v-data-table
            :headers="headers"
            :items="desserts"
            sort-by="name"
            class="elevation-1"
          >
            <template v-slot:top>
              <v-toolbar flat>
                <v-toolbar-title>Word Cup</v-toolbar-title>
                <v-divider class="mx-4" inset vertical></v-divider>
                <v-spacer></v-spacer>
              </v-toolbar>
            </template>
            <template v-slot:[`item.article`]>
              <a>Preview/ Recap</a>
            </template>
            <template v-slot:no-data>
              <v-btn color="primary" @click="initialize"> Reset </v-btn>
            </template>
          </v-data-table>
        </v-card>
      </v-container>
    </section>
  </div>
</template>
<script>
export default {
  data: () => ({
    headers: [
      {
        text: "Date",
        align: "start",
        value: "name",
      },
      { text: "EVENT", value: "event" },
      { text: "Time/RESULTS", value: "time" },
      { text: "VENUE", value: "venue" },
      { text: "ARTICLE", value: "article" },
    ],
    desserts: [],
  }),

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      this.desserts = [
        {
          name: "Frozen Yogurt",
          event: "Mancity vs MU",
          time: "14-5-5",
          venue: "Hà Nội",
        },
        {
          name: "Ice cream sandwich",
          event: "Mancity vs MU",
          time: "14-5-5",
          venue: "Hà Nội",
        },
        {
          name: "Eclair",
          event: "Mancity vs MU",
          time: "14-5-5",
          venue: "Hà Nội",
        },
        {
          name: "Cupcake",
          event: "Mancity vs MU",
          time: "14-5-5",
          venue: "Hà Nội",
        },
        {
          name: "Gingerbread",
          event: "Mancity vs MU",
          time: "14-5-5",
          venue: "Hà Nội",
        },
        {
          name: "Jelly bean",
          event: "Mancity vs MU",
          time: "14-5-5",
          venue: "Hà Nội",
        },
        {
          name: "Lollipop",
          event: "Mancity vs MU",
          time: "14-5-5",
          venue: "Hà Nội",
        },
        {
          name: "Honeycomb",
          event: "Mancity vs MU",
          time: "14-5-5",
          venue: "Hà Nội",
        },
        {
          name: "Donut",
          event: "Mancity vs MU",
          time: "14-5-5",
          venue: "Hà Nội",
        },
        {
          name: "KitKat",
          event: "Mancity vs MU",
          time: "14-5-5",
          venue: "Hà Nội",
        },
      ];
    },
  },
};
</script>