<template>
  <div id="right-table">

    <div class="search-product">
      <el-input v-model="searchOfWarehouseId" style="width: 240px" placeholder="请输入停车场ID..." />
      <el-button type="primary" color="#626aef" round plain @click="queryProductByWarehouseId(searchOfWarehouseId)">搜索该停车场ID下的所有车辆</el-button>
    </div>

    <div class="del-product">
      <el-input v-model="delOfWarehouseId" style="width: 240px" placeholder="请输入停车场ID..." />
      <el-button type="primary" color="#626aef" round plain @click="delProductByWarehouseId(delOfWarehouseId)">删除该停车场ID下的所有车辆</el-button>
    </div>

    <el-table :data="productData" style="width: 100%">
      <el-table-column label="ID" prop="id" />
      <el-table-column label="停车场ID" prop="warehouseId" />
      <el-table-column label="车辆名称" prop="name" />
      <el-table-column label="车辆描述" prop="description" />
    </el-table>

  </div>
</template>

<script setup>
import ax from "../http/axios_utils.js";
import {ElMessage} from "element-plus";

const productData = ref()
const searchOfWarehouseId = ref()
const delOfWarehouseId = ref()

//查询该库存下的所有产品
const queryProductByWarehouseId = (warehouseId) => {
  ax.get(`/product/query/from_warehouse/${warehouseId}`).then((success) => {
    if (success.data.code === 0) {
      productData.value = success.data.data
    }
  })
}

//删除该库存下的所有产品
const delProductByWarehouseId = (warehouseId) => {
  ax.get(`/product/del/from_warehouse/${warehouseId}`).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '车辆组删除成功！',
        type: 'success',
      })
    }
  })
}

</script>

<style lang="less" scoped>
  #right-table {
    width: 100%;
  }
</style>