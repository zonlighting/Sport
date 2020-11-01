<template>
  <div>
    <v-breadcrumbs :items="linkTournament" large>
      <template v-slot:divider>
        <v-icon>mdi-chevron-right</v-icon>
      </template>
    </v-breadcrumbs>
    <template>
      <v-data-table :headers="headers" :items="schedule">
        <template v-slot:top>
          <v-toolbar flat>
            <v-toolbar-title>Tournament</v-toolbar-title>
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              dark
              class="mb-2"
              @click="dialogCreate = !dialogCreate"
              >New Schedule</v-btn
            >
          </v-toolbar>
        </template>
        <template v-slot:[`item.status`]="{ item }">
          <div
            :style="
              item.status == 0
                ? 'color:green'
                : item.status == 1
                ? 'color:blue'
                : 'color:red'
            "
          >
            {{
              item.status == 0
                ? "Up Comming"
                : item.status == 1
                ? "On Game"
                : "Finished"
            }}
          </div>
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <router-link
            :to="{
              path: `tournament/` + item.idTournament,
              params: { userId: 123 },
            }"
            style="text-decoration: none"
          >
            <v-icon small class="mr-2"> mdi-arrow-right-bold </v-icon>
          </router-link>

          <v-icon small @click="deleteItem(item)" v-if="item.status == 0">
            mdi-delete
          </v-icon>
        </template>
      </v-data-table>
    </template>
    <v-dialog v-model="dialogCreate" width="700px" >
      <v-card>
      <ScheduleCreate />
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";
import ScheduleCreate from "./ScheduleCreate";

export default {
  components: {
    ScheduleCreate,
  },
  data() {
    return {
      dateStart: null,
      menuStart: false,
      dialogCreate: false,
      linkTournament: [
        {
          text: "Dashboard",
          disabled: false,
          href: "/admin/dashboard",
        },
        {
          text: "Schedule",
          disabled: true,
        },
      ],
      headers: [
        {
          text: "Name",
          value: "nameTournament",
          filter: this.nameFilter,
        },
        {
          text: "Time Start",
          value: "timeStart",
          filter: this.startFilter,
        },
        {
          text: "The Team 1",
          value: "team1",
          sortable: false,
          align: "center",
        },
        {
          text: "The Team 2",
          value: "team2",
          sortable: false,
          align: "center",
        },
        {
          text: "Status",
          value: "status",
          filter: this.statusFilter,
        },
        { text: "Action", value: "actions", sortable: false },
      ],
      itemSelect: [
        { text: "No chosse", status: "3" },
        { text: "UpComming", status: "0" },
        { text: "OnGame", status: "1" },
        { text: "Finished", status: "2" },
      ],
      selectStatus: "3",
      schedule: [],
    };
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  mounted() {
    this.getData();
  },
  methods: {
    hideDialog() {
      this.dialogCreate = !this.dialogCreate;
    },
    getData() {
      this.$store.commit("auth/auth_overlay");
      this.$store.dispatch("schedule/getAll").then((response) => {
        this.$store.commit("auth/auth_overlay");
        if (response.data.code == 0) {
          this.schedule = response.data.payload;
        }
      });
    },
  },
};
</script>