<template>
  <div id="left-menu">

      <el-menu
          default-active="1"
          class="el-menu-vertical-demo menu"
          @open="handleOpen"
          @close="handleClose"
      >
        <router-link :to="{name: 'admin'}">
          <el-menu-item index="1" class="row">
            <span>员工信息</span>
          </el-menu-item>
        </router-link>

        <router-link :to="{name: 'warehouse'}">
          <el-menu-item index="2" class="row">
            <span>停车场信息</span>
          </el-menu-item>
        </router-link>

        <router-link :to="{name: 'product'}">
          <el-menu-item index="3" class="row">
            <span>车辆信息</span>
          </el-menu-item>
        </router-link>

        <router-link :to="{name: 'product_group'}">
          <el-menu-item index="4" class="row">
            <span>车辆组</span>
          </el-menu-item>
        </router-link>

      </el-menu>
  </div>
</template>

<script setup>

</script>

<style lang="less" scoped>
#left-menu {
    width: 300px;
    .row {
      display: flex;
      justify-content: center;
    }
}

a {
  text-decoration: none
}
</style>