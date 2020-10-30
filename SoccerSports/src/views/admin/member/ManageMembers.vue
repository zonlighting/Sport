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
        <v-btn color="primary" dark class="ma-2"> Back To Team </v-btn>
      </v-row>
      <v-card max-width="95%" class="my-8 container">
        <v-img>
          <h2 class="pl-3 pt-2">Total : {{ members.length }} members</h2>
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
                    <v-img :src="data.item.avatar"></v-img>
                  </v-avatar>
                  {{ data.item.name }}
                </v-chip>
              </template>
            </v-autocomplete>
          </v-col>
        </v-img>

        <v-row>
          <v-col cols="12" md="6" xl="6" xm="12">
            <h2 style="text-align: center">Members Available</h2>
            <v-divider class="my-4"></v-divider>
            <v-btn color="primary" dark class="mb-5" @click="isOpenModalMember">
              Create Member
            </v-btn>
            <v-dialog v-model="dialogCreateMember" max-width="68%">
              <CreateMember
                :loadMemberAfterCreate="loadMemberAfterCreate"
                :isOpenModalMember="isOpenModalMember"
              />
            </v-dialog>
            <MembersAvailable />
          </v-col>
          <v-col cols="12" md="6" xl="6" xm="12">
            <h2 style="text-align: center">Members In Team</h2>
            <v-divider class="my-4"></v-divider>
            <v-btn color="primary" dark class="mb-5" @click="isOpenModalMember">
              Confirm List
            </v-btn>
            <MembersInTeam />
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
export default {
  components: { MembersAvailable, MembersInTeam, CreateMember },
  data() {
    const srcs = {
      1: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      2: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
      3: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
      4: "https://cdn.vuetifyjs.com/images/lists/4.jpg",
      5: "https://cdn.vuetifyjs.com/images/lists/5.jpg",
    };

    return {
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
          text: "Name of the team",
          disabled: false,
          href: `/admin/team/detail/${this.$route.params.idTeam}`,
        },
        {
          text: "Manager",
          disabled: true,
        },
      ],
      members: [
        "Sandra Williams 8",
        "Sandra Adams",
        "Britta Holt",
        "Ali Connors",
        "Trevor Hansen",
        "Tucker Smith",
        "Sandra Williams",
        "Ali Connors2",
        "Britta Holt5",
        "John Smith7",
        "Britta Holt52",
        "Sandra Williams 48",
        "John Smith72",
      ],
      players: [
        { name: "Sandra Adams", avatar: srcs[1] },
        { name: "Ali Connors", avatar: srcs[2] },
        { name: "Trevor Hansen", avatar: srcs[3] },
        { name: "Tucker Smith", avatar: srcs[2] },
        { name: "Britta Holt", avatar: srcs[4] },
        { name: "Jane Smith ", avatar: srcs[5] },
        { name: "John Smith", avatar: srcs[1] },
        { name: "Sandra Williams", avatar: srcs[3] },
        { name: "Sandra Adams1", avatar: srcs[1] },
        { name: "Ali Connors2", avatar: srcs[2] },
        { name: "Trevor Hansen3", avatar: srcs[3] },
        { name: "Tucker Smith4", avatar: srcs[2] },
        { name: "Britta Holt5", avatar: srcs[4] },
        { name: "Jane Smith 6", avatar: srcs[5] },
        { name: "John Smith7", avatar: srcs[1] },
        { name: "Sandra Williams 8", avatar: srcs[3] },
        { name: "Tucker Smith9", avatar: srcs[2] },
        { name: "Britta Holt52", avatar: srcs[4] },
        { name: "Jane Smith 624", avatar: srcs[5] },
        { name: "John Smith72", avatar: srcs[1] },
        { name: "Sandra Williams 48", avatar: srcs[3] },
      ],
    };
  },

  methods: {
    remove(item) {
      const index = this.friends.indexOf(item.name);
      if (index >= 0) this.friends.splice(index, 1);
    },

    loadMemberAfterCreate(member) {
      console.log(member);
      //this.desserts.unshift(member); // unshift is add into 1st positions , push is add last positions
    },

    isOpenModalMember() {
      this.dialogCreateMember = !this.dialogCreateMember;
    },
  },
};
</script>