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
        <v-row v-if="isEditTeam">
          <v-col cols="12" md="2" sm="2">
            <v-img max-width="200" :src="baseUrl + team.logo"></v-img>
          </v-col>
          <v-col cols="12" md="2" sm="2">
            <h3 class="name-team-text">{{ team.nameTeam }}</h3>
            <h5 class="country-text">Country: {{ team.country }}</h5>
            <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
              <div style="color: red" v-if="team.tourName != null">
                {{ team.tourName }}
              </div>
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
                team.totalmatch > 0
                  ? (team.totalwin / team.totalmatch) * 100
                  : 0
              }}
            </h5>
            <h5 class="country-text">Total Win : {{ team.totalwin }}</h5>
            <h5 style="line-height: 1.7; font-size: 1.2rem; font-weight: 300">
              Total Match: {{ team.totalmatch }}
            </h5>
            <div class="pb-15"></div>
            <v-btn color="primary" class="ml-15" dark @click="editTeam">
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

        <v-row v-else>
          <EditTeam
            :getTeamById="getTeamById"
            :editTeam="editTeam"
            :team="team"
          />
        </v-row>

        <v-divider class="my-8"></v-divider>

        <h2 class="my-4 ml-6">Members</h2>
        <template>
          <v-toolbar flat color="white">
            <v-btn color="primary" class="ma-2" dark @click="toManageMembers">
              Manage Members
            </v-btn>
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-row class="mt-4">
              <v-col cols="12" sm="6" md="2">
                <v-text-field
                  v-model="ageSearch"
                  label="Age"
                  single-line
                  hide-details
                ></v-text-field>
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
                <v-row justify="space-around">
                  <v-card width="400">
                    <v-img height="200px" src="">
                      <v-app-bar flat color="rgba(0, 0, 0, 0)">
                        <v-row>
                          <v-col cols="1" md="5" xm="5"></v-col>
                          <v-col cols="10" md="4" xm="4" class="pt-12">
                            <v-avatar color="red">
                              <span class="white--text headline">{{
                                player.name.match(/\b(\w)/g).join("")
                              }}</span>
                            </v-avatar>
                          </v-col>
                          <v-col cols="1" md="1" xm="1"></v-col>
                          <v-col cols="1" md="1" xm="1" class="pt-8">
                            <v-menu bottom left>
                              <template v-slot:activator="{ on, attrs }">
                                <v-btn
                                  color="black"
                                  dark
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
                                    class="row-pointer"
                                    @click="isModalEditMember(player.id)"
                                  >
                                    Edit
                                  </v-list-item-title>
                                </v-list-item>
                              </v-list>
                            </v-menu>
                          </v-col>
                        </v-row>
                      </v-app-bar>

                      <v-card-title class="black--text pb-0">
                        <p class="mx-auto my-auto pt-2 pl-2">
                          {{ player.name }}
                        </p>
                      </v-card-title>
                      <v-card-title class="black--text">
                        <p class="mx-auto pl-2">{{ player.position }}</p>
                      </v-card-title>
                    </v-img>
                  </v-card>
                </v-row>
              </v-container>
            </v-col>
          </v-row>
        </template>
        <template v-else>
          <h3 class="pl-5">Team Don't Have Any Members</h3>
        </template>

        <!-- <v-dialog v-model="modalEditMember" max-width="35%">
          <EditMember
            :teamId="team.idTeam"
            :getTeamById="getTeamById"
            :memberEdit="memberEdit"
            :isModalEditMember="isModalEditMember"
          />
        </v-dialog> -->
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
          text: "Name of the team",
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
      this.$store
        .dispatch("team/getTeamById", id)
        .then((response) => {
          self.team = response.data.payload;
          self.membersSearch = self.team.profile;
        })
        .catch(function (error) {
          alert(error);
        });
    },

    isModalEditMember(id) {
      this.$router.push({ path: `/admin/member/${id}` });
    },

    editTeam() {
      this.isEditTeam = !this.isEditTeam;
    },

    searchButton() {
      let newData = this.team.profile.filter((v) => {
        let isSearch = true;
        if (this.namePlayerSearch != "") {
          console.log("1");
          isSearch = v.name.toLowerCase().includes(this.namePlayerSearch)
            ? false
            : true;
        }
        if (this.ageSearch != "") {
          console.log("2");
          isSearch = v.age.toLowerCase().includes(this.ageSearch)
            ? false
            : true;
        }
        if (this.countrySearch != "") {
          console.log("3");
          isSearch = v.country.toLowerCase().includes(this.countrySearch)
            ? false
            : true;
        }
        if (this.positionSearch != "Default" && this.positionSearch != "") {
          isSearch = this.positionSearch != v.position ? false : true;
        }
        console.log(isSearch);
        return isSearch;
      });
      this.membersSearch = newData;
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

.row-pointer >>> tbody tr :hover {
  cursor: pointer;
}
</style>