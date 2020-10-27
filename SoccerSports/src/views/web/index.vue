<template>
  <div class="wrapper">
    <header class="header-main">
      <div class="header-upper">
        <div class="container">
          <div class="row">
            <v-spacer></v-spacer>
            <ul>
              <li><a href="#">Signup/login</a></li>
              <li><a href="#">Forget</a></li>
            </ul>
          </div>
        </div>
      </div>
      <div class="header-lower clearfix">
        <div class="container">
          <div class="row">
            <h1 class="logo">
              <a href="index-2.html"><v-img src=images/soccer-logo-by-Vexels.png width="200px"></v-img></a>
            </h1>

            <div class="menubar">
              <nav class="navbar">
                <div class="nav-wrapper">
                  <div class="navbar-header">
                    <button type="button" class="navbar-toggle">
                      <span class="sr-only">Toggle navigation</span>
                      <span class="icon-bar"></span>
                    </button>
                  </div>
                  <div class="nav-menu">
                    <ul class="nav navbar-nav menu-bar">
                      <li>
                        <a :href="$router.resolve({ path: '/Home' }).href"
                          >Home
                        </a>
                      </li>
                      <li>
                        <a :href="$router.resolve({ path: '/Team' }).href"
                          >Team
                        </a>
                      </li>
                      <li>
                        <a :href="$router.resolve({ path: '/Tournament' }).href"
                          >Tournament
                        </a>
                        <ul class="sub-menu">
                          <li><a href="gallerypage01.html">UpComming</a></li>
                          <li>
                            <a href="gallery02.html">Ongame</a>
                          </li>
                          <li>
                            <a href="gallery03.html">Finshed</a>
                          </li>
                        </ul>
                      </li>
                      <li>
                         <a :href="$router.resolve({ path: '/Schedule' }).href"
                          >Schedule
                        </a>
                        <ul class="sub-menu">
                          <li><a href="gallerypage01.html">UpComming</a></li>
                          <li>
                            <a href="gallery02.html">Ongame</a>
                          </li>
                          <li>
                            <a href="gallery03.html">Finshed</a>
                          </li>
                        </ul>
                      </li>
                      <li><a href="shop.html">Rank </a></li>
                      <li><a href="contact.html">About </a></li>
                    </ul>
                  </div>
                </div>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </header>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>
