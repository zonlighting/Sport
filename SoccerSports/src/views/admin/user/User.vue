<template>
  <div>
    <v-breadcrumbs :items="linkUser" large>
      <template v-slot:divider>
        <v-icon>mdi-chevron-right</v-icon>
      </template>
    </v-breadcrumbs>
    <v-row>
      <v-col cols="12" sm="2"> </v-col>
      <v-col cols="12" sm="8">
        <v-card>
          <v-card-title>
            User
            <v-spacer></v-spacer>
            <v-row>
              <v-col>
                <v-text-field
                  v-model="searchName"
                  append-icon="mdi-magnify"
                  label="Search Email"
                  single-line
                ></v-text-field>
              </v-col>
              <v-col>
                <v-select
                  label="Choose Role"
                  v-model="searchRole"
                  :items="role"
                  item-value="value"
                  item-text="text"
                >
                </v-select>
              </v-col>
            </v-row>
          </v-card-title>
          <v-data-table :headers="headers" :items="user" >
            <template v-slot:[`item.role`]="{ item }">
              {{
                item.role == "ROLE_ADMIN"
                  ? "ADMIN"
                  : item.role == "ROLE_MEMBER"
                  ? "MEMBER"
                  : "USER"
              }}
            </template>
            <template v-slot:[`item.profile`]="{ item }" >
              <div v-if="item.profile.idTeam!=0">
                <router-link
                  :to="{
                    path: '/admin/member/' + item.profile.id,
                  }"
                  style="text-decoration: none"
                >
                  <v-icon small class="mr-2"> mdi-arrow-right-bold </v-icon>
                </router-link>
              </div>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
      <v-col> </v-col>
    </v-row>
  </div>
</template>
<script>
export default {
  data() {
    return {
       linkUser: [
        {
          text: "Dashboard",
          disabled: false,
          href: "/admin/dashboard",
        },
        {
          text: "User",
          disabled: true,
        },
      ],
      searchName: "",
      searchEmail: "",
      searchRole: "",
      headers: [
        { text: "No", value: "id" },
        { text: "Email", value: "email", filter: this.emailFilter },
        { text: "Role", value: "role", filter: this.roleFilter },
        { text: "Action", value: "profile", sortable: false },
      ],
      user: [],
      role: [
        { value: "ALL", text: "ALL" },
        { value: "ROLE_ADMIN", text: "ADMIN" },
        { value: "ROLE_MEMBER", text: "MEMBER" },
        { value: "ROLE_USER", text: "USER" },
      ],
    };
  },
  created() {
    this.getData();
  },
  methods: {
    emailFilter(value) {
      if (!this.searchName) {
        return true;
      }
      return value.toLowerCase().includes(this.searchName.toLowerCase());
    },
    roleFilter(value) {
      if (!this.searchRole) {
        return true;
      }
      if (this.searchRole == "ALL") {
        return true;
      }
      return value == this.searchRole;
    },
    getData() {
      this.$store.dispatch("user/getAll").then((response) => {
        this.user = response.data.payload;
      });
    },
  },
};
</script>