<template>
  <div>
    <v-container>
      <v-row style="margin-left: 100px; margin-right: 100px">
        <v-col cols="12" sm="2"> </v-col>
        <v-col cols="12" sm="8">
          <v-card>
            <v-container>
              <b>Video</b>
              <hr />
              <video :src="baseUrl+schedule.video"  controls style="max-width:900px"></video>
            </v-container>
          </v-card>
          <v-card style="margin-top: 50px" >
            <v-container>
              <b>Photo</b>
              <hr />
              <v-img :src="baseUrl+schedule.image" style="max-width:900px" ></v-img>
            </v-container>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
        schedule:{}
    };
  },

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      this.$store.commit("auth/auth_overlay_true");
      this.$store
        .dispatch("schedule/getById", this.$route.params.id)
        .then((response) => {
          this.$store.commit("auth/auth_overlay_false");
          if (response.data.code == 0) {
            this.schedule = response.data.payload;
          } else {
            alert(response.data.message);
          }
        });
    },
  },
};
</script>