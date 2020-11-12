<template>
  <div>
    <v-breadcrumbs :items="linkTournament" large>
      <template v-slot:divider>
        <v-icon>mdi-chevron-right</v-icon>
      </template>
    </v-breadcrumbs>
    <template>
      <v-data-table :headers="headers" :items="tournament">
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
              >New Tournament</v-btn
            >
          </v-toolbar>
          <v-toolbar flat>
            <v-row>
              <v-col>
                <v-text-field
                  v-model="searchName"
                  append-icon="mdi-magnify"
                  label="Search Name"
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
              <v-col>
                <v-menu
                  ref="menuEnd"
                  v-model="menuEnd"
                  :close-on-content-click="false"
                  :return-value.sync="dateEnd"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="dateEnd"
                      label="Picker in time end"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="dateEnd" no-title scrollable>
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="dateEnd = null">
                      Reset
                    </v-btn>
                    <v-btn
                      text
                      color="primary"
                      @click="$refs.menuEnd.save(dateEnd)"
                    >
                      OK
                    </v-btn>
                  </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
          </v-toolbar>
        </template>
        <template v-slot:[`item.banner`]="{ item }">
          <v-img
            max-height="100"
            max-width="100"
            :src="
              !!item.banner
                ? baseUrl + item.banner
                : 'https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/No_image_3x4.svg/1200px-No_image_3x4.svg.png'
            "
          ></v-img>
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
    <v-dialog v-model="dialogDelete" max-width="600">
      <v-card>
        <v-card-title class="headline">
          Delete Tournament: {{ nameDelete }}
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
    <v-dialog v-model="dialogCreate" fullscreen persistent>
      <TournamentCreate :hideDialog="hideDialog" :getData="getData" />
    </v-dialog>
  </div>
</template>

<script>
import { ENV } from "@/config/env.js";
import TournamentCreate from "./TournamentCreate";
export default {
  components: {
    TournamentCreate,
  },
  data() {
    return {
      menuEnd: false,
      dateEnd: null,
      dateStart: null,
      menuStart: false,
      searchName: "",
      dialogCreate: false,
      linkTournament: [
        {
          text: "Dashboard",
          disabled: false,
          href: "/admin/dashboard",
        },
        {
          text: "Tournament",
          disabled: true,
        },
      ],
      dialogDelete: false,
      idDelete: "",
      tournament: [],
      nameDelete: "",
      editedIndex: "",
      headers: [
        {
          text: "Name",
          value: "nameTournament",
          width: "300px",
          filter: this.nameFilter,
        },
        { text: "Banner", value: "banner", width: "200px" },
        {
          text: "Time Start",
          value: "timeStart",
          width: "150px",
          filter: this.startFilter,
        },
        {
          text: "Time End",
          value: "timeEnd",
          width: "150px",
          filter: this.endFilter,
        },
        {
          text: "Description",
          value: "description",
          sortable: false,
          width: "500px",
          align: "center",
        },
        { text: "Total Team", value: "team.length", width: "120px" },
        {
          text: "Status",
          value: "status",
          width: "150px",
          filter: this.statusFilter,
        },
        { text: "Action", value: "actions", sortable: false },
      ],
      selectStatus: "3",
      itemSelect: [
        { text: "No chosse", status: "3" },
        { text: "UpComming", status: "0" },
        { text: "OnGame", status: "1" },
        { text: "Finished", status: "2" },
      ],
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
    getData() {
      this.$store.commit("auth/auth_overlay_true");
      this.$store.dispatch("tournament/getAll").then((response) => {
        this.$store.commit("auth/auth_overlay_false");
        if (response.data.code == 0) {
          console.log(response.data.payload);
          this.tournament = response.data.payload;
        }
      });
    },
    deleteItem(item) {
      this.idDelete = item.idTournament;
      this.nameDelete = item.nameTournament;
      this.editedIndex = this.tournament.indexOf(item);
      this.dialogDelete = true;
    },
    hideDialog() {
      this.dialogCreate = !this.dialogCreate;
    },
    deleteItemConfirm() {
      this.dialogDelete = false;
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("tournament/deleteTournament", this.idDelete)
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            this.tournament.splice(this.editedIndex, 1);
            alert(response.data.message);
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
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
    endFilter(value) {
      if (this.dateEnd == null) {
        return true;
      } else {
        return value < this.dateEnd;
      }
    },
    startFilter(value) {
      if (this.dateStart == null) {
        return true;
      } else {
        return value > this.dateStart;
      }
    },
  },
};
</script>