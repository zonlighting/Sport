<template>
  <div>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">Tên</th>
            <th class="text-center">Logo</th>
            <th class="text-center">Số thành viên</th>
            <th class="text-center">Description</th>
            <th class="text-center">Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in tournament.team" :key="index">
            <td>{{ item.nameTeam }}</td>
            <td>
              <v-avatar>
                <img :src="item.logo" alt="John" />
              </v-avatar>
            </td>
            <td>{{ item.profile.length }}</td>
            <td>{{ item.description }}</td>
            <td><v-icon style="cursor: pointer" @click="deleteDialog">mdi-delete</v-icon></td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      dataTournament: "",
    };
  },
  props: {
    getData: Function,
    tournament: Object,
  },
  created() {
    this.dataTournament = this.tournament;
  },
};
</script>