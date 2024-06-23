<template>
  <div id="nav">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ name: 'ProductTable' }">商城主页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ name: 'OrderTable' }">订单列表</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ name: 'ProductTable' }">订单详情页</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script setup>

</script>

<style lang="less" scoped>
#nav {
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>