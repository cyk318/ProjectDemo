<script setup>
</script>

<template>
  <div id="app">
    <Nav></Nav>
    <div class="container">
      <LeftMenu></LeftMenu>
      <router-view></router-view>
    </div>
  </div>
</template>

<style>
* {
  /* 防止边框将元素撑大 */
  box-sizing: border-box;
  /* 去除浏览器默认样式 */
  padding: 0;
  margin: 0;
}

html {
  height: 100%;
}

body {
  height: 100%;
}

#app .container {
  display: flex;
  justify-content: left;
}

</style>
