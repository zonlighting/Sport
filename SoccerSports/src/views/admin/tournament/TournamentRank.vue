<template>
  <v-card>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">#</th>
            <th class="text-left">Team</th>
            <th class="text-left">Logo</th>
            <th class="text-left">GP</th>
            <th class="text-left">Win</th>
            <th class="text-left">ADRAW</th>
            <th class="text-left">LOSE</th>
            <th class="text-left">Point</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in rank" :key="index">
            <td>
              {{ index + 1 }}
            </td>
            <td>{{ item.nameTeam }}</td>
            <td>
              <v-avatar tile>
                <img :src="baseUrl + item.logo" alt="John" />
              </v-avatar>
            </td>
            <td>{{ item.totalMatchByTour }}</td>
            <td>{{ item.totalWinByTour }}</td>
            <td>{{ item.totalAdrawByTour }}</td>
            <td>
              {{
                item.totalMatchByTour -
                item.totalAdrawByTour -
                item.totalWinByTour
              }}
            </td>
            <td>{{ item.pointByTour }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </v-card>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
      rank: "",
    };
  },
  props: {
    tournament: Object,
  },
  created() {
    this.$store.commit("auth/auth_overlay_true");
    this.$store
      .dispatch("tournament/tournamentRank", this.tournament.idTournament)
      .then((response) => {
        this.$store.commit("auth/auth_overlay_false");
        if (response.data.code == 0) {
          this.rank = response.data.payload;
        }
      });
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
};
</script>