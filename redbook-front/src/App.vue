<script setup>
</script>

<template>
  <router-view></router-view>
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

input {
  border: none; /* 去掉边框 */
  outline: none; /* 去掉点击时的默认高亮 */
}

button {
  border: none; /* 去除边框 */
  background: none; /* 去除背景颜色 */
  outline: none; /* 去除点击时的默认高亮 */
  padding: 0; /* 去除内边距 */
  margin: 0; /* 去除外边距 */
  cursor: pointer; /* 添加鼠标指针样式 */
  border-radius: 999px;
}

button:hover {
  background-color: rgba(245, 245, 245); /* 鼠标经过时背景变成黑色 */
}

</style>
