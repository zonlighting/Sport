<template>
  <div>
    <v-breadcrumbs style="color: #06b4c2" :items="teamLink" large>
      <template v-slot:divider>
        <v-icon>mdi-chevron-right</v-icon>
      </template>
    </v-breadcrumbs>

    <template>
      <h5 class="titleText pl-10">TEAM DETAIL</h5>
      <v-card max-width="95%" class="my-8 container">
        <v-row>
          <v-col cols="12" md="2" sm="2">
            <v-img
              max-width="230"
              src="https://picsum.photos/id/11/500/300"
            ></v-img>
          </v-col>
          <v-col cols="12" md="2" sm="2">
            <h3 class="name-team-text">Name Team</h3>
            <h5 class="country-text">Country</h5>
            <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
              Total Win :
            </h5>
            <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
              Total Match:
            </h5>
          </v-col>

          <v-col cols="12" md="2" sm="2" class="pt-15">
            <h5 class="country-text">Win Rate:</h5>
            <h5 class="country-text">Current Members:</h5>
            <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
              Establish date:
            </h5>
          </v-col>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-col cols="12" md="5" sm="5">
            <h2>Description</h2>
            <p>
              Angel Correa stats and transfer history on Scoreboard.com. This
              page is about Angel Correa, (Atl. Madrid/Argentina). If you're
              searching for player profile of another player with the name Angel
              Correa, please use the "Search" icon in the sports menu. Follow
              Angel Correa stats (appearances, goals, cards) and livescore,
              results and statistics from 30 sports and more than 5000
              competitions on Scoreboard.com!
            </p>
          </v-col>
        </v-row>

        <v-divider class="my-8"></v-divider>

        <h2 class="my-4">Members</h2>
        <template>
          <v-toolbar flat color="white">
            <v-btn color="primary" class="ma-2" dark @click="toManageMembers">
              Manage Members
            </v-btn>
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-row class="mt-4">
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  append-icon="mdi-magnify"
                  label="Name search"
                  single-line
                  hide-details
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  append-icon="mdi-magnify"
                  label="Age"
                  single-line
                  hide-details
                ></v-text-field>
              </v-col>
            </v-row>
          </v-toolbar>
        </template>
        <template v-slot:[`item.avatar`]="{ item }">
          <b-img
            :src="baseUrl + item.avatar"
            alt=""
            class="fixImg1"
            style="margin: 5px 0 5px 0"
          />
        </template>

        <template v-slot:[`item.idTeam`]="{ item }">
          <v-btn v-if="team.idTour == 0" @click="removeMember(item)" small
            >Remove</v-btn
          >
        </template>
      </v-card>
    </template>
  </div>
</template>
<script>
export default {
  data() {
    return {
      teamLink: [
        {
          text: "Dashboard",
          disabled: false,
          href: "/admin",
        },
        {
          text: "Teams",
          disabled: false,
          href: "/admin/teams",
        },
        {
          text: "Name of the team",
          disabled: false,
          href: `/admin/team/detail/${this.$route.params.idTeam}`,
        },
      ],

      headers: [
        {
          text: "Avatar",
          align: "start",
          sortable: false,
          value: "avatar",
        },
        { text: "Name", value: "name", filter: this.nameMemberFilter },
        { text: "Email", value: "email" },
        { text: "Phone", value: "phone" },
        { text: "Age", value: "age", filter: this.ageFilter },
        { text: "Gender", value: "gender" },
        {
          text: "Actions",
          value: "idTeam",
          filter: (value, search, item) => {
            if (item.idTeam != 0) return true;
            return value;
          },
        },
      ],
      desserts: [],
      search: "",
      team: {},
    };
  },

  created() {
    //  console.log(this.$route)
  },

  methods: {
    toManageMembers() {
      this.$router.push({
        path: `/admin/team/${this.$route.params.id}/manage`,
        params: { idTeam: this.$route.params.id },
      });
    },
  },
};
</script>
<style>
.titleText {
  font-weight: 700;
  color: #01c0c8;
  font-size: 22px;
  margin-top: 10px;
}

.name-team-text {
  color: #333;
  font-size: 2rem;
  font-family: inherit;
  font-weight: 400;
  line-height: 1.5;
}

.country-text {
  color: #333;
  font-size: 1.2rem;
  font-family: inherit;
  font-weight: 350;
  line-height: 1.7;
}
</style>