<template>
  <div>
    <v-row class="pl-10">
      <v-col cols="11" md="2" xm="2"></v-col>
      <v-col cols="11" md="9" xm="9">
        <v-row>
          <v-img
            lazy-src="https://picsum.photos/id/11/10/6"
            max-height="70"
            max-width="70"
            src="https://picsum.photos/id/11/500/300"
          ></v-img>
          <v-toolbar-title class="pl-5">
            <h1 style="font-weight: 500; line-height: 34px; color: #2b2c2d">
              Name Team
            </h1>
            <h5 class="pt-3" style="font-size: 12px; font-weight: 400">
              3rd in UEFA Nations League
            </h5>
          </v-toolbar-title>
        </v-row>
      </v-col>
    </v-row>
    <v-divider style="margin: 0 !important"></v-divider>
    <v-row class="pl-6">
      <v-col cols="12" md="2" xm="2"></v-col>
      <v-col>
        <v-tabs>
          <v-tab class="fix-tab-css">Fixtures</v-tab>
          <v-tab class="fix-tab-css">Results</v-tab>
          <v-tab class="fix-tab-css">Squad</v-tab>
          <v-tab class="fix-tab-css">Stats</v-tab>
        </v-tabs>
      </v-col>
    </v-row>
    <router-view></router-view>
  </div>
</template>

<script>
export default {};
</script>

<style>
.fix-tab-css {
  color: #151617;
  font-weight: 500;
  line-height: 19px;
  font-size: 14px;
}
</style>
