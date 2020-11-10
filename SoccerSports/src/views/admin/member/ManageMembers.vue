<template>
  <div>
    <v-breadcrumbs style="color: #06b4c2" :items="teamLink" large>
      <template v-slot:divider>
        <v-icon>mdi-chevron-right</v-icon>
      </template>
    </v-breadcrumbs>

    <template>
      <v-row class="mx-12">
        <h1 class="titleText">Team Management</h1>
        <v-spacer></v-spacer>
        <v-btn
          color="primary"
          dark
          class="ma-2"
          @click="
            $router.push({ path: `/admin/team/detail/${$route.params.id}` })
          "
        >
          Back To Team
        </v-btn>
      </v-row>
      <v-card max-width="95%" class="my-8 container">
        <v-img v-if="playersInTeam.length > 0">
          <h2 class="pl-3 pt-2">Total : {{ playersInTeam.length }} members</h2>
          <v-col cols="12">
            <v-autocomplete
              v-model="members"
              :items="players"
              solo
              chips
              item-text="name"
              item-value="name"
              disabled
              multiple
              append-icon=""
            >
              <template v-slot:selection="data">
                <v-chip>
                  <v-avatar left>
                    <v-img :src="baseUrl + data.item.avatar"></v-img>
                  </v-avatar>
                  {{ data.item.name }}
                </v-chip>
              </template>
            </v-autocomplete>
          </v-col>
        </v-img>
        <h2 v-else class="pl-3 pt-2" style="color: red">
          Team don't have any members
        </h2>
        <v-row>
          <v-col cols="12" md="6" xl="6" xm="12">
            <h2 style="text-align: center">Members Available</h2>
            <v-divider class="my-4"></v-divider>
            <v-btn color="primary" dark class="mb-5" @click="isOpenModalMember">
              Create Member
            </v-btn>
            <v-dialog persistent v-model="dialogCreateMember" max-width="68%">
              <CreateMember
                :loadMemberAfterCreate="loadMemberAfterCreate"
                :isOpenModalMember="isOpenModalMember"
              />
            </v-dialog>
            <MembersAvailable
              :isConfirm="isConfirm"
              :addedMember="addedMember"
              :playersAvailable="playersAvailable"
            />
          </v-col>
          <v-col cols="12" md="6" xl="6" xm="12">
            <h2 style="text-align: center">Members In Team</h2>
            <v-divider class="my-4"></v-divider>
            <v-btn color="primary" dark class="mb-5" @click="isConfirm(0)">
              Confirm List
            </v-btn>

            <v-dialog v-model="dialogConfirm" persistent max-width="500">
              <v-card class="container" v-if="!success">
                <v-card-title class="headline">
                  Confirm Apply This List Member?
                </v-card-title>
                <v-card-actions>
                  <v-btn
                    color="green darken-1"
                    text
                    @click="dialogConfirm = false"
                  >
                    Disagree
                  </v-btn>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="green darken-1"
                    text
                    @click="updateTeam($route.params.id, 0)"
                    v-if="manageConfirm"
                  >
                    Agree
                  </v-btn>
                  <v-btn
                    color="green darken-1"
                    text
                    @click="updateTeam($route.params.id, 1)"
                    v-else
                  >
                    Agree
                  </v-btn>
                </v-card-actions>
              </v-card>
              <template v-else>
                <v-alert class="mb-0" type="success">
                  Confirm List Success!
                </v-alert>
              </template>
            </v-dialog>
            <MembersInTeam
              :isConfirm="isConfirm"
              :removedMember="removedMember"
              :playersInTeam="playersInTeam"
            />
          </v-col>
        </v-row>
      </v-card>
    </template>
  </div>
</template>
<script>
import MembersAvailable from "@/views/admin/member/MembersAvailable";
import MembersInTeam from "@/views/admin/member/MembersInTeam";
import CreateMember from "@/views/admin/member/CreateMember.vue";
import { ENV } from "@/config/env.js";
export default {
  components: { MembersAvailable, MembersInTeam, CreateMember },
  data() {
    return {
      manageConfirm: true,
      success: false,
      dialogConfirm: false,
      dialogCreateMember: false,
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
          href: ``,
        },
        {
          text: "Manager",
          disabled: true,
        },
      ],
      members: [],
      players: [],
      playersAvailable: [],
      playersInTeam: [],
      teamDetail: {
        idTeam: parseInt(this.$route.params.id),
        profile: [],
      },
      idPlayer: "",
    };
  },

  mounted() {
    this.loadListMember();
    let routeLink = this.$route.params.id;
    if (routeLink == 0 || routeLink == "undefined" || routeLink == "") {
      alert("Can't Find Team");
      this.$router.push({
        path: `/admin/teams`,
      });
    } else {
      this.getTeam(this.$route.params.id);
    }
  },

  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },

  methods: {
    loadMemberAfterCreate(member) {
      this.playersAvailable.unshift(member); // unshift is add into 1st positions , push is add last positions
    },

    loadListMember() {
      let self = this;
      this.$store
        .dispatch("member/members")
        .then(function (response) {
          self.playersAvailable = response.data.payload.filter((item) => {
            return item.idTeam == 0;
          });
          self.playersInTeam = response.data.payload.filter((item) => {
            return item.idTeam == self.$route.params.id;
          });
          self.mapShowView(self.playersInTeam);
        })
        .catch(function (error) {
          alert(error);
        });
    },

    getTeam(id) {
      let self = this;
      this.$store
        .dispatch("team/getTeamById", id)
        .then((response) => {
          let res = response.data.payload;
          self.teamLink[2].text = res.nameTeam;
          self.teamLink[2].href = `/admin/team/detail/${res.idTeam}`;
        })
        .catch((e) => {
          alert(e);
        });
    },

    updateTeam(id, index) {
      let self = this;
      // console.log(this.teamDetail);
      this.$store
        .dispatch("team/updateMembersInTeam", this.teamDetail)
        .then(function () {
          self.success = !self.success;
          setTimeout(function () {
            self.dialogConfirm = !self.dialogConfirm;
            self.success = !self.success;
            self.loadListMember(id);
            console.log(index)
            if (index == 1) {
              self.$router.push({
                path: `/admin/member/${self.idPlayer}`,
              });
            }
          }, 1500);
        })
        .catch(function (error) {
          alert(error);
        });
    },

    addedMember(member, data) {
      member.idTeam = this.$route.params.id;
      this.playersAvailable = data;
      this.playersInTeam.push(member);
      this.mapShowView(this.playersInTeam);
    },

    removedMember(member, data) {
      member.idTeam = 0;
      this.playersInTeam = data;
      this.playersAvailable.push(member);
      this.mapShowView(this.playersInTeam);
    },

    mapShowView(data) {
      this.members = data.map((member) => member.name);
      this.players = data.map((member) => ({
        name: member.name,
        avatar: member.avatar,
      }));
      this.teamDetail.profile = this.playersInTeam;
    },

    isOpenModalMember() {
      this.dialogCreateMember = !this.dialogCreateMember;
    },

    isConfirm(id) {
      if (id != 0) {
        this.manageConfirm = false;
      }
      this.idPlayer = id;
      this.dialogConfirm = !this.dialogConfirm;
    },
  },
};
</script>