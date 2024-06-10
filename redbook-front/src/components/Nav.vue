<template>
  <div id="nav">
    <img class="logo" src="../infra/images/logo.png" alt="">
    <input class="search" type="text" placeholder="搜索小红书">
    <div class="my-info">
      <img class="my-avatar" src="../infra/images/default_avatar.gif">
      <span class="my-name">情感杀手</span>
    </div>
  </div>
</template>

<script setup>

</script>

<style lang="less" scoped>
#nav {
  width: 100%;
  height: 72px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .logo {
    width: 68px;
    height: 32px;
    margin-left: 35px;
  }

  .search {
    width: 500px;
    height: 50px;
    padding: 0 84px 0 16px;
    margin: 0 auto;
    font-size: 16px;
    line-height: 120%;
    caret-color: #ff3333;
    background-color: rgba(247,247,247);
    border-radius: 999px;
  }

  .my-info {
    width: 200px;
    display: flex;
    margin-right: 20px;
    justify-content: space-evenly;
    align-items: center;
    .my-avatar {
      width: 50px;
      height: 50px;
      border-radius: 25px;
    }
    .my-name {
      font-weight: 900;
    }
  }

}
</style>