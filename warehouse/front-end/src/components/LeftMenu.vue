<template>
  <div id="left-menu">
        <el-menu
            default-active="2"
            class="el-menu-vertical-demo menu"
            @open="handleOpen"
            @close="handleClose"
        >
          <router-link :to="{name: '管理员信息'}">
            <el-menu-item index="1" class="row">
              <span>管理员信息</span>
            </el-menu-item>
          </router-link>
            <el-menu-item index="2" class="row">
              <span>仓库信息</span>
            </el-menu-item>
          <el-menu-item index="3" class="row">
            <span>产品信息</span>
          </el-menu-item>
          <el-menu-item index="3" class="row">
            <span>仓库产品关系信息</span>
          </el-menu-item>
        </el-menu>
  </div>
</template>

<script setup>

</script>

<style lang="less" scoped>
#left-menu {
    width: 200px;
    .row {
      display: flex;
      justify-content: center;
    }
}

a {
  text-decoration: none
}
</style>