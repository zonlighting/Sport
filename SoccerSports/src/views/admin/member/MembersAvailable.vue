<template>
  <v-data-table
    :headers="headers"
    :items="playersAvailable"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar flat color="white">
        <v-row class="mt-4">
          <v-col cols="12" sm="3" md="3">
            <v-select
              v-model="genderSearch"
              :items="genderItems"
              label="Gender"
            ></v-select>
          </v-col>
          <v-col cols="12" sm="3" md="3">
            <v-text-field
              v-model="nameMemberSearch"
              append-icon="mdi-magnify"
              label="Name"
              single-line
              hide-details
            ></v-text-field>
          </v-col>
          <v-col cols="12" sm="3" md="3">
            <v-select
              v-model="positionSearch"
              label="Position"
              :items="positionItems"
            ></v-select>
          </v-col>
          <v-col cols="12" sm="3" md="3">
            <v-text-field
              v-model="ageSearch"
              append-icon="mdi-magnify"
              label="Age"
              single-line
              hide-details
            ></v-text-field>
          </v-col>
        </v-row>
      </v-toolbar>
    </template>
    <template v-slot:[`item.avatar`]="{ item }">
      <v-img
        :src="baseUrl + item.avatar"
        alt=""
        style="margin: 5px 0 5px 0"
        class="fixImg"
      />
    </template>

    <template v-slot:[`item.actions`]="{ item }">
      <template>
        <v-btn small @click="addMember(item)"
          >Add<v-icon small class="mr-2">add</v-icon></v-btn
        >
      </template>
    </template>
  </v-data-table>
</template>
<script>
import { ENV } from "@/config/env.js";
export default {
  props: {
    playersAvailable: Array,
    addedMember: {
      type: Function,
    }
  },
  data() {
    return {
      positionSearch: "",
      positionItems: ["Goalkeepers", "Defenders", "Midfielders", "Forwards"],
      genderSearch: "",
      genderItems: ["Male", "Female", "Orther"],
      nameMemberSearch: "",
      ageSearch: "",

      headers: [
        {
          text: "Avatar",
          align: "start",
          sortable: false,
          value: "avatar",
        },
        { text: "Name", value: "name", filter: this.nameMemberFilter },
        { text: "Age", value: "age", filter: this.ageFilter },
        { text: "Gender", value: "gender", filter: this.genderFilter },
        { text: "Position", value: "position", filter: this.positionFilter },
        { text: "Actions", value: "actions", sortable: false },
      ],
    };
  },
  mounted() {
    // console.log(this.playersAvailable);
  },
  computed: {
    baseUrl() {
      return ENV.BASE_IMAGE;
    },
  },
  watch: {},

  methods: {
    genderFilter(value) {
      // If this filter has no value we just skip the entire filter.
      if (!this.genderSearch) {
        return true;
      }

      return value === this.genderSearch;
    },

    nameMemberFilter(value) {
      // If this filter has no value we just skip the entire filter.
      if (!this.nameMemberSearch) {
        return true;
      }

      return value.toLowerCase().includes(this.nameMemberSearch.toLowerCase());
    },

    positionFilter(value) {
      // If this filter has no value we just skip the entire filter.
      if (!this.positionSearch) {
        return true;
      }
      return value === this.positionSearch;
    },

    ageFilter(value) {
      // If this filter has no value we just skip the entire filter.
      if (!this.ageSearch) {
        return true;
      }
      return value.toLowerCase().includes(this.ageSearch.toLowerCase());
    },

    addMember(member) {
      this.checkAdd = false;
      let indexRemove = 0;
      let obj = Object.assign({}, member);
      this.addedMember(obj);
      this.playersAvailable.forEach((element, index) => {
        if (element.id === member.id) {
          indexRemove = index;
        }
      });
      this.playersAvailable.splice(indexRemove, 1);
    },
  },
};
</script>
<style scoped>
.fixImg {
  height: 100px;
  width: 100px;
}
</style>