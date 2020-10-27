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
              <a >Preview/	Recap</a>
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
    dialog: false,
    dialogDelete: false,
    headers: [
      {
        text: "Date",
        align: "start",
        value: "name",
      },
      { text: "EVENT", value: "calories" },
      { text: "Time/RESULTS", value: "fat" },
      { text: "VENUE", value: "carbs" },
      { text: "ARTICLE", value: "article" },
     
    ],
    desserts: [],
    editedIndex: -1,
    editedItem: {
      name: "",
      calories: 0,
      fat: 0,
      carbs: 0,
      protein: 0,
    },
    defaultItem: {
      name: "",
      calories: 0,
      fat: 0,
      carbs: 0,
      protein: 0,
    },
  }),



  
  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      this.desserts = [
        {
          name: "Frozen Yogurt",
          calories: 159,
          fat: 6.0,
          carbs: 24,
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
          fat: 9.0,
          carbs: 37,
        },
        {
          name: "Eclair",
          calories: 262,
          fat: 16.0,
          carbs: 23,
        },
        {
          name: "Cupcake",
          calories: 305,
          fat: 3.7,
          carbs: 67,
        },
        {
          name: "Gingerbread",
          calories: 356,
          fat: 16.0,
          carbs: 49,
        },
        {
          name: "Jelly bean",
          calories: 375,
          fat: 0.0,
          carbs: 94,
        },
        {
          name: "Lollipop",
          calories: 392,
          fat: 0.2,
          carbs: 98,
        },
        {
          name: "Honeycomb",
          calories: 408,
          fat: 3.2,
          carbs: 87,
        },
        {
          name: "Donut",
          calories: 452,
          fat: 25.0,
          carbs: 51,
        },
        {
          name: "KitKat",
          calories: 518,
          fat: 26.0,
          carbs: 65,
        },
      ];
    },

  }
};
</script>