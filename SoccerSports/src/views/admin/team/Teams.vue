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
      :custom-sort="customSort"
    >
      <template v-slot:top>
        <v-toolbar flat color="white">
          <router-link :to="{ path: '/create', query: { maxId: 'maxTeamId' } }">
            <v-btn color="primary" dark class="mb-2"> New Team </v-btn>
          </router-link>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-row>
            <v-col cols="12" sm="3" md="3"> </v-col>
            <v-col cols="12" sm="3" md="3">
              <v-select
                v-model="typeSearch"
                :items="items"
                label="Search Type"
                class="pt-3"
              ></v-select>
            </v-col>
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
        <template v-if="item.idTour != 0 && item.tournament != null">
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

export default {
  data() {
    return {
    
      search: "",
      headers: [
        {
          text: "Logo",
          align: "start",
          sortable: false,
          value: "logo",
        },
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
      items: ["Football", "TableTennis", "Baseball", "Basketball"],
      nameTeamSearch: "",
      typeSearch: null,
      tourNameSearch: "",
      teamLink: [
        {
          text: "Dashboard",
          disabled: false,
          href: "/admin/home",
        },
        {
          text: "Teams",
          disabled: false,
          href: "/LayoutTeam",
        },
      ],
    };
  },

  mounted() {
    let self = this;
    this.$store.commit("auth/auth_overlay");
    this.$store
      .dispatch("team/getTeams")
      .then(function (response) {
        this.$store.commit("auth/auth_overlay");
        self.desserts = response.data;
        // console.log(self.desserts)
        self.maxTeamId =
          1 +
          Math.max.apply(
            Math,
            response.data.map(function (item) {
              return item.idTeam;
            })
          );
      })
      .catch(function (error) {
        console.log(error);
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
      // console.log(item);
      this.$router.push({ name: "TeamDetail", params: { id: item.idTeam } });
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
      // If this filter has no value we just skip the entire filter.
      if (!this.tourNameSearch) {
        return true;
      }
      if (value != undefined)
        return value.toLowerCase().includes(this.tourNameSearch.toLowerCase());
    },
    typeFilter(value) {
      // If this filter has no value we just skip the entire filter.
      if (!this.typeSearch) {
        return true;
      }

      return value === this.typeSearch;
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