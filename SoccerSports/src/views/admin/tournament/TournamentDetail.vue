<template>
  <div>
    <h1>{{tournament.nameTournament}}</h1>
    <p v-if="tournament.status==0?'Up Comming':tournament.status==1?'On Going':'Finished'"></p>
  </div>
</template>
<script>
export default {
  data: () => {
    return {
      tournament: "",
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("tournament/getById", this.$route.params.id)
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            this.tournament = response.data.payload;
          }
        });
    },
  },
};
</script>