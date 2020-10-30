<template>
  <div>
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
            <v-dialog v-model="createTeamDialog" max-width="1000px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn color="primary" dark v-bind="attrs" v-on="on">
                  New Team
                </v-btn>
              </template>
              <CreateTeam :closeCreateTeamDialog="closeCreateTeamDialog" />
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
          </v-row>
        </v-toolbar>
        <v-divider class="mt-4 mb-8"></v-divider>
      </template>

      <template v-slot:[`item.tournament.nameTour`]="{ item }">
        <template v-if="item.idTour != 0 || item.tournament != null">
          <div style="color: red">{{ item.tournament.nameTour }}</div>
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
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon class="mr-2" @click="editTeam(item)"> mdi-pencil </v-icon>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import { ENV } from "@/config/env.js";
import CreateTeam from "@/views/admin/team/CreateTeam";
0;

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
        { text: "Create Date", value: "createDate" },
        { text: "Team Name", value: "nameTeam", filter: this.nameTeamFilter },
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
        { text: "Team Detail", value: "actions", sortable: false },
      ],
      desserts: [],
      maxTeamId: 0,
      nameTeamSearch: "",
      typeSearch: null,
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

  computed: {
    // searchTrigger() {
    //   if (this.search.length >= 3) {
    //     return this.search;
    //   }
    // },

    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  methods: {
    editTeam(item) {
      this.$router.push({ path: `/admin/team/detail/${item.idTeam}` });
    },
    handleRowClick(item) {
      this.editTeam(item);
    },

    // customSort(items, index, isDesc) {
    //   items.sort((a, b) => {
    //     if (index === "date") {
    //       if (!isDesc) {
    //         return dateHelp.compare(a.date, b.date);
    //       } else {
    //         return dateHelp.compare(b.date, a.date);
    //       }
    //     } else {
    //       if (!isDesc) {
    //         return a[index] < b[index] ? -1 : 1;
    //       } else {
    //         return b[index] < a[index] ? -1 : 1;
    //       }
    //     }
    //   });
    //   return items;
    // },

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
    typeFilter(value) {
      if (!this.typeSearch) {
        return true;
      }

      return value === this.typeSearch;
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
