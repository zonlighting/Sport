<template>
  <div>
    <div style="margin-top:50px">
      <v-btn color="primary" style="margin-top: 50px" @click="dialogAdd = true" v-if="tournament.status==0"
        >Add Team</v-btn
      >
    </div>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th>Tên</th>
            <th>Logo</th>
            <th>Số thành viên</th>
            <th>Description</th>
            <th v-if="tournament.status==0">Action</th>
          </tr>
        </thead>
        <tbody >
          <tr v-for="(item, index) in listTeam" :key="index" >
            <td>{{ item.nameTeam }}</td>
            <td>
              <v-avatar>
                <img :src="baseUrl+item.logo" alt="John" />
              </v-avatar>
            </td>
            <td>{{!!item&&!!item.profile? item.profile.length:'' }}</td>
            <td>{{ item.description }}</td>
            <td v-if="tournament.status==0">
              <v-icon style="cursor: pointer" @click="deleteTeam(item)"
                >mdi-delete</v-icon
              >
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <v-dialog v-model="deleteDialog" max-width="290">
      <v-card>
        <v-card-title class="headline"> Delete </v-card-title>

        <v-card-text> You definitely want to delete! </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="green darken-1" text @click="cancel"> Disagree </v-btn>

          <v-btn color="green darken-1" text @click="okDelete"> Agree </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="dialogAdd" scrollable max-width="500px">
      <v-card>
        <v-card-title>Select Country</v-card-title>
        <v-divider></v-divider>
        <v-card-text style="height: 400px">
          <div v-if="this.listTeamWait.length != 0">
             <v-row style="margin-left:20px">
                    <v-col>
               <b>  Logo</b>
                    </v-col>
                    <v-col>
                     <b>  Name</b></v-col><v-col> <b>  Country</b></v-col>
                  </v-row>
            <v-radio-group v-model="selectTeam" column>
              <v-radio
                v-for="(item, index) in listTeamWait"
                :key="index"
                :value="item.idTeam"
              >
                <template v-slot:label>
                  <v-row >
                    <v-col>
                  <v-avatar >
                    <img :src="baseUrl + item.logo" alt="John" />
                  
                  </v-avatar>
                    </v-col>
                    <v-col>
                    {{ item.nameTeam }}</v-col><v-col>{{item.country}}</v-col>
                  </v-row>
                </template>
              </v-radio>
            </v-radio-group>
          </div>
          <div v-else>
            <h1>No Data</h1>
          </div>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn color="blue darken-1" text @click="dialogAdd = false">
            Cancel
          </v-btn>
          <v-btn color="blue darken-1" text @click="okAdd"> OK </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  data() {
    return {
      selectTeam: "",
      deleteDialog: false,
      idDelete: "",
      listTeam: "",
      editedIndex: "",
      dialogAdd: false,
      listTeamWait: "",
      tournamentData: "",
    };
  },
  props: {
    getData: Function,
    tournament: Object,
  },
  mounted(){
  },

  methods: {
    deleteTeam(event) {
      this.deleteDialog = true;
      this.idDelete = event.idTeam;
      this.editedIndex = this.listTeam.indexOf(event);
    },
    cancel() {
      this.deleteDialog = false;
    },
    okAdd() {
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("tournament/addTeam", {
          listTeam: this.selectTeam,
          idTournament: this.$route.params.id,
        })
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            this.getData();
            this.dialogAdd = false;
            alert(response.data.message);
          } else {
            alert(response.data.message);
          }
        })
        .catch(function (error) {
          alert(error);
        });
    },

    okDelete() {
      if(this.listTeam.length<=10){
        alert("The tournament must have at least 10 teams participating")
           this.cancel();
      }
      else{
      this.$store.commit("auth/auth_overlay");
      this.$store
        .dispatch("tournament/deleteTeam", {
          idTeam: this.idDelete,
          idTournament: this.$route.params.id,
        })
        .then((response) => {
          this.$store.commit("auth/auth_overlay");
          if (response.data.code == 0) {
            this.listTeam.splice(this.editedIndex, 1);
            this.cancel();
            alert(response.data.message);
          } else {
            alert(response.data.message);
                        this.cancel();
          }
        })
        .catch(function (error) {
          alert(error);
                      this.cancel();
        });
      }
    },
  },
  watch: {
    tournament() {
      this.listTeam=this.tournament.team;
    },
    listTeam() {
      this.$store.dispatch("team/getTeamNoTournament").then((response) => {
        this.listTeamWait = response.data.payload;
      });
    },
  },
};
</script>