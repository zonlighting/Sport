<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="desserts"
      class="elevation-1"
    ></v-data-table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      headers: [
        {
          text: "Avatar",
          align: "start",
          sortable: false,
          value: "avatar",
        },
        { text: "Name", value: "name", filter: this.nameMemberFilter },
        { text: "Email", value: "email", filter: this.emailFilter },
        { text: "Phone", value: "phone" },
        { text: "Age", value: "age", filter: this.ageFilter },
        { text: "Gender", value: "gender", filter: this.genderFilter },
        { text: "Current Team", value: "idTeam" },
        { text: "Actions", value: "actions", sortable: false },
      ],
      desserts: [],
    };
  },
};
</script>