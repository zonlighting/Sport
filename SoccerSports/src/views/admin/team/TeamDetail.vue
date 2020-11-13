<template>
  <div>
    <v-breadcrumbs style="color: #06b4c2" :items="teamLink" large>
      <template v-slot:divider>
        <v-icon>mdi-chevron-right</v-icon>
      </template>
    </v-breadcrumbs>

    <template>
      <v-row>
        <h5 class="titleText pl-15">TEAM DETAIL</h5>
        <v-spacer></v-spacer>
        <v-btn
          color="primary"
          dark
          class="mr-14"
          @click="$router.push({ path: `/admin/teams` })"
        >
          Back To Teams
        </v-btn>
      </v-row>
      <v-card max-width="95%" class="my-8 container">
        <v-row v-if="isEditTeam">
          <v-col cols="12" md="2" sm="2">
            <v-img max-width="200" :src="baseUrl + team.logo"></v-img>
          </v-col>
          <v-col cols="12" md="2" sm="2">
            <h3 class="name-team-text">{{ team.nameTeam }}</h3>
            <h5 class="country-text">Country: {{ team.country }}</h5>
            <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
              <router-link
                :to="{
                  path: `/admin/tournament/` + team.idTour,
                }"
                v-if="team.tourName != null"
                style="text-decoration: none"
              >
                {{ team.tourName }}</router-link
              >

              <div style="color: green" v-else>Not in tournament</div>
            </h5>
            <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
              Current Members:
              {{
                team.profile && team.profile.length ? team.profile.length : 0
              }}
            </h5>
          </v-col>
          <v-col cols="12" md="2" sm="2" class="pt-15 pl-10">
            <h5 class="country-text">
              Win Rate:
              {{
                team.rate == 0 || team.rate == undefined
                  ? 0
                  : team.rate.toFixed(2)
              }}
              %
            </h5>
            <h5 class="country-text">Total Win : {{ team.totalwin }}</h5>
            <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
              Total Match: {{ team.totalmatch }}
            </h5>
            <div class="pb-15"></div>
            <v-btn
              color="primary"
              class="ml-15"
              v-if="team.idTour == 0"
              dark
              @click="editTeam"
            >
              Edit Team
            </v-btn>
          </v-col>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-col cols="12" md="5" sm="5">
            <h2>Description</h2>
            <p>
              {{ team.description }}
            </p>
          </v-col>
        </v-row>

        <EditTeam
          v-else
          :getTeamById="getTeamById"
          :editTeam="editTeam"
          :team="team"
        />

        <v-divider class="my-8"></v-divider>

        <h2 class="titleText my-4 ml-6">Members</h2>
        <template>
          <v-toolbar flat color="white">
            <v-btn
              color="primary"
              class="ma-2"
              v-if="team.idTour == 0"
              dark
              @click="toManageMembers"
            >
              Manage Members
            </v-btn>
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-row class="mt-4">
              <v-col cols="12" sm="6" md="2">
                <v-select
                  v-model="ageSearch"
                  :items="ages"
                  label="Age"
                ></v-select>
              </v-col>
              <v-col cols="12" sm="6" md="2">
                <v-text-field
                  v-model="countrySearch"
                  label="Country"
                  single-line
                  hide-details
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="3">
                <v-text-field
                  v-model="namePlayerSearch"
                  label="Name search"
                  single-line
                  hide-details
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="3">
                <v-select
                  v-model="positionSearch"
                  :items="items"
                  label="Position"
                ></v-select>
              </v-col>
              <v-col cols="12" sm="3" md="1">
                <v-btn color="primary" dark @click="searchButton">
                  Search
                </v-btn>
              </v-col>
              <v-col cols="12" sm="3" md="1">
                <v-btn color="error" dark @click="reset"> Reset </v-btn>
              </v-col>
            </v-row>
          </v-toolbar>
        </template>
        <v-divider class="my-8 mx-6"></v-divider>
        <template class="container" v-if="team.profile && team.profile.length">
          <v-row class="px-6">
            <v-col
              cols="12"
              md="3"
              sm="3"
              v-for="player in membersSearch"
              :key="player.id"
            >
              <v-container>
                <v-card class="mx-auto" max-width="434" tile>
                  <v-img height="100%">
                    <v-row align="end" class="fill-height">
                      <v-col align-self="start" class="pa-0" cols="12">
                        <v-row>
                          <v-avatar
                            class="profile"
                            color="grey"
                            size="164"
                            tile
                          >
                            <v-img :src="baseUrl + player.avatar"></v-img>
                          </v-avatar>
                          <v-spacer></v-spacer>
                          <v-menu bottom left>
                            <template v-slot:activator="{ on, attrs }">
                              <v-btn
                                class="mr-10 mt-5"
                                color="black"
                                icon
                                v-bind="attrs"
                                v-on="on"
                              >
                                <v-icon>mdi-dots-vertical</v-icon>
                              </v-btn>
                            </template>

                            <v-list>
                              <v-list-item>
                                <v-list-item-title
                                  @click="editPlayer(player.id)"
                                  style="cursor: pointer"
                                  >Edit</v-list-item-title
                                >
                              </v-list-item>
                            </v-list>
                          </v-menu>
                        </v-row>
                      </v-col>
                      <v-col class="py-0">
                        <v-list-item color="rgba(0, 0, 0, .4)" dark>
                          <v-list-item-content>
                            <v-list-item-title
                              class="title"
                              style="color: black"
                            >
                              {{ player.name }}
                            </v-list-item-title>
                            <v-list-item-subtitle style="color: black"
                              >Country:
                              {{ player.country }}</v-list-item-subtitle
                            >
                          </v-list-item-content>
                        </v-list-item>
                      </v-col>
                      <v-col class="py-0">
                        <v-list-item color="rgba(0, 0, 0, .4)" dark>
                          <v-list-item-content>
                            <v-list-item-title
                              class="title"
                              style="color: black"
                            >
                              {{ player.position }}
                            </v-list-item-title>
                            <v-list-item-subtitle style="color: black"
                              >Age: {{ player.age }}
                            </v-list-item-subtitle>
                          </v-list-item-content>
                        </v-list-item>
                      </v-col>
                    </v-row>
                  </v-img>
                </v-card>
              </v-container>
            </v-col>
          </v-row>
        </template>
        <template v-else>
          <h3 class="pl-5">Team Don't Have Any Members</h3>
        </template>
      </v-card>
    </template>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";
