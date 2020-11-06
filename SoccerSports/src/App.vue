<template>
  <v-app>
    <v-main>
      <router-view />
      <v-overlay :value="overlay">
        <v-progress-circular indeterminate size="64"></v-progress-circular>
      </v-overlay>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",

  components: {},

  data: () => ({}),

  computed: {
    overlay() {
      return this.$store.state.auth.overlay;
    },
  },
};
</script>
