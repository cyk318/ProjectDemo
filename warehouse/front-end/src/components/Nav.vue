<template>
  <div id="nav">
    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo menu"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
    >
      <el-menu-item index="0">
        <img
            style="width: 100px"
            src="../image/logo.png"
            alt="Element logo"
        />
      </el-menu-item>
      <div class="flex-grow" />
      <el-menu-item index="1">退出登录</el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>

</script>

<style lang="less" scoped>
.flex-grow {
  flex-grow: 1;
}

#nav{
  width: 100%;
  .menu {
    height: 80px;
  }
}


</style>