import EditTeam from "@/views/admin/team/EditTeam";

export default {
  components: {
    EditTeam,
  },

  data() {
    return {
      isEditTeam: true,
      items: [
        "Default",
        "Goalkeepers",
        "Defenders",
        "Midfielders",
        "Forwards",
        "Coach",
      ],
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
          text: "",
          disabled: false,
          href: `/admin/team/detail/${this.$route.params.id}`,
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
      memberEdit: {},
      namePlayerSearch: "",
      ageSearch: "",
      positionSearch: "",
      countrySearch: "",
      membersSearch: [],
      ages: Array.from(Array(46).keys()).map((v) => v + 10),
    };
  },

  mounted() {
    this.getTeamById(this.$route.params.id);
  },

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  methods: {
    toManageMembers() {
      this.$router.push({
        path: `/admin/team/${this.$route.params.id}/manage`,
        params: { idTeam: this.$route.params.id },
      });
    },

    getTeamById(id) {
      let self = this;
      this.$store.commit("auth/auth_overlay_true");
      this.$store
        .dispatch("team/getTeamById", id)
        .then((response) => {
          this.$store.commit("auth/auth_overlay_false");
          self.team = response.data.payload;
          self.membersSearch = self.team.profile;
          self.teamLink[2].text = self.team.nameTeam;
        })
        .catch(function (error) {
          alert(error);
        });
    },

    isModalEditMember(id) {
      this.$router.push({
        path: `/admin/member/${id}`,
      });
    },

    editTeam() {
      this.isEditTeam = !this.isEditTeam;
    },

    editPlayer(idPlayer) {
      this.$router.push({
        path: `/admin/member/${idPlayer}`,
      });
    },

    searchButton() {
      let newData = this.team.profile
        .filter((v) => {
          let isSearch = true;
          if (this.namePlayerSearch != "") {
            isSearch = v.name
              .toLowerCase()
              .includes(this.namePlayerSearch.toLowerCase());
          }
          return isSearch;
        })
        .filter((v) => {
          let isSearch = true;
          if (this.ageSearch != "") {
            isSearch = v.age == this.ageSearch;
          }
          return isSearch;
        })
        .filter((v) => {
          let isSearch = true;
          if (this.countrySearch != "") {
            isSearch = v.country
              .toLowerCase()
              .includes(this.countrySearch.toLowerCase());
          }
          return isSearch;
        })
        .filter((v) => {
          let isSearch = true;
          if (this.positionSearch != "Default" && this.positionSearch != "") {
            isSearch = this.positionSearch == v.position;
          }
          return isSearch;
        });
      this.membersSearch = newData;
    },

    reset() {
      this.namePlayerSearch = "";
      this.ageSearch = "";
      this.countrySearch = "";
      this.positionSearch = "Default";
      this.searchButton();
    },
  },
};
</script>
<style>
.titleText {
  font-weight: 700;
  color: #01c0c8;
  font-size: 30px;
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

.row-pointer >>> tbody tr :hover {
  cursor: pointer;
}
</style>