<template>
  <div>
    <v-hover>
      <v-card>
        <v-row style="margin-top: 130px">
          <v-col class="text-center" cols="12" sm="3">
            <v-avatar size="128" tile
              ><img src="@/assets/logodemo.png" alt="John" /></v-avatar
          ></v-col>
          <v-col>
            <v-row>
              <v-col
                ><v-row
                  ><v-col
                    ><v-row
                      ><h2>Shanghai Shenhua</h2>
                      <v-avatar size="50"
                        ><img
                          src="@/assets/logodemo.png"
                          alt="John" /></v-avatar
                    ></v-row>
                    Miller Bolaños (29', 58')Hanchao Yu (90' PEN)</v-col
                  ></v-row
                ></v-col
              >
              <v-col class="text-center">
                <v-row>
                  <v-col><h2>1</h2></v-col>
                  <v-col><h2>FT</h2></v-col>
                  <v-col><h2>3</h2></v-col>
                </v-row>
              </v-col>
              <v-col
                ><v-row
                  ><v-col
                    ><v-row>
                      <v-avatar size="50"
                        ><img src="@/assets/logodemo.png" alt="John"
                      /></v-avatar>
                      <h2>Shanghai Shenhua</h2>
                    </v-row>
                    Miller Bolaños (29', 58')Hanchao Yu (90' PEN)</v-col
                  ></v-row
                ></v-col
              >
            </v-row>
          </v-col>
          <v-col class="text-center" cols="12" sm="3">
            <v-avatar size="128" tile
              ><img src="@/assets/logodemo.png" alt="John" /></v-avatar
          ></v-col>
        </v-row>
      </v-card>
    </v-hover>

      <v-container>
        <ul>
          <li><a class="active" href="#home" >Summary</a></li>
          <li><a href="#news" >Statistic</a></li>
          <li><a href="#contact" >Line-Ups</a></li>
        </ul>
        <hr />
      </v-container>
    <section>
      <router-view />
    </section>
  </div>
</template>
<script>
export default {
  data() {
    return {
    
    };
  },
};
</script>
<style scoped>


ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;  
}


li {
  float: left;
}

li a {
  display: block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

li a:hover {
  color: red;
}
</style>