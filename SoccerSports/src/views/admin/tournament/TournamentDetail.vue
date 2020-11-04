<template>
  <div>
    <div class="text-center">
      <h1>{{ tournament.nameTournament }}</h1>
      {{ tournament.timeStart }}<v-icon>mdi-arrow-right-bold</v-icon
      >{{ tournament.timeEnd }}<br />

      <div style="margin-top: 50px">
        <v-tabs v-model="tab" background-color="transparent" color="basil" grow>
          <v-tab>Team </v-tab> <v-tab>Schedule </v-tab> <v-tab>Rank </v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab">
          <v-tab-item>
            <v-card color="basil" flat>
              <v-card-text>
                  <TournamentTeam :getData="getData" :tournament="tournament"/>
              </v-card-text>
            </v-card>
          </v-tab-item>
           <v-tab-item>
            <v-card color="basil" flat>
              <v-card-text><TournamentSchedule :getData="getData" :tournament="tournament"/></v-card-text>
            </v-card>
          </v-tab-item>
           <v-tab-item>
            <v-card color="basil" flat>
              <v-card-text><TournamentRank :getData="getData" :tournament="tournament"/></v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs-items>
      </div>
    </div>
  </div>
</template>
<script>
import TournamentSchedule from './TournamentSchedule'
import TournamentTeam from './TournamentTeam'
import TournamentRank from './TournamentRank'
export default {
  data: () => {
    return {
      tournament: {},
      tab: null,
    };
  },
  components:{
    TournamentSchedule,TournamentTeam,TournamentRank

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