<template>
  <v-card>
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="close">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>Update</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn dark text @click="update"> Update </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    <v-row>
      <v-col
        ><v-list three-line subheader>
          <v-subheader>Team 1</v-subheader>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title> Add GOAL </v-list-item-title>
              <v-list-item-subtitle>
                <v-row>
                  <v-col
                    ><v-select
                      :items="memberTeam1"
                      item-text="name"
                      item-value="id"
                      v-model="selectTeam1"
                      label="Name member"
                    ></v-select
                  ></v-col>
                  <v-col>
                    Time<br />
                    <input-mask
                      style="border-bottom: solid 1px; height: 32px"
                      v-model="value1"
                      mask="99:99"
                      maskChar=" "
                    ></input-mask>
                  </v-col>
                  <v-col style="margin-top: 10px">
                    <v-btn color="primary" @click="ok1">OK</v-btn></v-col
                  >
                </v-row>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>Detail</v-list-item-title>
              <v-list-item-subtitle v-for="(item, index) in goal1" :key="index">
                <v-row>
                  <v-col>
                    <v-icon>mdi-soccer</v-icon>{{ item.profile.name }}-{{
                      item.time
                    }}</v-col
                  >
                  <v-col
                    ><v-icon small @click="deleteItem1(item)">
                      mdi-delete
                    </v-icon></v-col
                  >
                </v-row>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-col>
      <v-col
        ><v-list three-line subheader>
          <v-subheader>Team 2</v-subheader>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title> Add GOAL </v-list-item-title>
              <v-list-item-subtitle>
                <v-row>
                  <v-col
                    ><v-select
                      :items="memberTeam2"
                      item-text="name"
                      item-value="id"
                      v-model="selectTeam2"
                      label="Name member"
                    ></v-select
                  ></v-col>
                  <v-col>
                    Time<br />
                    <input-mask
                      style="border-bottom: solid 1px; height: 32px"
                      v-model="value2"
                      mask="99:99"
                      maskChar=" "
                    ></input-mask>
                  </v-col>
                  <v-col style="margin-top: 10px">
                    <v-btn color="primary" @click="ok2">OK</v-btn></v-col
                  >
                </v-row>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>Detail</v-list-item-title>
              <v-list-item-subtitle v-for="(item, index) in goal2" :key="index">
                <v-row>
                  <v-col>
                    <v-icon>mdi-soccer</v-icon>{{ item.profile.name }}-{{
                      item.time
                    }}</v-col
                  >
                  <v-col
                    ><v-icon small @click="deleteItem2(item)">
                      mdi-delete
                    </v-icon></v-col
                  >
                </v-row>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>

    <v-divider></v-divider>
    <v-list three-line subheader>
      <v-subheader>Summary</v-subheader>
      <v-list-item>
        <v-list-item-content>
          <h1 class="text-center">{{ score1 }}-{{ score2 }}</h1>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <hr />
    <v-list three-line subheader>
      <v-subheader>Video and Photo</v-subheader>
      <v-row>
        <v-col>
          <v-container>
            <v-file-input
              accept="image/png, image/jpeg, image/bmp"
              placeholder="Pick an Photo"
              prepend-icon="mdi-camera"
              v-model="fileImage"
              :rules="rulesImage"
            ></v-file-input>
            <img
              style="max-height: 204px"
              src="https://sonypicturespublicity.com/dom/img/no_banner_image.gif"
              id="image"
            />
          </v-container>
        </v-col>
        <v-col>
          <v-container>
            <v-file-input
              accept="video/mp4"
              placeholder="Pick an Video"
              prepend-icon="mdi-video"
              v-model="fileVideo"
              :rules="rulesVideo"
            ></v-file-input>
            <video width="320" height="240" controls src="" id="video"></video>
          </v-container>
        </v-col>
      </v-row>
    </v-list>
    <v-dialog v-model="dialogDelete1" max-width="500px">
      <v-card>
        <v-card-title class="headline">Are you sure delete </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeDelete1">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="deleteItemConfirm1"
            >OK</v-btn
          >
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="dialogDelete2" max-width="500px">
      <v-card>
        <v-card-title class="headline">Are you sure delete </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeDelete2">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="deleteItemConfirm2"
            >OK</v-btn
          >
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>
<script>
import { ENV } from "@/config/env.js";

