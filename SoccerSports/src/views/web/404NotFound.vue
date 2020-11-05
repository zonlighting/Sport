<template>
  <div
    style="
      background-image: url(https://eskipaper.com/images/sports-wallpaper-14.jpg);
      height: 1000px;
    "
  >
    <v-card-title>
      <h1 style="color: white">Opps Page Not Found !!!</h1>
    </v-card-title>
    <v-card-text style="color: white">
      OMG ! Looks like someone has run off with that page!
      <a @click="toHomePage" variant="primary" style="color: grey">
        Back To Home
      </a>
    </v-card-text>
  </div>
</template>
<script>
export default {
  methods: {
    toHomePage() {
      this.$router.push({
        path: `/home`,
      }).go();
    },
  },
};
</script>

<style>
</style>
