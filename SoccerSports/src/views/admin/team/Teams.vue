<template>
  <v-col>
    <v-breadcrumbs :items="teamLink" large>
      <template v-slot:divider>
        <v-icon>mdi-chevron-right</v-icon>
      </template>
    </v-breadcrumbs>
    <v-data-table
      @click:row="handleRowClick"
      :headers="headers"
      :items="desserts"
      class="elevation-1 container row-pointer"
    >
      <template v-slot:top>
        <v-toolbar flat color="white">
          <template>
            <v-dialog persistent v-model="createTeamDialog" max-width="1000px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn color="primary" dark v-bind="attrs" v-on="on">
                  New Team
                </v-btn>
              </template>
              <CreateTeam :getTeams="getTeams" :closeCreateTeamDialog="closeCreateTeamDialog" />
            </v-dialog>
          </template>

          <v-divider class="mx-4" inset vertical></v-divider>
          <v-row>
            <v-col cols="12" sm="3" md="3"> </v-col>

            <v-col cols="12" sm="3" md="3">
              <v-text-field
                v-model="nameTeamSearch"
                append-icon="mdi-magnify"
                label="Team Search"
                single-line
                hide-details
                class="pt-3"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="3" md="3">
              <v-text-field
                v-model="tourNameSearch"
                append-icon="mdi-magnify"
                label="Tournament Search"
                single-line
                hide-details
                class="pt-3"
              ></v-text-field>
            </v-col>

            <v-col cols="12" sm="3" md="3">
              <v-text-field
                v-model="dateSearch"
                append-icon="mdi-magnify"
                label="Date Search"
                single-line
                hide-details
                class="pt-3"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-toolbar>
        <v-divider class="mt-4 mb-8"></v-divider>
      </template>

      <template v-slot:[`item.tournament.nameTour`]="{ item }">
        <template v-if="item.idTour != 0 || item.tournament != null">
          <div style="color: red">{{ item.tournament.nameTournament }}</div>
        </template>
        <template v-else><div style="color: green">Available</div></template>
      </template>

      <template v-slot:[`item.logo`]="{ item }">
        <img
          :src="baseUrl + item.logo"
          width="100px"
          height="100px"
          style="margin: 3px 0 3px 0"
        />
      </template>
      <template v-slot:[`item.winRate`]="{ item }">
        {{ item.totalmatch != 0 ? (item.totalwin / item.totalmatch) * 100 : 0 }}
        %
      </template>
    </v-data-table>
  </v-col>
</template>

<script>
import { ENV } from "@/config/env.js";
import CreateTeam from "@/views/admin/team/CreateTeam";

export default {
  components: {
    CreateTeam,
  },
  data() {
    return {
      createTeamDialog: false,
      search: "",
      headers: [
        {
          text: "Logo",
          align: "start",
          sortable: false,
          value: "logo",
        },
        { text: "Create Date", value: "createDate", filter: this.dateFilter },
        { text: "Team Name", value: "nameTeam", filter: this.nameTeamFilter },
        { text: "Country", value: "country" },
        {
          text: "Tournament",
          value: "tournament.nameTour",
          filter: this.nameTourFilter,
        },
        {
          text: "Members Quatity",
          value: "profile.length",
        },
        { text: "Total Matchs", value: "totalmatch" },
        { text: "Total Wins", value: "totalwin" },
        { text: "Win Rate", value: "winRate" },
      ],
      desserts: [],
      maxTeamId: 0,
      nameTeamSearch: "",
      dateSearch: "",
      tourNameSearch: "",
      teamLink: [
        {
          text: "Dashboard",
          disabled: false,
          href: "/admin",
        },
        {
          text: "Teams",
          disabled: false,
          href: "/admin/team",
        },
      ],
    };
  },

  mounted() {
    this.getTeams();
  },

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  methods: {
    getTeams() {
      let self = this;
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("team/getTeams")
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code === 0) {
            self.desserts = response.data.payload;
            // console.log(self.desserts)
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },

    editTeam(item) {
      this.$router.push({ path: `/admin/team/detail/${item.idTeam}` });
    },

    handleRowClick(item) {
      this.editTeam(item);
    },

    nameTeamFilter(value) {
      // If this filter has no value we just skip the entire filter.
      if (!this.nameTeamSearch) {
        return true;
      }
      return value.toLowerCase().includes(this.nameTeamSearch.toLowerCase());
    },

    nameTourFilter(value) {
      if (!this.tourNameSearch) {
        return true;
      }
      if (value != undefined)
        return value.toLowerCase().includes(this.tourNameSearch.toLowerCase());
    },

    dateFilter(value) {
      if (!this.dateSearch) {
        return true;
      }
      if (value != undefined)
        return value.toLowerCase().includes(this.dateSearch.toLowerCase());
    },

    closeCreateTeamDialog() {
      this.createTeamDialog = !this.closeCreateTeamDialog;
    },
  },
};
</script>

<style lang="css" scoped>
.remove-padding > div {
  padding: 0;
}
.row-pointer >>> tbody tr :hover {
  cursor: pointer;
}
.v-data-table-header__icon {
  opacity: 1;
}
tbody tr:nth-child(odd) {
  background: #dee2e6;
}
</style>