export default {
  data() {
    return {
      rulesImage: [
        (v) => {
          if (v == undefined || Array.isArray(v)) {
            return true;
          } else {
            if (
              v.type == "image/png" ||
              v.type == "image/jpeg" ||
              v.type == "image/bmp"
            ) {
              return true;
            } else {
              return false || "Wrong data";
            }
          }
        },
      ],
      rulesVideo: [
        (v) => {
          if (v == undefined || Array.isArray(v)) {
            return true;
          } else {
            if (v.type == "video/mp4") {
              return true;
            } else {
              return false || "Wrong data";
            }
          }
        },
      ],
      fileImage: [],
      fileVideo: [],
      value1: "00:00",
      value2: "00:00",
      memberTeam1: [],
      memberTeam2: [],
      add: false,
      selectTeam1: "",
      dialogDelete1: "",
      selectTeam2: "",
      dialogDelete2: "",
      goal1: [],
      goal2: [],
      tg1: true,
      tg2: true,
      indexDelete1: "",
      indexDelete2: "",
      score1: "",
      score2: "",
    };
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  props: {
    hideDiaglog: Function,
    schedule: Object,
  },
  created() {
    console.log(this.schedule);
    this.score1 = this.schedule.score1;
    this.score2 = this.schedule.score2;

    this.memberTeam1 = this.schedule.team[0].profile;
    this.memberTeam2 = this.schedule.team[1].profile;
  },
  mounted() {
    this.getDataOld();
  },
  methods: {
    close() {
      this.hideDiaglog();
    },
    getDataOld() {
      if (this.schedule.image != null) {
        document.getElementById("image").src =
          this.baseUrl + this.schedule.image;
      }
      if (this.schedule.video != null) {
        document.getElementById("video").src =
          this.baseUrl + this.schedule.video;
      }
      this.schedule.goal.forEach((element) => {
        if (element.team == 1) {
          this.schedule.team[0].profile.forEach((profile) => {
            if (profile.id == element.idMember) {
              this.goal1.push({
                profile: profile,
                time: element.time,
                idSchedule: this.schedule.idSchedule,
              });
            }
          });
        }
        if (element.team == 2) {
          this.schedule.team[1].profile.forEach((profile) => {
            if (profile.id == element.idMember) {
              this.goal2.push({
                profile: profile,
                time: element.time,
                idSchedule: this.schedule.idSchedule,
              });
            }
          });
        }
      });
    },

    update() {
      this.$store.commit("auth/auth_overlay");
      this.$store.commit("auth/auth_overlay");
      const arr = this.goal1.concat(this.goal2);
      console.log(arr);
      var bodyFormData = new FormData();
      bodyFormData.append("idSchedule", this.schedule.idSchedule);
      bodyFormData.append("imageFile", this.fileImage);
      bodyFormData.append("videoFile", this.fileVideo);
      bodyFormData.append("score1", this.goal1.length);
      bodyFormData.append("score2", this.goal2.length);
      this.$store.dispatch("schedule/update", bodyFormData).then((response) => {
        this.$store.dispatch("schedule/goalUpdate", arr).then((response) => {
          if (response.data.code == 0) {
            alert("Update ");
            this.close();
          } else {
            alert("Error");
          }
        });
        if (response.data.code == 0) {
          console.log(response.data.payload);
        } else {
          alert("Error");
        }
      });
    },
    ok1() {
      this.tg1 = true;
      if (this.selectTeam1 == "") {
        alert("Not yet selected player");
      } else if (this.value1 == "00:00") {
        alert("No option time");
      } else {
        this.memberTeam1.forEach((element) => {
          if (element.id == this.selectTeam1) {
            if (this.goal1.length == "") {
              this.goal1.push({
                profile: element,
                time: this.value1,
                idSchedule: this.schedule.idSchedule,
              });
            } else {
              this.goal1.forEach((time) => {
                if (time.time == this.value1) {
                  alert("Time coincides");
                  this.tg1 = false;
                }
              });
              if (this.tg1 == true) {
                this.goal1.push({
                  profile: element,
                  time: this.value1,
                  idSchedule: this.schedule.idSchedule,
                });
              }
            }
          }
        });
      }
    },
    ok2() {
      this.tg2 = true;
      if (this.selectTeam2 == "") {
        alert("Not yet selected player");
      } else if (this.value2 == "00:00") {
        alert("No option time");
      } else {
        this.memberTeam2.forEach((element) => {
          if (element.id == this.selectTeam2) {
            if (this.goal2.length == "") {
              this.goal2.push({
                profile: element,
                time: this.value2,
                idSchedule: this.schedule.idSchedule,
              });
            } else {
              this.goal2.forEach((time) => {
                if (time.time == this.value2) {
                  alert("Time coincides");
                  this.tg2 = false;
                }
              });
              if (this.tg2 == true) {
                this.goal2.push({
                  profile: element,
                  time: this.value2,
                  idSchedule: this.schedule.idSchedule,
                });
              }
            }
          }
        });
      }
    },
    deleteItem1(item) {
      this.dialogDelete1 = true;
      this.indexDelete1 = this.goal1.indexOf(item);
    },
    deleteItemConfirm1() {
      this.goal1.splice(this.indexDelete1, 1);
      this.closeDelete1();
    },
    closeDelete1() {
      this.dialogDelete1 = false;
    },
    deleteItem2(item) {
      this.dialogDelete2 = true;
      this.indexDelete2 = this.goal2.indexOf(item);
    },
    deleteItemConfirm2() {
      this.goal2.splice(this.indexDelete2, 1);
      this.closeDelete2();
    },
    closeDelete2() {
      this.dialogDelete2 = false;
    },
  },
  watch: {
    goal1() {
      this.score1 = this.goal1.length;
      this.score2 = this.goal2.length;
    },
    goal2() {
      this.score1 = this.goal1.length;
      this.score2 = this.goal2.length;
    },
    fileImage(event) {
      if (this.fileImage == undefined || this.fileImage == "") {
        document.getElementById("image").src =
          "https://sonypicturespublicity.com/dom/img/no_banner_image.gif";
      } else {
        var reader = new FileReader();
        reader.onload = function () {
          var dataURL = reader.result;
          var output = document.getElementById("image");
          output.src = dataURL;
        };
        reader.readAsDataURL(event);
      }
    },
    fileVideo(event) {
      if (this.fileVideo == undefined || this.fileVideo == "") {
        document.getElementById("image").src = "";
      } else {
        var reader = new FileReader();
        reader.onload = function () {
          var dataURL = reader.result;
          var output = document.getElementById("video");
          output.src = dataURL;
        };
        reader.readAsDataURL(event);
      }
    },
  },
};
</script>