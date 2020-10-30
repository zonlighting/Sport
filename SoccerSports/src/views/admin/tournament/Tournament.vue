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
        </template>
        <template v-slot:[`item.banner`]="{ item }">
          <v-img
            max-height="100"
            max-width="100"
            :src="!!item.banner ? baseUrl + item.banner : null"
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
              params: { userId: 123 },
            }"
            style="text-decoration: none"
          >
            <v-icon small class="mr-2" >
              mdi-arrow-right-bold
            </v-icon>
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
      
            <TournamentCreate :hideDialog="hideDialog"/>
       
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
        },
        { text: "Banner", value: "banner", width: "200px" },
        { text: "Time Start", value: "timeStart", width: "150px" },
        { text: "Time End", value: "timeEnd", width: "150px" },
        {
          text: "Description",
          value: "description",
          sortable: false,
          width: "500px",
          align: "center",
        },
        { text: "Total Team", value: "team", width: "120px" },
        { text: "Status", value: "status", width: "150px" },
        { text: "Action", value: "actions", sortable: false },
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
      this.$store.commit("auth/auth_overlay");
      this.$store.dispatch("tournament/getAll").then((response) => {
        this.$store.commit("auth/auth_overlay");
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
    hideDialog(){
        this.dialogCreate=!this.dialogCreate;
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
  },
};
</script>