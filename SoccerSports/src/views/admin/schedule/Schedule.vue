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
            <v-toolbar-title>Schedule</v-toolbar-title>
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
          <v-toolbar flat>
            <v-row>
              <v-col>
                <v-text-field
                  v-model="searchName"
                  append-icon="mdi-magnify"
                  label="Search Tournament"
                  single-line
                  hide-details
                ></v-text-field>
              </v-col>
              <v-col>
                <v-select
                  :items="itemSelect"
                  v-model="selectStatus"
                  item-text="text"
                  item-value="status"
                  label="Select Status"
                ></v-select>
              </v-col>
              <v-col>
                <v-text-field
                  v-model="searchTeam1"
                  append-icon="mdi-magnify"
                  label="Home Team"
                  single-line
                  hide-details
                ></v-text-field>
              </v-col>
              <v-col>
                <v-text-field
                  v-model="searchTeam2"
                  append-icon="mdi-magnify"
                  label="Away Team"
                  single-line
                  hide-details
                ></v-text-field>
              </v-col>
              <v-col>
                <v-menu
                  ref="menuStart"
                  v-model="menuStart"
                  :close-on-content-click="false"
                  :return-value.sync="dateStart"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="dateStart"
                      label="Picker in time Start"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="dateStart" no-title scrollable>
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="dateStart = null">
                      Reset
                    </v-btn>
                    <v-btn
                      text
                      color="primary"
                      @click="$refs.menuStart.save(dateStart)"
                    >
                      OK
                    </v-btn>
                  </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
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
        <template v-slot:[`item.timeStart`]="{ item }">
          <div>
            {{ new Date(Date.parse(item.timeStart)) }}
          </div>
        </template>
        <template v-slot:[`item.team[0].nameTeam`]="{ item }">
          <div>
            <v-avatar tile>
              <img :src="'item.team[0].logo'" alt="Logo" />
            </v-avatar>
            {{ item.team[0].nameTeam }}
          </div>
        </template>
        <template v-slot:[`item.team[1].nameTeam`]="{ item }">
          <div>
            <v-avatar tile>
              <img :src="item.team[1].logo" alt="Logo" />
            </v-avatar>
            {{ item.team[1].nameTeam }}
          </div>
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <router-link
            :to="{
              path: `schedule/` + item.idSchedule
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
    <v-dialog v-model="dialogCreate" width="700px">
      <v-card>
        <ScheduleCreate :getData="getData" :hideDialog="hideDialog" />
      </v-card>
    </v-dialog>
     <v-dialog v-model="dialogDelete" max-width="600">
      <v-card>
        <v-card-title class="headline">
          Delete Schedule
        </v-card-title>

        <v-card-text> You definitely want to delete? </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="green darken-1" text @click="dialogDelete = false">
            Cancel
          </v-btn>

          <v-btn color="green darken-1" text @click="deleteItemConfirm">
            Delete
          </v-btn>
        </v-card-actions>
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
      searchName: "",
      searchTeam1: "",
      searchTeam2: "",
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
          value: "tournament.nameTournament",
          filter: this.nameFilter,
        },
        {
          text: "Time Start",
          value: "timeStart",
          filter: this.startFilter,
          width: "400px",
        },
        {
          text: "Location",
          value: "location",
        },
        {
          text: "Home Team",
          value: "team[0].nameTeam",
          sortable: false,
          align: "center",
          filter: this.teamFilter1,
        },
        {
          text: "Away team",
          value: "team[1].nameTeam",
          sortable: false,
          align: "center",
          filter: this.teamFilter2,
        },
        {
          text: "Status",
          value: "status",
          filter: this.statusFilter,
        },
        { text: "Action", value: "actions", sortable: false },
      ],
      dialogDelete: false,
      editedIndex: "",
      idDelete: "",

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
    nameFilter(value) {
      if (!this.searchName) {
        return true;
      }
      return value.toLowerCase().includes(this.searchName.toLowerCase());
    },
    statusFilter(value) {
      if (!this.selectStatus) {
        return true;
      }
      if (this.selectStatus == 3) {
        return true;
      } else {
        return value == this.selectStatus;
      }
    },
    teamFilter1(value) {
      if (!this.searchTeam1) {
        return true;
      }
      return value.toLowerCase().includes(this.searchTeam1.toLowerCase());
    },
    teamFilter2(value) {
      if (!this.searchTeam2) {
        return true;
      }
      return value.toLowerCase().includes(this.searchTeam2.toLowerCase());
    },
    deleteItem(item) {
      this.idDelete = item.idSchedule;
      this.editedIndex = this.schedule.indexOf(item);
      this.dialogDelete = true;
    },
     deleteItemConfirm() {
      this.dialogDelete = false;
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("schedule/deleteSchedule", this.idDelete)
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            this.schedule.splice(this.editedIndex, 1);
            alert(response.data.message);
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },
    startFilter(value) {
      if (this.dateStart == null) {
        return true;
      }
      return (
        new Date(Date.parse(value)).toISOString().substr(0, 10) >=
        this.dateStart
      );
    },
  },
};
</script>