<template>
  <v-app>
    <v-main>
      <Index></Index>
    </v-main>
  </v-app>
</template>

<script>
import Index from "./views/web/index";
export default {
  name: "App",

  components: {
    Index,
  },

  data: () => ({
    //
  }),
};
</script>
